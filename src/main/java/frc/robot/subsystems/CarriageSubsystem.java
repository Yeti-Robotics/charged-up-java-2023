package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CarriageConstants;
import frc.robot.Constants.CarriageConstants.CarriagePositions;

import javax.inject.Inject;
import javax.inject.Named;

public class CarriageSubsystem extends SubsystemBase {
    private final CANSparkMax rollerMotor;
    private final CANSparkMax flipMotor;
    private final SparkMaxPIDController flipPIDController;

    private CarriagePositions carriagePosition;

    @Inject
    public CarriageSubsystem(
            @Named(CarriageConstants.ROLLER_SPARK) CANSparkMax rollerMotor,
            @Named(CarriageConstants.FLIP_MOTOR_NAME) CANSparkMax flipMotor,
            @Named(CarriageConstants.FLIP_MOTOR_PID_NAME)SparkMaxPIDController flipPIDController) {
        this.rollerMotor = rollerMotor;
        this.flipMotor = flipMotor;
        this.flipPIDController = flipPIDController;
    }

    public void coneInCubeOut(){
        rollerMotor.set(-CarriageConstants.ROLLER_SPEED);
    }

    public void coneOutCubeIn(){
        rollerMotor.set(CarriageConstants.ROLLER_SPEED);
    }

    public double getRollerCurrent() {
        /* according to https://www.chiefdelphi.com/t/get-voltage-from-spark-max/344136/5 */
        return Math.abs(rollerMotor.getOutputCurrent());
    }


    public void rollerStop(){
        rollerMotor.stopMotor();
    }

    public double getAngle() {
        return flipMotor.getEncoder().getPosition();
    }

    public void setSetpoint(CarriagePositions setpoint){
        carriagePosition = setpoint;
        double radians = Math.toRadians(getAngle());
        double cosineScalar = Math.cos(radians);

        double FLIP_FEED_FORWARD = CarriageConstants.GRAVITY_FEEDFORWARD * cosineScalar;
        flipPIDController.setReference(setpoint.angle, CANSparkMax.ControlType.kPosition, 0,
                FLIP_FEED_FORWARD, SparkMaxPIDController.ArbFFUnits.kPercentOut); //make command later
    }

    public void flipMechanism(){
        carriagePosition = CarriagePositions.FLIPPED;
        flipPIDController.setReference(CarriagePositions.FLIPPED.angle, CANSparkMax.ControlType.kPosition);
    }

    public void reverseFlipMechanism(){
        carriagePosition = CarriagePositions.DOWN;
        flipPIDController.setReference(CarriagePositions.DOWN.angle, CANSparkMax.ControlType.kPosition);
    }

    public void flipOut() {
        flipMotor.set(CarriageConstants.FLIP_SPEED);
    }

    public void flipIn() {
        flipMotor.set(-CarriageConstants.FLIP_SPEED);
    }

    public void stopFlipMechanism() {
        flipMotor.stopMotor();
    }

    public CarriagePositions getCarriagePosition() {
        return carriagePosition;
    }
}

