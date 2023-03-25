package frc.robot.commands.carriage;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.CarriageConstants;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;


public class CarriageFlipOutCommand extends CommandBase {
    private final CarriageSubsystem carriageSubsystem;

    public CarriageFlipOutCommand(CarriageSubsystem carriageSubsystem) {
        this.carriageSubsystem = carriageSubsystem;

        addRequirements(carriageSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        carriageSubsystem.setSetpoint(CarriageConstants.CarriagePositions.FLIPPED);
    }

    @Override
    public boolean isFinished() {
        return carriageSubsystem.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
    }
}
