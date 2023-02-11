package frc.robot.di.devices;


import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import dagger.Module;
import dagger.Provides;
=======
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

    @Named ("armMotor1")
    public WPI_TalonFX providesArmMotor1(){
        WPI_TalonFX motor = new WPI_TalonFX(Constants.ArmConstants.MOTOR_1, "canivoreBus");
        motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 70, 80, 0.1));
        motor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 70, 80, 0.1));
        motor.configVoltageCompSaturation(Constants.ArmConstants.VOLTAGE_COMP);
        motor.enableVoltageCompensation(true);
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);

        motor.setStatusFramePeriod(StatusFrame.Status_1_General.Status_1_General, 250);
        motor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20);
        motor.config_kP(Constants.ArmConstants.MOTOR_1, Constants.ArmConstants.ARM_P);
        motor.config_kI(Constants.ArmConstants.MOTOR_1, Constants.ArmConstants.ARM_I);
        motor.config_kD(Constants.ArmConstants.MOTOR_1, Constants.ArmConstants.ARM_D);
        motor.config_kF(Constants.ArmConstants.MOTOR_1, Constants.ArmConstants.ARM_F);


        return motor;


    }


    @Provides
    @Singleton
    @Named ("armMotor2")
    public WPI_TalonFX providesArmMotor2(@Named ("armMotor1")WPI_TalonFX motor1){
        WPI_TalonFX motor = new WPI_TalonFX(Constants.ArmConstants.MOTOR_2, "canivoreBus");
        motor.follow(motor1); //makes one motor follow whatever the other motor does//
        motor.setInverted(true); //make motor inverted compared to other motor //
        motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 70, 80, 0.1));
        motor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 70, 80, 0.1));
        motor.configVoltageCompSaturation(Constants.ArmConstants.VOLTAGE_COMP);
        motor.enableVoltageCompensation(true);
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);

        motor.setStatusFramePeriod(StatusFrame.Status_1_General.Status_1_General, 250);
        motor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20);
        motor.config_kP(Constants.ArmConstants.MOTOR_2, Constants.ArmConstants.ARM_P);
        motor.config_kI(Constants.ArmConstants.MOTOR_2, Constants.ArmConstants.ARM_I);
        motor.config_kD(Constants.ArmConstants.MOTOR_2, Constants.ArmConstants.ARM_D);
        motor.config_kF(Constants.ArmConstants.MOTOR_2, Constants.ArmConstants.ARM_F);


        return motor;

}
=======
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