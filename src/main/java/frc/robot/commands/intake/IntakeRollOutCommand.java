package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeRollOutCommand extends StartEndCommand {
    private final IntakeSubsystem intakeSubsystem;

    public IntakeRollOutCommand(IntakeSubsystem intakeSubsystem, double speed) {
        super(() -> intakeSubsystem.rollOut(speed), intakeSubsystem::stop);
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }
}