package frc.robot.commands.carriage;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.CarriageSubsystem;

import javax.inject.Inject;


public class CarriageInCommand extends CommandBase {
    private final CarriageSubsystem carriageSubsystem;
    private final Timer timer;
    private double lastCurrent;
    @Inject
    public CarriageInCommand(CarriageSubsystem carriageSubsystem){
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
        carriageSubsystem.carriageIn();
    }

    @Override
    public boolean isFinished() {
        if (!timer.hasElapsed(0.500)) {
            return false;
        }

        double presentCurrent = carriageSubsystem.getRollerCurrent();
        boolean stopMotor = (presentCurrent - lastCurrent) > Constants.CarriageConstants.CONE_CURRENT_DELTA;
        if (stopMotor) {
            System.out.println("Stopping motor because presentCurrent(" + presentCurrent + ") - lastCurrent(" +
                    lastCurrent + ") > DELTA (" + Constants.CarriageConstants.CONE_CURRENT_DELTA + ")");
        }
        timer.reset();
        return stopMotor;
    }

    @Override
    public void end(boolean interrupted) {
        carriageSubsystem.rollerStop();
    }
}
