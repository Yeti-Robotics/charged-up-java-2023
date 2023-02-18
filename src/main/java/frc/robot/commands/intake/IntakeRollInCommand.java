package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.utils.CommandFactory;

import javax.inject.Inject;

public class IntakeRollInCommand extends CommandFactory {

    public static class IntakeRollInImpl extends StartEndCommand {
        private final IntakeSubsystem intakeSubsystem;
        public IntakeRollInImpl(IntakeSubsystem intakeSubsystem) {
            super(intakeSubsystem::rollIn, intakeSubsystem::stop);
            this.intakeSubsystem = intakeSubsystem;
            addRequirements(intakeSubsystem);
        }
    }

    private IntakeSubsystem intakeSubsystem;

    @Inject
    public IntakeRollInCommand(IntakeSubsystem intakeSubsystem){
        this.intakeSubsystem = intakeSubsystem;
    }

    public CommandBase create() {
        return new IntakeRollInImpl(intakeSubsystem);
    }
}