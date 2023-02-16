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
}