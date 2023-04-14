package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.IntakeConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeShootHighCommand extends SequentialCommandGroup {
    public IntakeShootHighCommand(IntakeSubsystem intakeSubsystem, ArmSubsystem armSubsystem, ElevatorSubsystem elevatorSubsystem) {
        addCommands(
                new SetArmPositionCommand(armSubsystem, elevatorSubsystem, ArmConstants.ArmPositions.SHOOT),
                new InstantCommand(intakeSubsystem::intakeClose),
                new IntakeRollInCommand(intakeSubsystem, 0.1).withTimeout(1),
                new IntakeRollOutCommand(intakeSubsystem, IntakeConstants.SHOOT_HIGH_SPEED).withTimeout(0.5)

        );
    }
}