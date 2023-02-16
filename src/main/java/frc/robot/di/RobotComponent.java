package frc.robot.di;

import dagger.Component;
import dagger.Provides;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.di.CommandsModule;
import frc.robot.di.RobotModule;
import frc.robot.di.SubsystemsModule;
import frc.robot.di.devices.DeviceModule;
import frc.robot.di.devices.SolenoidsModule;

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