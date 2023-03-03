package frc.robot.commands.drive;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.FieldConstants;
import frc.robot.constants.AutoConstants.ALIGNMENT_POSITION;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.Limelight;

import java.util.Arrays;

public class AutoAlignCommand extends CommandBase
{
    private final DrivetrainSubsystem drivetrainSubsystem;
    private final SwerveAutoBuilder autoBuilder;
    private PathPlannerTrajectory path;
    private Command autoCommand;
    private final Timer timer;

    private final ALIGNMENT_POSITION position;


    public AutoAlignCommand(DrivetrainSubsystem drivetrainSubsystem, SwerveAutoBuilder autoBuilder, ALIGNMENT_POSITION position){
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.autoBuilder = autoBuilder;
        this.position = position;
        timer = new Timer();
        timer.start();
    }

    @Override
    public void initialize(){
        Translation2d robotPose = drivetrainSubsystem.getPose().getTranslation();
        Translation2d tagLocation;
        if (!Limelight.hasTarget()) {
            tagLocation = robotPose.nearest(Arrays.asList(FieldConstants.aprilTagTranslations));
        } else {
            tagLocation = FieldConstants.aprilTagLayout.getTagPose((int) Limelight.getID()).get().getTranslation().toTranslation2d();
        }

        double targetX = tagLocation.getX() + position.offset.getX();
        double targetY = tagLocation.getY() + position.offset.getY();
        if (targetX < 0.0 || targetY < 0.0) {
            targetX = Math.abs(targetX);
            targetY = Math.abs(targetY);
        }
        Translation2d targetPose = new Translation2d(targetX, targetY);

        Translation2d translation2 = robotPose.interpolate(targetPose,0.5);
        path = PathPlanner.generatePath(AutoConstants.ALIGNMENT_CONSTRAINTS,
                new PathPoint(robotPose, position.heading, drivetrainSubsystem.getGyroscopeHeading()),
                new PathPoint(translation2, position.heading),
                new PathPoint(targetPose, position.heading, position.offset.getRotation()));

        autoCommand = autoBuilder.followPath(path);
        autoCommand.schedule();
        timer.reset();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(path.getTotalTimeSeconds());
    }

    @Override
    public void end(boolean interrupted) {
        autoCommand.end(interrupted);
        drivetrainSubsystem.stop();
    }
}
