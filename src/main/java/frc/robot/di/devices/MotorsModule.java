package frc.robot.di.devices;

import com.ctre.phoenix.motorcontrol.*;
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
    @Named(Constants.ElevatorConstants.ELEVATOR_MOTOR)
    public WPI_TalonFX elevatorMotor() {
        WPI_TalonFX motor = new WPI_TalonFX(Constants.ElevatorConstants.ELEVATOR_MOTOR_ID, "canivoreBus");
        motor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);

        motor.configForwardSoftLimitThreshold(Constants.ElevatorConstants.ELEVATOR_FORWARD_SOFT_LIMIT); //Need to check convertInchesToCounts
        motor.configForwardSoftLimitEnable(true);
        motor.configReverseSoftLimitThreshold(Constants.ElevatorConstants.ELEVATOR_REVERSE_SOFT_LIMIT); //Need to check convertInchesToCounts
        motor.configReverseSoftLimitEnable(true);

        motor.setInverted(TalonFXInvertType.Clockwise);
        motor.setNeutralMode(NeutralMode.Brake);

        motor.configSupplyCurrentLimit(Constants.ElevatorConstants.SUPPLY_CURRENT_LIMIT);
        motor.configStatorCurrentLimit(Constants.ElevatorConstants.STATOR_CURRENT_LIMIT);

        motor.config_kP(0, Constants.ElevatorConstants.ELEVATOR_P);
        motor.config_kI(0, Constants.ElevatorConstants.ELEVATOR_I);
        motor.config_kD(0, Constants.ElevatorConstants.ELEVATOR_D);
        motor.config_kF(0, Constants.ElevatorConstants.ELEVATOR_F);
        motor.config_IntegralZone(0, Constants.ElevatorConstants.IZONE);
        motor.configMotionCruiseVelocity(Constants.ElevatorConstants.MAX_VELOCITY);
        motor.configMotionAcceleration(Constants.ElevatorConstants.MAX_ACCEL);
        motor.configMotionSCurveStrength(Constants.ElevatorConstants.SMOOTHING);
        motor.configAllowableClosedloopError(0, Constants.ElevatorConstants.ELEVATOR_TOLERANCE);

        motor.setStatusFramePeriod(StatusFrame.Status_1_General, 50);
        motor.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 10);

        return motor;
    }
}