package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.carriage.ConeOutCubeInCommand;
import frc.robot.constants.*;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.carriage.ConeInCubeOutCommand;
import frc.robot.commands.elevator.SetElevatorPositionCommand;
import frc.robot.commands.intake.IntakeRollOutCommand;
import frc.robot.subsystems.*;

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
                new IntakeRollOutCommand(intakeSubsystem, IntakeConstants.INTAKE_SPEED).withTimeout(1.0)
                        .alongWith(new ConeOutCubeInCommand(carriageSubsystem)).withTimeout(2.5)
        );
    }
}
