package frc.robot.commands.wrist;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.wrist.SetWristPositionCommand;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.WristConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.WristSubsystem;
import frc.robot.utils.controllerUtils.MultiButton;

public class DriverWristPositionCommand extends CommandBase {
    private final WristSubsystem wristSubsystem;

    private final LEDSubsystem ledSubsystem;

    private final Timer timer;
    private final MultiButton button;
    private WristConstants.WristPositions position;
    private boolean isHeld;
    private boolean isSet;

    public DriverWristPositionCommand(WristSubsystem wristSubsystem, LEDSubsystem ledSubsystem, MultiButton button) {
        this.wristSubsystem = wristSubsystem;
        this.ledSubsystem = ledSubsystem;
        this.button = button;

        timer = new Timer();
        timer.start();
    }

    @Override
    public void initialize() {
        timer.reset();
        isHeld = false;
        isSet = false;

        WristConstants.WristPositions currentPosition = wristSubsystem.getWristPosition();

        if (ledSubsystem.getPieceTarget() == LEDSubsystem.PieceTarget.CONE) {
            if ((currentPosition == WristConstants.WristPositions.INTAKE_CONE_TIPPED_GROUND)) {
                position = WristConstants.WristPositions.INTAKE_CONE_TIPPED_GROUND;
            } else {
                position = WristConstants.WristPositions.SCORE_CONE_HIGH;
            }
        } else {
            if ((currentPosition == WristConstants.WristPositions.INTAKE_CUBE_GROUND)) {
                position = WristConstants.WristPositions.INTAKE_CUBE_GROUND;
            } else {
                position = WristConstants.WristPositions.SCORE_CUBE_HIGH;
            }

        }




    }

    @Override
    public void execute() {
        if (timer.hasElapsed(0.25) && !isSet) {
            isHeld = button.isPressed();
            if (wristSubsystem.getWristPosition().isScoring()) {
                position = ledSubsystem.getPieceTarget() == LEDSubsystem.PieceTarget.CONE ?
                         WristConstants.WristPositions.INTAKE_CONE_UPRIGHT_GROUND : WristConstants.WristPositions.INTAKE_CUBE_GROUND;
            } else {
                position = ledSubsystem.getPieceTarget() == LEDSubsystem.PieceTarget.CONE ?
                        WristConstants.WristPositions.SCORE_CONE_HIGH : WristConstants.WristPositions.SCORE_CUBE_HIGH;
            }
        }

        if (isHeld && !isSet) {
            new SetWristPositionCommand(wristSubsystem, position).schedule();
            isSet = true;
            if (position == WristConstants.WristPositions.INTAKE_CONE_TIPPED_GROUND) {
                position = ledSubsystem.getPieceTarget() == LEDSubsystem.PieceTarget.CONE ?
                        WristConstants.WristPositions.INTAKE_CONE_TIPPED_GROUND : WristConstants.WristPositions.INTAKE_CUBE_GROUND;
            } else {
                position = ledSubsystem.getPieceTarget() == LEDSubsystem.PieceTarget.CONE ?
              WristConstants.WristPositions.INTAKE_CONE_TIPPED_GROUND : WristConstants.WristPositions.INTAKE_CUBE_SINGLE;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return !button.isPressed();
    }

    @Override
    public void end(boolean interrupted) {
        new SetWristPositionCommand(wristSubsystem, position).schedule();
    }
}
