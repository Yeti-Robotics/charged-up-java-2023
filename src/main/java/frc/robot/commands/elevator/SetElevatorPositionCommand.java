package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.Constants.ElevatorConstants.ElevatorPositions;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;


public class SetElevatorPositionCommand extends SequentialCommandGroup {

    public SetElevatorPositionCommand(ElevatorSubsystem elevatorSubsystem, ArmSubsystem armSubsystem, ElevatorPositions position) {
        addCommands(
                new SetArmPositionCommand(armSubsystem, elevatorSubsystem, Constants.ArmConstants.ArmPositions.UP).until(
                        armSubsystem::isUP).andThen(
                        new InstantCommand(() -> elevatorSubsystem.setPosition(position))
                )


        );
    }
}