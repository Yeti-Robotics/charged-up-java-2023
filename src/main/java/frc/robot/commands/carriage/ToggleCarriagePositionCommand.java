package frc.robot.commands.carriage;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.CarriageConstants.CarriagePositions;
import frc.robot.subsystems.CarriageSubsystem;

public class ToggleCarriagePositionCommand extends CommandBase {
    private final CarriageSubsystem carriageSubsystem;

    private final CarriageFlipInCommand carriageFlipInCommand;
    private final CarriageFlipOutCommand carriageFlipOutCommand;
    public ToggleCarriagePositionCommand(CarriageSubsystem carriageSubsystem) {
        this.carriageSubsystem = carriageSubsystem;

        this.carriageFlipInCommand = new CarriageFlipInCommand(carriageSubsystem);
        this.carriageFlipOutCommand = new CarriageFlipOutCommand(carriageSubsystem);
        addRequirements(carriageSubsystem);
    }

    @Override
    public void initialize() {
        CarriagePositions currentPosition = carriageSubsystem.getCarriagePosition();

        if (currentPosition == CarriagePositions.FLIPPED) {
            carriageFlipInCommand.schedule();
        } else {
            carriageFlipOutCommand.schedule();
        }
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
