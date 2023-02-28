// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.ConeHandoffCommand;
import frc.robot.commands.arm.DriverArmPositionCommand;
import frc.robot.commands.drive.AutoAlignCommand;
import frc.robot.commands.carriage.ConeInCubeOutCommand;
import frc.robot.commands.carriage.ConeOutCubeInCommand;
import frc.robot.commands.carriage.ToggleCarriagePositionCommand;
import frc.robot.commands.drive.FieldOrientedDrive;
import frc.robot.commands.drive.SwerveLockCommand;
import frc.robot.commands.elevator.CycleElevatorPositionCommand;
import frc.robot.commands.elevator.SetElevatorDownCommand;
import frc.robot.commands.intake.*;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.IntakeConstants;
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

    private final SwerveAutoBuilder autoBuilder;

    @Inject
    public RobotContainer(
            CarriageSubsystem carriageSubsystem,
            DrivetrainSubsystem drivetrainSubsystem,
            IntakeSubsystem intakeSubsystem,
            ArmSubsystem armSubsystem,
            ElevatorSubsystem elevatorSubsystem,
            ControllerContainer controllerContainer,
            ButtonHelper buttonHelper,
            SwerveAutoBuilder autoBuilder) {
        this.carriageSubsystem = carriageSubsystem;
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        this.armSubsystem = armSubsystem;
        this.elevatorSubsystem = elevatorSubsystem;
        this.controllerContainer = controllerContainer;
        this.buttonHelper = buttonHelper;
        this.autoBuilder = autoBuilder;
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
        buttonHelper.createButton(1, 0, new IntakeRollInCommand(intakeSubsystem, IntakeConstants.INTAKE_SPEED)
                .alongWith(new ConeInCubeOutCommand(carriageSubsystem)), RunCondition.WHILE_HELD);

        buttonHelper.createButton(6, 0, new IntakeRollOutCommand(intakeSubsystem, IntakeConstants.INTAKE_SPEED)
                .alongWith(new ConeOutCubeInCommand(carriageSubsystem)), RunCondition.WHILE_HELD);

        buttonHelper.createButton(4, 0, new IntakeShootMidCommand(intakeSubsystem, armSubsystem, elevatorSubsystem), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(9, 0, new IntakeShootHighCommand(intakeSubsystem, armSubsystem, elevatorSubsystem), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(2, 0, new SetElevatorDownCommand(elevatorSubsystem, carriageSubsystem), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(7, 0, new CycleElevatorPositionCommand(elevatorSubsystem, armSubsystem), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(3, 0, new ConeHandoffCommand(armSubsystem, intakeSubsystem, elevatorSubsystem, carriageSubsystem), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(8, 0, new ToggleCarriagePositionCommand(carriageSubsystem), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(5, 0, new InstantCommand(() -> {
            drivetrainSubsystem.resetOdometer(new Pose2d());
        }), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(10, 0, new InstantCommand(() -> {
            buttonHelper.setAllLayers(1);
        }), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(10, 1, new InstantCommand(() -> {
            buttonHelper.setAllLayers(0);
        }), RunCondition.WHEN_PRESSED);


        buttonHelper.createButton(11, 0, new InstantCommand(() -> {
            if (armSubsystem.isUP()) {
                buttonHelper.setButtonLayer(0, buttonHelper.getButtonID(11), 1);
            } else {
                buttonHelper.setButtonLayer(0, buttonHelper.getButtonID(11), 0);
            }
        })
                .alongWith(new ToggleIntakeCommand(intakeSubsystem).unless(() -> armSubsystem.isUP())), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(11, 1, new InstantCommand(() -> {
            if (armSubsystem.isUP()) {
                buttonHelper.setButtonLayer(0, buttonHelper.getButtonID(11), 1);
            } else {
                buttonHelper.setButtonLayer(0, buttonHelper.getButtonID(11), 0);
            }
        })
                .alongWith(new SwerveLockCommand(drivetrainSubsystem).unless(() -> !armSubsystem.isUP())), RunCondition.WHILE_HELD);

        buttonHelper.createButton(3,2,new AutoAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.LEFT_DOUBLE_STATION), RunCondition.WHILE_HELD);
        buttonHelper.createButton(3,2,new AutoAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.LEFT), RunCondition.WHILE_HELD);
        buttonHelper.createButton(3,2,new AutoAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.MIDDLE), RunCondition.WHILE_HELD);
        buttonHelper.createButton(3,2,new AutoAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.RIGHT), RunCondition.WHILE_HELD);

        MultiButton rightJoystickButton = buttonHelper.createButton(12);
        buttonHelper.createButton(12, 0, new DriverArmPositionCommand(armSubsystem, elevatorSubsystem, rightJoystickButton), RunCondition.WHEN_PRESSED);
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
