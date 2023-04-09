package frc.robot.utils;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.utils.controllerUtils.MultiButton;

import static edu.wpi.first.util.ErrorMessages.requireNonNullParam;

public abstract class PressOrHoldCommand extends CommandBase {
    protected final MultiButton button;
    private final double holdTimeSeconds;
    private final Timer timer = new Timer();
    private boolean done = false;

    public PressOrHoldCommand(MultiButton button, double holdTimeSeconds) {
        requireNonNullParam(button, "button", "PressOrHoldCommand Constructor");
        this.button = button;
        this.holdTimeSeconds = holdTimeSeconds;
        timer.start();
    }

    public PressOrHoldCommand(MultiButton button) {
        this(button, 0.25);
    }

    /** Runs when <code>initialize</code> is called */
    public abstract void onInit();
    public abstract void onPress();
    public abstract void onHold();

    public final void initialize() {
        onInit();
        done = false;
        timer.reset();
    }

    public final void execute() {
        if (timer.hasElapsed(holdTimeSeconds) && button.isPressed()) {
            // They held it down, for long enough
            onHold();
            done = true;
        } else if (!button.isPressed()) {
            // They let go before the prev condition could occur
            onPress();
            done = true;
        }
    }

    public final boolean isFinished() {
        return done;
    }
}
