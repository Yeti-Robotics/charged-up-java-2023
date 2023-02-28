package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.carriage.CarriageFlipInCommand;
import frc.robot.constants.CarriageConstants;
import frc.robot.constants.ElevatorConstants.ElevatorPositions;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;


public class SetElevatorDownCommand extends CommandBase {
    private final ElevatorSubsystem elevatorSubsystem;
    private final CarriageSubsystem carriageSubsystem;
    public SetElevatorDownCommand(ElevatorSubsystem elevatorSubsystem, CarriageSubsystem carriageSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;
        this.carriageSubsystem = carriageSubsystem;

        addRequirements(this.elevatorSubsystem);
    }

    @Override
    public void initialize() {
        if (carriageSubsystem.getCarriagePosition() == CarriageConstants.CarriagePositions.FLIPPED) {
            new CarriageFlipInCommand(carriageSubsystem).asProxy().schedule();
        }
        elevatorSubsystem.setPosition(ElevatorPositions.DOWN);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return elevatorSubsystem.getElevatorEncoder() < 500;
    }

    @Override
    public void end(boolean interrupted) {
        elevatorSubsystem.stop();
    }
}