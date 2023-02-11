// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import dagger.Provides;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.ExampleCommand;
import frc.robot.di.RobotComponent;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.utils.controllerUtils.ControllerContainer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
@Singleton

public class RobotContainer
{
    // The robot's subsystems and commands are defined here...

   private RobotComponent robotComponent;

    private final IntakeSubsystem intakeSubsystem;
    private final Map<Class<?>, CommandBase> commands;

    public final ControllerContainer controllerContainer;
    
    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandXboxController controller =
            new CommandXboxController(Constants.OIConstants.XBOX_PORT);
    
@Inject

    public RobotContainer(IntakeSubsystem intakeSubsystem, ControllerContainer controllerContainer, Map<Class<?>, CommandBase> commands)
    {
        this.intakeSubsystem = intakeSubsystem;
        this.controllerContainer = controllerContainer;
        this.commands = commands;
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
    private void configureBindings()
    {
        /* change joystick button values*/
        new JoystickButton(controllerContainer.get(0), 1).onTrue(new InstantCommand(intakeSubsystem::intakeUnclamp));
        new JoystickButton(controllerContainer.get(0), 2).onTrue(new InstantCommand(intakeSubsystem::intakeClamp));
        new JoystickButton(controllerContainer.get(0), 3).onTrue(new InstantCommand(intakeSubsystem::rollIn));
        new JoystickButton(controllerContainer.get(0), 4).onTrue(new InstantCommand(intakeSubsystem::rollOut));

    }
    
    
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        return new InstantCommand();
    }
}
