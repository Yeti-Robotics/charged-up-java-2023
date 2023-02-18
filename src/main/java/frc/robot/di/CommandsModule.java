package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants;
import frc.robot.commands.AprilTagAlignCommand;
import frc.robot.commands.arm.ArmDownCommand;
import frc.robot.commands.arm.SetArmPositionHandoffCommand;
import frc.robot.commands.arm.ArmUpCommand;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.carriage.CarriageInCommand;
import frc.robot.commands.carriage.CarriageRollerStopCommand;
import frc.robot.commands.drive.FieldOrientedDrive;
import frc.robot.commands.drive.SwerveLockCommand;
import frc.robot.commands.elevator.MoveElevatorDownCommand;
import frc.robot.commands.elevator.MoveElevatorUpCommand;
import frc.robot.commands.elevator.SetElevatorPositionConeHandoffCommand;
import frc.robot.commands.elevator.SetElevatorPositionTopCommand;
import frc.robot.commands.intake.*;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.CommandFactory;
import frc.robot.utils.Limelight;

import javax.inject.Named;
import java.util.function.DoubleSupplier;

@Module
public class CommandsModule {
    @Provides
    @IntoMap
    @ClassKey(MoveElevatorDownCommand.class)
    public CommandFactory provideMoveElevatorDownCommand(ElevatorSubsystem elevatorSubsystem) {
        return new MoveElevatorDownCommand(elevatorSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(MoveElevatorUpCommand.class)
    public CommandFactory provideMoveElevatorUpCommand(ElevatorSubsystem elevatorSubsystem) {
        return new MoveElevatorUpCommand(elevatorSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(SetElevatorPositionConeHandoffCommand.class)
    public CommandFactory provideSetElevatorPositionConeHandoffCommand(ElevatorSubsystem elevatorSubsystem) {
        return new SetElevatorPositionConeHandoffCommand(elevatorSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(SetElevatorPositionTopCommand.class)
    public CommandFactory provideSetElevatorPositionTomCommand(ElevatorSubsystem elevatorSubsystem) {
        return new SetElevatorPositionTopCommand(elevatorSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(IntakeClampCommand.class)
    public CommandFactory provideIntakeClampCommand(IntakeSubsystem intakeSubsystem){
        return new IntakeClampCommand(intakeSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(IntakeUnclampCommand.class)
    public CommandFactory provideIntakeUnClampCommand(IntakeSubsystem intakeSubsystem){
        return new IntakeUnclampCommand(intakeSubsystem);

    }

    @Provides
    @IntoMap
    @ClassKey(IntakeRollInCommand.class)
    public CommandFactory provideIntakeRollInCommand(IntakeSubsystem intakeSubsystem){
        return new IntakeRollInCommand(intakeSubsystem);

    }

    @Provides
    @IntoMap
    @ClassKey(IntakeRollOutCommand.class)
    public CommandFactory provideIntakeRollOutCommand(IntakeSubsystem intakeSubsystem){
        return new IntakeRollOutCommand(intakeSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(IntakeShootCommand.class)
    public CommandFactory provideIntakeShootCommand(IntakeSubsystem intakeSubsystem) {
        return new IntakeShootCommand(intakeSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(ArmUpCommand.class)
    public CommandFactory provideArmUpCommand(ArmSubsystem armSubsystem) {
        return new ArmUpCommand(armSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(ArmDownCommand.class)
    public CommandFactory provideArmDownCommand(ArmSubsystem armSubsystem) {
        return new ArmDownCommand(armSubsystem);
    }


    @Provides
    @IntoMap
    @ClassKey(SetArmPositionCommand.class)
    public CommandFactory provideSetArmPositionCommand(ArmSubsystem armSubsystem) {
        return new SetArmPositionCommand(armSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(SetArmPositionHandoffCommand.class)
    public CommandFactory provideSetArmPositionHandoffCommand(ArmSubsystem armSubsystem) {
        return new SetArmPositionHandoffCommand(armSubsystem);
    }



    @Provides
    @IntoMap
    @ClassKey(FieldOrientedDrive.class)
    public CommandFactory provideFieldOrientedDrive(
            DrivetrainSubsystem drivetrainSubsystem,
            @Named(Constants.OIConstants.TRANSLATION_XSUPPLIER) DoubleSupplier translationXSupplier,
            @Named(Constants.OIConstants.TRANSLATION_YSUPPLIER) DoubleSupplier translationYSupplier,
            @Named(Constants.OIConstants.THETA_SUPPLIER) DoubleSupplier rotationSupplier) {
        return new FieldOrientedDrive(drivetrainSubsystem, translationXSupplier, translationYSupplier, rotationSupplier);
    }

    @Provides
    @IntoMap
    @ClassKey(SwerveLockCommand.class)
    public CommandFactory provideSwerveLockCommand(DrivetrainSubsystem drivetrainSubsystem) {
        return new SwerveLockCommand(drivetrainSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(CarriageInCommand.class)
    public CommandFactory provideCarriageInCommand(CarriageSubsystem carriageSubsystem, IntakeSubsystem intakeSubsystem) {
        return new CarriageInCommand(carriageSubsystem, intakeSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(CarriageRollerStopCommand.class)
    public CommandFactory provideCarriageRollerStopCommand(CarriageSubsystem carriageSubsystem) {
        return new CarriageRollerStopCommand(carriageSubsystem);
    }

    @Provides
    @IntoMap
    @ClassKey(AprilTagAlignCommand.class)
    public CommandFactory provideAprilTagAlign(
            DrivetrainSubsystem drivetrainSubsystem,
            Limelight visionSubsystem,
            PIDController pidControllerX,
            PIDController pidControllerY,
            PIDController pidControllerAngle
    ) {
        return new AprilTagAlignCommand(drivetrainSubsystem, visionSubsystem, pidControllerX, pidControllerY, pidControllerAngle);
    }
}