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
    private final MultiButton button;
    private ArmConstants.ArmPositions position;
    private boolean isHeld;
    private boolean isSet;

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
        isSet = false;

        ArmConstants.ArmPositions currentPosition = armSubsystem.getArmPosition();

        if (currentPosition == ArmConstants.ArmPositions.STOWED) {
            position = ArmConstants.ArmPositions.GROUND;
        } else {
            position = ArmConstants.ArmPositions.STOWED;
        }
    }

    @Override
    public void execute() {
        if (timer.hasElapsed(0.25) && !isSet) {
            isHeld = button.isPressed();
            if (armSubsystem.getArmPosition() == ArmConstants.ArmPositions.STOWED) {
                position = ArmConstants.ArmPositions.SINGLE_STATION;
            } else {
                position = ArmConstants.ArmPositions.SCORING;
            }
        }

        if (isHeld && !isSet) {
            new SetArmPositionCommand(armSubsystem, elevatorSubsystem, position).schedule();
            isSet = true;
            if (position == ArmConstants.ArmPositions.SINGLE_STATION) {
                position = ArmConstants.ArmPositions.STOWED;
            } else {
                position = ArmConstants.ArmPositions.SCORING;
            }
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
