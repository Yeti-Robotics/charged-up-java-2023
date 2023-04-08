package frc.robot.commands.wrist;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.WristConstants;
import frc.robot.constants.WristConstants.WristPositions;
import frc.robot.subsystems.WristSubsystem;


public class SetWristPositionCommand extends CommandBase {
    private final WristSubsystem wristSubsystem;

    private final Timer timer;
    private final WristPositions position;

    public SetWristPositionCommand(WristSubsystem wristSubsystem, WristPositions position) {
        // each subsystem used by the command must be passed into the
        this.wristSubsystem = wristSubsystem;
        this.position = position;

        timer = new Timer();
        timer.start();

        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.wristSubsystem);
    }

    @Override
    public void initialize() {
        timer.reset();
        wristSubsystem.disengageBrake();
        wristSubsystem.setPosition(position);
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return wristSubsystem.isMotionFinished() || timer.hasElapsed(1.2);
    }

    @Override
    public void end(boolean interrupted) {
        wristSubsystem.engageBrake();
    }
}
