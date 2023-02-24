package frc.robot.commands.drive;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.Limelight;


public class AutoAlignConeCommand extends CommandBase
{
    private final DrivetrainSubsystem drivetrainSubsystem;
    private final SwerveAutoBuilder autoBuilder;


    public AutoAlignConeCommand(DrivetrainSubsystem drivetrainSubsystem, SwerveAutoBuilder autoBuilder){
        this.drivetrainSubsystem=drivetrainSubsystem;
        this.autoBuilder = autoBuilder;
    }



    @Override
    public void initialize(){
        Translation2d tagLocation = new Translation2d(Limelight.getTranslation().getX() + Constants.AutoConstants.CONE_OFFSET, Limelight.getTranslation().getY() + Constants.AutoConstants.ALIGN_OFFSET);
        Translation2d robotPose = drivetrainSubsystem.getPose().getTranslation();
        Translation2d translation2 = robotPose.interpolate(tagLocation,.8);
        Translation2d translation3 = robotPose.interpolate(tagLocation,.4);
        PathPlannerTrajectory path = PathPlanner.generatePath(new PathConstraints(Constants.DriveConstants.MAX_VELOCITY_METERS_PER_SECOND, Constants.AutoConstants.MAX_ACCEL),
                new PathPoint(robotPose, drivetrainSubsystem.getGyroscopeHeading()), new PathPoint(translation2, drivetrainSubsystem.getGyroscopeHeading()), new PathPoint(translation3, drivetrainSubsystem.getGyroscopeHeading()), new PathPoint(tagLocation, drivetrainSubsystem.getGyroscopeHeading()));
        autoBuilder.followPath(path);

    }

    @Override
    public void execute() {
    }
}
