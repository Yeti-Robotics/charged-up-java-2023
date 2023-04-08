package frc.robot.commands.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;


public class AutoBalancingBangBangCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;
    private final Timer timer;

    public AutoBalancingBangBangCommand(DrivetrainSubsystem drivetrainSubsystem) {
        this.drivetrainSubsystem = drivetrainSubsystem;

        timer = new Timer();
        timer.start();
        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void initialize() {
        timer.reset();
    }

    @Override
    public void execute() {
        if(drivetrainSubsystem.getPitch().getDegrees() <= -10) {
            drivetrainSubsystem.drive(DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                    ChassisSpeeds.fromFieldRelativeSpeeds(0.5, 0.0, 0.0, drivetrainSubsystem.getPose().getRotation())));
        } else if(drivetrainSubsystem.getPitch().getDegrees() <= 10){
            drivetrainSubsystem.drive(DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                    ChassisSpeeds.fromFieldRelativeSpeeds(-0.5, 0.0, 0.0, drivetrainSubsystem.getPose().getRotation())));
//        } else if(drivetrainSubsystem.getPitch().getDegrees() <= -5){
//            drivetrainSubsystem.drive(DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
//                    ChassisSpeeds.fromFieldRelativeSpeeds(0.3, 0.0, 0.0, drivetrainSubsystem.getPose().getRotation())));
//
//    } else if(drivetrainSubsystem.getPitch().getDegrees() <= 5){
//        drivetrainSubsystem.drive(DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
//                ChassisSpeeds.fromFieldRelativeSpeeds(-0.3, 0.0, 0.0, drivetrainSubsystem.getPose().getRotation())));

    }
        else if(drivetrainSubsystem.getPitch().getDegrees() <= -3){
            drivetrainSubsystem.drive(DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                    ChassisSpeeds.fromFieldRelativeSpeeds(0.1, 0.0, 0.0, drivetrainSubsystem.getPose().getRotation())));

        } else if(drivetrainSubsystem.getPitch().getDegrees() <= 3){
            drivetrainSubsystem.drive(DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                    ChassisSpeeds.fromFieldRelativeSpeeds(-0.1, 0.0, 0.0, drivetrainSubsystem.getPose().getRotation())));

        } else{
            drivetrainSubsystem.stop();
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
