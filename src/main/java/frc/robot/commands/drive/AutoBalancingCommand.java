package frc.robot.commands.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;

import javax.inject.Inject;


public class AutoBalancingCommand extends CommandBase {
private final DrivetrainSubsystem drivetrainSubsystem;
private final PIDController pidController;

    @Inject
    public AutoBalancingCommand(DrivetrainSubsystem drivetrainSubsystem, PIDController pidController) {
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.pidController = pidController;



        addRequirements(drivetrainSubsystem);
    }

    /**
     * The initial subroutine of a command.  Called once when the command is initially scheduled.
     */
    @Override
    public void initialize() {

    }

    /**
     * The main body of a command.  Called repeatedly while the command is scheduled.
     * (That is, it is called repeatedly until {@link #isFinished()}) returns true.)
     */
    @Override
    public void execute() {
        double val = MathUtil.clamp(
                pidController.calculate(
                        drivetrainSubsystem.getPitch().getDegrees(), Constants.AutoConstants.PITCH_SET_POINT), -.35, .35);
        drivetrainSubsystem.drive(new Translation2d(val, 0).times(Constants.DriveConstants.MAX_VELOCITY_METERS_PER_SECOND), 0);

    }

    /**
     * <p>
     * Returns whether this command has finished. Once a command finishes -- indicated by
     * this method returning true -- the scheduler will call its {@link #end(boolean)} method.
     * </p><p>
     * Returning false will result in the command never ending automatically. It may still be
     * cancelled manually or interrupted by another command. Hard coding this command to always
     * return true will result in the command executing once and finishing immediately. It is
     * recommended to use * {@link edu.wpi.first.wpilibj2.command.InstantCommand InstantCommand}
     * for such an operation.
     * </p>
     *
     * @return whether this command has finished.
     */
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
