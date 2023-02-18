package frc.robot.di;

import javax.inject.Singleton;
import com.revrobotics.*;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;
import com.ctre.phoenix.sensors.WPI_Pigeon2;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import frc.robot.Constants.DriveConstants;
import frc.robot.di.devices.DeviceModule;
import frc.robot.di.devices.MotorsModule;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.subsystems.drivetrain.SwerveModule;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class SubsystemsModule {
    private static SwerveModule swerveModuleFactory(
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
    public IntakeSubsystem providesIntakeSubsystem(
            @Named(Constants.IntakeConstants.LEFT_SPARK) CANSparkMax leftSpark,
            @Named(Constants.IntakeConstants.RIGHT_SPARK) CANSparkMax rightSpark,
            @Named(Constants.IntakeConstants.INTAKE_PISTON_NAME) DoubleSolenoid intakePiston,
            @Named(Constants.IntakeConstants.INTAKE_PID) SparkMaxPIDController pidController,
            @Named(Constants.IntakeConstants.INTAKE_ENCODER) RelativeEncoder encoder,
            @Named(Constants.IntakeConstants.INTAKE_BEAM_BREAK) SparkMaxLimitSwitch beamBreak,
            @Named(Constants.IntakeConstants.INTAKE_REED_SWITCH) SparkMaxLimitSwitch reedSwitch) {
        return new IntakeSubsystem(
                leftSpark,
                rightSpark,
                intakePiston,
                pidController,
                encoder,
                beamBreak,
                reedSwitch
        );


    }

    @Provides
    @Singleton
    @Named(DriveConstants.FRONT_LEFT_MODULE_NAME)
    public SwerveModule providesFrontLeftSwerveModule() {
        return swerveModuleFactory(
                DriveConstants.FRONT_LEFT_DRIVE,
                DriveConstants.FRONT_LEFT_AZIMUTH,
                DriveConstants.FRONT_LEFT_ENCODER,
                DriveConstants.FRONT_LEFT_DRIVE_REVERSED,
                DriveConstants.FRONT_LEFT_ENCODER_OFFSET,
                DriveConstants.FRONT_LEFT_ENCODER_REVERSED
        );
    }

    @Provides
    @Singleton
    @Named(DriveConstants.FRONT_RIGHT_MODULE_NAME)
    public SwerveModule providesFrontRightSwerveModule() {
        return swerveModuleFactory(
                DriveConstants.FRONT_RIGHT_DRIVE,
                DriveConstants.FRONT_RIGHT_AZIMUTH,
                DriveConstants.FRONT_RIGHT_ENCODER,
                DriveConstants.FRONT_RIGHT_DRIVE_REVERSED,
                DriveConstants.FRONT_RIGHT_ENCODER_OFFSET,
                DriveConstants.FRONT_RIGHT_ENCODER_REVERSED
        );
    }

    @Provides
    @Singleton
    @Named(DriveConstants.BACK_LEFT_MODULE_NAME)
    public SwerveModule providesBackLeftSwerveModule() {
        return swerveModuleFactory(
                DriveConstants.BACK_LEFT_DRIVE,
                DriveConstants.BACK_LEFT_AZIMUTH,
                DriveConstants.BACK_LEFT_ENCODER,
                DriveConstants.BACK_LEFT_DRIVE_REVERSED,
                DriveConstants.BACK_LEFT_ENCODER_OFFSET,
                DriveConstants.BACK_LEFT_ENCODER_REVERSED
        );
    }

    @Provides
    @Singleton
    @Named(DriveConstants.BACK_RIGHT_MODULE_NAME)
    public SwerveModule providesBackRightSwerveModule() {
        return swerveModuleFactory(
                DriveConstants.BACK_RIGHT_DRIVE,
                DriveConstants.BACK_RIGHT_AZIMUTH,
                DriveConstants.BACK_RIGHT_ENCODER,
                DriveConstants.BACK_RIGHT_DRIVE_REVERSED,
                DriveConstants.BACK_RIGHT_ENCODER_OFFSET,
                DriveConstants.BACK_RIGHT_ENCODER_REVERSED
        );
    }

    @Provides
    @Singleton
    public DrivetrainSubsystem provideDriveTrainSubsystem(
            @Named(DriveConstants.FRONT_LEFT_MODULE_NAME) SwerveModule frontLeftModule,
            @Named(DriveConstants.FRONT_RIGHT_MODULE_NAME) SwerveModule frontRightModule,
            @Named(DriveConstants.BACK_LEFT_MODULE_NAME) SwerveModule backLeftModule,
            @Named(DriveConstants.BACK_RIGHT_MODULE_NAME) SwerveModule backRightModule,
            SwerveModulePosition[] swerveModulePositions,
            SwerveDriveOdometry odometer,
            WPI_Pigeon2 gyro) {
        return new DrivetrainSubsystem(
                frontLeftModule,
                frontRightModule,
                backLeftModule,
                backRightModule,
                swerveModulePositions,
                odometer,
                gyro
        );
    }

}