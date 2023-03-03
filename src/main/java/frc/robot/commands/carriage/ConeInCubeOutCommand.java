package frc.robot.commands.carriage;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CarriageSubsystem;

public class ConeInCubeOutCommand extends CommandBase {
    private final CarriageSubsystem carriageSubsystem;

    public ConeInCubeOutCommand(CarriageSubsystem carriageSubsystem) {
        this.carriageSubsystem = carriageSubsystem;

        addRequirements(carriageSubsystem);
    }

    @Override
    public void initialize() {
        carriageSubsystem.coneInCubeOut();
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
        carriageSubsystem.rollerStop();
    }
}
