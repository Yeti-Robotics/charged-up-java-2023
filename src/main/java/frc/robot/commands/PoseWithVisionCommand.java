package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.Limelight;


public class PoseWithVisionCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;
    private final Timer timer = new Timer();

    public PoseWithVisionCommand(DrivetrainSubsystem drivetrainSubsystem) {
        this.drivetrainSubsystem = drivetrainSubsystem;

        timer.start();
        addRequirements();
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
        if (timer.hasElapsed(0.050)) {
            double[] botpose = Limelight.getBotPose();
            drivetrainSubsystem.updateOdometerWithVision(
                    new Pose2d(botpose[0], botpose[1], Rotation2d.fromDegrees(botpose[5])),
                    Timer.getFPGATimestamp() - botpose[6] / 1000.0);
            timer.reset();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
