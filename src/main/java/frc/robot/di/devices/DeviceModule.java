package frc.robot.di.devices;

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import dagger.Module;
import dagger.Provides;
import frc.robot.Constants;
import frc.robot.di.devices.MotorsModule;
import javax.inject.Singleton;

@Module(includes = {MotorsModule.class})
public class DeviceModule {
    @Provides
    @Singleton
    public WPI_Pigeon2 providesGyro() {
        return new WPI_Pigeon2(Constants.DriveConstants.GYRO);
    }
}