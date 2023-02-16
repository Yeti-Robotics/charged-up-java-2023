package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.utils.controllerUtils.ButtonHelper;
import frc.robot.utils.controllerUtils.ControllerContainer;

import javax.inject.Singleton;
import java.util.Map;

@Module
public class RobotModule {
    @Provides
    @Singleton
    public RobotContainer providesRobotContainer(
            ElevatorSubsystem elevatorSubsystem,
            ControllerContainer controllerContainer,
            Map<Class<?>, CommandBase> commands,
            ButtonHelper buttonHelper) {
        return new RobotContainer(
                elevatorSubsystem,
                controllerContainer,
                commands,
                buttonHelper);
    }

    @Provides
    @Singleton
    public ControllerContainer providesController() {
        return new ControllerContainer();
    }

    @Provides
    @Singleton
    public ButtonHelper providesButtonHelper(ControllerContainer controllerContainer) {
        return new ButtonHelper(controllerContainer.getControllers());
    }
}