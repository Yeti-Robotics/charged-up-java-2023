package frc.robot.commands.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;

import javax.inject.Inject;


public class AutoBalancingCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;
    private final PIDController pidController;

    public AutoBalancingCommand(DrivetrainSubsystem drivetrainSubsystem, PIDController pidController) {
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.pidController = pidController;

        this.pidController.setTolerance(2.0);
        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double val = -MathUtil.clamp(
                pidController.calculate(
                        drivetrainSubsystem.getPitch().getDegrees(), Constants.AutoConstants.PITCH_SET_POINT), -.35, .35);
        drivetrainSubsystem.drive(Constants.DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                ChassisSpeeds.fromFieldRelativeSpeeds(val, 0.0, 0.0, drivetrainSubsystem.getPose().getRotation())));

    }

    @Override
    public boolean isFinished() {
        return pidController.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        drivetrainSubsystem.drive(
                new SwerveModuleState(0, Rotation2d.fromDegrees(45)),
                new SwerveModuleState(0, Rotation2d.fromDegrees(-45)),
                new SwerveModuleState(0, Rotation2d.fromDegrees(-45)),
                new SwerveModuleState(0, Rotation2d.fromDegrees(45))
        );
    }
}
