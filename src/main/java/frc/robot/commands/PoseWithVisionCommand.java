package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.Limelight;


public class PoseWithVisionCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;
    public PoseWithVisionCommand(DrivetrainSubsystem drivetrainSubsystem) {
        this.drivetrainSubsystem = drivetrainSubsystem;

        addRequirements();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double[] botpose = Limelight.getBotPose();
        System.out.println(botpose[0] + botpose[1] + botpose[2]);
        drivetrainSubsystem.updateOdometerWithVision(
                new Pose2d(botpose[0], botpose[1], Rotation2d.fromDegrees(botpose[5])),
                Timer.getFPGATimestamp() - botpose[6]);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
