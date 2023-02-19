package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.arm.SetArmPositionHandoffCommand;
import frc.robot.commands.carriage.CarriageInCommand;
import frc.robot.commands.elevator.MoveElevatorUpCommand;
import frc.robot.commands.elevator.SetElevatorPositionConeHandoffCommand;
import frc.robot.commands.intake.IntakeOpenCommand;
import frc.robot.utils.CommandFactory;

import java.util.Map;


public class ConeHandoffLowCommand extends CommandBase {



    public ConeHandoffLowCommand(Map<Class<?>, CommandFactory> commands) {
        andThen(
                commands.get(SetElevatorPositionConeHandoffCommand.class).create(),
                commands.get(SetArmPositionHandoffCommand.class).create(),
                commands.get(CarriageInCommand.class).create()
                        .alongWith(commands.get(IntakeOpenCommand.class).create())
                        .withTimeout(1.5),
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
