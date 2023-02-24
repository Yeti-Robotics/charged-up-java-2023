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

        flipPIDController.setReference(setpoint.angle, CANSparkMax.ControlType.kPosition, 0); //make command later
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

