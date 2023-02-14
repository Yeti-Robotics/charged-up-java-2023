package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.drive.FieldOrientedDrive;
import frc.robot.commands.drive.SwerveLockCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;

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
}