package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;

@Module
public class CommandsModule {

    @Provides
    @IntoMap
    @ClassKey(ExampleCommand.class)
    static CommandBase provideExampleCommand(ExampleSubsystem exampleSubsystem){
        return new ExampleCommand(exampleSubsystem);
    }
}