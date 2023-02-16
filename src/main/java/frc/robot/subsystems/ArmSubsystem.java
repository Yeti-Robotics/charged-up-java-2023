package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants.ArmPositions;

import javax.inject.Named;

public class ArmSubsystem extends SubsystemBase {

    private final WPI_TalonFX motor1, motor2;
    private final WPI_CANCoder encoder;

    private final DoubleSolenoid airBrake;

    public ArmSubsystem(
            @Named("armMotor1") WPI_TalonFX motor1,
            @Named("armMotor2") WPI_TalonFX motor2,
            @Named("armEncoder") WPI_CANCoder encoder,
            @Named("airBrake") DoubleSolenoid airBrake
            ) {
        this.motor1 = motor1;
        this.motor2 = motor2;
        this.encoder = encoder;
        this.airBrake = airBrake;

        engageBrake();
    }

    @Override
    public void periodic() {
    }

    public void setPosition(ArmPositions position) {
        if (isBrakeEngaged()) {
            stop();
            return;
        }
        motorsBrake();

        double radians = Math.toRadians(encoder.getAbsolutePosition() * Constants.ArmConstants.GEAR_RATIO);
        double cosineScalar = Math.cos(radians);

        motor1.set(ControlMode.MotionMagic, position.angle, DemandType.ArbitraryFeedForward, Constants.ArmConstants.GRAVITY_FEEDFORWARD * cosineScalar);
    }

    public double getPosition() {
        return encoder.getAbsolutePosition() * Constants.ArmConstants.GEAR_RATIO;
    }

    public void moveUp(double speed) {
        if (isBrakeEngaged()) {
            stop();
            return;
        }
        motorsBrake();

        motor1.set(ControlMode.PercentOutput, Math.abs(speed));
    }

    public void moveDown(double speed) {
        if (isBrakeEngaged()) {
            stop();
            return;
        }
        motorsBrake();

        motor1.set(ControlMode.PercentOutput, -Math.abs(speed));
    }

    public void engageBrake() {
        stop();
        airBrake.set(DoubleSolenoid.Value.kReverse);
        motorsCoast();
    }

    public void disengageBrake() {
        airBrake.set(DoubleSolenoid.Value.kForward);
        motorsBrake();
    }

    public void toggleBrake() {
        if (isBrakeEngaged()) {
            disengageBrake();
            return;
        }
        engageBrake();
    }

    public boolean isBrakeEngaged() {
        return airBrake.get() == DoubleSolenoid.Value.kReverse;
    }

    private void motorsBrake() {
        motor1.setNeutralMode(NeutralMode.Brake);
        motor2.setNeutralMode(NeutralMode.Brake);
    }

    private void motorsCoast() {
        motor1.setNeutralMode(NeutralMode.Coast);
        motor2.setNeutralMode(NeutralMode.Coast);
    }

    public void stop() {
        motor1.set(0);
        motor2.set(0);
    }
}

