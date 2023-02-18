package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.ArmSubsystem;

import javax.inject.Inject;


public class ToggleArmPositionCommand extends CommandBase {
    private final ArmSubsystem armSubsystem;

    @Inject
    public ToggleArmPositionCommand(ArmSubsystem armSubsystem) {
        this.armSubsystem = armSubsystem;

        addRequirements(armSubsystem);
    }


    @Override
    public void initialize() {
        armSubsystem.disengageBrake();

        ArmConstants.ArmPositions position = armSubsystem.getArmPosition();
        if (position == ArmConstants.ArmPositions.HANDOFF) {
            armSubsystem.setPosition(ArmConstants.ArmPositions.DOWN);
        } else if (position == ArmConstants.ArmPositions.DOWN) {
            armSubsystem.setPosition(ArmConstants.ArmPositions.UP);
        } else {
            armSubsystem.setPosition(ArmConstants.ArmPositions.DOWN);
        }
    }


    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return armSubsystem.isMotionFinished();
    }


    @Override
    public void end(boolean interrupted) {
        armSubsystem.engageBrake();
    }
}
