package frc.robot.commands.carriage;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.CarriageConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.CarriageSubsystem;

public class CarriageFlipInCommand extends CommandBase {
    private final CarriageSubsystem carriageSubsystem;
    public CarriageFlipInCommand(CarriageSubsystem carriageSubsystem) {
        this.carriageSubsystem = carriageSubsystem;

        addRequirements(carriageSubsystem);
    }

    @Override
    public void initialize() {
        carriageSubsystem.setSetpoint(CarriageConstants.CarriagePositions.DOWN);
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return carriageSubsystem.getAngle() < 5.0;
    }

    @Override
    public void end(boolean interrupted) {
        carriageSubsystem.stopFlipMechanism();
    }
}
