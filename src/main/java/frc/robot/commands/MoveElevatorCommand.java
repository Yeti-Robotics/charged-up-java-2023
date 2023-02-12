package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.ElevatorSubsystem;

import javax.inject.Inject;


public class MoveElevatorCommand extends CommandBase {

    private boolean stop;
    private final ElevatorSubsystem elevatorSubsystem;

    @Inject
    public MoveElevatorCommand(ElevatorSubsystem elevatorSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;
        addRequirements(elevatorSubsystem);
    }

    @Override
    public void initialize() {
        if (elevatorSubsystem.getLevel() != -1) {
            stop = false;
        }
        else {
            stop = true;
        }

    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return elevatorSubsystem.motionMagicOnTarget() || stop;
    }

    @Override
    public void end(boolean interrupted) {
        if (elevatorSubsystem.getLevel() == -1) {
            elevatorSubsystem.elevatorOff();
            //need to check how to disable, dont think we have pid
        }
        else {
            elevatorSubsystem.elevatorStop();
        }

    }
}
