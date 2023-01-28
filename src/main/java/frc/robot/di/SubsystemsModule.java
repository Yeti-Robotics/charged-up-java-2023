package frc.robot.di;

import javax.inject.Singleton;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import dagger.Module;
import dagger.Provides;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ExampleSubsystem;

import javax.inject.Named;

@Module
public class SubsystemsModule {
    @Provides
    @Singleton

    public ArmSubsystem providesArmSubsystem (
            @Named("armMotor1") WPI_TalonFX motor1, @Named("armMotor2")WPI_TalonFX motor2
    ){
       return new ArmSubsystem(motor1, motor2);
    }
    @Provides
    @Singleton
    public ExampleSubsystem provideExampleSubsystem() {
        return new ExampleSubsystem();
    }
}