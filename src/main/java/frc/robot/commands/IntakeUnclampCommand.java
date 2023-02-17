package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;
import javax.inject.Inject;

public class IntakeUnclampCommand extends InstantCommand {

    private final IntakeSubsystem intakeSubsystem;
    @Inject
    public IntakeUnclampCommand(IntakeSubsystem intakeSubsystem) {
        super(intakeSubsystem::intakeUnclamp);
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }
}
