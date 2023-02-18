package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.ArmSubsystem;



public class ArmDownCommand extends CommandBase {

    private final ArmSubsystem armSubsystem;

    public ArmDownCommand(ArmSubsystem armSubsystem) {
        this.armSubsystem = armSubsystem;
        addRequirements(armSubsystem);
    }

    @Override
    public void initialize() {

    }


    @Override
    public void execute() {
        armSubsystem.moveDown(ArmConstants.ARM_SPEED);
    }


    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
        armSubsystem.stop();
    }
}
