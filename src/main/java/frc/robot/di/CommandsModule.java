package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.commands.*;
import frc.robot.commands.arm.ArmDownCommand;
import frc.robot.commands.arm.SetArmPositionHandoffCommand;
import frc.robot.commands.arm.ArmUpCommand;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.carriage.CarriageInCommand;
import frc.robot.commands.drive.FieldOrientedDrive;
import frc.robot.commands.elevator.MoveElevatorDownCommand;
import frc.robot.commands.elevator.MoveElevatorUpCommand;
import frc.robot.commands.elevator.SetElevatorPositionConeHandoffCommand;
import frc.robot.commands.elevator.SetElevatorPositionTopCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.CommandFactory;

import javax.inject.Named;
import java.util.function.DoubleSupplier;

@Module
public class CommandsModule {
    @Provides
    @IntoMap
    @ClassKey(MoveElevatorDownCommand.class)
    public CommandBase provideMoveElevatorDownCommand(ElevatorSubsystem elevatorSubsystem) {
        return new MoveElevatorDownCommand(elevatorSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(MoveElevatorUpCommand.class)
    public CommandBase provideMoveElevatorUpCommand(ElevatorSubsystem elevatorSubsystem) {
        return new MoveElevatorUpCommand(elevatorSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(SetElevatorPositionConeHandoffCommand.class)
    public CommandBase provideSetElevatorPositionConeHandoffCommand(ElevatorSubsystem elevatorSubsystem) {
        return new SetElevatorPositionConeHandoffCommand(elevatorSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(SetElevatorPositionTopCommand.class)
    public CommandBase provideSetElevatorPositionTomCommand(ElevatorSubsystem elevatorSubsystem) {
        return new SetElevatorPositionTopCommand(elevatorSubsystem);
    }

    @ClassKey(IntakeClampCommand.class)
    public CommandFactory provideIntakeClampCommand(IntakeSubsystem intakeSubsystem){
        return new IntakeClampCommand(intakeSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(IntakeUnclampCommand.class)
    public CommandBase provideIntakeUnClampCommand(IntakeSubsystem intakeSubsystem){
        return new IntakeUnclampCommand(intakeSubsystem);

    }

    @ClassKey(IntakeRollInCommand.class)
    public CommandBase provideIntakeRollInCommand(IntakeSubsystem intakeSubsystem){
        return new IntakeRollInCommand(intakeSubsystem);

    }
    @ClassKey(IntakeRollOutCommand.class)
    public CommandBase provideIntakeRollOutCommand(IntakeSubsystem intakeSubsystem){
        return new IntakeRollOutCommand(intakeSubsystem);
    }

    @ClassKey(IntakeShootCommand.class)
    public CommandBase provideIntakeShootCommand(IntakeSubsystem intakeSubsystem) {
        return new IntakeShootCommand(intakeSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(ArmUpCommand.class)
    public CommandBase provideArmUpCommand(ArmSubsystem armSubsystem) {
        return new ArmUpCommand(armSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(ArmDownCommand.class)
    public CommandBase provideArmDownCommand(ArmSubsystem armSubsystem) {
        return new ArmDownCommand(armSubsystem);
    }


    @Provides
    @IntoMap
    @ClassKey(SetArmPositionCommand.class)
    public CommandBase provideSetArmPositionCommand(ArmSubsystem armSubsystem) {
        return new SetArmPositionCommand(armSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(SetArmPositionHandoffCommand.class)
    public CommandBase provideSetArmPositionHandoffCommand(ArmSubsystem armSubsystem) {
        return new SetArmPositionHandoffCommand(armSubsystem);
    }



    @Provides
    @IntoMap
    @ClassKey(FieldOrientedDrive.class)
    public CommandBase provideFieldOrientedDrive(
            DrivetrainSubsystem drivetrainSubsystem,
            @Named(Constants.OIConstants.TRANSLATION_XSUPPLIER) DoubleSupplier translationXSupplier,
            @Named(Constants.OIConstants.TRANSLATION_YSUPPLIER) DoubleSupplier translationYSupplier,
            @Named(Constants.OIConstants.THETA_SUPPLIER) DoubleSupplier rotationSupplier) {
        return new FieldOrientedDrive(drivetrainSubsystem, translationXSupplier, translationYSupplier, rotationSupplier);
    }

    @Provides
    @IntoMap
    @ClassKey(CarriageInCommand.class)
    public CommandBase provideCarriageInCommand(CarriageSubsystem carriageSubsystem, IntakeSubsystem intakeSubsystem) {
        return new CarriageInCommand(carriageSubsystem, intakeSubsystem);
    }
}