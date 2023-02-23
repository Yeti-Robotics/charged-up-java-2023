package frc.robot.utils.auto;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import frc.robot.Constants;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;

import java.util.HashMap;
import java.util.List;

public class AutoBuilder {

    private final SwerveAutoBuilder autoBuilder;
    private PathPlannerTrajectory trajectory;
    private PPSwerveControllerCommand swerveControllerCommand;


    public AutoBuilder(SwerveAutoBuilder autoBuilder) {

        this.autoBuilder = autoBuilder;

    }

    private Command generateAuto(String path){
        return autoBuilder.fullAuto(PathPlanner.loadPathGroup(path, DriveConstants.MAX_VELOCITY_METERS_PER_SECOND, AutoConstants.MAX_ACCEL));
    }

    private Pose2d getInitialPose(PathPlannerTrajectory path){
        return new Pose2d(
                path.getInitialPose().getTranslation(),
                path.getInitialState().holonomicRotation
        );
    }

    private void driveToPose(List<PathPoint> points){
        PathPlannerTrajectory path = PathPlanner.generatePath(new PathConstraints(DriveConstants.MAX_VELOCITY_METERS_PER_SECOND, AutoConstants.MAX_ACCEL),points);
        autoBuilder.fullAuto(path);
    }
}