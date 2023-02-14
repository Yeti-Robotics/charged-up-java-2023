package frc.robot.commands.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;

import javax.inject.Inject;

public class SwerveLockCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;

    @Inject
    public SwerveLockCommand(DrivetrainSubsystem drivetrainSubsystem) {
        this.drivetrainSubsystem = drivetrainSubsystem;

        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void initialize() {
        ChassisSpeeds chassisSpeeds = drivetrainSubsystem.getChassisSpeeds();
        if (Math.abs(chassisSpeeds.vxMetersPerSecond) > 0.5 || Math.abs(chassisSpeeds.vyMetersPerSecond) > 0.5) {
            this.cancel();
        }
    }

    @Override
    public void execute() {
        drivetrainSubsystem.drive(
                new SwerveModuleState(0, Rotation2d.fromDegrees(45)),
                new SwerveModuleState(0, Rotation2d.fromDegrees(-45)),
                new SwerveModuleState(0, Rotation2d.fromDegrees(-45)),
                new SwerveModuleState(0, Rotation2d.fromDegrees(45))
        );
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
