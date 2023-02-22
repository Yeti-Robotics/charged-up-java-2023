package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ElevatorConstants.ElevatorPositions;
import frc.robot.subsystems.ElevatorSubsystem;


public class SetElevatorPositionCommand extends CommandBase {
    private final ElevatorSubsystem elevatorSubsystem;
    private final ElevatorPositions position;

    public SetElevatorPositionCommand(ElevatorSubsystem elevatorSubsystem, ElevatorPositions position) {
        this.elevatorSubsystem = elevatorSubsystem;
        this.position = position;

        addRequirements(this.elevatorSubsystem);
    }

    @Override
    public void initialize() {
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