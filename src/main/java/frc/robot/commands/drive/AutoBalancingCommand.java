package frc.robot.commands.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;


public class AutoBalancingCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;
    //private final PIDController pidController;
    private final BangBangController bangController;
    private final Timer timer;

    private int sign = 1;

    public AutoBalancingCommand(DrivetrainSubsystem drivetrainSubsystem) {
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.bangController = new BangBangController();



        timer = new Timer();
        timer.start();
        this.bangController.setTolerance(AutoConstants.PITCH_TOLERANCE);
        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void initialize() {
        timer.reset();
        sign = 1;
        if (Math.abs(drivetrainSubsystem.getPose().getRotation().getDegrees()) >= 90.0) {
            sign = -1;
        }

    }

    @Override
    public void execute() {
        double pitch = -Math.abs(drivetrainSubsystem.getPitch().getDegrees());
        double val = bangController.calculate(pitch, AutoConstants.PITCH_SET_POINT);

        if (val == 1 ) {
            if (pitch <= 12) {
                drivetrainSubsystem.drive(DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                        ChassisSpeeds.fromFieldRelativeSpeeds(0.5 * sign, 0.0, 0.0, drivetrainSubsystem.getPose().getRotation())));
            } else {
                drivetrainSubsystem.drive(DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                        ChassisSpeeds.fromFieldRelativeSpeeds(0.8 * sign, 0.0, 0.0, drivetrainSubsystem.getPose().getRotation())));
            }
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
