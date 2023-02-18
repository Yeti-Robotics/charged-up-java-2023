package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.utils.CommandFactory;

import javax.inject.Inject;


public class SetArmPositionHandoffCommand extends CommandFactory {
    public class SetArmPositionHandoffImpl extends CommandBase {
        private final ArmSubsystem armSubsystem;

        public SetArmPositionHandoffImpl(ArmSubsystem armSubsystem) {
            this.armSubsystem = armSubsystem;
            // each subsystem used by the command must be passed into the
            // addRequirements() method (which takes a vararg of Subsystem)
            addRequirements(this.armSubsystem);
        }

        @Override
        public void initialize() {
            armSubsystem.setPosition(Constants.ArmConstants.ArmPositions.HANDOFF);
        }

        @Override
        public void execute() {}

        @Override
        public boolean isFinished() {
            // TODO: Make this return true when this Command no longer needs to run execute()
            return armSubsystem.isMotionFinished();
        }

        @Override
        public void end(boolean interrupted) {
            armSubsystem.engageBrake();
        }
    }

    private final ArmSubsystem armSubsystem;

    @Inject
    public SetArmPositionHandoffCommand(ArmSubsystem armSubsystem) {
        this.armSubsystem = armSubsystem;
    }

    @Override
    public CommandBase create() {
        return new SetArmPositionHandoffImpl(armSubsystem);
    }
}
