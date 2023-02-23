package frc.robot.commands.elevator;

import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ElevatorConstants.ElevatorPositions;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;


public class CycleElevatorPositionCommand extends CommandBase {
    private final ElevatorSubsystem elevatorSubsystem;
    private final ArmSubsystem armSubsystem;
    private ElevatorPositions position;

    public CycleElevatorPositionCommand(ElevatorSubsystem elevatorSubsystem, ArmSubsystem armSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;
        this.armSubsystem = armSubsystem;

        addRequirements(elevatorSubsystem);
    }

    @Override
    public void initialize() {
        ElevatorPositions currentPosition = elevatorSubsystem.getPosition();

        if (currentPosition == ElevatorPositions.LEVEL_TWO) {
            position = ElevatorPositions.UP;
        } else {
            position = ElevatorPositions.LEVEL_TWO;
        }

        new SetElevatorPositionCommand(elevatorSubsystem, armSubsystem, position).schedule();
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