package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.IntakeConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeShootMidCommand extends SequentialCommandGroup {
    public IntakeShootMidCommand(IntakeSubsystem intakeSubsystem, ArmSubsystem armSubsystem, ElevatorSubsystem elevatorSubsystem) {
        addCommands(
                new SetArmPositionCommand(armSubsystem, elevatorSubsystem, ArmConstants.ArmPositions.SHOOT),
                new WaitCommand(1),
                new IntakeRollOutCommand(intakeSubsystem, IntakeConstants.SHOOT_MID_SPEED).withTimeout(1.0)
        );

        this.unless(() -> !elevatorSubsystem.isDown());
    }
}