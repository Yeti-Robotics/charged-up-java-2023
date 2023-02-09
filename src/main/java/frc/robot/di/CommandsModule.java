package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.CarriageCloseCommand;
import frc.robot.commands.CarriageInCommand;
import frc.robot.commands.CarriageOpenCommand;
import frc.robot.commands.CarriageOutCommand;
import frc.robot.subsystems.CarriageSubsystem;

@Module
public class CommandsModule {

    @Provides
    @IntoMap
    @ClassKey(CarriageCloseCommand.class)
    static CommandBase provideCarriageCloseCommand(CarriageSubsystem carriageSubsystem){
        return new CarriageCloseCommand(carriageSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(CarriageOpenCommand.class)
    static CommandBase provideCarriageOpenCommand(CarriageSubsystem carriageSubsystem){
        return new CarriageOpenCommand(carriageSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(CarriageInCommand.class)
    static CommandBase provideCarriageInCommand(CarriageSubsystem carriageSubsystem){
        return new CarriageInCommand(carriageSubsystem);
    }
    @Provides
    @IntoMap
    @ClassKey(CarriageOutCommand.class)
    static CommandBase provideCarriageOutCommand(CarriageSubsystem carriageSubsystem){
        return new CarriageOutCommand(carriageSubsystem);
    }
}