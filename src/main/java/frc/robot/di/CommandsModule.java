package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.commands.AprilTagAlignCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.drive.AutoBalancingCommand;
import frc.robot.commands.drive.FieldOrientedDrive;
import frc.robot.commands.drive.SwerveLockCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.Limelight;

import javax.inject.Named;
import java.util.function.DoubleSupplier;

@Module
public class CommandsModule {

    @Provides
    @IntoMap
    @ClassKey(ExampleCommand.class)
    static CommandBase provideExampleCommand(ExampleSubsystem exampleSubsystem) {
        return new ExampleCommand(exampleSubsystem);
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