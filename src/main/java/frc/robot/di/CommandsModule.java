package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.*;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.CarriageSubsystem;

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


    @Provides
    @IntoMap
    @ClassKey(SetArmPositionCommand.class)
    static CommandBase provideSetArmPositionCommand(ArmSubsystem armSubsystem) {
        return new SetArmPositionCommand(armSubsystem);
    }
}