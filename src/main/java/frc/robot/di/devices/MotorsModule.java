package frc.robot.di.devices;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import dagger.Module;
import dagger.Provides;

import frc.robot.Constants;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class MotorsModule {

    @Provides
    @Singleton
    @Named(Constants.ArmConstants.ARM_MOTOR_1)
    public WPI_TalonFX providesArmMotor1(@Named("armEncoder") WPI_CANCoder armEncoder) {
        WPI_TalonFX motor = new WPI_TalonFX(Constants.ArmConstants.ARM_MOTOR_1_ID, "canivoreBus");
        motor.setSensorPhase(true);
        motor.setInverted(TalonFXInvertType.Clockwise);

        motor.configSupplyCurrentLimit(Constants.ArmConstants.SUPPLY_CURRENT_LIMIT);
        motor.configStatorCurrentLimit(Constants.ArmConstants.STATOR_CURRENT_LIMIT);

        motor.configRemoteFeedbackFilter(armEncoder, 0, 10);
        motor.configSelectedFeedbackSensor(RemoteFeedbackDevice.RemoteSensor0, 0, 10);

        motor.configForwardSoftLimitThreshold(Constants.ArmConstants.UPPER_LIMIT);
        motor.configForwardSoftLimitEnable(true);
        motor.configReverseSoftLimitThreshold(Constants.ArmConstants.LOWER_LIMIT);
        motor.configReverseSoftLimitEnable(true);

        motor.setStatusFramePeriod(StatusFrame.Status_1_General, 250);
        motor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20);

        motor.config_kP(0, Constants.ArmConstants.ARM_P);
        motor.config_kI(0, Constants.ArmConstants.ARM_I);
        motor.config_kD(0, Constants.ArmConstants.ARM_D);
        motor.config_kF(0, Constants.ArmConstants.ARM_F);
        motor.configMotionCruiseVelocity(Constants.ArmConstants.MAX_VELOCITY);
        motor.configMotionAcceleration(Constants.ArmConstants.MAX_ACCELERATION);
        motor.configMotionSCurveStrength(Constants.ArmConstants.MOTION_SMOOTHING);

        return motor;
    }
    @Provides
    @Singleton
    @Named(Constants.ArmConstants.ARM_MOTOR_2)
    public WPI_TalonFX providesArmMotor2(@Named(Constants.ArmConstants.ARM_MOTOR_1) WPI_TalonFX motor1) {
        WPI_TalonFX motor = new WPI_TalonFX(Constants.ArmConstants.ARM_MOTOR_2_ID, "canivoreBus");
        motor.follow(motor1);
        motor.setInverted(InvertType.OpposeMaster);


        motor.configSupplyCurrentLimit(Constants.ArmConstants.SUPPLY_CURRENT_LIMIT);
        motor.configStatorCurrentLimit(Constants.ArmConstants.STATOR_CURRENT_LIMIT);

        motor.setStatusFramePeriod(StatusFrame.Status_1_General, 250);
        motor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 50);

        motor.config_kP(0, Constants.ArmConstants.ARM_P);
        motor.config_kI(0, Constants.ArmConstants.ARM_I);
        motor.config_kD(0, Constants.ArmConstants.ARM_D);
        motor.config_kF(0, Constants.ArmConstants.ARM_F);
        motor.configMotionCruiseVelocity(Constants.ArmConstants.MAX_VELOCITY);
        motor.configMotionAcceleration(Constants.ArmConstants.MAX_ACCELERATION);
        motor.configMotionSCurveStrength(Constants.ArmConstants.MOTION_SMOOTHING);

        return motor;
    }
}