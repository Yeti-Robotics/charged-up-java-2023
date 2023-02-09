package frc.robot.di.devices;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxLimitSwitch;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj.Relay;
import frc.robot.Constants;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class MotorsModule {
    @Provides
    @Singleton
    @Named ("carriageNeo")
    public CANSparkMax providesCarriageNeo(){
        CANSparkMax carriageNeo = new CANSparkMax(Constants.CarriageConstants.CARRIAGE_NEO, CANSparkMaxLowLevel.MotorType.kBrushless);
        carriageNeo.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1,250);
        carriageNeo.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2,250);
        carriageNeo.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus3,250);
        carriageNeo.setSmartCurrentLimit(40);
        carriageNeo.enableVoltageCompensation(Constants.CarriageConstants.CARRIAGE_VOLTAGE_COMP);
        return carriageNeo;
    }

//    public SparkMaxLimitSwitch providesbeamBreak() {
//        SparkMaxLimitSwitch beamBreak = new SparkMaxLimitSwitch(providesSideNeo(), SparkMaxLimitSwitch.Direction.kForward, SparkMaxLimitSwitch.Type.kNormallyOpen);
//        return beamBreak;
//    } PAIN
}