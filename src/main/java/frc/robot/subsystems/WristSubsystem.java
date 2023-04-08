package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CANCoderConstants;
import frc.robot.constants.WristConstants;
import frc.robot.constants.WristConstants.WristPositions;

import javax.inject.Named;

public class WristSubsystem extends SubsystemBase implements Sendable {
    private final WPI_TalonFX wristMotor;
    private final WPI_CANCoder wristEncoder;

    private WristPositions wristPosition = WristConstants.WristPositions.SCORE_CONE;
    private boolean isBrakeEngaged;

    public WristSubsystem(@Named(WristConstants.WRIST_MOTOR) WPI_TalonFX wristMotor,
                          @Named(WristConstants.WRIST_ENCODER) WPI_CANCoder wristEncoder) {
        this.wristMotor = wristMotor;
        this.wristEncoder = wristEncoder;
    }

    @Override
    public void periodic() {
    }

    public void setPosition(WristPositions position) {
        if (isBrakeEngaged) {
            stop();
            System.out.println("stopping setPosition");
            return;
        }
        wristPosition = position;
        motorsBrake();

        double radians = Math.toRadians(getAngle());
        double cosineScalar = Math.cos(radians);

        wristMotor.set(ControlMode.MotionMagic, position.sensorUnits, DemandType.ArbitraryFeedForward, WristConstants.GRAVITY_FEEDFORWARD * cosineScalar);
    }

    public double getAngle() {
        return wristMotor.getSelectedSensorPosition() / CANCoderConstants.COUNTS_PER_DEG;
    }

    public WristPositions getWristPosition() {
        return wristPosition;
    }

    public boolean isMotionFinished() {
        return Math.abs(getAngle() - wristPosition.angle) < WristConstants.ANGLE_TOLERANCE;
    }

    public void moveUp(double speed) {
        if (isBrakeEngaged) {
            stop();
            return;
        }
        motorsBrake();

        wristMotor.set(ControlMode.PercentOutput, Math.abs(speed));
    }

    public void moveDown(double speed) {
        if (isBrakeEngaged) {
            stop();
            return;
        }
        motorsBrake();

        wristMotor.set(ControlMode.PercentOutput, -Math.abs(speed));
    }

    public void engageBrake() {
        stop();
        isBrakeEngaged = true;
        motorsCoast();
    }

    public void disengageBrake() {
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

    public boolean isWristDown() {
        return wristPosition == WristPositions.SCORE_CONE;
    }

    public boolean isUp() {
        return getAngle() >= 90;
    }

    private void motorsBrake() {
        wristMotor.setNeutralMode(NeutralMode.Brake);
    }

    private void motorsCoast() {
        wristMotor.setNeutralMode(NeutralMode.Coast);
    }

    public double getSuppliedCurrent() {
        return wristMotor.getSupplyCurrent();
    }

    public void stop() {
        wristMotor.stopMotor();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addStringProperty("Wrist Position", () -> getWristPosition().toString(), null);
        builder.addStringProperty("Wrist Angle", () -> String.format("%.2f", getAngle()), null);
    }

}


