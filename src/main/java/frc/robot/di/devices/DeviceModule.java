package frc.robot.di.devices;

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;

import javax.inject.Named;
import javax.inject.Singleton;

@Module(includes = {MotorsModule.class, SolenoidsModule.class})
public class DeviceModule {

    @Provides
    @Singleton
    public WPI_Pigeon2 providesGyro() {
        return new WPI_Pigeon2(Constants.DriveConstants.GYRO, "canivoreBus");
    }

    @Provides
    @Singleton
    @Named(Constants.ElevatorConstants.ELEVATOR_MAG_SWITCH)
    public DigitalInput providesElevatorMagSwitch(){
        return new DigitalInput(Constants.ElevatorConstants.MAG_SWITCH_PORT);
    }
}