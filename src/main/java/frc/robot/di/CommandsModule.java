package frc.robot.di;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.arm.*;
import frc.robot.subsystems.ArmSubsystem;

import javax.inject.Named;

@Module
public class CommandsModule {
    @Provides
    @IntoMap
    @ClassKey(MoveArmCommand.class)
    static CommandBase provideMoveArmCommand(ArmSubsystem armSubsystem){
        return new MoveArmCommand(armSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(ArmDownCommand.class)
    static CommandBase provideArmDownCommand(ArmSubsystem armSubsystem) {
        return new ArmDownCommand(armSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(ArmUpCommand.class)
    static CommandBase provideArmUpCommand(ArmSubsystem armSubsystem) {
        return new ArmUpCommand(armSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(SetPositionCommand.class)
    static CommandBase provideSetPositionCommand(ArmSubsystem armSubsystem) {
        return new SetPositionCommand(armSubsystem);
    }
}