// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.ConeHandoffCommand;
import frc.robot.commands.CubeHandoffCommand;
import frc.robot.commands.arm.DriverArmPositionCommand;
import frc.robot.commands.carriage.ConeInCubeOutCommand;
import frc.robot.commands.carriage.ConeOutCubeInCommand;
import frc.robot.commands.carriage.ToggleCarriagePositionCommand;
import frc.robot.commands.drive.FieldOrientedDrive;
import frc.robot.commands.drive.PIDAlignCommand;
import frc.robot.commands.drive.SwerveLockCommand;
import frc.robot.commands.elevator.CycleElevatorPositionCommand;
import frc.robot.commands.elevator.SetElevatorDownCommand;
import frc.robot.commands.intake.IntakeRollInCommand;
import frc.robot.commands.intake.IntakeRollOutCommand;
import frc.robot.commands.intake.IntakeShootHighCommand;
import frc.robot.commands.intake.ToggleIntakeCommand;
import frc.robot.commands.led.SetRGBCommand;
import frc.robot.constants.IntakeConstants;
import frc.robot.di.RobotComponent;
import frc.robot.subsystems.*;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.controllerUtils.ButtonHelper;
import frc.robot.utils.controllerUtils.Controller;
import frc.robot.utils.controllerUtils.ControllerContainer;
import frc.robot.utils.controllerUtils.MultiButton;
import frc.robot.utils.controllerUtils.MultiButton.RunCondition;
import java.util.function.BooleanSupplier;

import javax.inject.Inject;
import java.util.Set;
import java.util.function.BooleanSupplier;

public class RobotContainer {
    private RobotComponent robotComponent;

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
                        primaryController::getRightX
                ));
        configureBindings();


    }

    private void configureBindings(){
        Trigger elevatorUpTrigger = new Trigger(() -> !elevatorSubsystem.isDown());
        elevatorUpTrigger.onTrue(new SetRGBCommand(ledSubsystem, 0, 255,0 ));



        //human player signalling buttons (all of the numebrs in both of these are placeholders)
        buttonHelper.createButton(4, 0, new SetRGBCommand(ledSubsystem, 255, 0, 0), RunCondition.TOGGLE_WHEN_PRESSED);
        buttonHelper.createButton(15, 0, new SetRGBCommand(ledSubsystem, 2, 3, 4), RunCondition.WHILE_HELD);
        //end of human player buttons

        buttonHelper.createButton(1, 0, new IntakeRollInCommand(intakeSubsystem, IntakeConstants.INTAKE_SPEED)
                .alongWith(new ConeInCubeOutCommand(carriageSubsystem)), RunCondition.WHILE_HELD);
        buttonHelper.createButton(6, 0, new IntakeRollOutCommand(intakeSubsystem, IntakeConstants.INTAKE_SPEED)
                .alongWith(new ConeOutCubeInCommand(carriageSubsystem)), RunCondition.WHILE_HELD);

//        buttonHelper.createButton(4, 0, new IntakeShootMidCommand(intakeSubsystem, armSubsystem, elevatorSubsystem)
//                .unless(() -> !elevatorSubsystem.isDown()), RunCondition.WHEN_PRESSED);
        /*buttonHelper.createButton(4,0,new CubeHandoffCommand(armSubsystem, intakeSubsystem, elevatorSubsystem, carriageSubsystem).unless(
                () -> !armSubsystem.isUP()), RunCondition.WHEN_PRESSED);
*/
        buttonHelper.createButton(9, 0, new IntakeShootHighCommand(intakeSubsystem, armSubsystem, elevatorSubsystem)
                .unless(() -> !elevatorSubsystem.isDown()), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(2, 0, new SetElevatorDownCommand(elevatorSubsystem, armSubsystem, carriageSubsystem), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(7, 0, new CycleElevatorPositionCommand(elevatorSubsystem, armSubsystem), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(3, 0, new ConeHandoffCommand(armSubsystem, intakeSubsystem, elevatorSubsystem, carriageSubsystem, ledSubsystem)
                .unless(() -> !armSubsystem.isUP()), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(8,0, new PIDAlignCommand(drivetrainSubsystem,
                primaryController::getLeftY, primaryController::getRightX), RunCondition.WHILE_HELD);

        buttonHelper.createButton(10, 0, new ToggleCarriagePositionCommand(carriageSubsystem).alongWith(new StartEndCommand(carriageSubsystem::coneInCubeOut, carriageSubsystem::rollerStop).withTimeout(0.5)), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(5, 0, new InstantCommand(() -> {
            drivetrainSubsystem.resetOdometer(new Pose2d(0 ,0, Rotation2d.fromDegrees(0)));
        }), RunCondition.WHEN_PRESSED);

//        buttonHelper.createButton(10, 0, new StartEndCommand(() -> buttonHelper.setAllLayers(1), () -> buttonHelper.setAllLayers(0))
//                .alongWith(new PoseWithVisionCommand(drivetrainSubsystem)), RunCondition.WHILE_HELD);

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

//        buttonHelper.createButton(1, 1, new AutoAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.LEFT), RunCondition.WHEN_PRESSED);
//        buttonHelper.createButton(6, 1, new AutoAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.LEFT), RunCondition.WHEN_PRESSED);
//        buttonHelper.createButton(2, 1, new AutoAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.MIDDLE), RunCondition.WHEN_PRESSED);
//        buttonHelper.createButton(7, 1, new AutoAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.MIDDLE), RunCondition.WHEN_PRESSED);
//        buttonHelper.createButton(3, 1, new AutoAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.RIGHT), RunCondition.WHEN_PRESSED);
//        buttonHelper.createButton(8, 1, new AutoAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.RIGHT), RunCondition.WHEN_PRESSED);
//        buttonHelper.createButton(9, 1, new AutoAlignCommand(drivetrainSubsystem, autoBuilder, AutoConstants.ALIGNMENT_POSITION.SINGLE_STATION), RunCondition.WHEN_PRESSED);

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
