package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CarriageConstants;
import frc.robot.constants.CarriageConstants.CarriagePositions;

import javax.inject.Inject;
import javax.inject.Named;

public class CarriageSubsystem extends SubsystemBase {
    private final CANSparkMax rollerMotor;
    private final TalonFX flipMotor;

    private CarriagePositions carriagePosition;

    @Inject
    public CarriageSubsystem(
            @Named(CarriageConstants.ROLLER_SPARK) CANSparkMax rollerMotor,
            @Named(CarriageConstants.FLIP_MOTOR_NAME) TalonFX flipMotor){
            //@Named(CarriageConstants.FLIP_MOTOR_PID_NAME)SparkMaxPIDController flipPIDController)

        this.rollerMotor = rollerMotor;
        this.flipMotor = flipMotor;
        //this.flipPIDController = flipPIDController;
    }

    public void coneInCubeOut(){
        rollerMotor.set(-CarriageConstants.ROLLER_SPEED);
    }

    public void coneOutCubeIn(){
        rollerMotor.set(CarriageConstants.ROLLER_SPEED);
    }

    public double getRollerCurrent() {
        return Math.abs(rollerMotor.getOutputCurrent());
    }

    public void rollerStop(){
        rollerMotor.stopMotor();
    }

    //Check if correct method used
    public double getAngle() {
        return flipMotor.getSelectedSensorPosition();
    }

    public void setSetpoint(CarriagePositions setpoint){
        carriagePosition = setpoint;
        double radians = Math.toRadians(getAngle());
        double cosineScalar = Math.cos(radians);

        double FLIP_FEED_FORWARD = CarriageConstants.GRAVITY_FEEDFORWARD * cosineScalar;
        //flipPIDController.setReference(setpoint.angle, CANSparkMax.ControlType.kPosition, 0,
                //FLIP_FEED_FORWARD, SparkMaxPIDController.ArbFFUnits.kPercentOut); //make command later
    }

    //Check if correct method used
    public void flipOut() {
        flipMotor.set(TalonFXControlMode.PercentOutput, CarriageConstants.FLIP_SPEED);
    }

    //Check if correct method used
    public void flipIn() {
        flipMotor.set(ControlMode.PercentOutput,-CarriageConstants.FLIP_SPEED);
    }

    //Check if correct method used
    public void stopFlipMechanism() {
        flipMotor.set(ControlMode.PercentOutput, 0);
    }

    public CarriagePositions getCarriagePosition() {
        return carriagePosition;
    }
}

