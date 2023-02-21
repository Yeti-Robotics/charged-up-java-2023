package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.utils.CommandFactory;

import javax.inject.Inject;

public class IntakeShootCommand extends CommandFactory {

    private IntakeSubsystem intakeSubsystem;
    private ArmSubsystem armSubsystem;
    @Inject
    public IntakeShootCommand(IntakeSubsystem intakeSubsystem, ArmSubsystem armSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        this.armSubsystem = armSubsystem;
    }

    public CommandBase create() {
//        return Commands
//                .runOnce(() -> armSubsystem.disengageBrake())
//                .andThen(() -> armSubsystem.setPosition(Constants.ArmConstants.ArmPositions.SHOOT), armSubsystem)
//                .andThen(Commands.waitSeconds(1))
//                .andThen(armSubsystem::engageBrake, armSubsystem)
//                .andThen(() -> intakeSubsystem.roll(-0.6), intakeSubsystem)
//                .withTimeout(0.5)
//                .andThen(intakeSubsystem::stop, intakeSubsystem);
        return new IntakeShootImpl(intakeSubsystem, armSubsystem);
    }

    public static class IntakeShootImpl extends SequentialCommandGroup {
        private final IntakeSubsystem intakeSubsystem;
        private final ArmSubsystem armSubsystem;
        private boolean done;

        public IntakeShootImpl(IntakeSubsystem intakeSubsystem, ArmSubsystem armSubsystem) {
            this.intakeSubsystem = intakeSubsystem;
            this.armSubsystem = armSubsystem;
            addCommands(
                    new InstantCommand(armSubsystem::disengageBrake, armSubsystem),
                    new InstantCommand(() -> armSubsystem.setPosition(Constants.ArmConstants.ArmPositions.SHOOT), armSubsystem),
                    new WaitCommand(1),
                    new InstantCommand(armSubsystem::engageBrake, armSubsystem),
                    // Cube speed -0.55
                    // Cone -0.62
                    new InstantCommand(() -> intakeSubsystem.roll(-0.55), intakeSubsystem),
                    new WaitCommand(1),
                    new InstantCommand(intakeSubsystem::stop, intakeSubsystem)
            );
        }
    }
}