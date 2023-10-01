package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ArmConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.utils.controllerUtils.MultiButton;

public class DriverArmPositionCommand extends CommandBase {
    private final ArmSubsystem armSubsystem;

    private final ElevatorSubsystem elevatorSubsystem;
    private final Timer timer;
    private ArmConstants.ArmPositions position;
    private boolean isHeld;
    private boolean isSet;

    public DriverArmPositionCommand(ArmSubsystem armSubsystem, ElevatorSubsystem elevatorSubsystem) {
        this.armSubsystem = armSubsystem;
        this.elevatorSubsystem = elevatorSubsystem;

        timer = new Timer();
        timer.start();
    }

    @Override
    public void initialize() {
        ArmConstants.ArmPositions currentPosition = armSubsystem.getArmPosition();

        if (currentPosition == ArmConstants.ArmPositions.UP) {
            position = ArmConstants.ArmPositions.DOWN;
        } else {
            position = ArmConstants.ArmPositions.UP;
        }

        new SetArmPositionCommand(armSubsystem, elevatorSubsystem, position).schedule();
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
