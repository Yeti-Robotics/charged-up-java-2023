package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ElevatorSubsystem;

import javax.inject.Inject;


public class MoveElevatorDownCommand extends CommandBase {

    private final ElevatorSubsystem elevatorSubsystem;

    @Inject
    public MoveElevatorDownCommand(ElevatorSubsystem elevatorSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;
        addRequirements(elevatorSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() { elevatorSubsystem.elevatorDown(); }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
