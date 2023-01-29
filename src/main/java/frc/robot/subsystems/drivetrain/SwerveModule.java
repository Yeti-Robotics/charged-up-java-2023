package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderStatusFrame;
import dagger.internal.Beta;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants.DriveConstants;

import javax.inject.Inject;
import javax.inject.Named;

public class SwerveModule {
    private final WPI_TalonFX driveMotor;
    private final WPI_TalonFX steerMotor;

    private final CANCoder absoluteEncoder;
    private final PIDController drivePIDController = new PIDController(
            DriveConstants.DRIVE_MOTOR_P,
            DriveConstants.DRIVE_MOTOR_I,
            DriveConstants.DRIVE_MOTOR_D);
    private final ProfiledPIDController steeringPIDController = new ProfiledPIDController(
            DriveConstants.STEER_MOTOR_P,
            DriveConstants.STEER_MOTOR_I,
            DriveConstants.STEER_MOTOR_D,
            new TrapezoidProfile.Constraints(3 * Math.PI, 6 * Math.PI));
    private final SimpleMotorFeedforward driveFeedforward = new SimpleMotorFeedforward(
            DriveConstants.DRIVE_MOTOR_KS, DriveConstants.DRIVE_MOTOR_KV, DriveConstants.DRIVE_MOTOR_KA
    );

    private final SimpleMotorFeedforward steerFeedForward = new SimpleMotorFeedforward(
            DriveConstants.STEER_MOTOR_KS, DriveConstants.STEER_MOTOR_KV, DriveConstants.STEER_MOTOR_KA
    );
    private final SwerveModulePosition position = new SwerveModulePosition();

    @Inject
    public SwerveModule(
            @Named("drive motor") WPI_TalonFX driveMotor,
            @Named("steer motor") WPI_TalonFX steerMotor,
            @Named("absolute encoder") CANCoder absoluteEncoder) {

        this.driveMotor = driveMotor;
        this.steerMotor = steerMotor;
        this.absoluteEncoder = absoluteEncoder;


        steeringPIDController.enableContinuousInput(-Math.PI, Math.PI);

        // resetEncoders();
    }

    public void resetEncoders() {
        driveMotor.setSelectedSensorPosition(0);

        double absolutePosition = absoluteEncoder.getAbsolutePosition() * DriveConstants.DEGREES_TO_FALCON;
        steerMotor.setSelectedSensorPosition(0);
    }

    public double getDrivePosition() {
        return driveMotor.getSelectedSensorPosition() / 2048 *
                DriveConstants.SWERVE_X_REDUCTION *
                DriveConstants.WHEEL_DIAMETER * Math.PI;
    }

    public double getSteerPosition() {
        return Math.toRadians(absoluteEncoder.getAbsolutePosition());
        // return steerMotor.getSelectedSensorPosition() / DriveConstants.DEGREES_TO_FALCON;
    }

    public double getDriveVelocity() {
        return driveMotor.getSelectedSensorVelocity() * 10 / 2048
                * DriveConstants.SWERVE_X_REDUCTION *
                DriveConstants.WHEEL_DIAMETER * Math.PI;
    }

    public double getSteerVelocity() {
        return steerMotor.getSelectedSensorVelocity() * 10;
    }

    public SwerveModulePosition getPosition() {
        updateState();
        return this.position;
    }

    public void updateState() {
        position.distanceMeters = getDrivePosition();
        position.angle = new Rotation2d(getSteerPosition());
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        double driveVelocity = getDriveVelocity();
        double steerAngle = getSteerPosition();

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
                steeringPIDController.calculate(steerAngle, desiredState.angle.getRadians())
                        + steerFeedForward.calculate(steeringPIDController.getSetpoint().velocity);

//        driveMotor.setVoltage(desiredState.speedMetersPerSecond / DriveConstants.MAX_VELOCITY_METERS_PER_SECOND
//            * DriveConstants.MAX_VOLTAGE);
        driveMotor.setVoltage(driveOutput);
        // steerMotor.set(steeringPIDController.calculate(getSteerPosition(), desiredState.angle.getDegrees()));
        // steerMotor.set(steeringPIDController.calculate(getSteerPosition(), 45));
        steerMotor.setVoltage(steerOutput);
//
//        steerMotor.set(ControlMode.Position, desiredState.angle.getDegrees() * DriveConstants.DEGREES_TO_FALCON);
    }

    public void stop() {
        driveMotor.setVoltage(0.0);
        steerMotor.set(0.0);
    }
}