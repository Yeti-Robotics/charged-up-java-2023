package frc.robot.di.devices;

import com.revrobotics.*;
import dagger.Module;
import dagger.Provides;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;
import javax.inject.Named;
import javax.inject.Singleton;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import dagger.Module;

@Module
public class MotorsModule {
    /****** IntakeMotors ******/
    @Provides
    @Singleton
    @Named(Constants.IntakeConstants.LEFT_SPARK)
    public CANSparkMax providesIntakeSpark1(){
        CANSparkMax sparkMax = new CANSparkMax(Constants.IntakeConstants.LEFT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        sparkMax.setInverted(false);

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

        sparkMax.setSmartCurrentLimit(Constants.SparkConstants.CURRENT_LIM);
        sparkMax.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, Constants.SparkConstants.SPARK_PERIODMS);
        sparkMax.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, Constants.SparkConstants.SPARK_PERIODMS);
        sparkMax.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus3, Constants.SparkConstants.SPARK_PERIODMS);

        return sparkMax;
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