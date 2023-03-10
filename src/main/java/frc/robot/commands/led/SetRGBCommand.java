package frc.robot.commands.led;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LEDSubsystem;


public class SetRGBCommand extends CommandBase {

    private final LEDSubsystem ledSubsystem;
    private int r;
    private int g;
    private int b;

    public SetRGBCommand(LEDSubsystem ledSubsystem, int r, int g, int b) {
        this.ledSubsystem = ledSubsystem;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        this.r = r;
        this.g = g;
        this.b = b;
        addRequirements(this.ledSubsystem);

    }

    @Override
    public void initialize() {
        for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
            ledSubsystem.setRGB(i, r, g, b);
        }
        ledSubsystem.sendData();
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
