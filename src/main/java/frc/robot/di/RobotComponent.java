package frc.robot.di;

import dagger.Component;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.di.devices.DeviceModule;

import javax.inject.Singleton;

@Singleton
@Component(modules =
        {RobotModule.class, SubsystemsModule.class, CommandsModule.class, DeviceModule.class, RESTModule.class})
public interface RobotComponent {
    @Component.Builder
    interface Builder {
        RobotComponent build();
    }

    void inject(Robot robot);

    RobotContainer container();
}