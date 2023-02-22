package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants.ArmPositions;
import frc.robot.subsystems.ArmSubsystem;

public class SetArmPositionCommand extends CommandBase {
    private final ArmSubsystem armSubsystem;
    private final Timer timer;
    private ArmPositions position;

    public SetArmPositionCommand(ArmSubsystem armSubsystem, ArmPositions position) {
        this.armSubsystem = armSubsystem;
        this.position = position;
        timer = new Timer();
        timer.start();

        addRequirements(this.armSubsystem);
    }

    @Override
    public void initialize() {
        timer.reset();
        armSubsystem.disengageBrake();
        armSubsystem.setPosition(position);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return armSubsystem.isMotionFinished() || timer.hasElapsed(2.0);
    }

    @Override
    public void end(boolean interrupted) {
        armSubsystem.engageBrake();
    }
}
