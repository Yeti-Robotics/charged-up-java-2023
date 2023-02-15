package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.utils.controllerUtils.ControllerContainer;

import javax.inject.Singleton;
import java.util.Map;

@Module
public class RobotModule {
    @Provides
    @Singleton
    public RobotContainer providesRobotContainer(ArmSubsystem armSubsystem, ControllerContainer controllerContainer, Map<Class<?>, CommandBase> commands) {
        return new RobotContainer(
                armSubsystem, controllerContainer, commands
        );
    }

    @Provides
    @Singleton
    public ControllerContainer providesController() {
        return new ControllerContainer();
    }

}