package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.carriage.CarriageFlipInCommand;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.CarriageConstants;
import frc.robot.constants.ElevatorConstants.ElevatorPositions;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class SetElevatorDownCommand extends CommandBase {
    private final ElevatorSubsystem elevatorSubsystem;
    private final ArmSubsystem armSubsystem;
    private final CarriageSubsystem carriageSubsystem;

    private final Timer timer;
    public SetElevatorDownCommand(ElevatorSubsystem elevatorSubsystem, ArmSubsystem armSubsystem, CarriageSubsystem carriageSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;
        this.armSubsystem = armSubsystem;
        this.carriageSubsystem = carriageSubsystem;

        timer = new Timer();
        timer.start();
        addRequirements(this.elevatorSubsystem);
    }

    @Override
    public void initialize() {
        timer.reset();
        if (carriageSubsystem.getCarriagePosition() == CarriageConstants.CarriagePositions.FLIPPED) {
            new CarriageFlipInCommand(carriageSubsystem).schedule();
        }
        new SetArmPositionCommand(armSubsystem, elevatorSubsystem, ArmConstants.ArmPositions.UP).schedule();
    }

    @Override
    public void execute() {
        if (timer.hasElapsed(0.3)) {
            elevatorSubsystem.setPosition(ElevatorPositions.DOWN);
        }
    }

    @Override
    public boolean isFinished() {
        return elevatorSubsystem.getElevatorEncoder() < 500;
    }

    @Override
    public void end(boolean interrupted) {
        elevatorSubsystem.stop();
        carriageSubsystem.zeroFlip();
    }
}