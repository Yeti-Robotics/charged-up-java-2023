package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.IntakeSubsystem;

import javax.inject.Inject;


public class IntakeShootCommand extends StartEndCommand {

    private final IntakeSubsystem intakeSubsystem;
    private double setpoint;
    @Inject
    public IntakeShootCommand(IntakeSubsystem intakeSubsystem) {
        super(() -> intakeSubsystem.setSetPoint(3000), () -> intakeSubsystem.setSetPoint(0));
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }
}
