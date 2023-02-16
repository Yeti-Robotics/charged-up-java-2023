package frc.robot.di;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;
import frc.robot.subsystems.ElevatorSubsystem;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class SubsystemsModule {
    @Provides
    @Singleton
    @Named("Elevator Subsystem")
    public ElevatorSubsystem provideElevatorSubsystem(
            @Named(Constants.ElevatorConstants.ELEVATOR_MOTOR) WPI_TalonFX elevatorMotor,
            @Named(Constants.ElevatorConstants.ELEVATOR_MAG_SWITCH) DigitalInput elevatorBeamBreak) {
        return new ElevatorSubsystem(
                elevatorMotor,
                elevatorBeamBreak);
    }
}