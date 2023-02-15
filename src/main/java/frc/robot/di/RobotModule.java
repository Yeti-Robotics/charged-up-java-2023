package frc.robot.di;

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.controllerUtils.ButtonHelper;
import frc.robot.utils.controllerUtils.ControllerContainer;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Map;
import java.util.function.DoubleSupplier;

@Module
public class RobotModule {
    @Provides
    @Singleton
    public RobotContainer providesRobotContainer(
            DrivetrainSubsystem drivetrainSubsystem,
            ControllerContainer controllerContainer,
            ButtonHelper buttonHelper,
            Map<Class<?>, CommandBase> commands) {
        return new RobotContainer(
                drivetrainSubsystem,
                controllerContainer,
                buttonHelper,
                commands
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

    /*
     * The X axis is forward and backward
     */

    @Provides
    @Named("translationXSupplier")
    public DoubleSupplier provideTranslationXSupplier(ControllerContainer controllerContainer) {
        return () -> controllerContainer.get(0).getLeftY();
    }

    /*
     * The Y is side to side
     */
    @Provides
    @Named("translationYSupplier")
    public DoubleSupplier provideTranslationYSupplier(ControllerContainer controllerContainer) {
        return () -> controllerContainer.get(0).getLeftX();
    }

    @Provides
    @Named("thetaSupplier")
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