package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ElevatorSubsystem;


public class MoveElevatorUpCommand extends CommandBase {

    private final ElevatorSubsystem elevatorSubsystem;
    public MoveElevatorUpCommand(ElevatorSubsystem elevatorSubsystem) {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        this.elevatorSubsystem = elevatorSubsystem;
        addRequirements(elevatorSubsystem );
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        elevatorSubsystem.elevatorUp();
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
