package frc.robot.di;


import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.auto.AutoBuilder;
import frc.robot.utils.controllerUtils.ButtonHelper;
import frc.robot.utils.controllerUtils.ControllerContainer;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;

@Module
public class RobotModule {
    @Provides
    @Singleton
    public RobotContainer providesRobotContainer(
            CarriageSubsystem carriageSubsystem,
            DrivetrainSubsystem drivetrainSubsystem,
            IntakeSubsystem intakeSubsystem,
            ArmSubsystem armSubsystem,
            ElevatorSubsystem elevatorSubsystem,
            ControllerContainer controllerContainer,
            ButtonHelper buttonHelper) {
        return new RobotContainer(
                carriageSubsystem,
                drivetrainSubsystem,
                intakeSubsystem,
                armSubsystem,
                elevatorSubsystem,
                controllerContainer,
                buttonHelper
        );
    }

    @Provides
    @Singleton
    public ControllerContainer providesControllerContainer() {
        return new ControllerContainer();
    }

    @Provides
    @Singleton
    public ButtonHelper providesButtonHelper(ControllerContainer controllerContainer) {
        return new ButtonHelper(controllerContainer.getControllers());
    }

    @Provides
    public AutoBuilder providesAutoBuilder(DrivetrainSubsystem drivetrainSubsystem,
                                           IntakeSubsystem intakeSubsystem,
                                           ArmSubsystem armSubsystem,
                                           ElevatorSubsystem elevatorSubsystem,
                                           CarriageSubsystem carriageSubsystem){

        HashMap<String, Command> eventMap = new HashMap<String, Command>();
        eventMap.put("armDown", new SequentialCommandGroup(new SetArmPositionCommand(armSubsystem, Constants.ArmConstants.ArmPositions.DOWN)));


        SwerveAutoBuilder autoBuilder = new SwerveAutoBuilder(
                drivetrainSubsystem::getPose,
                drivetrainSubsystem::resetOdometer,
                Constants.DriveConstants.DRIVE_KINEMATICS,
                new PIDConstants(Constants.AutoConstants.X_CONTROLLER_P, 0, 0),
                new PIDConstants(Constants.AutoConstants.THETA_CONTROLLER_P, 0, 0),
                drivetrainSubsystem::drive,
                eventMap,
                drivetrainSubsystem);


        return new AutoBuilder(autoBuilder);
    }
    /*
     * The X axis is forward and backward
     */
    @Provides
    @Named(Constants.OIConstants.TRANSLATION_XSUPPLIER)
    public DoubleSupplier provideTranslationXSupplier(ControllerContainer controllerContainer) {
        return () -> controllerContainer.get(0).getLeftY();
    }

    /*
     * The Y is side to side
     */
    @Provides
    @Named(Constants.OIConstants.TRANSLATION_YSUPPLIER)
    public DoubleSupplier provideTranslationYSupplier(ControllerContainer controllerContainer) {
        return () -> controllerContainer.get(0).getLeftX();
    }

    @Provides
    @Named(Constants.OIConstants.THETA_SUPPLIER)
    public DoubleSupplier provideRotationSupplier(ControllerContainer controllerContainer) {
        return () -> controllerContainer.get(0).getRightX();
    }

    @Provides
    @Singleton
    public SwerveModulePosition[] providesSwerveModulePositions() {
        return new SwerveModulePosition[]{
                new SwerveModulePosition(),
                new SwerveModulePosition(),
                new SwerveModulePosition(),
                new SwerveModulePosition()
        };
    }

    @Provides
    @Singleton
    public SwerveDriveOdometry providesSwerveDriveOdometry(WPI_Pigeon2 gyro, SwerveModulePosition[] positions) {
        return new SwerveDriveOdometry(Constants.DriveConstants.DRIVE_KINEMATICS, gyro.getRotation2d(), positions);
    }
}