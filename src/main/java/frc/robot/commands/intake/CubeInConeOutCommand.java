package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class CubeInConeOutCommand extends StartEndCommand {
    private final IntakeSubsystem intakeSubsystem;

    public CubeInConeOutCommand(IntakeSubsystem intakeSubsystem, double speed) {
        super(() -> intakeSubsystem.rollOut(speed), intakeSubsystem::stop);
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }
}