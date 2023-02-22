package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.carriage.ConeInCubeOutCommand;
import frc.robot.commands.elevator.SetElevatorPositionCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class ConeHandoffCommand extends SequentialCommandGroup {
    public ConeHandoffCommand(
            ArmSubsystem armSubsystem,
            IntakeSubsystem intakeSubsystem,
            ElevatorSubsystem elevatorSubsystem,
            CarriageSubsystem carriageSubsystem) {
        addCommands(
                new SetElevatorPositionCommand(elevatorSubsystem, Constants.ElevatorConstants.ElevatorPositions.CONE_HANDOFF),
                new SetArmPositionCommand(armSubsystem, Constants.ArmConstants.ArmPositions.HANDOFF),
                new InstantCommand(intakeSubsystem::intakeOpen, intakeSubsystem)
                        .alongWith(new ConeInCubeOutCommand(carriageSubsystem)).withTimeout(2.5),
                new SetArmPositionCommand(armSubsystem, Constants.ArmConstants.ArmPositions.UP)
        );
    }
}
