package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.Constants.DriveConstants;

import javax.inject.Inject;

public class SwerveModule {
    private final WPI_TalonFX driveMotor;
    private final WPI_TalonFX azimuthMotor;

    private final WPI_CANCoder absoluteEncoder;
    private final PIDController drivePIDController = new PIDController(
            DriveConstants.DRIVE_MOTOR_P,
            DriveConstants.DRIVE_MOTOR_I,
            DriveConstants.DRIVE_MOTOR_D);
    private final ProfiledPIDController azimuthPIDController = new ProfiledPIDController(
            DriveConstants.AZIMUTH_MOTOR_P,
            DriveConstants.AZIMUTH_MOTOR_I,
            DriveConstants.AZIMUTH_MOTOR_D,
            new TrapezoidProfile.Constraints(3 * Math.PI, 6 * Math.PI));
    private final SimpleMotorFeedforward driveFeedforward = new SimpleMotorFeedforward(
            DriveConstants.DRIVE_MOTOR_KS, DriveConstants.DRIVE_MOTOR_KV, DriveConstants.DRIVE_MOTOR_KA
    );

    private final SimpleMotorFeedforward azimuthFeedForward = new SimpleMotorFeedforward(
            DriveConstants.AZIMUTH_MOTOR_KS, DriveConstants.AZIMUTH_MOTOR_KV, DriveConstants.AZIMUTH_MOTOR_KA
    );
    private final SwerveModulePosition position = new SwerveModulePosition();

    @Inject
    public SwerveModule(
            WPI_TalonFX driveMotor,
            WPI_TalonFX azimuthMotor,
            WPI_CANCoder absoluteEncoder) {

        this.driveMotor = driveMotor;
        this.azimuthMotor = azimuthMotor;
        this.absoluteEncoder = absoluteEncoder;


        azimuthPIDController.enableContinuousInput(-Math.PI, Math.PI);

        resetEncoders();
    }

    public void resetEncoders() {
        driveMotor.setSelectedSensorPosition(0);
        azimuthMotor.setSelectedSensorPosition(0);
    }

    public double getDrivePosition() {
        return driveMotor.getSelectedSensorPosition() / 2048 *
                DriveConstants.SWERVE_X_REDUCTION *
                DriveConstants.WHEEL_DIAMETER * Math.PI;
    }

    public double getAzimuthPosition() {
        return Math.toRadians(absoluteEncoder.getAbsolutePosition());
    }

    public double getDriveVelocity() {
        return driveMotor.getSelectedSensorVelocity() * 10 / 2048 *
                (DriveConstants.WHEEL_DIAMETER * Math.PI) * DriveConstants.SWERVE_X_REDUCTION;
    }

    public SwerveModulePosition getPosition() {
        updateModulePosition();
        return this.position;
    }

    public void updateModulePosition() {
        position.distanceMeters = getDrivePosition();
        position.angle = new Rotation2d(getAzimuthPosition());
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        double driveVelocity = getDriveVelocity();
        double azimuthPosition = getAzimuthPosition();

        desiredState = SwerveModuleState.optimize(desiredState, new Rotation2d(azimuthPosition));
        if (Math.abs(desiredState.speedMetersPerSecond) < 0.01
                && Math.abs(desiredState.angle.getRadians() - azimuthPosition) < 0.05) {
            stop();
            return;
        }

        final double driveOutput =
                drivePIDController.calculate(driveVelocity, desiredState.speedMetersPerSecond)
                        + driveFeedforward.calculate(desiredState.speedMetersPerSecond);

        final double azimuthOutput =
                azimuthPIDController.calculate(azimuthPosition, desiredState.angle.getRadians())
                        + azimuthFeedForward.calculate(azimuthPIDController.getSetpoint().velocity);
//        System.out.println(azimuthOutput);

        driveMotor.setVoltage(driveOutput);
        azimuthMotor.setVoltage(azimuthOutput);
    }

    public void stop() {
        driveMotor.setVoltage(0.0);
        azimuthMotor.set(0.0);
    }
}