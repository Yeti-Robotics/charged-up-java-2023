package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.arm.SetArmPositionHandoffCommand;
import frc.robot.commands.carriage.CarriageInCommand;
import frc.robot.commands.elevator.SetElevatorPositionTopCommand;
import frc.robot.commands.intake.CubeRollOutCommand;
import frc.robot.commands.intake.IntakeUnclampCommand;
import frc.robot.utils.CommandFactory;

import java.util.Map;


public class CubeHandoffHighCommand extends CommandBase {


    public CubeHandoffHighCommand(Map<Class<?>, CommandFactory> commands) {
        andThen(
                commands.get(SetArmPositionHandoffCommand.class).create(),
                commands.get(CarriageInCommand.class).create()
                        .alongWith(commands.get(IntakeUnclampCommand.class).create())
                        .withTimeout(1.5).alongWith(commands.get(CubeRollOutCommand.class).create())
                        .withTimeout(.5),
                commands.get(SetElevatorPositionTopCommand  .class).create()
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
