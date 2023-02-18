package frc.robot.di;

import javax.inject.Singleton;

import com.revrobotics.*;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;

import javax.inject.Named;

@Module
public class SubsystemsModule {
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
}