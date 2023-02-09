package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CarriageSubsystem;

import javax.inject.Inject;


public class AutoActuateCarriageCommand extends CommandBase {
    private final CarriageSubsystem carriageSubsystem;
    @Inject
    public AutoActuateCarriageCommand(CarriageSubsystem carriageSubsystem){
        this.carriageSubsystem = carriageSubsystem;
        addRequirements(carriageSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if(!carriageSubsystem.getBeamBreak()){
            carriageSubsystem.carriageClose();
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
