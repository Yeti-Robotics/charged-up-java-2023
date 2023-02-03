package frc.robot.di;

import javax.inject.Singleton;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ExampleSubsystem;

import javax.inject.Named;

@Module
public class SubsystemsModule {

    @Provides
    @Singleton
    @Named("Elevator Subsystem")
    public ElevatorSubsystem provideElevatorSubsystem(@Named("elevatorMotor")WPI_TalonFX elevatorMotor, @Named("beamBreak") DigitalInput beamBreak) {
        return new ElevatorSubsystem(elevatorMotor, beamBreak);
    }

    @Provides
    @Singleton
    public ExampleSubsystem provideExampleSubsystem() {
        return new ExampleSubsystem();
    }


}