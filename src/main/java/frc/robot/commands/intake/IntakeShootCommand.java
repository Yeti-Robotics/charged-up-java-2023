package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.utils.CommandFactory;

import javax.inject.Inject;

public class IntakeShootCommand extends CommandFactory {

    private IntakeSubsystem intakeSubsystem;
    private ArmSubsystem armSubsystem;
    @Inject
    public IntakeShootCommand(IntakeSubsystem intakeSubsystem, ArmSubsystem armSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        this.armSubsystem = armSubsystem;
    }

    public CommandBase create() {
        return Commands
                .runOnce(() -> armSubsystem.setPosition(Constants.ArmConstants.ArmPositions.SHOOT), armSubsystem)
                .andThen(Commands.waitUntil(armSubsystem::isMotionFinished))
                .andThen(Commands.runOnce(armSubsystem::engageBrake, armSubsystem))
                .andThen(Commands.runOnce(() -> intakeSubsystem.setSetPoint(2.0), intakeSubsystem))
                .withTimeout(1.0)
                .andThen(Commands.runOnce(intakeSubsystem::stop, intakeSubsystem));
    }

    public static class IntakeShootImpl extends CommandBase {
        private final IntakeSubsystem intakeSubsystem;
        private final ArmSubsystem armSubsystem;
        private boolean done;

        public IntakeShootImpl(IntakeSubsystem intakeSubsystem, ArmSubsystem armSubsystem) {
            this.intakeSubsystem = intakeSubsystem;
            this.armSubsystem = armSubsystem;
            addRequirements(intakeSubsystem, armSubsystem);
        }

        @Override
        public void initialize() {
            done = false;
            armSubsystem.setPosition(Constants.ArmConstants.ArmPositions.SHOOT);
        }

        @Override
        public void execute() {
            if (armSubsystem.isMotionFinished()) {
                intakeSubsystem.setSetPoint(2.0);
                done = true;
            }
        }

        @Override
        public boolean isFinished() {
            return done;
        }

        @Override
        public void end(boolean interrupted) {
            intakeSubsystem.stop();
        }
    }
}