package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.ElevatorSubsystem;

import javax.inject.Inject;


public class DecrementElevatorLevelCommand extends CommandBase {

    private final ElevatorSubsystem elevatorSubsystem;



    @Inject
    public DecrementElevatorLevelCommand(ElevatorSubsystem elevatorSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;
        addRequirements(elevatorSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (elevatorSubsystem.getLevel() > -1){
            elevatorSubsystem.decrementLevel();
        }

    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return true;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
