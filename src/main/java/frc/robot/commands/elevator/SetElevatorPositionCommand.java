package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants.ElevatorPositions;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;


public class SetElevatorPositionCommand extends CommandBase {
    private final ElevatorSubsystem elevatorSubsystem;
    private final ArmSubsystem armSubsystem;
    private final ElevatorPositions position;

    public SetElevatorPositionCommand(ElevatorSubsystem elevatorSubsystem, ArmSubsystem armSubsystem, ElevatorPositions position) {
        this.elevatorSubsystem = elevatorSubsystem;
        this.armSubsystem = armSubsystem;
        this.position = position;

        addRequirements(elevatorSubsystem);
    }

    @Override
    public void initialize() {
//        if (!armSubsystem.isUp() && position != ElevatorPositions.DOWN) {
//            this.cancel();
//            return;
//        }

        elevatorSubsystem.setPosition(position);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }
}