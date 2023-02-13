package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
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
import javax.inject.Named;

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

        // resetEncoders();
    }

    public void resetEncoders() {
        driveMotor.setSelectedSensorPosition(0);

        double absolutePosition = absoluteEncoder.getAbsolutePosition() * DriveConstants.DEGREES_TO_FALCON;
        azimuthMotor.setSelectedSensorPosition(0);
    }

    public double getDrivePosition() {
        return driveMotor.getSelectedSensorPosition() / 2048 *
                DriveConstants.SWERVE_X_REDUCTION *
                DriveConstants.WHEEL_DIAMETER * Math.PI;
    }

    public double getAzimuthPosition() {
        return Math.toRadians(absoluteEncoder.getAbsolutePosition());
        // return steerMotor.getSelectedSensorPosition() / DriveConstants.DEGREES_TO_FALCON;
    }

    public double getDriveVelocity() {
        return driveMotor.getSelectedSensorVelocity() * 10 / 2048 *
                (DriveConstants.WHEEL_DIAMETER * Math.PI) * DriveConstants.SWERVE_X_REDUCTION;
    }

    public double getAzimuthVelocity() {
        return azimuthMotor.getSelectedSensorVelocity() * 10;
    }

    public SwerveModulePosition getPosition() {
        updateState();
        return this.position;
    }

    public void updateState() {
        position.distanceMeters = getDrivePosition();
        position.angle = new Rotation2d(getAzimuthPosition());
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        double driveVelocity = getDriveVelocity();
        double steerAngle = getAzimuthPosition();

        if (Math.abs(desiredState.speedMetersPerSecond) < 0.01
                && Math.abs(desiredState.angle.getRadians() - steerAngle) < 0.05) {
            stop();
            return;
        }
        desiredState = SwerveModuleState.optimize(desiredState, new Rotation2d(steerAngle));

        final double driveOutput =
                drivePIDController.calculate(driveVelocity, desiredState.speedMetersPerSecond)
                        + driveFeedforward.calculate(desiredState.speedMetersPerSecond);

        final double steerOutput =
                azimuthPIDController.calculate(steerAngle, desiredState.angle.getRadians())
                        + azimuthFeedForward.calculate(azimuthPIDController.getSetpoint().velocity);
        System.out.println(steerOutput);

        driveMotor.setVoltage(driveOutput);
        azimuthMotor.setVoltage(steerOutput);
    }

    public void stop() {
        driveMotor.setVoltage(0.0);
        azimuthMotor.set(0.0);
    }
}