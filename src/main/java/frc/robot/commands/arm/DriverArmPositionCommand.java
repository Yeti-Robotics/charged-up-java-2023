package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.utils.controllerUtils.ButtonHelper;
import frc.robot.utils.controllerUtils.MultiButton;

public class DriverArmPositionCommand extends CommandBase {
    private final ArmSubsystem armSubsystem;

    private final ElevatorSubsystem elevatorSubsystem;
    private final Timer timer;
    private final MultiButton button;
    private ArmConstants.ArmPositions position;
    private boolean isHeld;

    public DriverArmPositionCommand(ArmSubsystem armSubsystem, ElevatorSubsystem elevatorSubsystem, MultiButton button) {
        this.armSubsystem = armSubsystem;
        this.elevatorSubsystem = elevatorSubsystem;
        this.button = button;

        timer = new Timer();
        timer.start();
    }

    @Override
    public void initialize() {
        timer.reset();
        isHeld = false;

        ArmConstants.ArmPositions currentPosition = armSubsystem.getArmPosition();

        if (currentPosition == ArmConstants.ArmPositions.UP) {
            position = ArmConstants.ArmPositions.DOWN;
        }
        else if (currentPosition == ArmConstants.ArmPositions.HANDOFF) {
            position = ArmConstants.ArmPositions.UP;
        } else {
            position = ArmConstants.ArmPositions.UP;
        }
    }

    @Override
    public void execute() {
        if (timer.hasElapsed(0.5) && !isHeld) {
            isHeld = button.isPressed();
            position = ArmConstants.ArmPositions.CONE_FLIP;
        }

        if (isHeld && position == ArmConstants.ArmPositions.CONE_FLIP) {
            new SetArmPositionCommand(armSubsystem, elevatorSubsystem , position).schedule();
            position = ArmConstants.ArmPositions.DOWN;
        }
    }

    @Override
    public boolean isFinished() {
        return !button.isPressed();
    }

    @Override
    public void end(boolean interrupted) {
        new SetArmPositionCommand(armSubsystem, elevatorSubsystem, position).schedule();
    }
}
