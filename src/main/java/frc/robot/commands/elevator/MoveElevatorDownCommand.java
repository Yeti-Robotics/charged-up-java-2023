package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.utils.CommandFactory;

import javax.inject.Inject;


public class MoveElevatorDownCommand extends CommandFactory {
    public class MoveElevatorDownImpl extends CommandBase {

        private final ElevatorSubsystem elevatorSubsystem;

        public MoveElevatorDownImpl(ElevatorSubsystem elevatorSubsystem) {
            this.elevatorSubsystem = elevatorSubsystem;
            addRequirements(elevatorSubsystem);
        }

        @Override
        public void initialize() {}

        @Override
        public void execute() { elevatorSubsystem.elevatorDown(); }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public void end(boolean interrupted) {
            elevatorSubsystem.stop();
        }
    }

    private final ElevatorSubsystem elevatorSubsystem;

    @Inject
    public MoveElevatorDownCommand(ElevatorSubsystem elevatorSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;
    }

    @Override
    public CommandBase create() {
        return new MoveElevatorDownImpl(elevatorSubsystem);
    }
}
