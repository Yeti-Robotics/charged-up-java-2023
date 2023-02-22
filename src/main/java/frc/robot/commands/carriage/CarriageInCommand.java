package frc.robot.commands.carriage;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class CarriageInCommand extends CommandBase {
    private final CarriageSubsystem carriageSubsystem;
    private final IntakeSubsystem intakeSubsystem;
    private final Timer timer;
    private double lastCurrent;
    private boolean isCube;
    private double delta;

    public CarriageInCommand(CarriageSubsystem carriageSubsystem, IntakeSubsystem intakeSubsystem) {
        this.carriageSubsystem = carriageSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        timer = new Timer();
        addRequirements(carriageSubsystem);
    }

    @Override
    public void initialize() {
        timer.start();
        lastCurrent = carriageSubsystem.getRollerCurrent();
        isCube = intakeSubsystem.isCube();
        delta = isCube ? Constants.CarriageConstants.CUBE_CURRENT_DELTA : Constants.CarriageConstants.CONE_CURRENT_DELTA;
        if (isCube) {
            carriageSubsystem.carriageIn();
        } else {
            carriageSubsystem.carriageIn();
        }
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {

//            double presentCurrent = carriageSubsystem.getRollerCurrent();
//            boolean stopMotor = (presentCurrent - lastCurrent) > delta;
//            if (stopMotor) {
//                System.out.println("Stopping motor because presentCurrent(" + presentCurrent + ") - lastCurrent(" +
//                        lastCurrent + ") > DELTA (" + delta + ")");
//            }
//            timer.reset();
//            return stopMotor;
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        carriageSubsystem.rollerStop();
    }
}
