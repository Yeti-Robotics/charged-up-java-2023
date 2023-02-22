package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.Limelight;


public class AprilTagAlignCommand extends CommandBase {

    private final DrivetrainSubsystem drivetrainSubsystem;
    private final Limelight visionSubsystem;
    private final PIDController pidControllerX;
    private final PIDController pidControllerY;
    private final PIDController pidControllerAngle;

    public AprilTagAlignCommand(
            DrivetrainSubsystem drivetrainSubsystem,
            Limelight visionSubsystem,
            PIDController pidControllerX,
            PIDController pidControllerY,
            PIDController pidControllerAngle
    ) {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.visionSubsystem = visionSubsystem;
        this.pidControllerX = pidControllerX;
        this.pidControllerY = pidControllerY;
        this.pidControllerAngle = pidControllerAngle;
        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void initialize() {


    }

    @Override
    public void execute() {

        if (Limelight.isTarget()) {
            double translationX = MathUtil.clamp(pidControllerX.calculate(Limelight.getTx()), -Constants.VisionConstants.CENTER_LIMIT, Constants.VisionConstants.CENTER_LIMIT);
            double translationY = MathUtil.clamp(pidControllerX.calculate(visionSubsystem.getTy()), -Constants.VisionConstants.CENTER_LIMIT, Constants.VisionConstants.CENTER_LIMIT);
            double translationAngle = pidControllerAngle.calculate(visionSubsystem.getYaw());
            drivetrainSubsystem.drive(new Translation2d(translationX, translationY), translationAngle);
        }
    }

    @Override
    public boolean isFinished() {
        return (pidControllerY.atSetpoint() && pidControllerY.atSetpoint() && pidControllerAngle.atSetpoint());
    }

    @Override
    public void end(boolean interrupted) {


    }
}
