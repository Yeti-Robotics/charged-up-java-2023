package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.IntakeSubsystem;

import javax.inject.Inject;


public class IntakeRollOutCommand extends StartEndCommand {

    public final IntakeSubsystem intakeSubsystem;
    @Inject
    public IntakeRollOutCommand(IntakeSubsystem intakeSubsystem) {
        super(intakeSubsystem::rollOut, intakeSubsystem::stop);
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }
}
