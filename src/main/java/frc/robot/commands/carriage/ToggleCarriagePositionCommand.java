package frc.robot.commands.carriage;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.CarriageConstants.CarriagePositions;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.LEDSubsystem;

public class ToggleCarriagePositionCommand extends CommandBase {
    private final CarriageSubsystem carriageSubsystem;
    private final ElevatorSubsystem elevatorSubsystem;

    private final LEDSubsystem ledSubsystem;

    private final CarriageFlipInCommand carriageFlipInCommand;
    private final CarriageFlipOutCommand carriageFlipOutCommand;

    public ToggleCarriagePositionCommand(CarriageSubsystem carriageSubsystem, ElevatorSubsystem elevatorSubsystem, LEDSubsystem ledSubsystem) {
        this.carriageSubsystem = carriageSubsystem;
        this.elevatorSubsystem = elevatorSubsystem;
        this.ledSubsystem = ledSubsystem;

        this.carriageFlipInCommand = new CarriageFlipInCommand(carriageSubsystem);
        this.carriageFlipOutCommand = new CarriageFlipOutCommand(carriageSubsystem);
        addRequirements(carriageSubsystem);
    }

    @Override
    public void initialize() {
        CarriagePositions currentPosition = carriageSubsystem.getCarriagePosition();

        if (currentPosition != CarriagePositions.DOWN) {
            carriageFlipInCommand.schedule();
        } else if (elevatorSubsystem.getElevatorEncoder() < 1000) {
            if (ledSubsystem.getPieceTarget() != LEDSubsystem.PieceTarget.CUBE){
            carriageSubsystem.setSetpoint(CarriagePositions.CHUTE);
        } else {
                carriageSubsystem.setSetpoint(CarriagePositions.CUBE);
            }
        }
            else {
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
