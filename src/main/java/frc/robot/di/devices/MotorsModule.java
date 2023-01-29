package frc.robot.di.devices;

import com.ctre.phoenix.motorcontrol.FollowerType;
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
    public WPI_TalonFX proviesArmMotor1(){
        WPI_TalonFX motor = new WPI_TalonFX(Constants.ArmConstants.MOTOR_1);
        return motor;


    }


    @Provides
    @Singleton
    @Named ("armMotor2")
    public WPI_TalonFX proviesArmMotor2(@Named ("armMotor1")WPI_TalonFX motor1){
        WPI_TalonFX motor = new WPI_TalonFX(Constants.ArmConstants.MOTOR_2);
        motor.follow(motor1); //makes one motor follow whatever the other motor does//
        motor.setInverted(true); //make motor inverted compared to other motor //
        return motor;

}
}