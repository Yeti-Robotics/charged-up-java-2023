package frc.robot.commands.drive;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.AutoConstants.ALIGNMENT_POSITION;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.FieldConstants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.Limelight;

public class HumanStationAlignCommand extends CommandBase
{
    private final DrivetrainSubsystem drivetrainSubsystem;
    private final SwerveAutoBuilder autoBuilder;
    private PathPlannerTrajectory path;
    private Command autoCommand;
    private final Timer timer;

    private final ALIGNMENT_POSITION position;
    private final Rotation2d flipRotation = Rotation2d.fromDegrees(180.0);

    public HumanStationAlignCommand(DrivetrainSubsystem drivetrainSubsystem, SwerveAutoBuilder autoBuilder, ALIGNMENT_POSITION position){
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.autoBuilder = autoBuilder;
        this.position = position;
        timer = new Timer();
        timer.start();
    }

    @Override
    public void initialize(){
        Pose2d robotPose = drivetrainSubsystem.getPose();
        Pose2d tagLocation = FieldConstants.allianceAprilTags.get(3);

        double targetX = tagLocation.getX() + position.offset.getX();
        double targetY = tagLocation.getY() + position.offset.getY();
        Rotation2d targetTheta = position.offset.getRotation();
        Rotation2d heading = position.heading;

        Pose2d midPoint = new Pose2d(targetX, targetY + 0.4, targetTheta);
        Pose2d targetPose = new Pose2d(targetX, targetY, targetTheta);

        path = PathPlannerTrajectory.transformTrajectoryForAlliance(PathPlanner.generatePath(AutoConstants.ALIGNMENT_CONSTRAINTS,
                new PathPoint(robotPose.getTranslation(), heading, robotPose.getRotation()),
                new PathPoint(midPoint.getTranslation(), heading, midPoint.getRotation()),
                new PathPoint(targetPose.getTranslation(),heading, targetPose.getRotation())), DriverStation.getAlliance());


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
