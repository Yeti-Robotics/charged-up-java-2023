package frc.robot.di;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.utils.controllerUtils.ButtonHelper;
import frc.robot.utils.controllerUtils.ControllerContainer;

import javax.inject.Singleton;
import java.util.Map;

@Module
public class RobotModule {
    @Provides
    @Singleton
    public RobotContainer providesRobotContainer(IntakeSubsystem intakeSubsystem, ControllerContainer controllerContainer, Map<Class<?>, CommandBase> commands, ButtonHelper buttonHelper) {
        return new RobotContainer(
                intakeSubsystem, controllerContainer, commands, buttonHelper
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
}
