package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.constants.ArmConstants;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.carriage.ConeInCubeOutCommand;
import frc.robot.commands.elevator.SetElevatorPositionCommand;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.LEDConstants;
import frc.robot.subsystems.*;

public class ConeHandoffCommand extends SequentialCommandGroup {
    public ConeHandoffCommand(
            ArmSubsystem armSubsystem,
            IntakeSubsystem intakeSubsystem,
            ElevatorSubsystem elevatorSubsystem,
            CarriageSubsystem carriageSubsystem,
            LEDSubsystem ledSubsystem) {
        addCommands(
                new InstantCommand(() -> ledSubsystem.setRGB(ledSubsystem.getBufferLength(), 92,33,254)),
                new SetElevatorPositionCommand(elevatorSubsystem, armSubsystem, ElevatorConstants.ElevatorPositions.CONE_HANDOFF)
                        .alongWith(new WaitCommand(0.5)),
                new SetArmPositionCommand(armSubsystem, elevatorSubsystem, ArmConstants.ArmPositions.HANDOFF)
                        .alongWith(new WaitCommand(1.0)),
                new InstantCommand(intakeSubsystem::intakeOpen, intakeSubsystem),
                new ConeInCubeOutCommand(carriageSubsystem).withTimeout(2.0),
        new InstantCommand(() -> ledSubsystem.setRGB(ledSubsystem.getBufferLength(), LEDConstants.DEFAULT_R,LEDConstants.DEFAULT_G,LEDConstants.DEFAULT_B)));

    }
}
