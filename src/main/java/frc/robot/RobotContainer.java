// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.AprilTagAlignCommand;
import frc.robot.commands.arm.ArmDownCommand;
import frc.robot.commands.arm.ArmUpCommand;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.drive.AutoBalancingCommand;
import frc.robot.commands.drive.FieldOrientedDrive;
import frc.robot.commands.drive.SwerveLockCommand;
import frc.robot.di.RobotComponent;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.controllerUtils.ButtonHelper;
import frc.robot.utils.controllerUtils.ControllerContainer;
import frc.robot.utils.controllerUtils.MultiButton;
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


    private final ArmSubsystem armSubsystem;

    private final IntakeSubsystem intakeSubsystem;

    private final DrivetrainSubsystem drivetrainSubsystem;

    private final Map<Class<?>, CommandBase> commands;

    private final ButtonHelper buttonHelper;

    public final ControllerContainer controllerContainer;

    @Inject
    public RobotContainer(DrivetrainSubsystem drivetrainSubsystem, IntakeSubsystem intakeSubsystem, ArmSubsystem armSubsystem, ControllerContainer controllerContainer, Map<Class<?>, CommandBase> commands, ButtonHelper buttonHelper) {
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        this.armSubsystem = armSubsystem;
        this.controllerContainer = controllerContainer;
        this.commands = commands;
        this.buttonHelper = buttonHelper;
        drivetrainSubsystem.setDefaultCommand(commands.get(FieldOrientedDrive.class));
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

        buttonHelper.createButton(3, 0, commands.get(SetArmPositionCommand.class), MultiButton.RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(1, 0, new InstantCommand(armSubsystem::toggleBrake, armSubsystem), MultiButton.RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(6, 0, commands.get(ArmDownCommand.class), MultiButton.RunCondition.WHILE_HELD);
        buttonHelper.createButton(7, 0, commands.get(ArmUpCommand.class), MultiButton.RunCondition.WHILE_HELD);

        buttonHelper.createButton(12, 0, commands.get(SwerveLockCommand.class), RunCondition.WHILE_HELD);

        buttonHelper.createButton(2, 0, new InstantCommand(() -> {
            drivetrainSubsystem.resetOdometer(new Pose2d());
        }), RunCondition.WHEN_PRESSED);

        buttonHelper.createButton(4, 0, commands.get(AutoBalancingCommand.class), RunCondition.WHEN_PRESSED);
        buttonHelper.createButton(5, 0, commands.get(AprilTagAlignCommand.class), RunCondition.WHEN_PRESSED);

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

