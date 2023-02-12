package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import javax.inject.Inject;


public class CarriageFlip extends CommandBase {
    private final CarriageSubsystem carriageSubsystem;
    @Inject
    public CarriageFlip(CarriageSubsystem carriageSubsystem){
        this.carriageSubsystem = carriageSubsystem;
        addRequirements(carriageSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        carriageSubsystem.flipMechanism();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
