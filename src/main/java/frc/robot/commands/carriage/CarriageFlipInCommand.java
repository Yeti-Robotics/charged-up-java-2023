package frc.robot.commands.carriage;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.CarriageConstants;
import frc.robot.subsystems.CarriageSubsystem;

public class CarriageFlipInCommand extends CommandBase {
    private final CarriageSubsystem carriageSubsystem;
    private final Timer timer = new Timer();

    public CarriageFlipInCommand(CarriageSubsystem carriageSubsystem) {
        this.carriageSubsystem = carriageSubsystem;

        timer.start();
        addRequirements(carriageSubsystem);
    }

    @Override
    public void initialize() {
        timer.reset();
    }

    @Override
    public void execute() {
        carriageSubsystem.setSetpoint(CarriageConstants.CarriagePositions.DOWN);
    }

    @Override
    public boolean isFinished() {
        return carriageSubsystem.atSetpoint() || timer.hasElapsed(1.0);
    }

    @Override
    public void end(boolean interrupted) {
        carriageSubsystem.stopFlipMechanism();
    }
}
