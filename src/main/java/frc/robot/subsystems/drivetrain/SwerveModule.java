package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.constants.CANCoderConstants;
import frc.robot.constants.DriveConstants;

import javax.inject.Inject;

public class SwerveModule {
    private final WPI_TalonFX driveMotor;
    private final WPI_TalonFX azimuthMotor;

    private final WPI_CANCoder absoluteEncoder;
    private final SwerveModuleState state = new SwerveModuleState();
    private final SwerveModulePosition position = new SwerveModulePosition();

    @Inject
    public SwerveModule(
            WPI_TalonFX driveMotor,
            WPI_TalonFX azimuthMotor,
            WPI_CANCoder absoluteEncoder) {

        this.driveMotor = driveMotor;
        this.azimuthMotor = azimuthMotor;
        this.absoluteEncoder = absoluteEncoder;

        resetEncoders();
    }

    public void resetEncoders() {
        driveMotor.setSelectedSensorPosition(0);
    }

    public double getDrivePosition() {
        return driveMotor.getSelectedSensorPosition() / 2048 *
                DriveConstants.SWERVE_X_REDUCTION *
                DriveConstants.WHEEL_DIAMETER * Math.PI;
    }

    public double getDriveVelocity() {
        return driveMotor.getSelectedSensorVelocity() * 10 / 2048 *
                (DriveConstants.WHEEL_DIAMETER * Math.PI) * DriveConstants.SWERVE_X_REDUCTION;
    }
    public Rotation2d getAzimuthPosition() {
        return new Rotation2d(Math.toRadians(absoluteEncoder.getAbsolutePosition()));
    }

    public SwerveModuleState getState() {
        state.speedMetersPerSecond = getDriveVelocity();
        state.angle = getAzimuthPosition();
        return state;
    }
    public SwerveModulePosition getPosition() {
        position.distanceMeters = getDrivePosition();
        position.angle = getAzimuthPosition();
        return position;
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        Rotation2d azimuthPosition = getAzimuthPosition();

        desiredState = SwerveModuleState.optimize(desiredState, azimuthPosition);
        if (Math.abs(desiredState.speedMetersPerSecond) < 0.01
                && Math.abs(desiredState.angle.getRadians() - azimuthPosition.getRadians()) < 0.05) {
            stop();
            return;
        }

        final double driveOutput = (desiredState.speedMetersPerSecond * DriveConstants.COUNTS_TO_METERS) / 10;

        final double azimuthOutput = azimuthPosition.getDegrees() * CANCoderConstants.COUNTS_PER_DEG;

        driveMotor.set(ControlMode.Velocity, driveOutput);
        azimuthMotor.set(ControlMode.MotionMagic, azimuthOutput);
    }

    public void stop() {
        driveMotor.stopMotor();
        driveMotor.stopMotor();
    }
}