package frc.robot.commands.drive;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.*;
import frc.robot.constants.AutoConstants.ALIGNMENT_POSITION;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;

import java.util.function.DoubleSupplier;

public class GridAlignCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;

    private final DoubleSupplier xSupplier;
    private final PIDController yController;
    private final PIDController thetaController;
    private final CarriageSubsystem carriageSubsystem;
    private final LEDSubsystem ledSubsystem;


    private double targetY;
    private Rotation2d targetTheta;
    private final Timer timer;
    private final ALIGNMENT_POSITION position;
    private final PIDController xController;
    private Command autoCommand;

    public GridAlignCommand(DrivetrainSubsystem drivetrainSubsystem, LEDSubsystem ledSubsystem, ElevatorSubsystem elevatorSubsystem,CarriageSubsystem carriageSubsystem, DoubleSupplier xSpeed, ALIGNMENT_POSITION position) {
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.carriageSubsystem = carriageSubsystem;
        this.ledSubsystem = ledSubsystem;
        this.position = position;
        this.xController = new PIDController(AutoConstants.TRANSLATION_P, AutoConstants.TRANSLATION_I, AutoConstants.TRANSLATION_D);
        this.xSupplier = xSpeed;
        this.yController = new PIDController(AutoConstants.TRANSLATION_P, AutoConstants.TRANSLATION_I, AutoConstants.TRANSLATION_D);
        this.thetaController = new PIDController(AutoConstants.THETA_CONTROLLER_P, AutoConstants.THETA_CONTROLLER_I, AutoConstants.THETA_CONTROLLER_D);
        timer = new Timer();
        timer.start();

        yController.setTolerance(0.2);
        thetaController.setTolerance(0.01);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void initialize() {
        timer.reset();
        yController.reset();
        thetaController.reset();

        targetY = FieldConstants.humanStationAprilTag.getY() + position.offset.getY();
        targetTheta = position.offset.getRotation();

        yController.setSetpoint(targetY);
        thetaController.setSetpoint(targetTheta.getRadians());
        if(ledSubsystem.getPieceTarget() == LEDSubsystem.PieceTarget.CUBE){
            carriageSubsystem.setSetpoint(CarriageConstants.CarriagePositions.CUBE_STATION);

        } else {
            carriageSubsystem.setSetpoint(CarriageConstants.CarriagePositions.FLIPPED);

        }
    }


    @Override
    public void execute() {
        Pose2d robotPose = drivetrainSubsystem.getPose();
        double xSpeed = DrivetrainSubsystem.modifyAxis(xSupplier.getAsDouble()) * AutoConstants.ALIGNMENT_CONSTRAINTS.maxVelocity;
        double ySpeed = 0.0;
        double thetaSpeed = MathUtil.clamp(
                thetaController.calculate(robotPose.getRotation().getRadians()),
                -AutoConstants.ALIGNMENT_CONSTRAINTS.maxVelocity,
                AutoConstants.ALIGNMENT_CONSTRAINTS.maxVelocity);

        if (!yController.atSetpoint()) {
            ySpeed = MathUtil.clamp(
                    yController.calculate(robotPose.getY()),
                    -1.0,
                    1.0);
        }

        drivetrainSubsystem.drive(
                DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                        ChassisSpeeds.fromFieldRelativeSpeeds(
                                xSpeed,
                                ySpeed,
                                thetaSpeed,
                                robotPose.getRotation()
                        )
                )
        );
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrainSubsystem.stop();
    }
}
