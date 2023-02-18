package frc.robot.commands.drive;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;

import javax.inject.Inject;
import java.util.function.DoubleSupplier;


public class FieldOrientedDrive extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;
    private final DoubleSupplier translationXSupplier;
    private final DoubleSupplier translationYSupplier;
    private final DoubleSupplier rotationSupplier;

    @Inject
    public FieldOrientedDrive(DrivetrainSubsystem drivetrainSubsystem, DoubleSupplier translationXSupplier, DoubleSupplier translationYSupplier, DoubleSupplier rotationSupplier) {
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.translationXSupplier = translationXSupplier;
        this.translationYSupplier = translationYSupplier;
        this.rotationSupplier = rotationSupplier;

        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(drivetrainSubsystem);
    }


    @Override
    public void execute() {
        double xSpeed = modifyAxis(translationXSupplier.getAsDouble()) *
                DriveConstants.MAX_VELOCITY_METERS_PER_SECOND;
        double ySpeed = modifyAxis(translationYSupplier.getAsDouble()) *
                DriveConstants.MAX_VELOCITY_METERS_PER_SECOND;
        double thetaSpeed = modifyAxis(rotationSupplier.getAsDouble()) *
                DriveConstants.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND;

        drivetrainSubsystem.drive(
                DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                        ChassisSpeeds.fromFieldRelativeSpeeds(
                                xSpeed,
                                ySpeed,
                                thetaSpeed,
                                drivetrainSubsystem.getPose().getRotation()))
        );
    }

    private double modifyAxis(double value) {
        if (Math.abs(value) <= OIConstants.DEADBAND) {
            return 0.0;
        }

        return Math.copySign(value * value, value);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrainSubsystem.stop();
    }
}