package frc.robot.di;


import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.constants.DriveConstants;
import frc.robot.RobotContainer;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.constants.OIConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.controllerUtils.ButtonHelper;
import frc.robot.utils.controllerUtils.ControllerContainer;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashMap;
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
            ButtonHelper buttonHelper,
            SwerveAutoBuilder autoBuilder) {
        return new RobotContainer(
                carriageSubsystem,
                drivetrainSubsystem,
                intakeSubsystem,
                armSubsystem,
                elevatorSubsystem,
                controllerContainer,
                buttonHelper,
                autoBuilder
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
    @Singleton
    @Named("event map")
    public HashMap<String, Command> providesEventMap(
            IntakeSubsystem intakeSubsystem,
            ArmSubsystem armSubsystem,
            ElevatorSubsystem elevatorSubsystem,
            CarriageSubsystem carriageSubsystem
    ){
        HashMap<String, Command> eventMap = new HashMap<String, Command>();
        eventMap.put("armDown", new SequentialCommandGroup(new SetArmPositionCommand(armSubsystem, elevatorSubsystem, Constants.ArmConstants.ArmPositions.DOWN)));
        return eventMap;
    }

    @Provides
    @Singleton
    public SwerveAutoBuilder providesAutoBuilder(DrivetrainSubsystem drivetrainSubsystem, @Named("event map") HashMap<String, Command> eventMap){



        SwerveAutoBuilder autoBuilder = new SwerveAutoBuilder(
                drivetrainSubsystem::getPose,
                drivetrainSubsystem::resetOdometer,
                Constants.DriveConstants.DRIVE_KINEMATICS,
                Constants.AutoConstants.TRANSLATION_CONTROLLER,
                Constants.AutoConstants.THETA_CONTROLLER,
                drivetrainSubsystem::drive,
                eventMap,
                drivetrainSubsystem);


        return autoBuilder;
    }
    /*
     * The X axis is forward and backward
     */
    @Provides
    @Named(OIConstants.TRANSLATION_XSUPPLIER)
    public DoubleSupplier provideTranslationXSupplier(ControllerContainer controllerContainer) {
        return () -> controllerContainer.get(0).getLeftY();
    }

    /*
     * The Y is side to side
     */
    @Provides
    @Named(OIConstants.TRANSLATION_YSUPPLIER)
    public DoubleSupplier provideTranslationYSupplier(ControllerContainer controllerContainer) {
        return () -> controllerContainer.get(0).getLeftX();
    }

    @Provides
    @Named(OIConstants.THETA_SUPPLIER)
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
        return new SwerveDriveOdometry(DriveConstants.DRIVE_KINEMATICS, gyro.getRotation2d(), positions);
    }
}