package frc.robot.commands.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.Limelight;

import java.util.function.DoubleSupplier;


public class PIDAlignCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;
    private PIDController rotationPID;
    private PIDController yController;

    private DoubleSupplier xSpeed;

    public PIDAlignCommand(DrivetrainSubsystem drivetrainSubsystem, DoubleSupplier xSpeed){
        this.drivetrainSubsystem = drivetrainSubsystem;
        rotationPID = new PIDController(AutoConstants.ROTATION_P, AutoConstants.ROTATION_I, AutoConstants. ROTATION_D);
        rotationPID.enableContinuousInput(AutoConstants.MINIMUM_ANGLE, AutoConstants.MAXIMUM_ANGLE);
        yController = new PIDController(AutoConstants.TAPE_P, AutoConstants.TAPE_I, AutoConstants.TAPE_D);
        this.xSpeed = xSpeed;
        addRequirements(this.drivetrainSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(!Limelight.hasTarget() == true){
            this.cancel();
            return;
        }
        double currentHeading = MathUtil.inputModulus(
                drivetrainSubsystem.getGyroscopeHeading().getDegrees(),
                -180, 180);
        double rotation = rotationPID.calculate(currentHeading, 180);
        double ySpeed = -yController.calculate(Limelight.getTx(), 0.0);

        ChassisSpeeds chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
                xSpeed.getAsDouble(), ySpeed, rotation, drivetrainSubsystem.getGyroscopeHeading()
        );

        drivetrainSubsystem.drive(
                DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                        chassisSpeeds
                )
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
