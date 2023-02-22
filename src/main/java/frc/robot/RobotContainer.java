// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.*;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.drive.AutoBalancingCommand;
import frc.robot.commands.drive.FieldOrientedDrive;
import frc.robot.commands.elevator.*;
import frc.robot.commands.intake.*;
import frc.robot.di.RobotComponent;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.controllerUtils.ButtonHelper;
import frc.robot.utils.controllerUtils.Controller;
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


    private final ButtonHelper buttonHelper;

    public final ControllerContainer controllerContainer;
    private final Controller primaryController;

    @Inject
    public RobotContainer(
            CarriageSubsystem carriageSubsystem,
            DrivetrainSubsystem drivetrainSubsystem,
            IntakeSubsystem intakeSubsystem,
            ArmSubsystem armSubsystem,
            ElevatorSubsystem elevatorSubsystem,
            ControllerContainer controllerContainer,
            ButtonHelper buttonHelper) {
        this.carriageSubsystem = carriageSubsystem;
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        this.armSubsystem = armSubsystem;
        this.elevatorSubsystem = elevatorSubsystem;
        this.controllerContainer = controllerContainer;
        this.buttonHelper = buttonHelper;

        this.primaryController = controllerContainer.get(0);
        drivetrainSubsystem.setDefaultCommand(
                new FieldOrientedDrive(
                        drivetrainSubsystem,
                        primaryController::getLeftY,
                        primaryController::getLeftX,
                        primaryController::getRightX
                ));
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

//        buttonHelper.createButton(1, 0, commands.get(IntakeRollInCommand.class).create(), RunCondition.WHILE_HELD);
//        buttonHelper.createButton(2, 0, commands.get(IntakeRollOutCommand.class).create(), RunCondition.WHILE_HELD);
//
//        buttonHelper.createButton(6, 0, commands.get(SetElevatorPositionDownCommand.class).create(), RunCondition.WHEN_PRESSED);
//        buttonHelper.createButton(7, 0, commands.get(IntakeOpenCommand.class).create(), RunCondition.WHEN_PRESSED);
////        buttonHelper.createButton(4, 0, new StartEndCommand(carriageSubsystem::flipOut, carriageSubsystem::stopFlipMechanism, carriageSubsystem), RunCondition.WHILE_HELD);
////        buttonHelper.createButton(9, 0, new StartEndCommand(carriageSubsystem::flipIn, carriageSubsystem::stopFlipMechanism, carriageSubsystem), RunCondition.WHILE_HELD);
//        HandoffCommands handoffCommands = new HandoffCommands(commands);
//        buttonHelper.createButton(8, 0, commands.get(IntakeShootCommand.class).create(), RunCondition.WHEN_PRESSED);
//        buttonHelper.createButton(3, 0, handoffCommands.coneLow, RunCondition.WHEN_PRESSED);
//
////        buttonHelper.createButton(3, 0, commands.get(MoveElevatorUpCommand.class).create(), RunCondition.WHILE_HELD);
////        buttonHelper.createButton(4,0,commands.get(SetElevatorPositionConeHandoffCommand.class).create(),RunCondition.WHEN_PRESSED);
////        buttonHelper.createButton(9,0,commands.get(SetElevatorPositionTopCommand.class).create(),RunCondition.WHEN_PRESSED);
////        buttonHelper.createButton(8, 0, commands.get(SetElevatorPositionMidCommand.class).create(), RunCondition.WHEN_PRESSED);
////        buttonHelper.createButton(3, 0, commands.get(SetElevatorPositionDownCommand.class).create(), RunCondition.WHEN_PRESSED);
//        //CARRIAGE IN AND OUt
////        buttonHelper.createButton(9,0,new StartEndCommand(carriageSubsystem::carriageOut, carriageSubsystem::rollerStop), RunCondition.WHILE_HELD);
////        buttonHelper.createButton(4,0,new StartEndCommand(carriageSubsystem::carriageIn, carriageSubsystem::rollerStop), RunCondition.WHILE_HELD);
//
//
//        buttonHelper.createButton(11, 0, commands.get(IntakeCloseCommand.class).create(), RunCondition.WHEN_PRESSED);
//        buttonHelper.createButton(12, 0, commands.get(SetArmPositionCommand.class).create(), RunCondition.WHEN_PRESSED);
//
//        buttonHelper.createButton(4, 0, new AutoBalancingCommand(drivetrainSubsystem,
//                new PIDController(1.0, 0.0, 0.0)), RunCondition.WHEN_PRESSED);
//
//        buttonHelper.createButton(10, 0, new InstantCommand(() -> {
//            drivetrainSubsystem.resetOdometer(new Pose2d());
//        }), RunCondition.WHEN_PRESSED);
//        // stop burnout buttonHelper.createButton(5,0, new StartEndCommand(carriageSubsystem::flipOut, carriageSubsystem::flipIn), RunCondition.WHILE_HELD);
//
////        buttonHelper.createButton(5, 0, commands.get(AprilTagAlignCommand.class).create(), RunCondition.WHEN_PRESSED);

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
