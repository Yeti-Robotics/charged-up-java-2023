package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.utils.CommandFactory;

import javax.inject.Inject;

public class IntakeShootCommand extends CommandFactory {

    public static class IntakeShootImpl extends InstantCommand {
        private final IntakeSubsystem intakeSubsystem;
        private double setpoint;

        public IntakeShootImpl(IntakeSubsystem intakeSubsystem) {
            super(() -> intakeSubsystem.setSetPoint(2.0));
            this.intakeSubsystem = intakeSubsystem;
            addRequirements(intakeSubsystem);
        }
    }

        private IntakeSubsystem intakeSubsystem;

        public CommandBase create(){
            return new IntakeShootImpl(intakeSubsystem);
        }


        @Inject
        public IntakeShootCommand(IntakeSubsystem intakeSubsystem) {

            this.intakeSubsystem = intakeSubsystem;

    }
}