package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeRollInCommand extends StartEndCommand {
    private final IntakeSubsystem intakeSubsystem;

    public IntakeRollInCommand(IntakeSubsystem intakeSubsystem) {
        super(intakeSubsystem::rollIn, intakeSubsystem::stop);
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }
}