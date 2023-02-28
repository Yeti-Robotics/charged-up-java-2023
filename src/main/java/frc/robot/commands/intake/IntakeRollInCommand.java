package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeRollInCommand extends StartEndCommand {
    private final IntakeSubsystem intakeSubsystem;

    public IntakeRollInCommand(IntakeSubsystem intakeSubsystem, double speed) {
        super(() -> intakeSubsystem.rollIn(speed), intakeSubsystem::stop);
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }
}