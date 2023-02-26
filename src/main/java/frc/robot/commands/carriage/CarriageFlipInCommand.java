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
        boolean isZeroed = carriageSubsystem.getAngle() < 2.0;
        if (isZeroed) {
            carriageSubsystem.zeroFlip();
        }
        return isZeroed;
    }

    @Override
    public void end(boolean interrupted) {
        carriageSubsystem.stopFlipMechanism();
    }
}
