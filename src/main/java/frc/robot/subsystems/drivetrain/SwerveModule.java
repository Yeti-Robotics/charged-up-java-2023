package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
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

    private final CANCoder absoluteEncoder;
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
            @Named("drive motor") WPI_TalonFX driveMotor,
            @Named("azimuth motor") WPI_TalonFX azimuthMotor,
            @Named("absolute encoder") CANCoder absoluteEncoder) {

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
        return driveMotor.getSelectedSensorVelocity() * 10 / 2048
                * DriveConstants.SWERVE_X_REDUCTION *
                DriveConstants.WHEEL_DIAMETER * Math.PI;
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

//        driveMotor.setVoltage(desiredState.speedMetersPerSecond / DriveConstants.MAX_VELOCITY_METERS_PER_SECOND
//            * DriveConstants.MAX_VOLTAGE);
        driveMotor.setVoltage(driveOutput);
        // steerMotor.set(steeringPIDController.calculate(getSteerPosition(), desiredState.angle.getDegrees()));
        // steerMotor.set(steeringPIDController.calculate(getSteerPosition(), 45));
        azimuthMotor.setVoltage(steerOutput);
//
//        steerMotor.set(ControlMode.Position, desiredState.angle.getDegrees() * DriveConstants.DEGREES_TO_FALCON);
    }

    public void stop() {
        driveMotor.setVoltage(0.0);
        azimuthMotor.set(0.0);
    }
}