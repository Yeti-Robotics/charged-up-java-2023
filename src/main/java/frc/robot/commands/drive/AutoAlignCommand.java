package frc.robot.commands.drive;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.FieldConstants;
import frc.robot.constants.AutoConstants.ALIGNMENT_POSITION;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.Limelight;


public class AutoAlignCommand extends CommandBase
{
    private final DrivetrainSubsystem drivetrainSubsystem;
    private final SwerveAutoBuilder autoBuilder;

    private final ALIGNMENT_POSITION position;


    public AutoAlignCommand(DrivetrainSubsystem drivetrainSubsystem, SwerveAutoBuilder autoBuilder, ALIGNMENT_POSITION position){
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.autoBuilder = autoBuilder;
        this.position = position;
    }

    @Override
    public void initialize(){
        if (!Limelight.hasTarget()) {
            this.cancel();
            return;
        }

        Pose3d tagLocation = FieldConstants.aprilTagLayout.getTagPose((int) Limelight.getID()).get();

        Translation2d targetPose;
        if (DriverStation.getAlliance() == DriverStation.Alliance.Blue) {
            targetPose = new Translation2d(tagLocation.getX() + position.offset.getX(),
                    tagLocation.getY() + position.offset.getY());
        } else {
            targetPose = new Translation2d(tagLocation.getX() + position.offset.getX(),
                    tagLocation.getY() - position.offset.getY());
        }

        Translation2d robotPose = drivetrainSubsystem.getPose().getTranslation();
        Translation2d translation2 = robotPose.interpolate(targetPose,.4);
        Translation2d translation3 = robotPose.interpolate(targetPose,.8);
        PathPlannerTrajectory path = PathPlanner.generatePath(AutoConstants.DEFAULT_CONSTRAINTS,
                new PathPoint(robotPose, position.heading, drivetrainSubsystem.getGyroscopeHeading()),
                new PathPoint(translation2, position.heading),
                new PathPoint(translation3, position.heading),
                new PathPoint(targetPose, position.heading, position.offset.getRotation()));

        autoBuilder.followPath(path);
    }

    @Override
    public void execute() {
    }
}
