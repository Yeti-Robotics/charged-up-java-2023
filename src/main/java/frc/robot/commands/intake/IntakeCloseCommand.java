package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCloseCommand extends InstantCommand {
    private final IntakeSubsystem intakeSubsystem;

    public IntakeCloseCommand(IntakeSubsystem intakeSubsystem) {
        super(intakeSubsystem::intakeClose);
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }
}
