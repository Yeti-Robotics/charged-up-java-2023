package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.IntakeSubsystem;

import javax.inject.Inject;


public class IntakeRollInCommand extends RunCommand {


    private final IntakeSubsystem intakeSubsystem;
    @Inject
    public IntakeRollInCommand(IntakeSubsystem intakeSubsystem) {
        super(intakeSubsystem::rollIn);
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }
}
