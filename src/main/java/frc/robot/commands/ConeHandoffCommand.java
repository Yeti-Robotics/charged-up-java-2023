package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.carriage.ConeInCubeOutCommand;
import frc.robot.commands.elevator.SetElevatorPositionCommand;
import frc.robot.constants.ElevatorConstants;
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
                new SetElevatorPositionCommand(elevatorSubsystem, armSubsystem, ElevatorConstants.ElevatorPositions.CONE_HANDOFF)
                        .alongWith(new WaitCommand(0.5)),
                new SetArmPositionCommand(armSubsystem, elevatorSubsystem, ArmConstants.ArmPositions.HANDOFF)
                        .alongWith(new WaitCommand(1.0)),
                new InstantCommand(intakeSubsystem::intakeOpen, intakeSubsystem),
                new ConeInCubeOutCommand(carriageSubsystem).withTimeout(2.0)
        );

        this.unless(() -> !armSubsystem.isUP());
    }
}
