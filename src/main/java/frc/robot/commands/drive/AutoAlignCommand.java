package frc.robot.commands.drive;

import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.Limelight;

public class AutoAlignCommand extends CommandBase
{
    private final DrivetrainSubsystem drivetrainSubsystem;
    private final SwerveAutoBuilder autoBuilder;
    private final Limelight limelight;


    public AutoAlignCommand(DrivetrainSubsystem drivetrainSubsystem, SwerveAutoBuilder autoBuilder, Limelight limelight){
        this.drivetrainSubsystem=drivetrainSubsystem;
        this.autoBuilder = autoBuilder;
        this.limelight = limelight;
    }
    @Override
    public void initialize(){
        double initPitch = limelight.getPitch();
        double initYaw = l.getYaw();
        Pose2d robotPose = drivetrainSubsystem.getPose();

    }

    @Override
    public void execute() {
        double targetTranslation=Limelight.getTranslation()
    }
}
