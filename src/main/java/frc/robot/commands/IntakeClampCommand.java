package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

import javax.inject.Inject;


public class IntakeClampCommand extends InstantCommand {

    private final IntakeSubsystem intakeSubsystem;
    @Inject
    public IntakeClampCommand(IntakeSubsystem intakeSubsystem) {
        super(intakeSubsystem::intakeClamp);
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }
}
