package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.CANCoderConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.ArmConstants.ArmPositions;

import javax.inject.Inject;
import javax.inject.Named;

public class ArmSubsystem extends SubsystemBase {

    private final WPI_TalonFX armMotor1;
    private final WPI_CANCoder encoder;


    private final DoubleSolenoid airBrake;

    private ArmPositions armPosition = ArmPositions.UP;
    private boolean isBrakeEngaged;

    @Inject
    public ArmSubsystem(
            @Named(ArmConstants.ARM_MOTOR) WPI_TalonFX armMotor1,
            @Named(ArmConstants.ARM_ENCODER) WPI_CANCoder encoder,
            @Named(ArmConstants.AIR_BRAKE) DoubleSolenoid airBrake) {
        this.armMotor1 = armMotor1;
        this.encoder = encoder;
        this.airBrake = airBrake;

        engageBrake();
    }

    @Override
    public void periodic() {
    }

    public void setPosition(ArmPositions position) {
        if (isBrakeEngaged) {
            stop();
            return;
        }
        armPosition = position;
        motorsBrake();

        double radians = Math.toRadians(getAngle());
        double cosineScalar = Math.cos(radians);

        armMotor1.set(ControlMode.MotionMagic, position.sensorUnits, DemandType.ArbitraryFeedForward, ArmConstants.GRAVITY_FEEDFORWARD * cosineScalar);
        System.out.println("MAGIC MOTION SET: " + position.angle);
    }

    public double getAngle() {
        return armMotor1.getSelectedSensorPosition() / CANCoderConstants.COUNTS_PER_DEG * ArmConstants.GEAR_RATIO;
    }

    public ArmPositions getArmPosition() {
        return armPosition;
    }

    public boolean isMotionFinished() {
        return Math.abs(getAngle() - armPosition.angle) < ArmConstants.ANGLE_TOLERANCE;
    }

    public void moveUp(double speed) {
        if (isBrakeEngaged) {
            stop();
            return;
        }
        motorsBrake();

        armMotor1.set(ControlMode.PercentOutput, Math.abs(speed));
    }

    public void moveDown(double speed) {
        if (isBrakeEngaged) {
            stop();
            return;
        }
        motorsBrake();

        armMotor1.set(ControlMode.PercentOutput, -Math.abs(speed));
    }

    public void engageBrake() {
        stop();
        airBrake.set(DoubleSolenoid.Value.kReverse);
        isBrakeEngaged = true;
        motorsCoast();
    }

    public void disengageBrake() {
        airBrake.set(DoubleSolenoid.Value.kForward);
        isBrakeEngaged = false;
        motorsBrake();
    }

    public void toggleBrake() {
        if (isBrakeEngaged) {
            disengageBrake();
            return;
        }
        engageBrake();
    }

    public boolean isBrakeEngaged() {
        return isBrakeEngaged;
    }

    public boolean isUP(){
        return getAngle() >= 90;
    }

    private void motorsBrake() {
        armMotor1.setNeutralMode(NeutralMode.Brake);
    }

    private void motorsCoast() {
        armMotor1.setNeutralMode(NeutralMode.Coast);
    }

    public void stop() {
        armMotor1.stopMotor();
    }
}

