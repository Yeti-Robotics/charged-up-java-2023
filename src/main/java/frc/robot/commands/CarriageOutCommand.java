package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import javax.inject.Inject;


public class CarriageOutCommand extends CommandBase {
    private final CarriageSubsystem carriageSubsystem;
    @Inject
    public CarriageOutCommand(CarriageSubsystem carriageSubsystem){
        this.carriageSubsystem = carriageSubsystem;
        addRequirements(carriageSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        carriageSubsystem.carriageOut();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
