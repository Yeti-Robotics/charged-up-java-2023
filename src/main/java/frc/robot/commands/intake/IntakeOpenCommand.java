package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeOpenCommand extends InstantCommand {
    private final IntakeSubsystem intakeSubsystem;

    public IntakeOpenCommand(IntakeSubsystem intakeSubsystem) {
        super(intakeSubsystem::intakeOpen);
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }
}
