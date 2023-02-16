package frc.robot.di.devices;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import dagger.Module;
import dagger.Provides;
import frc.robot.Constants;

import javax.inject.Named;

@Module
public class MotorsModule {

    @Provides
    @Named("rollerMotor")
    public CANSparkMax rollerMotor() {
        CANSparkMax rollerMotor = new CANSparkMax(Constants.CarriageConstants.ROLLER_NEO, CANSparkMaxLowLevel.MotorType.kBrushless);

        rollerMotor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, 250);
        rollerMotor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, 250);
        rollerMotor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus3, 250);
        rollerMotor.setSmartCurrentLimit(40);
        rollerMotor.enableVoltageCompensation(Constants.CarriageConstants.CARRIAGE_VOLTAGE_COMP);

        return rollerMotor;
    }

    @Provides
    @Named("flipMotor")
    public CANSparkMax flipMotor() {
        CANSparkMax flipMotor = new CANSparkMax(Constants.CarriageConstants.CARRIAGE_NEO, CANSparkMaxLowLevel.MotorType.kBrushless);
        flipMotor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, 250);
        flipMotor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, 250);
        flipMotor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus3, 250);
        flipMotor.setSmartCurrentLimit(40);
        flipMotor.enableVoltageCompensation(Constants.CarriageConstants.CARRIAGE_VOLTAGE_COMP);



        return flipMotor;
    }

    @Provides
    @Named("carriageNeo")
    public CANSparkMax carriageNeo() {
        CANSparkMax carriageNeo = new CANSparkMax(Constants.CarriageConstants.CARRIAGE_NEO, CANSparkMaxLowLevel.MotorType.kBrushless);
        carriageNeo.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, 250);
        carriageNeo.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, 250);
        carriageNeo.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus3, 250);
        carriageNeo.setSmartCurrentLimit(40);
        carriageNeo.enableVoltageCompensation(Constants.CarriageConstants.CARRIAGE_VOLTAGE_COMP);

        return carriageNeo;
    }
}