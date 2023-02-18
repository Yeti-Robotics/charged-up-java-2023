package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.commands.*;
import frc.robot.commands.arm.ArmDownCommand;
import frc.robot.commands.arm.ToggleArmPositionCommand;
import frc.robot.commands.carriage.CarriageInCommand;
import frc.robot.commands.carriage.CarriageOutCommand;
import frc.robot.commands.drive.FieldOrientedDrive;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;

import javax.inject.Named;
import java.util.function.DoubleSupplier;



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
    @ClassKey(ToggleArmPositionCommand.class)
    static CommandBase ToggleArmPositionCommand(ArmSubsystem armSubsystem) {
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

    @Provides
    @IntoMap
    @ClassKey(ArmDownCommand.class)
    static CommandBase provideArmDownCommand(ArmSubsystem armSubsystem) {
        return new ArmDownCommand(armSubsystem);
    }

}