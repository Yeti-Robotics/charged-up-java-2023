package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.CarriageSubsystem;

import javax.inject.Inject;


public class CarriageOutCommand extends CommandBase {
    private final CarriageSubsystem carriageSubsystem;
    private final Timer timer;
    private double lastCurrent;
    @Inject
    public CarriageOutCommand(CarriageSubsystem carriageSubsystem){
        this.carriageSubsystem = carriageSubsystem;
        timer = new Timer();
        addRequirements(carriageSubsystem);
    }

    @Override
    public void initialize() {
        timer.start();
        lastCurrent = carriageSubsystem.getRollerCurrent();
    }

    @Override
    public void execute() {
        carriageSubsystem.carriageOut();
    }

    @Override
    public boolean isFinished() {
        if (timer.hasElapsed(0.500)) {
            double presentCurrent = carriageSubsystem.getRollerCurrent();
            boolean stopMotor = (presentCurrent - lastCurrent) > Constants.CarriageConstants.CUBE_CURRENT_DELTA;
            timer.reset();
            return stopMotor;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        carriageSubsystem.rollerStop();
    }
}
