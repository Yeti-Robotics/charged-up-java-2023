package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.IntakeConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeShootMidCommand extends SequentialCommandGroup {
    public IntakeShootMidCommand(IntakeSubsystem intakeSubsystem, ArmSubsystem armSubsystem, ElevatorSubsystem elevatorSubsystem) {
        addCommands(
                new SetArmPositionCommand(armSubsystem, elevatorSubsystem, ArmConstants.ArmPositions.SHOOT).withTimeout(0.3),
                new IntakeRollOutCommand(intakeSubsystem, IntakeConstants.SHOOT_MID_SPEED).withTimeout(0.3)
        );
    }
}