package frc.robot.commands.carriage;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.CarriageConstants;
import frc.robot.subsystems.CarriageSubsystem;

public class CarriageFlipInCommand extends CommandBase {
    private final CarriageSubsystem carriageSubsystem;

    public CarriageFlipInCommand(CarriageSubsystem carriageSubsystem) {
        this.carriageSubsystem = carriageSubsystem;

        addRequirements(carriageSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        carriageSubsystem.setSetpoint(CarriageConstants.CarriagePositions.DOWN);
    }

    @Override
    public boolean isFinished() {
        return carriageSubsystem.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        carriageSubsystem.stopFlipMechanism();
    }
}
