package frc.robot.di;

import javax.inject.Singleton;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;

import javax.inject.Named;

@Module
public class SubsystemsModule {
    @Provides
    @Singleton
    public ArmSubsystem providesArmSubsystem (
            @Named(Constants.ArmConstants.ARM_MOTOR) WPI_TalonFX armMotor,
            @Named(Constants.ArmConstants.ARM_ENCODER) WPI_CANCoder encoder,
            @Named(Constants.ArmConstants.AIR_BRAKE) DoubleSolenoid airBrake
            ) {
       return new ArmSubsystem(
               armMotor,
               encoder,
               airBrake);
    }
}
