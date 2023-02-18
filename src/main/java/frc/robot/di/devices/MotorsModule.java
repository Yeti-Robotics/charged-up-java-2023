package frc.robot.di.devices;


import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
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

    /****** IntakeMotors ******/
    @Provides
    @Singleton
    @Named(Constants.IntakeConstants.LEFT_SPARK)
    public CANSparkMax providesIntakeSpark1(){
        CANSparkMax sparkMax = new CANSparkMax(Constants.IntakeConstants.LEFT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        sparkMax.setInverted(false);
        sparkMax.setIdleMode(CANSparkMax.IdleMode.kBrake);

        sparkMax.setSmartCurrentLimit(Constants.SparkConstants.CURRENT_LIM);
        sparkMax.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, Constants.SparkConstants.SPARK_PERIODMS);
        sparkMax.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, Constants.SparkConstants.SPARK_PERIODMS);
        sparkMax.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus3, Constants.SparkConstants.SPARK_PERIODMS);
        sparkMax.getPIDController();

        return sparkMax;
    }
    @Provides
    @Singleton
    @Named(Constants.IntakeConstants.RIGHT_SPARK)
    public CANSparkMax providesIntakeSpark2(@Named(Constants.IntakeConstants.LEFT_SPARK) CANSparkMax sparkMaxZero){
        CANSparkMax sparkMax = new CANSparkMax(Constants.IntakeConstants.RIGHT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        sparkMax.follow(sparkMaxZero, true);
        sparkMax.setIdleMode(CANSparkMax.IdleMode.kBrake);

        sparkMax.setSmartCurrentLimit(Constants.SparkConstants.CURRENT_LIM);
        sparkMax.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, Constants.SparkConstants.SPARK_PERIODMS);
        sparkMax.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, Constants.SparkConstants.SPARK_PERIODMS);

        return sparkMax;

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
    @Provides
    @Singleton
    @Named(Constants.IntakeConstants.INTAKE_PID)
    public SparkMaxPIDController provideIntakePID(
            @Named(Constants.IntakeConstants.LEFT_SPARK) CANSparkMax sparkMaxZero,
            @Named(Constants.IntakeConstants.INTAKE_ENCODER) RelativeEncoder encoder) {
        SparkMaxPIDController pidController = sparkMaxZero.getPIDController();
        pidController.setFeedbackDevice(encoder);

        pidController.setP(Constants.IntakeConstants.INTAKE_P);
        pidController.setI(Constants.IntakeConstants.INTAKE_I);
        pidController.setD(Constants.IntakeConstants.INTAKE_D);
        pidController.setFF(Constants.IntakeConstants.INTAKE_F);

        return pidController;
    }

    @Provides
    @Singleton
    @Named(Constants.IntakeConstants.INTAKE_ENCODER)
    public RelativeEncoder provideIntakeEncoder(@Named(Constants.IntakeConstants.LEFT_SPARK) CANSparkMax sparkMaxZero) {
        RelativeEncoder encoder = sparkMaxZero.getEncoder();

        encoder.setVelocityConversionFactor(Constants.IntakeConstants.VELOCITY_CONVERSION);

        return encoder;
    }

    public static WPI_TalonFX driveMotorFactory(int id, boolean driveInverted) {
        WPI_TalonFX driveMotor = new WPI_TalonFX(id, "canivoreBus");
        driveMotor.setNeutralMode(NeutralMode.Brake);
        driveMotor.setInverted(driveInverted);
        driveMotor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 60, 65, 0.1));
        driveMotor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 60, 65, 0.1));
        driveMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        driveMotor.setStatusFramePeriod(StatusFrame.Status_1_General, 250);
        driveMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20);
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