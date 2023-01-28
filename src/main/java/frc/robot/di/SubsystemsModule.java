package frc.robot.di;

import javax.inject.Singleton;

import com.revrobotics.CANSparkMax;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

import javax.inject.Named;

@Module
public class SubsystemsModule {
    @Provides
    @Singleton
    public IntakeSubsystem providesIntakeSubsystem(
            @Named("intake spark 1") CANSparkMax intakeSpark1,
            @Named ("intake spark 2") CANSparkMax intakeSpark2,
            @Named ("intake piston") DoubleSolenoid intakePiston) {

        return new IntakeSubsystem(
                intakePiston,
                intakeSpark1,
                intakeSpark2
        );


    }
}