package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.arm.SetArmPositionHandoffCommand;
import frc.robot.commands.carriage.CarriageInCommand;
import frc.robot.commands.elevator.MoveElevatorUpCommand;
import frc.robot.commands.intake.CubeRollOutCommand;
import frc.robot.commands.intake.IntakeOpenCommand;
import frc.robot.utils.CommandFactory;

import java.util.Map;


public class CubeHandoffLowCommand extends CommandBase {
    public CubeHandoffLowCommand(Map<Class<?>, CommandFactory> commands) {
        andThen(
                commands.get(SetArmPositionHandoffCommand.class).create(),
                commands.get(CarriageInCommand.class).create()
                        .alongWith(commands.get(IntakeOpenCommand.class).create())
                        .withTimeout(1.5).alongWith(commands.get(CubeRollOutCommand.class).create())
                        .withTimeout(.5),
                commands.get(MoveElevatorUpCommand.class).create()
        );
    }

    @Override
    public void initialize() {


    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        //carriage has cone
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }

}
