package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.utils.CommandFactory;

import javax.inject.Inject;


public class SetElevatorPositionMidCommand extends CommandFactory {
    public class SetElevatorPositionMidImpl extends CommandBase {
        private final ElevatorSubsystem elevatorSubsystem;

        public SetElevatorPositionMidImpl(ElevatorSubsystem elevatorSubsystem) {
            this.elevatorSubsystem = elevatorSubsystem;
            // each subsystem used by the command must be passed into the
            // addRequirements() method (which takes a vararg of Subsystem)
            addRequirements(this.elevatorSubsystem);
        }

        @Override
        public void initialize() {
            elevatorSubsystem.setPosition(Constants.ElevatorConstants.ElevatorPositions.LEVEL_TWO);
        }

        @Override
        public void execute() {

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

    private final ElevatorSubsystem elevatorSubsystem;

    @Inject
    public SetElevatorPositionMidCommand(ElevatorSubsystem elevatorSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;
    }

    @Override
    public CommandBase create() {
        return new SetElevatorPositionMidImpl(elevatorSubsystem);
    }
}
