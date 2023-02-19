package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.utils.CommandFactory;

import javax.inject.Inject;


public class SetElevatorPositionConeHandoffCommand extends CommandFactory {
    public class SetElevatorPositionConeHandoffImpl extends CommandBase {
        private final ElevatorSubsystem elevatorSubsystem;
        public SetElevatorPositionConeHandoffImpl(ElevatorSubsystem elevatorSubsystem) {
            this.elevatorSubsystem = elevatorSubsystem;
            // each subsystem used by the command must be passed into the
            // addRequirements() method (which takes a vararg of Subsystem)
            addRequirements(this.elevatorSubsystem);
        }

        @Override
        public void initialize() {
            elevatorSubsystem.setPosition(Constants.ElevatorConstants.ElevatorPositions.CONE_HANDOFF);
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
    public SetElevatorPositionConeHandoffCommand(ElevatorSubsystem elevatorSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;
    }

    @Override
    public CommandBase create() {
        return new SetElevatorPositionConeHandoffImpl(elevatorSubsystem);
    }
}
