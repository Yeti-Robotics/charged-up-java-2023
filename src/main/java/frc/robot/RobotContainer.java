// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.commands.HighScoreCommandGroup;
import frc.robot.commands.LowDumpCommandGroup;
import frc.robot.commands.MidScoreCommandGroup;
import frc.robot.commands.PoseWithVisionCommand;
import frc.robot.commands.arm.DriverArmPositionCommand;

import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.drive.*;
import frc.robot.commands.elevator.CycleElevatorPositionCommand;
import frc.robot.commands.elevator.SetElevatorDownCommand;
import frc.robot.commands.elevator.SetElevatorPositionCommand;
import frc.robot.commands.intake.*;
import frc.robot.commands.led.PieceLEDCommand;
import frc.robot.commands.wrist.DriverWristPositionCommand;
import frc.robot.commands.wrist.SetWristPositionCommand;
import frc.robot.constants.*;
import frc.robot.di.RobotComponent;
import frc.robot.subsystems.*;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.controllerUtils.ButtonHelper;
import frc.robot.utils.controllerUtils.Controller;
import frc.robot.utils.controllerUtils.ControllerContainer;
import frc.robot.utils.controllerUtils.MultiButton;
import frc.robot.utils.controllerUtils.MultiButton.RunCondition;

import javax.inject.Inject;
import java.sql.Driver;

public class RobotContainer {
    public final ElevatorSubsystem elevatorSubsystem;
    public final ArmSubsystem armSubsystem;
    public final IntakeSubsystem intakeSubsystem;
    public final DrivetrainSubsystem drivetrainSubsystem;

    public final WristSubsystem wristSubsystem;
    public final LEDSubsystem ledSubsystem;
    public final ButtonHelper buttonHelper;
    public final ControllerContainer controllerContainer;
    public final Controller primaryController;
    private final SwerveAutoBuilder autoBuilder;
    private RobotComponent robotComponent;

    @Inject
    public RobotContainer(
            DrivetrainSubsystem drivetrainSubsystem,
            IntakeSubsystem intakeSubsystem,
            ArmSubsystem armSubsystem,
            ElevatorSubsystem elevatorSubsystem,
            LEDSubsystem ledSubsystem,
            WristSubsystem wristSubsystem,
            ControllerContainer controllerContainer,
            ButtonHelper buttonHelper,
            SwerveAutoBuilder autoBuilder) {
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        this.armSubsystem = armSubsystem;
        this.elevatorSubsystem = elevatorSubsystem;
        this.ledSubsystem = ledSubsystem;
        this.wristSubsystem = wristSubsystem;
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
        buttonHelper.createButton(1, 0,
                ledSubsystem.getPieceTarget() == LEDSubsystem.PieceTarget.CONE ?
                        new ConeInCubeOutCommand(intakeSubsystem, IntakeConstants.INTAKE_SPEED) : new CubeInConeOutCommand(intakeSubsystem, IntakeConstants.INTAKE_SPEED), RunCondition.WHILE_HELD);
        buttonHelper.createButton(6, 0,
                ledSubsystem.getPieceTarget() == LEDSubsystem.PieceTarget.CONE ?
                        new CubeInConeOutCommand(intakeSubsystem, IntakeConstants.INTAKE_SPEED) : new ConeInCubeOutCommand(intakeSubsystem, IntakeConstants.INTAKE_SPEED), RunCondition.WHILE_HELD);

        buttonHelper.createButton(2, 0, new SetElevatorDownCommand(elevatorSubsystem, armSubsystem), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(7, 0, new CycleElevatorPositionCommand(elevatorSubsystem, armSubsystem), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(3, 0, new MidScoreCommandGroup(armSubsystem, elevatorSubsystem, ledSubsystem, wristSubsystem),
        RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(8, 0, new HighScoreCommandGroup(armSubsystem, elevatorSubsystem, ledSubsystem, wristSubsystem),
                RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(10, 0, new LowDumpCommandGroup(armSubsystem, elevatorSubsystem, ledSubsystem, wristSubsystem),
                RunCondition.WHEN_PRESSED);



        buttonHelper.createButton(12, 0, new PieceLEDCommand(ledSubsystem, elevatorSubsystem), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(5, 0, new StartEndCommand(() -> buttonHelper.setAllLayers(1), () -> buttonHelper.setAllLayers(0))
                .alongWith(new PoseWithVisionCommand(drivetrainSubsystem)), RunCondition.WHILE_HELD);

        MultiButton buttonNine = buttonHelper.createButton(9);
        buttonHelper.createButton(9, 0, new DriverWristPositionCommand(wristSubsystem, ledSubsystem, buttonNine), RunCondition.WHEN_PRESSED);




        buttonHelper.createButton(11, 0, new InstantCommand(() -> {
            if (armSubsystem.isUp()) {
                buttonHelper.setButtonLayer(0, buttonHelper.getButtonID(11), 1);
            } else {
                buttonHelper.setButtonLayer(0, buttonHelper.getButtonID(11), 0);
            }
        }),RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(11, 1, new InstantCommand(() -> {
            if (armSubsystem.isUp()) {
                buttonHelper.setButtonLayer(0, buttonHelper.getButtonID(11), 1);
            } else {
                buttonHelper.setButtonLayer(0, buttonHelper.getButtonID(11), 0);
            }
        })
                .alongWith(new SwerveLockCommand(drivetrainSubsystem).unless(() -> !armSubsystem.isUp())), RunCondition.WHILE_HELD);

        buttonHelper.createButton(1, 1, new GridAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.RIGHT), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(6, 1, new GridAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.RIGHT), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(2, 1, new GridAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.MIDDLE), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(7, 1, new GridAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.MIDDLE), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(3, 1, new GridAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.LEFT), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(8, 1, new GridAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.LEFT), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(9, 1, new DoubleStationAlignCommand(drivetrainSubsystem, elevatorSubsystem, ledSubsystem, primaryController::getLeftY, AutoConstants.ALIGNMENT_POSITION.LEFT_DOUBLE_STATION), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(10, 1, new DoubleStationAlignCommand(drivetrainSubsystem, elevatorSubsystem, ledSubsystem, primaryController::getLeftY, AutoConstants.ALIGNMENT_POSITION.RIGHT_DOUBLE_STATION), RunCondition.WHEN_PRESSED);

        MultiButton rightJoystickButton = buttonHelper.createButton(4);
        buttonHelper.createButton(4, 0, new DriverArmPositionCommand(armSubsystem, elevatorSubsystem, rightJoystickButton)
                .beforeStarting(new SetElevatorDownCommand(elevatorSubsystem, armSubsystem).unless(elevatorSubsystem::isDown)), RunCondition.WHEN_PRESSED);
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
