package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.*;
import frc.robot.subsystems.IntakeSubsystem;

@Module
public class CommandsModule {

    @Provides
    @IntoMap
    @ClassKey(IntakeClampCommand.class)
    static CommandBase provideIntakeClampCommand(IntakeSubsystem intakeSubsystem){
        return new IntakeClampCommand(intakeSubsystem);
    }
    @Provides
    @IntoMap
    @ClassKey(IntakeUnclampCommand.class)
    static CommandBase provideIntakeUnClampCommand(IntakeSubsystem intakeSubsystem){
        return new IntakeUnclampCommand(intakeSubsystem);
    }
    @Provides
    @IntoMap
    @ClassKey(IntakeRollInCommand.class)
    static CommandBase provideIntakeRollInCommand(IntakeSubsystem intakeSubsystem){
        return new IntakeRollInCommand(intakeSubsystem);
    }
    @Provides
    @IntoMap
    @ClassKey(IntakeRollOutCommand.class)
    static CommandBase provideIntakeRollOutCommand(IntakeSubsystem intakeSubsystem){
        return new IntakeRollOutCommand(intakeSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(IntakeShootCommand.class)
    static CommandBase provideIntakeShootCommand(IntakeSubsystem intakeSubsystem){
        return new IntakeShootCommand(intakeSubsystem);
    }

}