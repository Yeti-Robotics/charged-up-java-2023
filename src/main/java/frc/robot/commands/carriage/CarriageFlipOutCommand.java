package frc.robot.commands.carriage;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.CarriageConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.CarriageSubsystem;


public class CarriageFlipOutCommand extends CommandBase {
    private final CarriageSubsystem carriageSubsystem;

    public CarriageFlipOutCommand(CarriageSubsystem carriageSubsystem) {
        this.carriageSubsystem = carriageSubsystem;

        addRequirements(carriageSubsystem);
    }

    @Override
    public void initialize() {
        carriageSubsystem.setSetpoint(CarriageConstants.CarriagePositions.FLIPPED);
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
