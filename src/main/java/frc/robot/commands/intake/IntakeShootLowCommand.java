package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.IntakeConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeShootLowCommand extends SequentialCommandGroup {
    public IntakeShootLowCommand(IntakeSubsystem intakeSubsystem, ArmSubsystem armSubsystem, ElevatorSubsystem elevatorSubsystem) {
        addCommands(
                new SetArmPositionCommand(armSubsystem, elevatorSubsystem, ArmConstants.ArmPositions.LOW).withTimeout(0.5),
                new IntakeRollInCommand(intakeSubsystem, 0.15).withTimeout(0.5),
                new IntakeRollOutCommand(intakeSubsystem, IntakeConstants.SHOOT_LOW_SPEED).withTimeout(0.3)
        );
    }
}