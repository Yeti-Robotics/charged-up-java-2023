package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.carriage.ConeInCubeOutCommand;
import frc.robot.commands.elevator.SetElevatorPositionCommand;
import frc.robot.commands.intake.IntakeRollOutCommand;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class CubeHandoffCommand extends SequentialCommandGroup {
    public CubeHandoffCommand(
            ArmSubsystem armSubsystem,
            IntakeSubsystem intakeSubsystem,
            ElevatorSubsystem elevatorSubsystem,
            CarriageSubsystem carriageSubsystem) {
        addCommands(
                new SetElevatorPositionCommand(elevatorSubsystem, armSubsystem, ElevatorConstants.ElevatorPositions.CONE_HANDOFF),
                new SetArmPositionCommand(armSubsystem, elevatorSubsystem, ArmConstants.ArmPositions.HANDOFF),
                new InstantCommand(intakeSubsystem::intakeOpen, intakeSubsystem),
                new IntakeRollOutCommand(intakeSubsystem).withTimeout(1.0)
                        .alongWith(new ConeInCubeOutCommand(carriageSubsystem)).withTimeout(2.5)
        );
    }
}
