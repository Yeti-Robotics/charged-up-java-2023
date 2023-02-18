package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.ArmSubsystem;

import javax.inject.Inject;


public class SetArmPositionCommand extends CommandBase {
    private final ArmSubsystem armSubsystem;
    @Inject
    public SetArmPositionCommand(ArmSubsystem armSubsystem) {
        this.armSubsystem = armSubsystem;

        addRequirements(armSubsystem);
    }


    @Override
    public void initialize() {
        armSubsystem.disengageBrake();

        double position = armSubsystem.getAngle();
        if (Math.abs(position - ArmConstants.ArmPositions.UP.angle) <= Constants.ArmConstants.ANGLE_TOLERANCE) {
            armSubsystem.setPosition(ArmConstants.ArmPositions.DOWN);
        }
        else if (Math.abs(position - ArmConstants.ArmPositions.HANDOFF.angle) <= Constants.ArmConstants.ANGLE_TOLERANCE) {
            armSubsystem.setPosition(ArmConstants.ArmPositions.DOWN);
        }
        else if (Math.abs(position - ArmConstants.ArmPositions.DOWN.angle) <= Constants.ArmConstants.ANGLE_TOLERANCE) {
            armSubsystem.setPosition(ArmConstants.ArmPositions.HANDOFF);
        } else {
            armSubsystem.setPosition(ArmConstants.ArmPositions.UP);
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