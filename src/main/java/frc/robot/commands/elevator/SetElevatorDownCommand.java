package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants.ElevatorPositions;
import frc.robot.subsystems.ElevatorSubsystem;


public class SetElevatorDownCommand extends CommandBase {
    private final ElevatorSubsystem elevatorSubsystem;
    public SetElevatorDownCommand(ElevatorSubsystem elevatorSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;

        addRequirements(this.elevatorSubsystem);
    }

    @Override
    public void initialize() {
        elevatorSubsystem.setPosition(ElevatorPositions.DOWN);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return elevatorSubsystem.getElevatorEncoder() < 500;
    }

    @Override
    public void end(boolean interrupted) {
        elevatorSubsystem.stop();
    }
}