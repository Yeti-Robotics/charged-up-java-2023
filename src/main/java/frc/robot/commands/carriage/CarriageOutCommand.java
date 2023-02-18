package frc.robot.commands.carriage;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.CarriageSubsystem;

import javax.inject.Inject;


public class CarriageOutCommand extends CommandBase {
    private final CarriageSubsystem carriageSubsystem;
    private double lastCurrent = 0.0;
    @Inject
    public CarriageOutCommand(CarriageSubsystem carriageSubsystem){
        this.carriageSubsystem = carriageSubsystem;
        addRequirements(carriageSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        carriageSubsystem.carriageOut();
    }

    @Override
    public boolean isFinished() {
        // Stops the motor when the piece can no longer move, indicated by a voltage spike
        double presentCurrent = carriageSubsystem.getRollerCurrent();
        boolean stopMotor = (presentCurrent - lastCurrent) > Constants.CarriageConstants.STOP_ROLLER_CURRENT_DELTA;
        if (stopMotor) {
            System.out.println("Stopping motor because presentCurrent(" + presentCurrent + ") - lastCurrent(" +
                    lastCurrent + ") > DELTA (" + Constants.CarriageConstants.STOP_ROLLER_CURRENT_DELTA + ")");
        }
        lastCurrent = presentCurrent;
        return stopMotor;
    }

    @Override
    public void end(boolean interrupted) {
        carriageSubsystem.rollerStop();
    }
}
