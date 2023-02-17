package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.IntakeSubsystem;

import javax.inject.Inject;


public class IntakeRollOutCommand extends RunCommand {

    public final IntakeSubsystem intakeSubsystem;
    @Inject
    public IntakeRollOutCommand(IntakeSubsystem intakeSubsystem) {
        super(intakeSubsystem::rollOut);
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }
}
