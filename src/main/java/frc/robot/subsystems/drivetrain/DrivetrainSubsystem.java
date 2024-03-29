package frc.robot.subsystems.drivetrain;


import com.ctre.phoenix.sensors.WPI_Pigeon2;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.math.kinematics.*;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.FieldConstants;
import frc.robot.constants.OIConstants;
import frc.robot.utils.GeometryUtils;
import frc.robot.utils.Limelight;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import org.opencv.core.Mat;

import javax.inject.Inject;
import javax.inject.Named;

public class DrivetrainSubsystem extends SubsystemBase implements Sendable {
    private final SwerveModule frontLeftModule, frontRightModule, backLeftModule, backRightModule;

    private final SwerveModulePosition[] positions;
    private final SwerveDrivePoseEstimator odometer;
    private final WPI_Pigeon2 gyro;
    private double chuteOffset = -1.72;
    private ChassisSpeeds chassisSpeeds = new ChassisSpeeds(0, 0, 0);

    @Inject
    public DrivetrainSubsystem(
            @Named(DriveConstants.FRONT_LEFT_MODULE_NAME) SwerveModule frontLeftModule,
            @Named(DriveConstants.FRONT_RIGHT_MODULE_NAME) SwerveModule frontRightModule,
            @Named(DriveConstants.BACK_LEFT_MODULE_NAME) SwerveModule backLeftModule,
            @Named(DriveConstants.BACK_RIGHT_MODULE_NAME) SwerveModule backRightModule,
            SwerveModulePosition[] swerveModulePosition,
            SwerveDrivePoseEstimator odometer,
            WPI_Pigeon2 gyro) {
        this.frontLeftModule = frontLeftModule;
        this.frontRightModule = frontRightModule;
        this.backLeftModule = backLeftModule;
        this.backRightModule = backRightModule;
        this.positions = swerveModulePosition;
        this.odometer = odometer;
        this.gyro = gyro;

        updateSwerveModulePositions();

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

    public Rotation2d getPitch() {
        return Rotation2d.fromDegrees(gyro.getRoll());
    }

    public Pose2d getPose() {
        return odometer.getEstimatedPosition();
    }

    public void resetOdometer(Pose2d pose) {
        updateSwerveModulePositions();
        odometer.resetPosition(getGyroscopeHeading(), positions, pose);
    }

    public void updateOdometerWithVision(Pose2d visionPose, double timestamp) {
        odometer.addVisionMeasurement(visionPose, timestamp);
    }

    public void drive(SwerveModuleState... desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, DriveConstants.MAX_VELOCITY_METERS_PER_SECOND);
        frontLeftModule.setDesiredState(desiredStates[0]);
        frontRightModule.setDesiredState(desiredStates[1]);
        backLeftModule.setDesiredState(desiredStates[2]);
        backRightModule.setDesiredState(desiredStates[3]);
    }
    private static ChassisSpeeds correctForDynamics(ChassisSpeeds originalSpeeds) { //Jimmy's 5727 2nd order swerve kinematics implementation <3
        final double LOOP_TIME_S = 0.02;
        Pose2d futureRobotPose =
            new Pose2d(
                originalSpeeds.vxMetersPerSecond * LOOP_TIME_S,
                originalSpeeds.vyMetersPerSecond * LOOP_TIME_S,
                Rotation2d.fromRadians(originalSpeeds.omegaRadiansPerSecond * LOOP_TIME_S));
        Twist2d twistForPose = GeometryUtils.log(futureRobotPose);
        ChassisSpeeds updatedSpeeds =
            new ChassisSpeeds(
                twistForPose.dx / LOOP_TIME_S,
                twistForPose.dy / LOOP_TIME_S,
                twistForPose.dtheta / LOOP_TIME_S);
        return updatedSpeeds;
    }

    public ChassisSpeeds getChassisSpeeds() {
        chassisSpeeds = DriveConstants.DRIVE_KINEMATICS.toChassisSpeeds(
                frontLeftModule.getState(),
                frontRightModule.getState(),
                backLeftModule.getState(),
                backRightModule.getState()
        );
        chassisSpeeds = correctForDynamics(chassisSpeeds);
        return chassisSpeeds;
    }

    public void stop() {
        drive(
                DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(new ChassisSpeeds())
        );
    }

    public static double modifyAxis(double value, int pow) {
        if (Math.abs(value) <= OIConstants.DEADBAND) {
            return 0.0;
        }

        return Math.copySign(Math.pow(value, pow), value);
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

    public double getChuteOffset() {
        return -chuteOffset;
    }

    public void setChuteOffset(double offset) {
        chuteOffset = Math.abs(offset);
    }
    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Chute Offset", () -> getChuteOffset(), this::setChuteOffset);
        builder.addStringProperty("pose", () -> String.format("Pose (x,y): (%.2f, %.2f) Rotation(deg): %.2f", getPose().getX(), getPose().getY(), getPose().getRotation().getDegrees()), null);
    }
}


