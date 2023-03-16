package frc.robot.commands.led;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.LEDSubsystem;

import java.util.concurrent.atomic.AtomicBoolean;


public class PieceLEDCommand extends CommandBase {
    private final LEDSubsystem ledSubsystem;
    private final ElevatorSubsystem elevatorSubsystem;
    private AtomicBoolean lastShownYellow;


    public PieceLEDCommand(
            LEDSubsystem ledSubsystem,
            ElevatorSubsystem elevatorSubsystem,
            AtomicBoolean lastShownYellow
    ) {
        this.ledSubsystem = ledSubsystem;
        this.elevatorSubsystem = elevatorSubsystem;
        this.lastShownYellow = lastShownYellow;

        addRequirements(ledSubsystem);
    }

    @Override
    public void initialize() {
        if (lastShownYellow.get()) {
            ledSubsystem.setCubePurple();
            lastShownYellow.set(false);
        } else {
            ledSubsystem.setConeYellow();
            lastShownYellow.set(true);
        }
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
