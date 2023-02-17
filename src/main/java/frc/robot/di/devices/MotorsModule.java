package frc.robot.di.devices;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
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
    @Named(Constants.IntakeConstants.INTAKE_SPARK_1_NAME)
    public CANSparkMax providesIntakeSpark1(){
        CANSparkMax intakeSpark1 = new CANSparkMax(Constants.IntakeConstants.INTAKE_SPARK_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        intakeSpark1.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, Constants.SparkConstants.SPARK_PERIODMS);
        intakeSpark1.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, Constants.SparkConstants.SPARK_PERIODMS);
        intakeSpark1.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus3, Constants.SparkConstants.SPARK_PERIODMS);
       intakeSpark1.getPIDController();


        intakeSpark1.setSmartCurrentLimit(Constants.SparkConstants.CURRENT_LIM);
        intakeSpark1.enableVoltageCompensation(Constants.IntakeConstants.INTAKE_VOLTAGE_COMP);
        return intakeSpark1;
    }
    @Provides
    @Singleton
    @Named(Constants.IntakeConstants.INTAKE_SPARK_2_NAME)
    public CANSparkMax providesIntakeSpark2(@Named ("intake spark 1") CANSparkMax intakeSpark1){
        CANSparkMax intakeSpark2= new CANSparkMax(Constants.IntakeConstants.INTAKE_SPARK_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        intakeSpark2.follow(intakeSpark1, true);
        intakeSpark2.setSmartCurrentLimit(Constants.SparkConstants.CURRENT_LIM);
        intakeSpark2.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, Constants.SparkConstants.SPARK_PERIODMS);
        intakeSpark2.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, Constants.SparkConstants.SPARK_PERIODMS);
        intakeSpark2.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus3, Constants.SparkConstants.SPARK_PERIODMS);

       intakeSpark2.enableVoltageCompensation(Constants.IntakeConstants.INTAKE_VOLTAGE_COMP);

        return intakeSpark2;

    }



}