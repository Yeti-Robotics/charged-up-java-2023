package frc.robot.di.devices;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoderStatusFrame;
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
    @Singleton
    @Named("armEncoder")
    public WPI_CANCoder providesArmEncoder()        {
        WPI_CANCoder encoder = new WPI_CANCoder(Constants.ArmConstants.ARM_ENCODER, "canivoreBus");

        encoder.configAbsoluteSensorRange(AbsoluteSensorRange.Unsigned_0_to_360);
        encoder.configMagnetOffset(Constants.ArmConstants.ENCODER_OFFSET);
        encoder.configSensorDirection(Constants.ArmConstants.ARM_ENCODER_REVERSE);
        encoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData, 20);
        encoder.setStatusFramePeriod(CANCoderStatusFrame.VbatAndFaults, 250);

        return encoder;
    }
}