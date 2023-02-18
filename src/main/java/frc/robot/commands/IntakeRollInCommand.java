package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.IntakeSubsystem;

import javax.inject.Inject;


public class IntakeRollInCommand extends StartEndCommand {
    private final IntakeSubsystem intakeSubsystem;
    @Inject
    public IntakeRollInCommand(IntakeSubsystem intakeSubsystem) {
        super(intakeSubsystem::rollIn, intakeSubsystem::stop);
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }
}
