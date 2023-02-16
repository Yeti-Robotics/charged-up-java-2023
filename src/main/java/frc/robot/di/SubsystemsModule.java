package frc.robot.di;

import com.revrobotics.CANSparkMax;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.VisionSubsystem;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class SubsystemsModule {

    @Provides
    @Singleton
    public CarriageSubsystem provideCarriageSubsystem(
            @Named(Constants.CarriageConstants.ROLLER_MOTOR_NAME) CANSparkMax rollerMotor,
            @Named(Constants.CarriageConstants.FLIP_MOTOR_NAME) CANSparkMax flipMotor
    ) {
        return new CarriageSubsystem(rollerMotor, flipMotor);
    }

    @Provides
    @Singleton
    public VisionSubsystem providesVisionSubsystem(@Named(Constants.VisionConstants.TABLE_NAME) NetworkTableInstance table) {
        return new VisionSubsystem(NetworkTableInstance.getDefault());
    }
}