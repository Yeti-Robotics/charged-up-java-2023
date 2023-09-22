package frc.robot.commands.drive;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class FieldOrientedDrive extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;
    private final DoubleSupplier translationXSupplier;
    private final DoubleSupplier translationYSupplier;
    private final DoubleSupplier rotationSupplier;
    private final Supplier<ElevatorConstants.ElevatorPositions> elevatorPositionSupplier;

    public FieldOrientedDrive(
            DrivetrainSubsystem drivetrainSubsystem,
            DoubleSupplier translationXSupplier,
            DoubleSupplier translationYSupplier,
            DoubleSupplier rotationSupplier,
            Supplier<ElevatorConstants.ElevatorPositions> elevatorPositionSupplier) {
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.translationXSupplier = translationXSupplier;
        this.translationYSupplier = translationYSupplier;
        this.rotationSupplier = rotationSupplier;
        this.elevatorPositionSupplier = elevatorPositionSupplier;

        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void execute() {
        double xSpeed = DrivetrainSubsystem.modifyAxis(translationXSupplier.getAsDouble(), 2) *
                DriveConstants.MAX_VELOCITY_METERS_PER_SECOND;
        double ySpeed = DrivetrainSubsystem.modifyAxis(translationYSupplier.getAsDouble(), 2) *
                DriveConstants.MAX_VELOCITY_METERS_PER_SECOND;
        double thetaSpeed = DrivetrainSubsystem.modifyAxis(rotationSupplier.getAsDouble(), 4) *
                DriveConstants.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND;
//        if (elevatorPositionSupplier.get() != ElevatorConstants.ElevatorPositions.DOWN)
//            thetaSpeed = DrivetrainSubsystem.modifyAxis(rotationSupplier.getAsDouble(), 3) *
//                    DriveConstants.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND;
//        else {
//            thetaSpeed = DrivetrainSubsystem.modifyAxis(rotationSupplier.getAsDouble(), 2) *
//                    DriveConstants.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND;
//        }

        drivetrainSubsystem.drive(
                DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                        ChassisSpeeds.fromFieldRelativeSpeeds(
                                xSpeed,
                                ySpeed,
                                thetaSpeed,
                                drivetrainSubsystem.getPose().getRotation()))
        );
    }


    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrainSubsystem.stop();
    }
}
