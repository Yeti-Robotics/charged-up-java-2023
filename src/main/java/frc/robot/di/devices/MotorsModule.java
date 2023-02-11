package frc.robot.di.devices;

import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import dagger.Module;
import dagger.Provides;
import frc.robot.Constants;
import frc.robot.subsystems.ElevatorSubsystem;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class MotorsModule {
    @Provides
    public SupplyCurrentLimitConfiguration elevatorCurrentConfig(){
        return new SupplyCurrentLimitConfiguration(true, 0.0, 0.0, 0.0);
    }

    @Provides
    @Singleton
    @Named("elevatorMotor")
    public WPI_TalonFX elevatorMotor(SupplyCurrentLimitConfiguration elevatorCurrentConfig) {
        WPI_TalonFX motor = new WPI_TalonFX(Constants.ElevatorConstants.ELEVATOR_MOTOR, "canivoreBus");

        motor.config_kP(0, Constants.ElevatorConstants.ELEVATOR_P);
        motor.config_kI(0, Constants.ElevatorConstants.ELEVATOR_I);
        motor.config_kD(0, Constants.ElevatorConstants.ELEVATOR_D);
        motor.configMotionCruiseVelocity((int) (Constants.ElevatorConstants.ELEVATOR_CRUISING_VELOCITY));
        motor.configMotionAcceleration((int) (Constants.ElevatorConstants.ELEVATOR_CRUISING_ACCELERATION));
        motor.configAllowableClosedloopError(0, Constants.ElevatorConstants.ELEVATOR_TOLERANCE);
        motor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10);
        motor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);
        motor.configPeakOutputForward(.6);
        motor.configSupplyCurrentLimit(elevatorCurrentConfig);
        motor.configClosedloopRamp(0.3);







        return motor;
    }

}