package frc.robot.di.devices;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import dagger.Module;
import dagger.Provides;
import frc.robot.Constants;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class MotorsModule {
    @Provides
    @Singleton
    @Named ("armMotor1")
    public WPI_TalonFX proviesArmMotor1(){
        WPI_TalonFX motor = new WPI_TalonFX(Constants.ArmConstants.MOTOR_1);
        return motor;


    }


    @Provides
    @Singleton
    @Named ("armMotor2")
    public WPI_TalonFX proviesArmMotor2(){
        WPI_TalonFX motor = new WPI_TalonFX(Constants.ArmConstants.MOTOR_2);
        return motor;
}
}