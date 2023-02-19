package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.arm.SetArmPositionHandoffCommand;
import frc.robot.commands.carriage.CarriageInCommand;
import frc.robot.commands.elevator.MoveElevatorUpCommand;
import frc.robot.commands.elevator.SetElevatorPositionConeHandoffCommand;
import frc.robot.commands.elevator.SetElevatorPositionTopCommand;
import frc.robot.commands.intake.IntakeOpenCommand;
import frc.robot.utils.CommandFactory;

import javax.inject.Inject;
import java.util.Map;

public class HandoffCommands {
    public final CommandBase coneHigh;
    public final CommandBase cubeHigh;

    public final CommandBase coneLow;

    public final CommandBase cubeLow;

    @Inject
    public HandoffCommands(Map<Class<?>, CommandFactory> commands) {
        coneHigh = commands.get(SetElevatorPositionConeHandoffCommand.class).create()
                        .andThen(
                                commands.get(SetArmPositionHandoffCommand.class).create(),
                                commands.get(CarriageInCommand.class).create()
                                        .alongWith(commands.get(IntakeOpenCommand.class).create())
                                        .withTimeout(1.5),
                                commands.get(SetElevatorPositionTopCommand.class).create()
                        );

        cubeHigh = commands.get(SetArmPositionHandoffCommand.class).create()
                .andThen(
                        commands.get(CarriageInCommand.class).create()
                                .alongWith(commands.get(IntakeOpenCommand.class).create())
                                .withTimeout(1.5),
                        commands.get(SetElevatorPositionTopCommand.class).create()
                );

        coneLow = commands.get(SetElevatorPositionConeHandoffCommand.class).create()
                .andThen(
                        commands.get(SetArmPositionHandoffCommand.class).create(),
                        commands.get(CarriageInCommand.class).create()
                                .alongWith(commands.get(IntakeOpenCommand.class).create())
                                .withTimeout(1.5),
                        commands.get(MoveElevatorUpCommand.class).create()
                );

        cubeLow = commands.get(SetArmPositionHandoffCommand.class).create()
                .andThen(
                        commands.get(CarriageInCommand.class).create()
                                .alongWith(commands.get(IntakeOpenCommand.class).create())
                                .withTimeout(1.5),
                        commands.get(MoveElevatorUpCommand.class).create()
                );

    }


}
