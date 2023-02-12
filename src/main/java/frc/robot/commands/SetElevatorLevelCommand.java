package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.ElevatorSubsystem;

import javax.inject.Inject;


public class SetElevatorLevelCommand extends CommandBase {

    private final ElevatorSubsystem elevatorSubsystem;

    double elevatorLevel;

    @Inject
    public SetElevatorLevelCommand(ElevatorSubsystem elevatorSubsystem, double elevatorLevel) {
        this.elevatorSubsystem = elevatorSubsystem;
        this.elevatorLevel = elevatorLevel;
        addRequirements(elevatorSubsystem);
    }

    @Override
    public void initialize() {
        elevatorSubsystem.setMotionMagic(elevatorLevel);
    }

    @Override
    public void execute() { System.out.println(elevatorSubsystem.getElevatorEncoder()); }

    @Override
    public boolean isFinished() {
        return elevatorSubsystem.motionMagicOnTarget();
    }

    @Override
    public void end(boolean interrupted) {

    }
}
