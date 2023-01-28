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
    /****** IntakeMotors ******/
    @Provides
    @Singleton
    @Named("intake spark 1")
    public CANSparkMax providesIntakeSpark1(){
        return new CANSparkMax(Constants.IntakeConstants.INTAKE_SPARK_1, CANSparkMaxLowLevel.MotorType.kBrushless);
    }
    @Provides
    @Singleton
    @Named("intake spark 1")
    public CANSparkMax providesIntakeSpark2(){
        return new CANSparkMax(Constants.IntakeConstants.INTAKE_SPARK_2, CANSparkMaxLowLevel.MotorType.kBrushless);
    }




}