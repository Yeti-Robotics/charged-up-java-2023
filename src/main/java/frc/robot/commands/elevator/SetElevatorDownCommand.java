package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.ElevatorConstants.ElevatorPositions;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class SetElevatorDownCommand extends SequentialCommandGroup {
    public SetElevatorDownCommand(ElevatorSubsystem elevatorSubsystem, ArmSubsystem armSubsystem) {

        addRequirements(elevatorSubsystem, armSubsystem);
        addCommands(
                new ConditionalCommand(new SetArmPositionCommand(armSubsystem, elevatorSubsystem, ArmConstants.ArmPositions.STOWED), new InstantCommand(), () -> armSubsystem.getArmPosition() != ArmConstants.ArmPositions.STOWED),
                new StartEndCommand(() -> elevatorSubsystem.setPosition(ElevatorPositions.DOWN), elevatorSubsystem::stop)
                        .until(() -> elevatorSubsystem.getElevatorEncoder() < 500)

                );
    }
}