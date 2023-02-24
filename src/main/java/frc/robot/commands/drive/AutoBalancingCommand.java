package frc.robot.commands.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;


public class AutoBalancingCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;
    private final PIDController pidController;

    public AutoBalancingCommand(DrivetrainSubsystem drivetrainSubsystem) {
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.pidController = new PIDController(
                AutoConstants.PITCH_P,
                AutoConstants.PITCH_I,
                AutoConstants.PITCH_D
        );

        this.pidController.setTolerance(AutoConstants.PITCH_TOLERANCE);
        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double val = -MathUtil.clamp(
                pidController.calculate(
                        drivetrainSubsystem.getPitch().getDegrees(), AutoConstants.PITCH_SET_POINT), -.35, .35);
        drivetrainSubsystem.drive(DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                ChassisSpeeds.fromFieldRelativeSpeeds(val, 0.0, 0.0, drivetrainSubsystem.getPose().getRotation())));

    }

    @Override
    public boolean isFinished() {
        return pidController.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        andThen(new SwerveLockCommand(drivetrainSubsystem));
    }
}
