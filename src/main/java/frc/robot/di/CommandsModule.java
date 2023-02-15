package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.arm.*;
import frc.robot.subsystems.ArmSubsystem;

@Module
public class CommandsModule {
    @Provides
    @IntoMap
    @ClassKey(MoveArmCommand.class)
    static CommandBase provideMoveArmCommand(ArmSubsystem armSubsystem){
        return new MoveArmCommand(armSubsystem);
    }
}