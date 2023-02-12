package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.*;
import frc.robot.subsystems.ElevatorSubsystem;

import javax.inject.Named;

@Module
public class CommandsModule {
    @Provides
    @IntoMap
    @ClassKey(DecrementElevatorLevelCommand.class)
    static CommandBase provideDecrementElevatorLevelCommand(ElevatorSubsystem elevatorSubsystem) {
        return new DecrementElevatorLevelCommand(elevatorSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(ElevatorStopCommand.class)
    static CommandBase provideElevatorStopCommand(ElevatorSubsystem elevatorSubsystem) {
        return new ElevatorStopCommand(elevatorSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(IncrementElevatorLevelCommand.class)
    static CommandBase provideIncrementElevatorLevelCommand(ElevatorSubsystem elevatorSubsystem) {
        return new IncrementElevatorLevelCommand(elevatorSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(MoveElevatorCommand.class)
    static CommandBase provideMoveElevatorCommand(ElevatorSubsystem elevatorSubsystem) {
        return new MoveElevatorCommand(elevatorSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(MoveElevatorDownCommand.class)
    static CommandBase provideMoveElevatorDownCommand(ElevatorSubsystem elevatorSubsystem) {
        return new MoveElevatorDownCommand(elevatorSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(MoveElevatorUpCommand.class)
    static CommandBase provideMoveElevatorUpCommand(ElevatorSubsystem elevatorSubsystem) {
        return new MoveElevatorUpCommand(elevatorSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(SetElevatorDeployLevelCommand.class)
    static CommandBase provideSetElevatorDeployLevelCommand(ElevatorSubsystem elevatorSubsystem, @Named("Elevator Level")int level) {
        return new SetElevatorDeployLevelCommand(elevatorSubsystem, level);
    }

    @Provides
    @Named("Elevator Level")
    int elevatorLevel(){
        return 0;
    }
    @Provides
    @IntoMap
    @ClassKey(SetElevatorLevelCommand.class)
    static CommandBase provideSetElevatorLevelCommand(ElevatorSubsystem elevatorSubsystem, @Named("Elevator Level") int level) {
        return new SetElevatorLevelCommand(elevatorSubsystem, level);
    }

    @Provides
    @IntoMap
    @ClassKey(ZeroElevatorCommand.class)
    static CommandBase provideZeroElevatorCommand(ElevatorSubsystem elevatorSubsystem) {
        return new ZeroElevatorCommand(elevatorSubsystem);
    }

}