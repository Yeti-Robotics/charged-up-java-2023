package frc.robot.di.devices;

import com.revrobotics.*;
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
}