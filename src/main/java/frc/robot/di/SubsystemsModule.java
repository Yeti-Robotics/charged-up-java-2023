package frc.robot.di;

import javax.inject.Singleton;

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
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

    @Provides
    @Singleton
    public DrivetrainSubsystem provideDriveTrainSubsystem(
            @Named("front left module") SwerveModule frontLeftModule,
            @Named("front right module") SwerveModule frontRightModule,
            @Named("back left module") SwerveModule backLeftModule,
            @Named("back right module") SwerveModule backrightModule,
            SwerveDriveOdometry odometer,
            WPI_Pigeon2 gyro,
            ControllerContainer controllerContainer) {
        return new DrivetrainSubsystem(
                frontLeftModule,
                frontRightModule,
                backLeftModule,
                backrightModule,
                odometer,
                gyro,
                controllerContainer
        );
    }
}