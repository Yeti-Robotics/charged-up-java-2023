package frc.robot.subsystems.drivetrain;


import com.ctre.phoenix.sensors.WPI_Pigeon2;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;

import javax.inject.Inject;
import javax.inject.Named;

public class DrivetrainSubsystem extends SubsystemBase {
    private final SwerveModule frontLeftModule, frontRightModule, backLeftModule, backRightModule;

    private final SwerveModulePosition[] positions;
    private final PIDController yController = new PIDController(AutoConstants.Y_CONTROLLER_P, 0.0, AutoConstants.X_CONTROLLER_D);
    private final PIDController xController = new PIDController(AutoConstants.X_CONTROLLER_P, 0.0, AutoConstants.Y_CONTROLLER_D);
    private final PIDController thetaController = new PIDController(AutoConstants.THETA_CONTROLLER_P,
            0.0, 0.0);
    private final SwerveDriveOdometry odometer;
    private final WPI_Pigeon2 gyro;
    private ChassisSpeeds chassisSpeeds = new ChassisSpeeds(0, 0, 0);

    @Inject
    public DrivetrainSubsystem(
            @Named("front left") SwerveModule frontLeftModule,
            @Named("front right") SwerveModule frontRightModule,
            @Named("back left") SwerveModule backLeftModule,
            @Named("back right") SwerveModule backRightModule,
            SwerveModulePosition[] swerveModulePosition,
            SwerveDriveOdometry odometer,
            WPI_Pigeon2 gyro) {
        this.frontLeftModule = frontLeftModule;
        this.frontRightModule = frontRightModule;
        this.backLeftModule = backLeftModule;
        this.backRightModule = backRightModule;
        this.positions = swerveModulePosition;
        this.odometer = odometer;
        this.gyro = gyro;

        updateSwerveModulePositions();
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                zeroGyroscope();
            } catch (Exception e) {
                System.out.println("FAILED TO ZERO GYROSCOPE");
            }
        }).start();
    }

    public void zeroGyroscope() {
        gyro.reset();
    }

    public Rotation2d getGyroscopeHeading() {
        return Rotation2d.fromDegrees(gyro.getYaw());
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

    public void drive(SwerveModuleState... desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, DriveConstants.MAX_VELOCITY_METERS_PER_SECOND);
        frontLeftModule.setDesiredState(desiredStates[0]);
        frontRightModule.setDesiredState(desiredStates[1]);
        backLeftModule.setDesiredState(desiredStates[2]);
        backRightModule.setDesiredState(desiredStates[3]);
    }

    public ChassisSpeeds getChassisSpeeds() {
        return chassisSpeeds = DriveConstants.DRIVE_KINEMATICS.toChassisSpeeds(
                frontLeftModule.getState(),
                frontRightModule.getState(),
                backLeftModule.getState(),
                backRightModule.getState()
        );
    }

    public void stop() {
        drive(
                DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(new ChassisSpeeds())
        );
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


