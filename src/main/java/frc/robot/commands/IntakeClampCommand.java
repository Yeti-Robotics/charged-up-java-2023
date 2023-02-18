package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.utils.CommandFactory;

import javax.inject.Inject;


public class IntakeClampCommand extends CommandFactory {
    public static class IntakeClampImpl extends InstantCommand {
        private final IntakeSubsystem intakeSubsystem;
        public IntakeClampImpl(IntakeSubsystem intakeSubsystem) {
            super(intakeSubsystem::intakeClamp);
            this.intakeSubsystem = intakeSubsystem;
            addRequirements(intakeSubsystem);
        }
    }

    private IntakeSubsystem intakeSubsystem;

    @Inject
    public IntakeClampCommand(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
    }

    public CommandBase create() {
        return new IntakeClampImpl(intakeSubsystem);
    }
}
