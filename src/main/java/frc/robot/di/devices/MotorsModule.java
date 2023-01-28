package frc.robot.di.devices;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import dagger.Module;
import dagger.Provides;
import frc.robot.Constants;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class MotorsModule {
    @Provides
    @Singleton
    @Named ("sideNeo")
    public CANSparkMax providesSideNeo(){
        CANSparkMax sideNeo = new CANSparkMax(Constants.CarriageConstants.CARRIAGE_NEO, CANSparkMaxLowLevel.MotorType.kBrushless);
        sideNeo.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1,250);
        sideNeo.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2,250);
        sideNeo.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus3,250);
        sideNeo.setSmartCurrentLimit(40);
        sideNeo.enableVoltageCompensation(Constants.CarriageConstants.CARRIAGE_VOLTAGE_COMP);
        return sideNeo;
    }
}