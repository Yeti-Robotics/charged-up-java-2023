package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.CarriageSubsystem;

import javax.inject.Inject;


public class CarriageInCommand extends CommandBase {
    private double lastCurrent = 0.0;
    private final CarriageSubsystem carriageSubsystem;
    @Inject
    public CarriageInCommand(CarriageSubsystem carriageSubsystem){
        this.carriageSubsystem = carriageSubsystem;
        addRequirements(carriageSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        carriageSubsystem.carriageIn();
    }

    @Override
    public boolean isFinished() {
        // Stops the motor when the piece can no longer move, indicated by a voltage spike
        double presentCurrent = carriageSubsystem.getRollerCurrent();
        boolean stopMotor = (presentCurrent - lastCurrent) > Constants.CarriageConstants.STOP_ROLLER_CURRENT_DELTA;
        lastCurrent = presentCurrent;
        return stopMotor;
    }

    @Override
    public void end(boolean interrupted) {
        carriageSubsystem.rollerStop();
    }
}
