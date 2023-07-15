// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.commands.PoseWithVisionCommand;
import frc.robot.commands.carriage.ConeInCubeOutCommand;
import frc.robot.commands.carriage.ConeOutCubeInCommand;
import frc.robot.commands.carriage.ToggleCarriagePositionCommand;
import frc.robot.commands.drive.*;
import frc.robot.commands.elevator.CycleElevatorPositionCommand;
import frc.robot.commands.elevator.SetElevatorDownCommand;
import frc.robot.commands.led.PieceLEDCommand;
import frc.robot.constants.AutoConstants;
import frc.robot.di.RobotComponent;
import frc.robot.subsystems.*;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.controllerUtils.*;
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
        configureControllerOne();
        configureControllerTwo();
    }

    private void configureControllerOne() {
        //Set up branch to add xbox commands
        buttonHelper.setController(0);

        buttonHelper.createButton(XboxController.Button.kStart.value, 0, new PoseWithVisionCommand(drivetrainSubsystem), RunCondition.WHILE_HELD);

        buttonHelper.createButton(XboxController.Button.kRightBumper.value, 0, new ConeInCubeOutCommand(carriageSubsystem), RunCondition.WHILE_HELD);
        buttonHelper.createButton(XboxController.Button.kLeftBumper.value, 0, new ConeOutCubeInCommand(carriageSubsystem), RunCondition.WHILE_HELD);

        buttonHelper.createButton(XboxController.Button.kA.value, 0, new SwerveLockCommand(drivetrainSubsystem), RunCondition.WHILE_HELD);

        buttonHelper.createButton(XboxController.Button.kX.value, 0, new SetElevatorDownCommand(elevatorSubsystem, carriageSubsystem), RunCondition.WHEN_PRESSED);
    }

    private void configureControllerTwo() {
        buttonHelper.setController(1);

        buttonHelper.createButton(1, 0, new SetElevatorDownCommand(elevatorSubsystem, carriageSubsystem), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(XboxController.Button.kRightBumper.value, 0,new CycleElevatorPositionCommand(elevatorSubsystem, carriageSubsystem, ledSubsystem), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(4, 0, new PieceLEDCommand(ledSubsystem, elevatorSubsystem), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(10, 0, new StartEndCommand(() -> buttonHelper.setAllLayers(1), () -> buttonHelper.setAllLayers(0))
                .alongWith(new PoseWithVisionCommand(drivetrainSubsystem)), RunCondition.WHILE_HELD);

        buttonHelper.createButton(XboxController.Button.kLeftBumper.value, 0, new ToggleCarriagePositionCommand(carriageSubsystem, elevatorSubsystem, ledSubsystem), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(7, 0, new ConeInCubeOutCommand(carriageSubsystem), RunCondition.WHILE_HELD);
        buttonHelper.createButton(8, 0, new ConeOutCubeInCommand(carriageSubsystem), RunCondition.WHILE_HELD);

        buttonHelper.createPOVButton(0, POVDirections.LEFT,1, new GridAlignCommand(drivetrainSubsystem, carriageSubsystem, ledSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.RIGHT), RunCondition.WHEN_PRESSED);
        buttonHelper.createPOVButton(0,POVDirections.UP, 1, new GridAlignCommand(drivetrainSubsystem, carriageSubsystem, ledSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.MIDDLE), RunCondition.WHEN_PRESSED);
        buttonHelper.createPOVButton(0,POVDirections.DOWN, 1, new GridAlignCommand(drivetrainSubsystem, carriageSubsystem, ledSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.MIDDLE), RunCondition.WHEN_PRESSED);
        buttonHelper.createPOVButton(0, POVDirections.RIGHT,1, new GridAlignCommand(drivetrainSubsystem, carriageSubsystem, ledSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.LEFT), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(XboxController.Button.kA.value, 1, new ChuteAlignCommand(drivetrainSubsystem, ledSubsystem, carriageSubsystem, primaryController::getLeftX, AutoConstants.ALIGNMENT_POSITION.SINGLE_STATION), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(XboxController.Button.kLeftBumper.value, 1, new DoubleStationAlignCommand(drivetrainSubsystem, elevatorSubsystem, ledSubsystem, carriageSubsystem, primaryController::getLeftY, AutoConstants.ALIGNMENT_POSITION.LEFT_DOUBLE_STATION), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(XboxController.Button.kRightBumper.value, 1, new DoubleStationAlignCommand(drivetrainSubsystem, elevatorSubsystem, ledSubsystem, carriageSubsystem, primaryController::getLeftY, AutoConstants.ALIGNMENT_POSITION.RIGHT_DOUBLE_STATION), RunCondition.WHEN_PRESSED);
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
