package frc.robot.di.devices;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import dagger.Module;
import dagger.Provides;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class MotorsModule {
    /****** IntakeMotors ******/
    @Provides
    @Singleton
    @Named("intakeSpark1")
    public CANSparkMax providesIntakeSpark1(){
        CANSparkMax intakeSpark1 = new CANSparkMax(Constants.IntakeConstants.INTAKE_SPARK_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        intakeSpark1.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, 250);
        intakeSpark1.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, 250);
        intakeSpark1.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus3, 250);

        intakeSpark1.setSmartCurrentLimit(40);
        intakeSpark1.enableVoltageCompensation(Constants.IntakeConstants.INTAKE_VOLTAGE_COMP);

        return intakeSpark1;
    }
    @Provides
    @Singleton
    @Named("intakeSpark2")
    public CANSparkMax providesIntakeSpark2(CANSparkMax intakeSpark1){
        CANSparkMax intakeSpark2= new CANSparkMax(Constants.IntakeConstants.INTAKE_SPARK_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        intakeSpark2.follow(intakeSpark1, true);
        intakeSpark2.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, Constants.SPARK_PERIODMS);
        intakeSpark2.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, Constants.SPARK_PERIODMS);
        intakeSpark2.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus3, Constants.SPARK_PERIODMS);

        intakeSpark2.setSmartCurrentLimit(40);
        intakeSpark2.enableVoltageCompensation(Constants.IntakeConstants.INTAKE_VOLTAGE_COMP);

        return intakeSpark2;

    }




}