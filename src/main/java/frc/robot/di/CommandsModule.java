package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.*;

@Module
public class CommandsModule {
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

    @Provides
    @IntoMap
    @ClassKey(CarriageStop.class)
    static CommandBase provideCarriageStopCommand(CarriageSubsystem carriageSubsystem){
        return new CarriageStop(carriageSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(CarriageFlip.class)
    static CommandBase provideCarriageFlipCommand(CarriageSubsystem carriageSubsystem){
        return new CarriageFlip(carriageSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(CarriageReverseFlip.class)
    static CommandBase provideCarriageReverseFlipCommand(CarriageSubsystem carriageSubsystem){
        return new CarriageInCommand(carriageSubsystem);
    }

}