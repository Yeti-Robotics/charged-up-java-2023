package frc.robot.commands.drive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.OIConstants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.Limelight;

import java.util.function.DoubleSupplier;


public class RetroReflectAlignCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;
    private PIDController yController;

    private DoubleSupplier xSupplier;
    private DoubleSupplier thetaSupplier;

    public RetroReflectAlignCommand(DrivetrainSubsystem drivetrainSubsystem, DoubleSupplier xSupplier, DoubleSupplier thetaSupplier) {
        this.drivetrainSubsystem = drivetrainSubsystem;
        yController = new PIDController(AutoConstants.TAPE_P, AutoConstants.TAPE_I, AutoConstants.TAPE_D);
        this.xSupplier = xSupplier;
        this.thetaSupplier = thetaSupplier;
        addRequirements(this.drivetrainSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (!Limelight.hasTarget()) {
            this.cancel();
            return;
        }

        double xSpeed = modifyAxis(xSupplier.getAsDouble()) * DriveConstants.MAX_VELOCITY_METERS_PER_SECOND;
        double ySpeed = yController.calculate(Limelight.getTx(), 0.0);
        double thetaSpeed = modifyAxis(thetaSupplier.getAsDouble()) * DriveConstants.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND;

        drivetrainSubsystem.drive(
                DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                        ChassisSpeeds.fromFieldRelativeSpeeds(
                                xSpeed,
                                ySpeed,
                                thetaSpeed,
                                drivetrainSubsystem.getPose().getRotation()))
        );
    }

    public double modifyAxis(double value) {
        if (Math.abs(value) <= OIConstants.DEADBAND) {
            return 0.0;
        }

        return Math.copySign(value * value, value);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
