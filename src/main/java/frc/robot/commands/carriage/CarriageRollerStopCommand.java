package frc.robot.commands.carriage;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.utils.CommandFactory;

import javax.inject.Inject;


public class CarriageRollerStopCommand extends CommandFactory {
    public class CarriageRollerStopImpl extends CommandBase {
        private final CarriageSubsystem carriageSubsystem;
        public CarriageRollerStopImpl(CarriageSubsystem carriageSubsystem){
            this.carriageSubsystem = carriageSubsystem;
            addRequirements(carriageSubsystem);
        }

        @Override
        public void initialize() {}

        @Override
        public void execute() {
            carriageSubsystem.rollerStop();
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public void end(boolean interrupted) {

        }
    }

    private final CarriageSubsystem carriageSubsystem;

    @Inject
    public CarriageRollerStopCommand(CarriageSubsystem carriageSubsystem) {
        this.carriageSubsystem = carriageSubsystem;
    }

    @Override
    public CommandBase create() {
        return new CarriageRollerStopImpl(carriageSubsystem);
    }
}
