package frc.robot.di;

import javax.inject.Singleton;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.subsystems.ArmSubsystem;

import javax.inject.Named;

@Module
public class SubsystemsModule {
    @Provides
    @Singleton
    public ArmSubsystem providesArmSubsystem (
            @Named("armMotor1") WPI_TalonFX motor1,
            @Named("armMotor2") WPI_TalonFX motor2,
            @Named("armEncoder") WPI_CANCoder encoder,
            @Named("airBrake") DoubleSolenoid airBrake
            ) {
       return new ArmSubsystem(
               motor1,
               motor2,
               encoder,
               airBrake);
    }
}
