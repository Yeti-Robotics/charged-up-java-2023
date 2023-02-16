package frc.robot.di;

import javax.inject.Singleton;

import com.revrobotics.CANSparkMax;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Constants;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

import javax.inject.Named;

@Module
public class SubsystemsModule {
    @Provides
    @Singleton
    public IntakeSubsystem providesIntakeSubsystem(
            @Named(Constants.IntakeConstants.INTAKE_SPARK_1_NAME) CANSparkMax intakeSpark1,
            @Named (Constants.IntakeConstants.INTAKE_SPARK_2_NAME) CANSparkMax intakeSpark2,
            @Named (Constants.IntakeConstants.INTAKE_PISTON_NAME) DoubleSolenoid intakePiston) {

        return new IntakeSubsystem(
                intakeSpark1,
                intakeSpark2,
                intakePiston
        );


    }
}