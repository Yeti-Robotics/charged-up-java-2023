package frc.robot.commands.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;


public class AutoBalancingCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;
    private final PIDController pidController;
    private final Timer timer;

    public AutoBalancingCommand(DrivetrainSubsystem drivetrainSubsystem) {
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.pidController = new PIDController(
                AutoConstants.PITCH_P,
                AutoConstants.PITCH_I,
                AutoConstants.PITCH_D
        );

        timer = new Timer();
        timer.start();
        this.pidController.setTolerance(AutoConstants.PITCH_TOLERANCE);
        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void initialize() {
        timer.reset();
    }

    @Override
    public void execute() {
        double multiplier = 1;

        if (Math.abs(drivetrainSubsystem.getPose().getRotation().getDegrees()) >= 90.0) {
            multiplier = -1;
        }

        if(drivetrainSubsystem.getPitch().getDegrees()<= 12){
            drivetrainSubsystem.drive(DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                    ChassisSpeeds.fromFieldRelativeSpeeds(0.3 * multiplier, 0.0, 0.0, drivetrainSubsystem.getPose().getRotation())));
        }else{
            drivetrainSubsystem.drive(DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                    ChassisSpeeds.fromFieldRelativeSpeeds(0.6 * multiplier, 0.0, 0.0, drivetrainSubsystem.getPose().getRotation())));
        }

    }

    @Override
    public boolean isFinished() {
        if (timer.hasElapsed(0.50)) {
            timer.reset();
            return Math.abs(drivetrainSubsystem.getPitch().getDegrees()) <= AutoConstants.PITCH_TOLERANCE;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
