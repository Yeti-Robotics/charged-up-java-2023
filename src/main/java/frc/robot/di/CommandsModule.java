package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.*;
import frc.robot.subsystems.ExampleSubsystem;
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
    @ClassKey(IntakeUnClampCommand.class)
    static CommandBase provideIntakeUnClampCommand(IntakeSubsystem intakeSubsystem){
        return new IntakeUnClampCommand(intakeSubsystem);
    }
    @Provides
    @IntoMap
    @ClassKey(intakeRollInCommand.class)
    static CommandBase provideIntakeRollIn(IntakeSubsystem intakeSubsystem){
        return new intakeRollInCommand(intakeSubsystem);
    }
    @Provides
    @IntoMap
    @ClassKey(intakeRollOutCommand.class)
    static CommandBase provideIntakeRollOut(IntakeSubsystem intakeSubsystem){
        return new intakeRollOutCommand(intakeSubsystem);
    }
}