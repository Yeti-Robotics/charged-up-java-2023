package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

import javax.inject.Inject;


public class IntakeShootCommand extends InstantCommand {

    private final IntakeSubsystem intakeSubsystem;
    private double setpoint;
    @Inject
    public IntakeShootCommand(IntakeSubsystem intakeSubsystem) {
        super(() -> intakeSubsystem.setSetPoint(2.0));
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }
}
