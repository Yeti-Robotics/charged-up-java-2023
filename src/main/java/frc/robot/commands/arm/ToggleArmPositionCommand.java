package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ArmConstants.ArmPositions;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class SetArmPositionCommand extends CommandBase {
    private final ArmSubsystem armSubsystem;
    private final ElevatorSubsystem elevatorSubsystem;
    private final Timer timer;
    private final ArmPositions position;

    public SetArmPositionCommand(ArmSubsystem armSubsystem, ElevatorSubsystem elevatorSubsystem, ArmPositions position) {
        this.armSubsystem = armSubsystem;
        this.elevatorSubsystem = elevatorSubsystem;
        this.position = position;

        timer = new Timer();
        timer.start();

        addRequirements(this.armSubsystem);
    }

    @Override
    public void initialize() {
        if (!elevatorSubsystem.isDown() && (position.angle <= 95.00)) {
            this.cancel();
            return;
        }

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
