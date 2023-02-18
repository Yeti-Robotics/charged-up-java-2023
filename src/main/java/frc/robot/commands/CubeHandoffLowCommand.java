package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.commands.arm.ArmDownCommand;
import frc.robot.commands.arm.SetArmPositionHandoffCommand;
import frc.robot.commands.carriage.CarriageInCommand;
import frc.robot.commands.elevator.MoveElevatorUpCommand;
import frc.robot.commands.elevator.SetElevatorPositionConeHandoffCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

import javax.inject.Inject;
import java.util.Map;


public class CubeHandoffLowCommand extends CommandBase {



    public CubeHandoffLowCommand(Map<Class<?>, CommandBase> commands) {
        andThen(
                commands.get(SetArmPositionHandoffCommand.class),
                commands.get(CarriageInCommand.class)
                        .alongWith(commands.get(IntakeUnclampCommand.class))
                        .withTimeout(1.5),
                commands.get(MoveElevatorUpCommand.class)
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
