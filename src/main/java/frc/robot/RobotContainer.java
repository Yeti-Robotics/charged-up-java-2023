// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.arm.DriverArmPositionCommand;
import frc.robot.commands.carriage.CarriageFlipInCommand;
import frc.robot.commands.carriage.CarriageFlipOutCommand;
import frc.robot.commands.carriage.ConeInCubeOutCommand;
import frc.robot.commands.carriage.ConeOutCubeInCommand;
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
import frc.robot.utils.controllerUtils.MultiButton;
import frc.robot.utils.controllerUtils.MultiButton.RunCondition;
import frc.robot.Constants.ElevatorConstants.ElevatorPositions;

import javax.inject.Inject;

public class RobotContainer {
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

    private void configureBindings() {
        buttonHelper.createButton(1, 0, new IntakeRollInCommand(intakeSubsystem), RunCondition.WHILE_HELD);
        buttonHelper.createButton(6, 0, new IntakeRollOutCommand(intakeSubsystem), RunCondition.WHILE_HELD);

        buttonHelper.createButton(6, 0, new SetElevatorPositionCommand(elevatorSubsystem, ElevatorPositions.DOWN), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(8, 0, new IntakeShootCommand(intakeSubsystem, armSubsystem), RunCondition.WHEN_PRESSED);
//        buttonHelper.createButton(3, 0, handoffCommands.coneLow, RunCondition.WHEN_PRESSED);

//        buttonHelper.createButton(9,0,commands.get(SetElevatorPositionTopCommand.class).create(),RunCondition.WHEN_PRESSED);
//        buttonHelper.createButton(8, 0, commands.get(SetElevatorPositionMidCommand.class).create(), RunCondition.WHEN_PRESSED);
//        buttonHelper.createButton(3, 0, commands.get(SetElevatorPositionDownCommand.class).create(), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(2,0, new ConeInCubeOutCommand(carriageSubsystem), RunCondition.WHILE_HELD);
        buttonHelper.createButton(7,0, new ConeOutCubeInCommand(carriageSubsystem), RunCondition.WHILE_HELD);
        buttonHelper.createButton(8, 0, new CarriageFlipOutCommand(carriageSubsystem), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(3, 0, new CarriageFlipInCommand(carriageSubsystem), RunCondition.WHEN_PRESSED);



        buttonHelper.createButton(5, 0, new AutoBalancingCommand(drivetrainSubsystem,
                new PIDController(1.0, 0.0, 0.0)), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(10, 0, new SetElevatorDownCommand(elevatorSubsystem), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(10, 1, new InstantCommand(() -> {
            drivetrainSubsystem.resetOdometer(new Pose2d());
        }), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(11, 0, new ToggleIntakeCommand(intakeSubsystem), RunCondition.WHEN_PRESSED);

        MultiButton rightJoystickButton = buttonHelper.createButton(12);
        buttonHelper.createButton(12, 0, new DriverArmPositionCommand(armSubsystem, rightJoystickButton), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(9, 0, new InstantCommand(() ->
            buttonHelper.setAllLayers(buttonHelper.getAllLayers() == 1 ? 0 : 1)), RunCondition.WHEN_PRESSED);
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
