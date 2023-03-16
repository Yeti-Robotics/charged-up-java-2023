package frc.robot.commands.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.AutoConstants.ALIGNMENT_POSITION;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.FieldConstants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;

import java.util.function.DoubleSupplier;

public class DoubleStationAlignCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;
    private final DoubleSupplier xSupplier;
    private final PIDController yController;
    private final PIDController thetaController;
    private final Timer timer;

    private final double targetY;
    private final Rotation2d targetTheta;

    public DoubleStationAlignCommand(DrivetrainSubsystem drivetrainSubsystem, DoubleSupplier xSpeed, ALIGNMENT_POSITION position) {
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.xSupplier = xSpeed;
        this.yController = new PIDController(AutoConstants.TRANSLATION_P, AutoConstants.TRANSLATION_I, AutoConstants.TRANSLATION_D);
        this.thetaController = new PIDController(AutoConstants.THETA_CONTROLLER_P, AutoConstants.THETA_CONTROLLER_I, AutoConstants.THETA_CONTROLLER_D);
        this.timer = new Timer();
        this.timer.start();

        yController.setTolerance(0.2);
        thetaController.setTolerance(0.5);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        this.targetY = FieldConstants.humanStationAprilTag.getY() + position.offset.getY();
        this.targetTheta = position.offset.getRotation();

        yController.setSetpoint(targetY);
        thetaController.setSetpoint(targetTheta.getRadians());
        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void initialize() {
        timer.reset();
        yController.reset();
        thetaController.reset();
    }

    @Override
    public void execute() {
        Pose2d robotPose = drivetrainSubsystem.getPose();
        double xSpeed = DrivetrainSubsystem.modifyAxis(xSupplier.getAsDouble()) * AutoConstants.ALIGNMENT_CONSTRAINTS.maxVelocity;
        double ySpeed = MathUtil.clamp(
                yController.calculate(robotPose.getY()),
                -AutoConstants.ALIGNMENT_CONSTRAINTS.maxVelocity,
                AutoConstants.ALIGNMENT_CONSTRAINTS.maxVelocity);
        double thetaSpeed = MathUtil.clamp(
                thetaController.calculate(robotPose.getRotation().getRadians()),
                -AutoConstants.ALIGNMENT_CONSTRAINTS.maxVelocity,
                AutoConstants.ALIGNMENT_CONSTRAINTS.maxVelocity);

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
