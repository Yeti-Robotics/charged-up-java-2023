package frc.robot.commands.led;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.LEDSubsystem;


public class PieceLEDCommand extends CommandBase {
    private final LEDSubsystem ledSubsystem;
    private final ElevatorSubsystem elevatorSubsystem;


    public PieceLEDCommand(
            LEDSubsystem ledSubsystem,
            ElevatorSubsystem elevatorSubsystem
    ) {
        this.ledSubsystem = ledSubsystem;
        this.elevatorSubsystem = elevatorSubsystem;

        addRequirements(ledSubsystem);
    }

    @Override
    public void initialize() {
        if (ledSubsystem.getPieceTarget() == LEDSubsystem.PieceTarget.CONE) {
            // These methods also update the piece target
            ledSubsystem.setCubePurple();
        } else {
            ledSubsystem.setConeYellow();
        }
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}