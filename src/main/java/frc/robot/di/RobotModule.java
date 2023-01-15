package frc.robot.di;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.utils.controllerUtils.ControllerContainer;

import javax.inject.Singleton;
import java.util.Map;

@Module
public class RobotModule {
    @Provides
    @Singleton
    public RobotContainer providesRobotContainer(ExampleSubsystem exampleSubsystem,ControllerContainer controllerContainer, Map<Class<?>, CommandBase> commands) {
        return new RobotContainer(
                exampleSubsystem, controllerContainer, commands
        );
    }

    @Provides
    @Singleton
    public ControllerContainer providesController() {
        return new ControllerContainer();
    }

}