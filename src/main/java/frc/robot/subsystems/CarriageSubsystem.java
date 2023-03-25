package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CarriageConstants;
import frc.robot.constants.CarriageConstants.CarriagePositions;
import frc.robot.constants.ElevatorConstants;

import javax.inject.Inject;
import javax.inject.Named;

public class CarriageSubsystem extends SubsystemBase implements Sendable {
    private final CANSparkMax rollerMotor;
    private final TalonFX flipMotor;

    private CarriagePositions carriagePosition = CarriagePositions.DOWN;

    @Inject
    public CarriageSubsystem(
            @Named(CarriageConstants.ROLLER_SPARK) CANSparkMax rollerMotor,
            @Named(CarriageConstants.FLIP_MOTOR_NAME) TalonFX flipMotor){

        this.rollerMotor = rollerMotor;
        this.flipMotor = flipMotor;
        zeroFlip();
    }

    @Override
    public void periodic() {
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
        return flipMotor.getSelectedSensorPosition() * CarriageConstants.COUNTS_TO_DEGREES;
    }

    public void setSetpoint(CarriagePositions setpoint){
        carriagePosition = setpoint;
        double radians = Math.toRadians(getAngle());
        double cosineScalar = Math.cos(radians);
        double FLIP_FEED_FORWARD = CarriageConstants.GRAVITY_FEEDFORWARD * cosineScalar;

        flipMotor.set(ControlMode.MotionMagic, carriagePosition.sensorUnits, DemandType.ArbitraryFeedForward, FLIP_FEED_FORWARD);
    }

    public boolean atSetpoint() {
        return Math.abs(carriagePosition.angle - getAngle()) <= CarriageConstants.FLIP_TOLERANCE;
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

    public void zeroFlip() {
        flipMotor.setSelectedSensorPosition(2 / CarriageConstants.COUNTS_TO_DEGREES);
    }
    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addStringProperty("Carriage Position", () -> getCarriagePosition().toString(), null);
        builder.addStringProperty("Carriage Angle", () -> String.format("%.2f", getAngle()), null);

    }
}

