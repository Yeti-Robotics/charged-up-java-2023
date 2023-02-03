package frc.robot.di;

import javax.inject.Singleton;

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import frc.robot.Constants.*;
import frc.robot.di.devices.DeviceModule;
import frc.robot.di.devices.MotorsModule;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.subsystems.drivetrain.SwerveModule;
import frc.robot.utils.controllerUtils.ControllerContainer;


import javax.inject.Named;

@Module
public class SubsystemsModule {
    @Provides
    @Singleton
    public ExampleSubsystem provideExampleSubsystem() {
        return new ExampleSubsystem();
    }

    private static SwerveModule swerveModuleFactory (
        int driveMotorID, int steerMotorID, int CANcoderID, boolean driveInverted, double encoderOffset, boolean encoderReversed
        ) {
        return new SwerveModule(
                MotorsModule.driveMotorFactory(driveMotorID, driveInverted),
                MotorsModule.azimuthMotorFactory(steerMotorID),
                DeviceModule.absoluteEncoderFactory(CANcoderID, encoderOffset, encoderReversed)
                );
    }

    @Provides
    @Singleton
    @Named("front left")
    public SwerveModule providesFrontLeftSwerveModule() {
        return swerveModuleFactory(
                DriveConstants.FRONT_LEFT_DRIVE,
                DriveConstants.FRONT_LEFT_AZIMUTH,
                DriveConstants.FRONT_LEFT_CAN,
                DriveConstants.FRONT_LEFT_DRIVE_REVERSED,
                DriveConstants.FRONT_LEFT_ENCODER_OFFSET,
                DriveConstants.FRONT_LEFT_ENCODER_REVERSED
        );
    }

    @Provides
    @Singleton
    @Named("front right")
    public SwerveModule providesFrontRightSwerveModule() {
        return swerveModuleFactory(
                DriveConstants.FRONT_RIGHT_DRIVE,
                DriveConstants.FRONT_RIGHT_AZIMUTH,
                DriveConstants.FRONT_RIGHT_CAN,
                DriveConstants.FRONT_RIGHT_DRIVE_REVERSED,
                DriveConstants.FRONT_RIGHT_ENCODER_OFFSET,
                DriveConstants.FRONT_RIGHT_ENCODER_REVERSED
        );
    }

    @Provides
    @Singleton
    @Named("back left")
    public SwerveModule providesBackLeftSwerveModule() {
        return swerveModuleFactory(
                DriveConstants.BACK_LEFT_DRIVE,
                DriveConstants.BACK_LEFT_AZIMUTH,
                DriveConstants.BACK_LEFT_CAN,
                DriveConstants.BACK_LEFT_DRIVE_REVERSED,
                DriveConstants.BACK_LEFT_ENCODER_OFFSET,
                DriveConstants.BACK_LEFT_ENCODER_REVERSED
        );
    }

    @Provides
    @Singleton
    @Named("back right")
    public SwerveModule providesBackRightSwerveModule() {
        return swerveModuleFactory(
                DriveConstants.BACK_RIGHT_DRIVE,
                DriveConstants.BACK_RIGHT_AZIMUTH,
                DriveConstants.BACK_RIGHT_CAN,
                DriveConstants.BACK_RIGHT_DRIVE_REVERSED,
                DriveConstants.BACK_RIGHT_ENCODER_OFFSET,
                DriveConstants.BACK_RIGHT_ENCODER_REVERSED
        );    }


    @Provides
    @Singleton
    public DrivetrainSubsystem provideDriveTrainSubsystem(
            @Named("front left") SwerveModule frontLeftModule,
            @Named("front right") SwerveModule frontRightModule,
            @Named("back left") SwerveModule backLeftModule,
            @Named("back right") SwerveModule backRightModule,
            SwerveDriveOdometry odometer,
            WPI_Pigeon2 gyro,
            ControllerContainer controllerContainer) {
        return new DrivetrainSubsystem(
                frontLeftModule,
                frontRightModule,
                backLeftModule,
                backRightModule,
                odometer,
                gyro,
                controllerContainer
        );
    }
}