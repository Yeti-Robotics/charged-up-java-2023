package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.commands.*;
import frc.robot.commands.arm.ArmDownCommand;
import frc.robot.commands.arm.ArmUpCommand;
import frc.robot.commands.arm.ToggleArmPositionCommand;
import frc.robot.commands.drive.FieldOrientedDrive;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;

import javax.inject.Named;
import java.util.function.DoubleSupplier;

import frc.robot.subsystems.ElevatorSubsystem;

@Module
public class CommandsModule {
    @Provides
    @IntoMap
    @ClassKey(MoveElevatorDownCommand.class)
    static CommandBase provideMoveElevatorDownCommand(ElevatorSubsystem elevatorSubsystem) {
        return new MoveElevatorDownCommand(elevatorSubsystem);
    }

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

    @ClassKey(IntakeRollInCommand.class)
    static CommandBase provideIntakeRollInCommand(IntakeSubsystem intakeSubsystem){
        return new IntakeRollInCommand(intakeSubsystem);

    }
    @ClassKey(IntakeRollOutCommand.class)
    static CommandBase provideIntakeRollOutCommand(IntakeSubsystem intakeSubsystem){
        return new IntakeRollOutCommand(intakeSubsystem);
    }

    @ClassKey(IntakeShootCommand.class)
    static CommandBase provideIntakeShootCommand(IntakeSubsystem intakeSubsystem) {
        return new IntakeShootCommand(intakeSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(ArmUpCommand.class)
    static CommandBase provideArmUpCommand(ArmSubsystem armSubsystem) {
        return new ArmUpCommand(armSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(ArmDownCommand.class)
    static CommandBase provideArmDownCommand(ArmSubsystem armSubsystem) {
        return new ArmDownCommand(armSubsystem);
    }


    @Provides
    @IntoMap
    @ClassKey(ToggleArmPositionCommand.class)
    static CommandBase provideSetArmPositionCommand(ArmSubsystem armSubsystem) {
        return new ToggleArmPositionCommand(armSubsystem);
    }



    @Provides
    @IntoMap
    @ClassKey(FieldOrientedDrive.class)
    static CommandBase provideFieldOrientedDrive(
            DrivetrainSubsystem drivetrainSubsystem,
            @Named(Constants.OIConstants.TRANSLATION_XSUPPLIER) DoubleSupplier translationXSupplier,
            @Named(Constants.OIConstants.TRANSLATION_YSUPPLIER) DoubleSupplier translationYSupplier,
            @Named(Constants.OIConstants.THETA_SUPPLIER) DoubleSupplier rotationSupplier) {
        return new FieldOrientedDrive(drivetrainSubsystem, translationXSupplier, translationYSupplier, rotationSupplier);
    }
}