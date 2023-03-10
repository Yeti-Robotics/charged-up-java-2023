package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.carriage.CarriageFlipInCommand;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.CarriageConstants;
import frc.robot.constants.ElevatorConstants.ElevatorPositions;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class SetElevatorDownCommand extends SequentialCommandGroup {
    public SetElevatorDownCommand(ElevatorSubsystem elevatorSubsystem, ArmSubsystem armSubsystem, CarriageSubsystem carriageSubsystem) {

        addRequirements(elevatorSubsystem, armSubsystem, carriageSubsystem);
        addCommands(
                new ConditionalCommand(new CarriageFlipInCommand(carriageSubsystem), new InstantCommand(), () -> carriageSubsystem.getCarriagePosition() == CarriageConstants.CarriagePositions.FLIPPED),
                new SetArmPositionCommand(armSubsystem, elevatorSubsystem, ArmConstants.ArmPositions.UP),
                new StartEndCommand(() -> elevatorSubsystem.setPosition(ElevatorPositions.DOWN), elevatorSubsystem::stop)
                        .until(() -> elevatorSubsystem.getElevatorEncoder() < 500)
        );
    }
}