package frc.robot.di.devices;

import com.ctre.phoenix.sensors.*;
import dagger.Module;
import dagger.Provides;
import frc.robot.Constants.*;
import javax.inject.Singleton;

@Module(includes = {MotorsModule.class})
public class DeviceModule {
    @Provides
    @Singleton
    public WPI_Pigeon2 providesGyro() {
        return new WPI_Pigeon2(DriveConstants.GYRO, "canivoreBus");
    }

    public static WPI_CANCoder absoluteEncoderFactory(int id, double degreesOffset, boolean reversed) {
        WPI_CANCoder absoluteEncoder = new WPI_CANCoder(id, "canivoreBus");
        absoluteEncoder.configAbsoluteSensorRange(AbsoluteSensorRange.Signed_PlusMinus180);
        absoluteEncoder.configMagnetOffset(degreesOffset);
        absoluteEncoder.configSensorDirection(reversed);
        absoluteEncoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData, 20);
        absoluteEncoder.setStatusFramePeriod(CANCoderStatusFrame.VbatAndFaults, 250);
        return absoluteEncoder;
    }
}