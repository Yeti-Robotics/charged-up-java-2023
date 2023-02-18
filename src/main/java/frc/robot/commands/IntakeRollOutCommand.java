package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.utils.CommandFactory;

import javax.inject.Inject;

public class IntakeRollOutCommand extends CommandFactory {

    public static class IntakeRollOutImpl extends StartEndCommand {
        private final IntakeSubsystem intakeSubsystem;


        public IntakeRollOutImpl(IntakeSubsystem intakeSubsystem) {
            super(intakeSubsystem::rollIn, intakeSubsystem::stop);
            this.intakeSubsystem = intakeSubsystem;
            addRequirements(intakeSubsystem);
        }
    }

    private IntakeSubsystem intakeSubsystem;

    @Inject
    public IntakeRollOutCommand(IntakeSubsystem intakeSubsystem){
        this.intakeSubsystem = intakeSubsystem;
    }

    public CommandBase create() {
        return new IntakeRollOutImpl(intakeSubsystem);
    }
}