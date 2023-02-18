package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.utils.CommandFactory;

import javax.inject.Inject;


public class ArmUpCommand extends CommandFactory {
    public class ArmUpImpl extends CommandBase {
        private final ArmSubsystem armSubsystem;

        public ArmUpImpl(ArmSubsystem armSubsystem) {
            this.armSubsystem = armSubsystem;
            addRequirements(armSubsystem);
        }

        @Override
        public void initialize() {}


        @Override
        public void execute() {
            armSubsystem.moveUp(ArmConstants.ARM_SPEED);
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

    private final ArmSubsystem armSubsystem;

    @Inject
    public ArmUpCommand(ArmSubsystem armSubsystem) {
        this.armSubsystem = armSubsystem;
    }

    public CommandBase create() {
        return new ArmUpImpl(armSubsystem);
    }
}
