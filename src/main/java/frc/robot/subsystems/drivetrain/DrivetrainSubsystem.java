package frc.robot.subsystems.drivetrain;


import com.ctre.phoenix.sensors.WPI_Pigeon2;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.controllerUtils.Controller;
import frc.robot.utils.controllerUtils.ControllerContainer;

import javax.inject.Inject;
import javax.inject.Named;

public class DrivetrainSubsystem extends SubsystemBase {
    private final SwerveModule frontLeftModule, frontRightModule, backLeftModule, backRightModule;

    private final SwerveModulePosition[] positions = new SwerveModulePosition[4];
    private final PIDController yController = new PIDController(Constants.AutoConstants.Y_CONTROLLER_P, 0.0, Constants.AutoConstants.X_CONTROLLER_D);
    private final PIDController xController = new PIDController(Constants.AutoConstants.X_CONTROLLER_P, 0.0, Constants.AutoConstants.Y_CONTROLLER_D);
    private final PIDController thetaController = new PIDController(Constants.AutoConstants.THETA_CONTROLLER_P,
            0.0, 0.0);
    private final SwerveDriveOdometry odometer;

    private boolean isSwerveLock;

    private final WPI_Pigeon2 gyro;

    private ChassisSpeeds chassisSpeeds = new ChassisSpeeds(0, 0, 0);

    @Inject
    public DrivetrainSubsystem(
            @Named("front left module") SwerveModule frontLeftModule,
            @Named("front right module") SwerveModule frontRightModule,
            @Named("back left module") SwerveModule backLeftModule,
            @Named("back right module") SwerveModule backRightModule,
            SwerveDriveOdometry odometer,
            WPI_Pigeon2 gyro) {
        this.frontLeftModule = frontLeftModule;
        this.frontRightModule = frontRightModule;
        this.backLeftModule = backLeftModule;
        this.backRightModule = backRightModule;
        this.odometer = odometer;
        this.gyro = gyro;

        updateSwerveModulePositions();
        thetaController.enableContinuousInput(-Math.PI, Math.PI);
    }

    public void zeroGyroscope() {
        gyro.reset();
    }

    public Rotation2d getGyroscopeHeading() {
        return Rotation2d.fromDegrees((gyro.getYaw() % 360) - 180);
    }

    public Pose2d getPose() {
        return odometer.getPoseMeters();
    }

    public void resetOdometer(Pose2d pose) {
        updateSwerveModulePositions();
        odometer.resetPosition(getGyroscopeHeading(), positions, pose);
    }

    public PIDController getxController() {
        return xController;
    }

    public PIDController getyController() {
        return yController;
    }

    public PIDController getThetaController() {
        return thetaController;
    }

    public void setDesiredStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.DriveConstants.MAX_VELOCITY_METERS_PER_SECOND);
        frontLeftModule.setDesiredState(desiredStates[0]);
        frontRightModule.setDesiredState(desiredStates[1]);
        backLeftModule.setDesiredState(desiredStates[2]);
        backRightModule.setDesiredState(desiredStates[3]);
    }

    public void drive(ChassisSpeeds chassisSpeeds) {
        if (isSwerveLock) {
            swerveLock();
            return;
        }
        this.chassisSpeeds = chassisSpeeds;

        SwerveModuleState[] states = Constants.DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(chassisSpeeds);
        setDesiredStates(states);
//        System.out.println(states[0]);
    }

    private void swerveLock() {
        if (chassisSpeeds.vxMetersPerSecond > 0.5 && chassisSpeeds.vyMetersPerSecond > 0.5) {
            isSwerveLock = false;
            return;
        }

        SwerveModuleState[] desiredStates = new SwerveModuleState[4];
        desiredStates[0] = new SwerveModuleState(0, Rotation2d.fromDegrees(45));
        desiredStates[1] = new SwerveModuleState(0, Rotation2d.fromDegrees(-45));
        desiredStates[2] = new SwerveModuleState(0, Rotation2d.fromDegrees(-45));
        desiredStates[3] = new SwerveModuleState(0, Rotation2d.fromDegrees(45));
        setDesiredStates(desiredStates);
    }

    public void toggleSwerveLock() {
        isSwerveLock = !isSwerveLock;
    }

    public ChassisSpeeds getChassisSpeeds() {
        return chassisSpeeds;
    }

    public void updateSwerveModulePositions() {
        positions[0] = frontLeftModule.getPosition();
        positions[1] = frontRightModule.getPosition();
        positions[2] = backLeftModule.getPosition();
        positions[3] = backRightModule.getPosition();
    }

    @Override
    public void periodic() {
        updateSwerveModulePositions();
        odometer.update(getGyroscopeHeading(), positions);
    }
}


