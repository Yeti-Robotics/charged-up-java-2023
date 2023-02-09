package frc.robot.di.devices;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
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
    @Named ("armMotor1")
    public WPI_TalonFX providesArmMotor1(){
        WPI_TalonFX motor = new WPI_TalonFX(Constants.ArmConstants.MOTOR_1);
        motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 70, 80, 0.1));
        motor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 70, 80, 0.1));
        motor.configVoltageCompSaturation(Constants.ArmConstants.VOLTAGE_COMP);
        motor.enableVoltageCompensation(true);
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);

        motor.setStatusFramePeriod(StatusFrame.Status_1_General.Status_1_General, 250);
        motor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20);


        return motor;


    }


    @Provides
    @Singleton
    @Named ("armMotor2")
    public WPI_TalonFX providesArmMotor2(@Named ("armMotor1")WPI_TalonFX motor1){
        WPI_TalonFX motor = new WPI_TalonFX(Constants.ArmConstants.MOTOR_2);
        motor.follow(motor1); //makes one motor follow whatever the other motor does//
        motor.setInverted(true); //make motor inverted compared to other motor //
        motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 70, 80, 0.1));
        motor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 70, 80, 0.1));
        motor.configVoltageCompSaturation(Constants.ArmConstants.VOLTAGE_COMP);
        motor.enableVoltageCompensation(true);
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);

        motor.setStatusFramePeriod(StatusFrame.Status_1_General.Status_1_General, 250);
        motor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20);

        return motor;

}
}