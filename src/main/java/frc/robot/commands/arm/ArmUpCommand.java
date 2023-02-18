package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.ArmSubsystem;



public class ArmUpCommand extends CommandBase {

    private final ArmSubsystem armSubsystem;

    public ArmUpCommand(ArmSubsystem armSubsystem) {
        this.armSubsystem = armSubsystem;
        addRequirements(armSubsystem);
    }

    @Override
    public void initialize() {
        armSubsystem.moveUp(ArmConstants.ARM_SPEED);
    }


    @Override
    public void execute() {
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
