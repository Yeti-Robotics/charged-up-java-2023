// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import dagger.Lazy;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.constants.AutoConstants.AutoModes;
import frc.robot.constants.FieldConstants;
import frc.robot.di.DaggerRobotComponent;
import frc.robot.di.RobotComponent;
import frc.robot.utils.rests.restUtils.RESTHandler;

import javax.inject.Inject;
import java.util.List;


/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private static SendableChooser<AutoModes> autoChooser;
    @Inject
    RobotContainer robotContainer;
    @Inject
    SwerveAutoBuilder autoBuilder;
    @Inject
    Lazy<RESTHandler> restHandler;
    private Command autonomousCommand;
    private AutoModes previousSelectedAuto;
    private DriverStation.Alliance previousAlliance = DriverStation.Alliance.Blue;

    public Robot() {
        FieldConstants.aprilTagLayout.setOrigin(AprilTagFieldLayout.OriginPosition.kBlueAllianceWallRightSide);
        FieldConstants.updateAprilTagTranslations();
        RobotComponent robotComponent = DaggerRobotComponent.builder().build();
        robotComponent.inject(this);
        robotContainer.setRobotComponent(robotComponent);
    }


    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        autoChooser = new SendableChooser<>();
        autoChooser.setDefaultOption(AutoModes.CONE_ONE.name, AutoModes.CONE_ONE);
        autoChooser.addOption(AutoModes.MIDDLE_BALANCE.name, AutoModes.MIDDLE_BALANCE);
        autoChooser.addOption(AutoModes.SHOOT_BALANCE_TWO.name, AutoModes.SHOOT_BALANCE_TWO);
        autoChooser.addOption(AutoModes.CONE_BALANCE_ONE.name, AutoModes.CONE_BALANCE_ONE);
        autoChooser.addOption(AutoModes.CONE_MOBILITY_BALANCE_TWO.name, AutoModes.CONE_MOBILITY_BALANCE_TWO);
        autoChooser.addOption(AutoModes.CONE_BALANCE_TWO.name, AutoModes.CONE_BALANCE_TWO);
        autoChooser.addOption(AutoModes.CONE_BALANCE_THREE.name, AutoModes.CONE_BALANCE_THREE);
        autoChooser.addOption(AutoModes.TWO_PIECE_BALANCE_ONE.name, AutoModes.TWO_PIECE_BALANCE_ONE);
        autoChooser.addOption(AutoModes.TWO_PIECE_BALANCE_TWO.name, AutoModes.TWO_PIECE_BALANCE_TWO);
        autoChooser.addOption(AutoModes.TWO_PIECE_ONE.name, AutoModes.TWO_PIECE_ONE);
        autoChooser.addOption(AutoModes.TWO_PIECE_TWO.name, AutoModes.TWO_PIECE_TWO);
        autoChooser.addOption(AutoModes.CONE_ONE.name, AutoModes.CONE_ONE);
        autoChooser.addOption(AutoModes.CONE_THREE.name, AutoModes.CONE_THREE);
        autoChooser.addOption(AutoModes.SHOOT_BALANCE_THREE.name, AutoModes.SHOOT_BALANCE_THREE);
        autoChooser.addOption(AutoModes.CONE_ONE_WAIT.name, AutoModes.CONE_ONE_WAIT);
        autoChooser.addOption(AutoModes.CONE_THREE_WAIT.name, AutoModes.CONE_THREE_WAIT);
        autoChooser.addOption(AutoModes.THREE_CUBE_TWO.name, AutoModes.THREE_CUBE_TWO);
        SmartDashboard.putData("Auto Chooser", autoChooser);
        previousSelectedAuto = autoChooser.getSelected();

        List<PathPlannerTrajectory> trajectory = PathPlanner.loadPathGroup(
                previousSelectedAuto.name, previousSelectedAuto.initConstraint, previousSelectedAuto.pathConstraints);
        autonomousCommand = autoBuilder.fullAuto(trajectory);

        SmartDashboard.putData(robotContainer.drivetrainSubsystem);
        SmartDashboard.putData(robotContainer.armSubsystem);
        SmartDashboard.putData(robotContainer.carriageSubsystem);
        SmartDashboard.putData(robotContainer.elevatorSubsystem);

        robotContainer.ledSubsystem.setYetiBlue();
    }


    /**
     * This method is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic methods, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
    }


    /**
     * This method is called once each time the robot enters Disabled mode.
     */
    @Override
    public void disabledInit() {
    }


    @Override
    public void disabledPeriodic() {
        if (previousSelectedAuto != autoChooser.getSelected()) {
            previousSelectedAuto = autoChooser.getSelected();

            List<PathPlannerTrajectory> trajectory = PathPlanner.loadPathGroup(
                    previousSelectedAuto.name, previousSelectedAuto.initConstraint, previousSelectedAuto.pathConstraints);
            autonomousCommand = autoBuilder.fullAuto(trajectory);
        }

        if (DriverStation.getAlliance() != previousAlliance) {
            previousAlliance = DriverStation.getAlliance();
            if (previousAlliance == DriverStation.Alliance.Blue) {
                FieldConstants.aprilTagLayout.setOrigin(AprilTagFieldLayout.OriginPosition.kBlueAllianceWallRightSide);
            } else {
                FieldConstants.aprilTagLayout.setOrigin(AprilTagFieldLayout.OriginPosition.kRedAllianceWallRightSide);
            }
            FieldConstants.updateAprilTagTranslations();
        }
    }


    /**
     * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
        robotContainer.ledSubsystem.setConeYellow();
    }


    /**
     * This method is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
    }


    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }


    /**
     * This method is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
    }


    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
        LiveWindow.setEnabled(false);
        restHandler.get().init();
        restHandler.get().fullTest();
    }


    /**
     * This method is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }


    /**
     * This method is called once when the robot is first started up.
     */
    @Override
    public void simulationInit() {
    }


    /**
     * This method is called periodically whilst in simulation.
     */
    @Override
    public void simulationPeriodic() {
    }
}
