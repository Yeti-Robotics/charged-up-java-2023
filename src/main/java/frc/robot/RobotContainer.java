// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.*;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.drive.FieldOrientedDrive;
import frc.robot.commands.drive.SwerveLockCommand;
import frc.robot.commands.intake.IntakeCloseCommand;
import frc.robot.commands.intake.IntakeOpenCommand;
import frc.robot.commands.intake.IntakeRollInCommand;
import frc.robot.commands.intake.IntakeRollOutCommand;
import frc.robot.di.RobotComponent;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.CommandFactory;
import frc.robot.utils.controllerUtils.ButtonHelper;
import frc.robot.utils.controllerUtils.ControllerContainer;
import frc.robot.utils.controllerUtils.MultiButton.RunCondition;

import javax.inject.Inject;
import java.util.Map;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...

    private RobotComponent robotComponent;

    private final ElevatorSubsystem elevatorSubsystem;


    private final ArmSubsystem armSubsystem;

    private final IntakeSubsystem intakeSubsystem;

    private final CarriageSubsystem carriageSubsystem;
    private final DrivetrainSubsystem drivetrainSubsystem;

    private final Map<Class<?>, CommandFactory> commands;

    private final ButtonHelper buttonHelper;

    public final ControllerContainer controllerContainer;

    @Inject
    public RobotContainer(CarriageSubsystem carriageSubsystem, DrivetrainSubsystem drivetrainSubsystem, IntakeSubsystem intakeSubsystem, ArmSubsystem armSubsystem, ElevatorSubsystem elevatorSubsystem, ControllerContainer controllerContainer, Map<Class<?>, CommandFactory> commands, ButtonHelper buttonHelper) {
        this.carriageSubsystem = carriageSubsystem;
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        this.armSubsystem = armSubsystem;
        this.elevatorSubsystem = elevatorSubsystem;
        this.controllerContainer = controllerContainer;
        this.buttonHelper = buttonHelper;
        this.commands = commands;
        drivetrainSubsystem.setDefaultCommand(commands.get(FieldOrientedDrive.class).create());
        configureBindings();
    }


    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {

        buttonHelper.createButton(1, 0, commands.get(IntakeRollInCommand.class).create(), RunCondition.WHILE_HELD);
        buttonHelper.createButton(2, 0, commands.get(IntakeRollOutCommand.class).create(), RunCondition.WHILE_HELD);

        buttonHelper.createButton(6, 0, commands.get(IntakeCloseCommand.class).create(), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(7, 0, commands.get(IntakeOpenCommand.class).create(), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(4, 0, new InstantCommand(carriageSubsystem::flipOut, carriageSubsystem), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(9, 0, new InstantCommand(carriageSubsystem::flipIn, carriageSubsystem), RunCondition.WHEN_PRESSED);
        HandoffCommands handoffCommands = new HandoffCommands(commands);
        buttonHelper.createButton(3, 0, new ConditionalCommand(handoffCommands.cubeHigh, handoffCommands.coneHigh, intakeSubsystem::isCube), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(8, 0, new ConditionalCommand(handoffCommands.cubeLow, handoffCommands.coneLow, intakeSubsystem::isCube), RunCondition.WHEN_PRESSED);
        //CARRIAGE IN AND OUt



        buttonHelper.createButton(11, 0, commands.get(SwerveLockCommand.class).create(), RunCondition.WHILE_HELD);
        buttonHelper.createButton(12, 0, commands.get(SetArmPositionCommand.class).create(), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(10, 0, new InstantCommand(() -> {
            drivetrainSubsystem.resetOdometer(new Pose2d());
        }), RunCondition.WHEN_PRESSED);

//        buttonHelper.createButton(5, 0, commands.get(AprilTagAlignCommand.class).create(), RunCondition.WHEN_PRESSED);

    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new InstantCommand();
    }

    public void setRobotComponent(RobotComponent robotComponent) {
        this.robotComponent = robotComponent;
    }

    public RobotComponent getRobotComponent() {
        return robotComponent;
    }
}
