package frc.robot.di.devices;

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;
import org.photonvision.PhotonCamera;

import javax.inject.Named;
import javax.inject.Singleton;

@Module(includes = {MotorsModule.class, SolenoidsModule.class})
public class DeviceModule {

    @Provides
    @Singleton
    public DigitalInput providesLimitSwitch() {
        return new DigitalInput(Constants.CarriageConstants.CARRIAGE_LIMITSWITCH);
    }

    @Provides
    @Singleton
    public WPI_Pigeon2 providesGyro() {
        return new WPI_Pigeon2(Constants.DriveConstants.GYRO, "canivoreBus");
    }

    @Provides
    @Singleton
    public PhotonCamera providesCamera(){
        return new PhotonCamera("camera");
    }

    @Provides
    @Named(Constants.CarriageConstants.CARRIAGE_NEO_NAME)
    public CANSparkMax providesCarriageNeo() {
        return new CANSparkMax(Constants.CarriageConstants.CARRIAGE_NEO, CANSparkMaxLowLevel.MotorType.kBrushless);
    }
}