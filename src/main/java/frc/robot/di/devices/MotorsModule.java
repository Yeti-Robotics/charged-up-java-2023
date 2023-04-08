package frc.robot.di.devices;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import dagger.Module;
import dagger.Provides;
import frc.robot.constants.*;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class MotorsModule {
    @Provides
    @Singleton
    @Named(ArmConstants.ARM_MOTOR)
    public WPI_TalonFX providesArmMotor1(@Named(ArmConstants.ARM_ENCODER) WPI_CANCoder armEncoder) {
        WPI_TalonFX motor = new WPI_TalonFX(ArmConstants.ARM_MOTOR_ID, "canivoreBus");
        motor.setSensorPhase(true);
        motor.setInverted(TalonFXInvertType.Clockwise);

        motor.configSupplyCurrentLimit(ArmConstants.SUPPLY_CURRENT_LIMIT);
        motor.configStatorCurrentLimit(ArmConstants.STATOR_CURRENT_LIMIT);

        motor.configRemoteFeedbackFilter(armEncoder, 0, 10);
        motor.configSelectedFeedbackSensor(RemoteFeedbackDevice.RemoteSensor0, 0, 10);

        motor.configForwardSoftLimitThreshold(ArmConstants.UPPER_LIMIT);
        motor.configForwardSoftLimitEnable(true);
        motor.configReverseSoftLimitThreshold(ArmConstants.LOWER_LIMIT);
        motor.configReverseSoftLimitEnable(true);

        motor.setStatusFramePeriod(StatusFrame.Status_1_General, 250);
        motor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20);

        motor.config_kP(0, ArmConstants.ARM_P);
        motor.config_kI(0, ArmConstants.ARM_I);
        motor.config_kD(0, ArmConstants.ARM_D);
        motor.config_kF(0, ArmConstants.ARM_F);
        motor.configMotionCruiseVelocity(ArmConstants.MAX_VELOCITY);
        motor.configMotionAcceleration(ArmConstants.MAX_ACCELERATION);
        motor.configMotionSCurveStrength(ArmConstants.MOTION_SMOOTHING);
        motor.configAllowableClosedloopError(0,
                (ArmConstants.ANGLE_TOLERANCE / ArmConstants.GEAR_RATIO)
                        * CANCoderConstants.COUNTS_PER_DEG);

        return motor;
    }

    @Provides
    @Singleton
    @Named(ElevatorConstants.ELEVATOR_MOTOR)
    public WPI_TalonFX elevatorMotor() {
        WPI_TalonFX motor = new WPI_TalonFX(ElevatorConstants.ELEVATOR_MOTOR_ID, "canivoreBus");
        motor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);

        motor.configForwardSoftLimitThreshold(ElevatorConstants.ELEVATOR_FORWARD_SOFT_LIMIT); //Need to check convertInchesToCounts
        motor.configForwardSoftLimitEnable(true);
        motor.configReverseSoftLimitThreshold(ElevatorConstants.ELEVATOR_REVERSE_SOFT_LIMIT); //Need to check convertInchesToCounts
        motor.configReverseSoftLimitEnable(true);

        motor.setInverted(TalonFXInvertType.Clockwise);
        motor.setNeutralMode(NeutralMode.Brake);

        motor.configSupplyCurrentLimit(ElevatorConstants.SUPPLY_CURRENT_LIMIT);
        motor.configStatorCurrentLimit(ElevatorConstants.STATOR_CURRENT_LIMIT);

        motor.config_kP(0, ElevatorConstants.ELEVATOR_P);
        motor.config_kI(0, ElevatorConstants.ELEVATOR_I);
        motor.config_kD(0, ElevatorConstants.ELEVATOR_D);
        motor.config_kF(0, ElevatorConstants.ELEVATOR_F);
        motor.config_IntegralZone(0, ElevatorConstants.IZONE);
        motor.configMotionCruiseVelocity(ElevatorConstants.MAX_VELOCITY);
        motor.configMotionAcceleration(ElevatorConstants.MAX_ACCEL);
        motor.configMotionSCurveStrength(ElevatorConstants.SMOOTHING);
        motor.configAllowableClosedloopError(0, ElevatorConstants.ELEVATOR_TOLERANCE);

        motor.setStatusFramePeriod(StatusFrame.Status_1_General, 50);
        motor.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 10);

        return motor;
    }

    /****** IntakeMotors ******/
    @Provides
    @Singleton
    @Named(IntakeConstants.SPARK)
    public CANSparkMax providesLeftIntakeSpark() {
        CANSparkMax sparkMax = new CANSparkMax(IntakeConstants.SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        sparkMax.setInverted(false);
        sparkMax.setIdleMode(CANSparkMax.IdleMode.kBrake);
        sparkMax.enableVoltageCompensation(IntakeConstants.VOLTAGE_COMP);

        sparkMax.setSmartCurrentLimit(30);
        sparkMax.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus0, SparkMaxConstants.HIGH_PRIORITY_MS);
        sparkMax.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, SparkMaxConstants.MEDIUM_PRIORITY_MS);
        sparkMax.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, SparkMaxConstants.MEDIUM_PRIORITY_MS);
        sparkMax.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus3, SparkMaxConstants.LOW_PRIORITY_MS);
        sparkMax.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus4, SparkMaxConstants.LOW_PRIORITY_MS);

        return sparkMax;
    }

    @Provides
    @Singleton
    @Named(WristConstants.WRIST_MOTOR)
    public WPI_TalonFX providesWristMotor(@Named(WristConstants.WRIST_ENCODER) WPI_CANCoder wristEncoder) {
        WPI_TalonFX motor = new WPI_TalonFX(WristConstants.WRIST_MOTOR_ID, "canivoreBus");
        //motor.setSelectedSensorPosition();
        motor.configSelectedFeedbackSensor(RemoteFeedbackDevice.RemoteSensor0, 0, 10);

        return motor;
    }


    //Check if implementation of each method


    public static WPI_TalonFX driveMotorFactory(int id, boolean driveInverted) {
        WPI_TalonFX driveMotor = new WPI_TalonFX(id, "canivoreBus");
        driveMotor.setNeutralMode(NeutralMode.Brake);
        driveMotor.setInverted(driveInverted);
        driveMotor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 60, 65, 0.1));
        driveMotor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 60, 65, 0.1));
        driveMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        driveMotor.setStatusFramePeriod(StatusFrame.Status_1_General, 250);
        driveMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20);
        driveMotor.configOpenloopRamp(0.0);

        return driveMotor;
    }

    public static WPI_TalonFX azimuthMotorFactory(int id) {
        WPI_TalonFX steerMotor = new WPI_TalonFX(id, "canivoreBus");
        steerMotor.setNeutralMode(NeutralMode.Brake);
        steerMotor.setInverted(true);
        steerMotor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 60, 65, 0.1));
        steerMotor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 60, 65, 0.1));

        steerMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);


        steerMotor.setStatusFramePeriod(StatusFrame.Status_1_General, 250);
        steerMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 250);
        return steerMotor;
    }
}