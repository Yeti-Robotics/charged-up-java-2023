package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.utils.CommandFactory;

import javax.inject.Inject;


public class IntakeCloseCommand extends CommandFactory {
    public static class IntakeClampImpl extends InstantCommand {
        private final IntakeSubsystem intakeSubsystem;
        public IntakeClampImpl(IntakeSubsystem intakeSubsystem) {
            super(intakeSubsystem::intakeClose);
            this.intakeSubsystem = intakeSubsystem;
            addRequirements(intakeSubsystem);
        }
    }

    private IntakeSubsystem intakeSubsystem;

    @Inject
    public IntakeCloseCommand(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
    }

    public CommandBase create() {
        return new IntakeClampImpl(intakeSubsystem);
    }
}
