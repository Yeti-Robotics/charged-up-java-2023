package frc.robot.di;


import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.RobotContainer;
import frc.robot.commands.ConeHandoffCommand;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.carriage.CarriageFlipInCommand;
import frc.robot.commands.carriage.CarriageFlipOutCommand;
import frc.robot.commands.carriage.ConeOutCubeInCommand;
import frc.robot.commands.drive.AutoBalancingCommand;
import frc.robot.commands.drive.SwerveLockCommand;
import frc.robot.commands.elevator.SetElevatorDownCommand;
import frc.robot.commands.elevator.SetElevatorPositionCommand;
import frc.robot.commands.intake.IntakeRollInCommand;
import frc.robot.commands.intake.IntakeRollOutCommand;
import frc.robot.commands.intake.IntakeShootHighCommand;
import frc.robot.constants.*;
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
            DrivetrainSubsystem drivetrainSubsystem,
            IntakeSubsystem intakeSubsystem,
            ArmSubsystem armSubsystem,
            ElevatorSubsystem elevatorSubsystem,
            CarriageSubsystem carriageSubsystem) {
        HashMap<String, Command> eventMap = new HashMap<String, Command>();
        eventMap.put("autoBalance", new AutoBalancingCommand(drivetrainSubsystem));
        eventMap.put("swerveLock", new SwerveLockCommand(drivetrainSubsystem));
        eventMap.put("armDown", new SequentialCommandGroup(new SetArmPositionCommand(armSubsystem, elevatorSubsystem, ArmConstants.ArmPositions.DOWN)));
        eventMap.put("armUp", new SetArmPositionCommand(armSubsystem, elevatorSubsystem, ArmConstants.ArmPositions.UP));
        eventMap.put("intakeOut", new IntakeRollOutCommand(intakeSubsystem, IntakeConstants.INTAKE_SPEED));
        eventMap.put("intakeIn", new IntakeRollInCommand(intakeSubsystem, IntakeConstants.INTAKE_SPEED));
        eventMap.put("intakeInOneSec", new IntakeRollInCommand(intakeSubsystem, IntakeConstants.INTAKE_SPEED).withTimeout(1));
        eventMap.put("intakeOpen", Commands.runOnce(intakeSubsystem::intakeOpen, intakeSubsystem));
        eventMap.put("intakeClose", Commands.runOnce(intakeSubsystem::intakeClose, intakeSubsystem));
        eventMap.put("intakeStop", Commands.runOnce(intakeSubsystem::stop, intakeSubsystem));
        eventMap.put("elevatorDown", Commands.sequence(
                new CarriageFlipInCommand(carriageSubsystem),
                new SetArmPositionCommand(armSubsystem, elevatorSubsystem, ArmConstants.ArmPositions.UP),
                new WaitCommand(0.15),
                new SetElevatorPositionCommand(elevatorSubsystem, armSubsystem, ElevatorConstants.ElevatorPositions.DOWN),
                new WaitCommand(1.3),
                new InstantCommand(elevatorSubsystem::stop, elevatorSubsystem)
                ));
        eventMap.put("elevatorMid", new SetElevatorPositionCommand(elevatorSubsystem, armSubsystem, ElevatorConstants.ElevatorPositions.LEVEL_TWO));
        eventMap.put("elevatorHigh", new SetElevatorPositionCommand(elevatorSubsystem, armSubsystem, ElevatorConstants.ElevatorPositions.UP));
        eventMap.put("carriageOut", new ConeOutCubeInCommand(carriageSubsystem).withTimeout(0.50));
        eventMap.put("flipCarriageOut", new CarriageFlipOutCommand(carriageSubsystem));
        eventMap.put("coneHigh", Commands.sequence(
                new SetElevatorPositionCommand(elevatorSubsystem, armSubsystem, ElevatorConstants.ElevatorPositions.UP),
                new WaitCommand(1.0),
                new CarriageFlipOutCommand(carriageSubsystem),
                new WaitCommand(1.3),
                new ConeOutCubeInCommand(carriageSubsystem).withTimeout(0.2)
        ));
        eventMap.put("coneMid", Commands.sequence(
                new SetElevatorPositionCommand(elevatorSubsystem, armSubsystem, ElevatorConstants.ElevatorPositions.LEVEL_TWO),
                new WaitCommand(0.75),
                new CarriageFlipOutCommand(carriageSubsystem),
                new WaitCommand(1.3),
                new ConeOutCubeInCommand(carriageSubsystem).withTimeout(0.2)
        ));
        eventMap.put("shootHigh", new IntakeShootHighCommand(intakeSubsystem, armSubsystem, elevatorSubsystem));
        eventMap.put("coneAutoWait", new WaitCommand(7.0));
        eventMap.put("waitHalfSecond", new WaitCommand(0.5));
        eventMap.put("handoff", new ConeHandoffCommand(armSubsystem, intakeSubsystem, elevatorSubsystem, carriageSubsystem));
        return eventMap;
    }

    @Provides
    @Singleton
    public SwerveAutoBuilder providesAutoBuilder(DrivetrainSubsystem drivetrainSubsystem, @Named("event map") HashMap<String, Command> eventMap) {
        return new SwerveAutoBuilder(
                drivetrainSubsystem::getPose,
                drivetrainSubsystem::resetOdometer,
                DriveConstants.DRIVE_KINEMATICS,
                AutoConstants.TRANSLATION_CONTROLLER,
                AutoConstants.THETA_CONTROLLER,
                drivetrainSubsystem::drive,
                eventMap,
                true,
                drivetrainSubsystem);
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
    public SwerveDrivePoseEstimator providesSwerveDrivePoserEstimator(WPI_Pigeon2 gyro, SwerveModulePosition[] positions) {
        return new SwerveDrivePoseEstimator(DriveConstants.DRIVE_KINEMATICS, gyro.getRotation2d(), positions, new Pose2d(0, 0, new Rotation2d()));
    }
}