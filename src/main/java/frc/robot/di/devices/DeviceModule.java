package frc.robot.di.devices;

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch;
import dagger.Module;
import dagger.Provides;
import frc.robot.Constants;

import javax.inject.Named;
import javax.inject.Singleton;

@Module(includes = {MotorsModule.class, SolenoidsModule.class})
public class DeviceModule {

    @Provides
    @Singleton
    public WPI_Pigeon2 providesGyro() {
        return new WPI_Pigeon2(Constants.DriveConstants.GYRO);
    }

    @Provides
    @Singleton
    @Named(Constants.IntakeConstants.INTAKE_BEAM_BREAK)
    public SparkMaxLimitSwitch providesIntakeBeamBreak(@Named(Constants.IntakeConstants.LEFT_SPARK) CANSparkMax sparkMax) {
        return sparkMax.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    }

    @Provides
    @Singleton
    @Named(Constants.IntakeConstants.INTAKE_REED_SWITCH)
    public SparkMaxLimitSwitch providesIntakeReedSwitch(@Named(Constants.IntakeConstants.RIGHT_SPARK) CANSparkMax sparkMax) {
        return sparkMax.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    }
}