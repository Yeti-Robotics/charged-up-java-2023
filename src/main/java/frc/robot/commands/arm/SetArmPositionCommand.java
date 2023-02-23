package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ArmConstants.ArmPositions;
import frc.robot.commands.elevator.SetElevatorDownCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class SetArmPositionCommand extends SequentialCommandGroup {
    private ArmPositions position;

    public SetArmPositionCommand(ArmSubsystem armSubsystem, ElevatorSubsystem elevatorSubsystem, ArmPositions position) {
        addCommands(
                new SetElevatorDownCommand(elevatorSubsystem).until(elevatorSubsystem::isDown).andThen(
                        new InstantCommand(armSubsystem::disengageBrake),
                        new InstantCommand(() -> armSubsystem.setPosition(position)).withTimeout(2),
                        new InstantCommand(armSubsystem::engageBrake)
                )
        );
    }

}
