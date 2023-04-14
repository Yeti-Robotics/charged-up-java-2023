package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.carriage.CarriageFlipOutCommand;
import frc.robot.constants.CarriageConstants;
import frc.robot.constants.ElevatorConstants.ElevatorPositions;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.LEDSubsystem;


public class CycleElevatorPositionCommand extends CommandBase {
    private final ElevatorSubsystem elevatorSubsystem;
    private final ArmSubsystem armSubsystem;
    private final CarriageSubsystem carriageSubsystem;

    private ElevatorPositions position;
    private SetElevatorPositionCommand command;

    public CycleElevatorPositionCommand(ElevatorSubsystem elevatorSubsystem, ArmSubsystem armSubsystem, CarriageSubsystem carriageSubsystem, LEDSubsystem ledSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;
        this.armSubsystem = armSubsystem;
        this.carriageSubsystem = carriageSubsystem;

        addRequirements(elevatorSubsystem, carriageSubsystem);
    }

    @Override
    public void initialize() {
        ElevatorPositions currentPosition = elevatorSubsystem.getPosition();

        if (currentPosition == ElevatorPositions.LEVEL_TWO) {
            position = ElevatorPositions.UP;
        } else {
            position = ElevatorPositions.LEVEL_TWO;
        }
        new SetElevatorPositionCommand(elevatorSubsystem, armSubsystem, position).schedule();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}