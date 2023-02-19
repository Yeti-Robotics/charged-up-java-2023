package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.utils.CommandFactory;

import javax.inject.Inject;

public class SetArmPositionCommand extends CommandFactory {
    public class SetArmPositionImpl extends CommandBase {
        private final ArmSubsystem armSubsystem;

        private final Timer timer;
        public SetArmPositionImpl(ArmSubsystem armSubsystem) {
            this.armSubsystem = armSubsystem;
            timer = new Timer();
            timer.start();
            addRequirements(armSubsystem);
        }


        @Override
        public void initialize() {
            timer.reset();
            armSubsystem.disengageBrake();

            ArmConstants.ArmPositions position = armSubsystem.getArmPosition();
            if (position == ArmConstants.ArmPositions.UP) {
                armSubsystem.setPosition(ArmConstants.ArmPositions.DOWN);
            }
            else if (position == ArmConstants.ArmPositions.HANDOFF) {
                armSubsystem.setPosition(ArmConstants.ArmPositions.UP);
            } else {
                armSubsystem.setPosition(ArmConstants.ArmPositions.UP);
            }
        }

        @Override
        public void execute() {}

        @Override
        public boolean isFinished() {
            return armSubsystem.isMotionFinished() || timer.hasElapsed(5.0);
        }


        @Override
        public void end(boolean interrupted) {
            armSubsystem.engageBrake();
        }
    }

    private final ArmSubsystem armSubsystem;

    @Inject
    public SetArmPositionCommand(ArmSubsystem armSubsystem) {
        this.armSubsystem = armSubsystem;
    }

    @Override
    public CommandBase create() {
        return new SetArmPositionImpl(armSubsystem);
    }
}
