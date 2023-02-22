package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeShootCommand extends SequentialCommandGroup {

    private IntakeSubsystem intakeSubsystem;
    private ArmSubsystem armSubsystem;

    public IntakeShootCommand(IntakeSubsystem intakeSubsystem, ArmSubsystem armSubsystem) {
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