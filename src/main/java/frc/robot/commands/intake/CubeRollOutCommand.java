package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.utils.CommandFactory;

import javax.inject.Inject;

public class CubeRollOutCommand extends CommandFactory {

    public static class CubeRollOutImpl extends StartEndCommand {
        private final IntakeSubsystem intakeSubsystem;
        public CubeRollOutImpl(IntakeSubsystem intakeSubsystem) {
            super(() -> intakeSubsystem.roll(0.05), intakeSubsystem::stop);
            this.intakeSubsystem = intakeSubsystem;
            addRequirements(intakeSubsystem);
        }
    }

    private IntakeSubsystem intakeSubsystem;

    @Inject
    public CubeRollOutCommand(IntakeSubsystem intakeSubsystem){
        this.intakeSubsystem = intakeSubsystem;
    }

    public CommandBase create() {
        return new CubeRollOutImpl(intakeSubsystem);
    }
}