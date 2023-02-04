package frc.robot.di;

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.controllerUtils.ControllerContainer;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Map;
import java.util.function.DoubleSupplier;

@Module
public class RobotModule {
    @Provides
    @Singleton
    public RobotContainer providesRobotContainer(DrivetrainSubsystem drivetrainSubsystem, ControllerContainer controllerContainer, Map<Class<?>, CommandBase> commands) {
        return new RobotContainer(
                drivetrainSubsystem, controllerContainer, commands
        );
    }

    @Provides
    @Singleton
    public ControllerContainer providesController() {
        return new ControllerContainer();
    }


    @Provides
    @Named("translationXSupplier")
    public DoubleSupplier provideTranslationXSupplier(ControllerContainer controllerContainer) {
        return () -> controllerContainer.get(0).getLeftX();
    }

    @Provides
    @Named("translationYSupplier")
    public DoubleSupplier provideTranslationYSupplier(ControllerContainer controllerContainer) {
        return () -> controllerContainer.get(0).getLeftY();
    }

    @Provides
    @Named("rotationSupplier")
    public DoubleSupplier provideRotationSupplier(ControllerContainer controllerContainer) {
        return () -> controllerContainer.get(0).getRightX();
    }

    @Provides
    @Named("swerveModulePosition")
    public SwerveModulePosition[] providesSwerveModulePositions() {
        return new SwerveModulePosition[4];
    }

    @Provides
    @Singleton
    public SwerveDriveOdometry providesSwerveDriveOdometry (WPI_Pigeon2 gyro, @Named("swerveModulePosition") SwerveModulePosition[] positions){
        return new SwerveDriveOdometry(Constants.DriveConstants.DRIVE_KINEMATICS, gyro.getRotation2d(), positions);
    }

    @Provides
    @Singleton
    public ChassisSpeeds providesChassisSpeeds(){
        return new ChassisSpeeds();
    }

}