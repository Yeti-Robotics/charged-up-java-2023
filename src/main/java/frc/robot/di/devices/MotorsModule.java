package frc.robot.di.devices;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
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
    @Named(Constants.CarriageConstants.ROLLER_MOTOR_NAME)
    public CANSparkMax rollerMotor() {
        CANSparkMax rollerMotor = new CANSparkMax(Constants.CarriageConstants.ROLLER_NEO, CANSparkMaxLowLevel.MotorType.kBrushless);

        rollerMotor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, 250);
        rollerMotor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, 250);
        rollerMotor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus3, 250);
        rollerMotor.setSmartCurrentLimit(40);
        rollerMotor.enableVoltageCompensation(Constants.CarriageConstants.CARRIAGE_VOLTAGE_COMP);
        rollerMotor.getEncoder().setVelocityConversionFactor(Constants.CarriageConstants.ROLLER_RATIO);

        return rollerMotor;
    }

    @Provides
    @Singleton
    @Named(Constants.CarriageConstants.FLIP_MOTOR_NAME)
    public CANSparkMax flipMotor() {
        CANSparkMax flipMotor = new CANSparkMax(Constants.CarriageConstants.FLIP_NEO, CANSparkMaxLowLevel.MotorType.kBrushless);
        flipMotor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus1, 250);
        flipMotor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2, 250);
        flipMotor.setPeriodicFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus3, 250);
        flipMotor.setSmartCurrentLimit(40);
        flipMotor.enableVoltageCompensation(Constants.CarriageConstants.CARRIAGE_VOLTAGE_COMP);
        flipMotor.getEncoder().setPositionConversionFactor(Constants.CarriageConstants.FLIP_RATIO);

        return flipMotor;
    }

    @Provides
    @Singleton
    @Named(Constants.CarriageConstants.FLIP_MOTOR_PID_NAME)
    public SparkMaxPIDController carriageFlipMotorPID(@Named(Constants.CarriageConstants.FLIP_MOTOR_NAME) CANSparkMax flipMotor) {
        SparkMaxPIDController pidController = flipMotor.getPIDController();
        pidController.setFeedbackDevice(flipMotor.getEncoder());
        pidController.setP(Constants.CarriageConstants.FLIP_P);
        pidController.setI(Constants.CarriageConstants.FLIP_I);
        pidController.setD(Constants.CarriageConstants.FLIP_D);
        pidController.setFF(Constants.CarriageConstants.FLIP_F);

        return pidController;
    }
}