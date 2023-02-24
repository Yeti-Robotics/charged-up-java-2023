package frc.robot.di.devices;


import com.ctre.phoenix.sensors.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.IntakeConstants;

import javax.inject.Named;
import javax.inject.Singleton;

import static frc.robot.constants.DriveConstants.GYRO;

@Module(includes = {MotorsModule.class, SolenoidsModule.class})
public class DeviceModule {
    @Provides
    @Singleton
    public WPI_Pigeon2 providesGyro() {
        WPI_Pigeon2 gyro = new WPI_Pigeon2(GYRO, "canivoreBus");

        gyro.configMountPose(180.0, 0, 0);
        return gyro;
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

    @Provides
    @Singleton
    @Named(IntakeConstants.INTAKE_BEAM_BREAK)
    public SparkMaxLimitSwitch providesIntakeBeamBreak(@Named(IntakeConstants.LEFT_SPARK) CANSparkMax sparkMax) {
        return sparkMax.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    }

    @Provides
    @Singleton
    @Named(ArmConstants.ARM_ENCODER)
    public WPI_CANCoder providesArmEncoder() {
        WPI_CANCoder encoder = new WPI_CANCoder(ArmConstants.ARM_ENCODER_ID, "canivoreBus");

        encoder.configSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition);
        encoder.configAbsoluteSensorRange(AbsoluteSensorRange.Unsigned_0_to_360);
        encoder.configMagnetOffset(ArmConstants.ENCODER_OFFSET);
        encoder.configSensorDirection(ArmConstants.ARM_ENCODER_REVERSE);
        encoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData, 20);
        encoder.setStatusFramePeriod(CANCoderStatusFrame.VbatAndFaults, 250);
        encoder.setPositionToAbsolute();

        return encoder;
    }
    @Provides
    @Named(IntakeConstants.INTAKE_REED_SWITCH)
    public SparkMaxLimitSwitch providesIntakeReedSwitch(@Named(IntakeConstants.RIGHT_SPARK) CANSparkMax sparkMax) {
        return sparkMax.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    }

    @Provides
    @Singleton
    @Named(ElevatorConstants.ELEVATOR_MAG_SWITCH)
    public DigitalInput providesElevatorMagSwitch(){
        return new DigitalInput(ElevatorConstants.MAG_SWITCH_PORT);
    }
}