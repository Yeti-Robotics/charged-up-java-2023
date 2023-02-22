package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class ToggleIntakeCommand extends InstantCommand {
    private final IntakeSubsystem intakeSubsystem;

    public ToggleIntakeCommand(IntakeSubsystem intakeSubsystem) {
        super(intakeSubsystem::toggle);
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }
}
