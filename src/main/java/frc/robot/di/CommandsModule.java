package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.commands.*;
import frc.robot.commands.arm.ArmDownCommand;
import frc.robot.commands.arm.ArmUpCommand;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.drive.AutoBalancingCommand;
import frc.robot.commands.drive.FieldOrientedDrive;
import frc.robot.commands.drive.SwerveLockCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.Limelight;

import javax.inject.Named;
import java.util.function.DoubleSupplier;

@Module
public class CommandsModule {

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
    @ClassKey(SetArmPositionCommand.class)
    static CommandBase provideSetArmPositionCommand(ArmSubsystem armSubsystem) {
        return new SetArmPositionCommand(armSubsystem);
    }



    @Provides
    @IntoMap
    @ClassKey(FieldOrientedDrive.class)
    static CommandBase provideFieldOrientedDrive(
            DrivetrainSubsystem drivetrainSubsystem,
            @Named("translationXSupplier") DoubleSupplier translationXSupplier,
            @Named("translationYSupplier") DoubleSupplier translationYSupplier,
            @Named("thetaSupplier") DoubleSupplier rotationSupplier) {
        return new FieldOrientedDrive(drivetrainSubsystem, translationXSupplier, translationYSupplier, rotationSupplier);
    }

    @Provides
    @IntoMap
    @ClassKey(SwerveLockCommand.class)
    static CommandBase provideSwerveLockCommand(DrivetrainSubsystem drivetrainSubsystem) {
        return new SwerveLockCommand(drivetrainSubsystem);
    }
    @Provides
    @IntoMap
    @ClassKey(AutoBalancingCommand.class)
    static CommandBase providesAutoBalancingCommand(
            DrivetrainSubsystem drivetrainSubsystem) {
        PIDController pidController = new PIDController(Constants.AutoConstants.PITCH_P, Constants.AutoConstants.PITCH_I, Constants.AutoConstants.PITCH_D, Constants.AutoConstants.PITCH_F);
        pidController.setTolerance(1);
        pidController.setSetpoint(Constants.AutoConstants.PITCH_SET_POINT);
        return new AutoBalancingCommand(drivetrainSubsystem, pidController);
    }


    @Provides
    @IntoMap
    @ClassKey(AprilTagAlignCommand.class)
    static CommandBase providesAprilTagAlignCommand(
            DrivetrainSubsystem drivetrainSubsystem) {
        PIDController pidControllerX = new PIDController(Constants.DriveConstants.DRIVE_MOTOR_P, Constants.DriveConstants.DRIVE_MOTOR_I, Constants.DriveConstants.DRIVE_MOTOR_D);

        pidControllerX.setTolerance(0.15);
        pidControllerX.setSetpoint(1.2);

        PIDController pidControllerY = new PIDController(Constants.DriveConstants.DRIVE_MOTOR_P, Constants.DriveConstants.DRIVE_MOTOR_I, Constants.DriveConstants.DRIVE_MOTOR_D);

        pidControllerX.setTolerance(0.3);
        pidControllerX.setSetpoint(0);

        PIDController pidControllerAngle = new PIDController(Constants.DriveConstants.AZIMUTH_MOTOR_P, Constants.DriveConstants.AZIMUTH_MOTOR_I, Constants.DriveConstants.AZIMUTH_MOTOR_D);
        pidControllerX.setTolerance(1);
        pidControllerX.setSetpoint(0);

        Limelight visionSubsystem = new Limelight();

        return new AprilTagAlignCommand(drivetrainSubsystem, visionSubsystem, pidControllerX, pidControllerY, pidControllerAngle);
    }
}