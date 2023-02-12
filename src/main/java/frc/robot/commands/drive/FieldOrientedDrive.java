package frc.robot.commands.drive;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;

import javax.inject.Inject;
import java.util.function.DoubleSupplier;


public class FieldOrientedDrive extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;
    private final DoubleSupplier translationXSupplier;
    private final DoubleSupplier translationYSupplier;
    private final DoubleSupplier rotationSupplier;
    private ChassisSpeeds chassisSpeeds;

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
                Constants.DriveConstants.MAX_VELOCITY_METERS_PER_SECOND;
        double ySpeed = modifyAxis(translationYSupplier.getAsDouble()) *
                Constants.DriveConstants.MAX_VELOCITY_METERS_PER_SECOND;
        double thetaSpeed = modifyAxis(rotationSupplier.getAsDouble()) *
                Constants.DriveConstants.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND;

        chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
                xSpeed,
                ySpeed,
                thetaSpeed,
                drivetrainSubsystem.getPose().getRotation());
        drivetrainSubsystem.drive(
                chassisSpeeds
        );
//        System.out.println(chassisSpeeds);
//        System.out.printf("X: %f, Y: %f, T:%f\n", xSpeed, ySpeed, thetaSpeed);
    }
    private double modifyAxis(double value) {
        if (Math.abs(value) <= Constants.OIConstants.DEADBAND) {
            return 0.0;
        }

        return Math.copySign(value * value * value, value);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrainSubsystem.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
    }
    }