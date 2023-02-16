package frc.robot.di.devices;

import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.ctre.phoenix.sensors.WPI_Pigeon2;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;
import frc.robot.di.devices.MotorsModule;

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
    @Named("elevatorBeamBreak")
    public DigitalInput providesbeamBreak(){
        return new DigitalInput(Constants.ElevatorConstants.BEAM_BREAK);
    }

    @Provides
    @Named("elevatorEncoder")
    public WPI_CANCoder providesEncoder(){return new WPI_CANCoder(Constants.ElevatorConstants.ELEVATOR_ENCODER);}
}