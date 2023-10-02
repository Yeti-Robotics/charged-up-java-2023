// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.SwerveAutoBuilder;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.ConeHandoffCommand;
import frc.robot.commands.PoseWithVisionCommand;
import frc.robot.commands.arm.DriverArmPositionCommand;
import frc.robot.commands.carriage.ConeInCubeOutCommand;
import frc.robot.commands.carriage.ConeOutCubeInCommand;
import frc.robot.commands.carriage.ToggleCarriagePositionCommand;
import frc.robot.commands.drive.*;
import frc.robot.commands.elevator.CycleElevatorPositionCommand;
import frc.robot.commands.elevator.SetElevatorDownCommand;
import frc.robot.commands.intake.*;
import frc.robot.commands.led.PieceLEDCommand;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.IntakeConstants;
import frc.robot.di.RobotComponent;
import frc.robot.subsystems.*;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.controllerUtils.ButtonHelper;
import frc.robot.utils.controllerUtils.Controller;
import frc.robot.utils.controllerUtils.ControllerContainer;
import frc.robot.utils.controllerUtils.MultiButton;
import frc.robot.utils.controllerUtils.MultiButton.RunCondition;

import javax.inject.Inject;

public class RobotContainer {
    public final ElevatorSubsystem elevatorSubsystem;
    public final ArmSubsystem armSubsystem;
    public final IntakeSubsystem intakeSubsystem;
    public final CarriageSubsystem carriageSubsystem;
    public final DrivetrainSubsystem drivetrainSubsystem;
    public final LEDSubsystem ledSubsystem;
    public final ButtonHelper buttonHelper;
    public final ControllerContainer controllerContainer;
    public final Controller primaryController;
    private final SwerveAutoBuilder autoBuilder;
    private RobotComponent robotComponent;

    @Inject
    public RobotContainer(
            CarriageSubsystem carriageSubsystem,
            DrivetrainSubsystem drivetrainSubsystem,
            IntakeSubsystem intakeSubsystem,
            ArmSubsystem armSubsystem,
            ElevatorSubsystem elevatorSubsystem,
            LEDSubsystem ledSubsystem,
            ControllerContainer controllerContainer,
            ButtonHelper buttonHelper,
            SwerveAutoBuilder autoBuilder) {
        this.carriageSubsystem = carriageSubsystem;
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        this.armSubsystem = armSubsystem;
        this.elevatorSubsystem = elevatorSubsystem;
        this.ledSubsystem = ledSubsystem;
        this.controllerContainer = controllerContainer;
        this.buttonHelper = buttonHelper;
        this.autoBuilder = autoBuilder;
        this.primaryController = controllerContainer.get(0);
        drivetrainSubsystem.setDefaultCommand(
                new FieldOrientedDrive(
                        drivetrainSubsystem,
                        primaryController::getLeftY,
                        primaryController::getLeftX,
                        primaryController::getRightX,
                        elevatorSubsystem::getPosition
                ));
        configureBindings();
        SmartDashboard.putData(buttonHelper);
    }

    private void configureBindings() {
        buttonHelper.createButton(1, 0, new IntakeRollInCommand(intakeSubsystem, 0.45)
                .alongWith(new ConeInCubeOutCommand(carriageSubsystem)), RunCondition.WHILE_HELD);
        buttonHelper.createButton(6, 0, new IntakeRollOutCommand(intakeSubsystem, IntakeConstants.INTAKE_OUT_SPEED)
                .alongWith(new ConeOutCubeInCommand(carriageSubsystem)), RunCondition.WHILE_HELD);

        buttonHelper.createButton(4, 0, new IntakeShootMidCommand(intakeSubsystem, armSubsystem, elevatorSubsystem).unless(
                () -> !elevatorSubsystem.isDown()), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(9, 0, new IntakeShootHighCommand(intakeSubsystem, armSubsystem, elevatorSubsystem)
                .unless(() -> !elevatorSubsystem.isDown()), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(5, 0, new StartEndCommand(() -> buttonHelper.setAllLayers(1), () -> buttonHelper.setAllLayers(0)), RunCondition.WHILE_HELD);
        buttonHelper.createButton(2, 0, new SetElevatorDownCommand(elevatorSubsystem, armSubsystem, carriageSubsystem), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(7, 0, new CycleElevatorPositionCommand(elevatorSubsystem, armSubsystem, carriageSubsystem, ledSubsystem), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(3, 0, new IntakeShootLowCommand(intakeSubsystem, armSubsystem, elevatorSubsystem).unless(
                () -> !elevatorSubsystem.isDown()), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(8, 0, new PieceLEDCommand(ledSubsystem, elevatorSubsystem), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(5, 0, new StartEndCommand(() -> buttonHelper.setAllLayers(1), () -> buttonHelper.setAllLayers(0))
                .alongWith(new PoseWithVisionCommand(drivetrainSubsystem)), RunCondition.WHILE_HELD);

        buttonHelper.createButton(10, 0, new ToggleCarriagePositionCommand(carriageSubsystem, elevatorSubsystem, ledSubsystem), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(11, 0, new SwerveLockCommand(drivetrainSubsystem), RunCondition.WHILE_HELD);

        buttonHelper.createButton(1, 1, new GridAlignCommand(drivetrainSubsystem, carriageSubsystem, ledSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.RIGHT), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(6, 1, new GridAlignCommand(drivetrainSubsystem, carriageSubsystem, ledSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.RIGHT), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(2, 1, new GridAlignCommand(drivetrainSubsystem, carriageSubsystem, ledSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.MIDDLE), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(7, 1, new GridAlignCommand(drivetrainSubsystem, carriageSubsystem, ledSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.MIDDLE), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(3, 1, new GridAlignCommand(drivetrainSubsystem, carriageSubsystem, ledSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.LEFT), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(8, 1, new GridAlignCommand(drivetrainSubsystem, carriageSubsystem, ledSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.LEFT), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(4, 1, new ChuteAlignCommand(drivetrainSubsystem, ledSubsystem, carriageSubsystem, primaryController::getLeftX, AutoConstants.ALIGNMENT_POSITION.SINGLE_STATION), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(9, 1, new DoubleStationAlignCommand(drivetrainSubsystem, elevatorSubsystem, ledSubsystem, carriageSubsystem, primaryController::getLeftY, AutoConstants.ALIGNMENT_POSITION.LEFT_DOUBLE_STATION), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(10, 1, new DoubleStationAlignCommand(drivetrainSubsystem, elevatorSubsystem, ledSubsystem, carriageSubsystem, primaryController::getLeftY, AutoConstants.ALIGNMENT_POSITION.RIGHT_DOUBLE_STATION), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(12, 0, new DriverArmPositionCommand(armSubsystem, elevatorSubsystem)
                .beforeStarting(new SetElevatorDownCommand(elevatorSubsystem, armSubsystem, carriageSubsystem).unless(elevatorSubsystem::isDown)), RunCondition.WHEN_PRESSED);
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new InstantCommand();
    }

    public RobotComponent getRobotComponent() {
        return robotComponent;
    }

    public void setRobotComponent(RobotComponent robotComponent) {
        this.robotComponent = robotComponent;
    }
}
