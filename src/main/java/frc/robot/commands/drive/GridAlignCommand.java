package frc.robot.commands.drive;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.AutoConstants.ALIGNMENT_POSITION;
import frc.robot.constants.CarriageConstants;
import frc.robot.constants.FieldConstants;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;

public class GridAlignCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;
    private final CarriageSubsystem carriageSubsystem;
    private final LEDSubsystem ledSubsystem;
    private final SwerveAutoBuilder autoBuilder;
    private final Timer timer;
    private final ALIGNMENT_POSITION position;
    private PathPlannerTrajectory path;
    private Command autoCommand;

    public GridAlignCommand(DrivetrainSubsystem drivetrainSubsystem, CarriageSubsystem carriageSubsystem, LEDSubsystem ledSubsystem, SwerveAutoBuilder autoBuilder, ALIGNMENT_POSITION position) {
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.carriageSubsystem = carriageSubsystem;
        this.ledSubsystem = ledSubsystem;
        this.autoBuilder = autoBuilder;
        this.position = position;
        timer = new Timer();
        timer.start();
    }

    @Override
    public void initialize() {
        Pose2d robotPose = drivetrainSubsystem.getPose();
        Pose2d tagLocation = robotPose.nearest(FieldConstants.gridAprilTags);

        double targetX = tagLocation.getX() + position.offset.getX();
        double targetY = tagLocation.getY() + position.offset.getY();
        Rotation2d targetTheta = position.offset.getRotation();

        Pose2d midPoint = new Pose2d(targetX + 0.2, targetY, targetTheta);
        Pose2d targetPose = new Pose2d(targetX, targetY, targetTheta);

        path = PathPlanner.generatePath(AutoConstants.ALIGNMENT_CONSTRAINTS,
                new PathPoint(robotPose.getTranslation(), position.heading, robotPose.getRotation()),
                new PathPoint(midPoint.getTranslation(), position.heading, midPoint.getRotation()),
                new PathPoint(targetPose.getTranslation(), position.heading, targetPose.getRotation()));

        carriageSubsystem.setSetpoint(CarriageConstants.CarriagePositions.FLIPPED);
        if (ledSubsystem.getPieceTarget() == LEDSubsystem.PieceTarget.CUBE) {
            carriageSubsystem.coneOutCubeIn();
        } else {
            carriageSubsystem.coneInCubeOut();
        }

        autoCommand = autoBuilder.followPath(path);
        autoCommand.schedule();
        timer.reset();
    }

    @Override
    public void execute() {
        if (timer.hasElapsed(0.5)) {
            carriageSubsystem.rollerStop();
        }
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
