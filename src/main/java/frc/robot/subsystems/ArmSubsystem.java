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

    private final WPI_TalonFX armMotor;
    private final WPI_CANCoder encoder;

    private final DoubleSolenoid airBrake;

    private ArmPositions armPosition;

    public ArmSubsystem(
            @Named(Constants.ArmConstants.ARM_MOTOR) WPI_TalonFX armMotor,
            @Named(Constants.ArmConstants.ARM_ENCODER) WPI_CANCoder encoder,
            @Named(Constants.ArmConstants.AIR_BRAKE) DoubleSolenoid airBrake
            ) {
        this.armMotor = armMotor;
        this.encoder = encoder;
        this.airBrake = airBrake;

        engageBrake();
    }

    @Override
    public void periodic() {
        System.out.println(encoder.getPosition() + " : " + encoder.getAbsolutePosition() + " : " + armMotor.getSelectedSensorPosition() + " : " + getAngle());
    }

    public void setPosition(ArmPositions position) {
        if (isBrakeEngaged()) {
            stop();
            return;
        }
        armPosition = position;
        motorsBrake();

        double radians = Math.toRadians(getAngle());
        double cosineScalar = Math.cos(radians);

        armMotor.set(ControlMode.MotionMagic, position.sensorUnits, DemandType.ArbitraryFeedForward, Constants.ArmConstants.GRAVITY_FEEDFORWARD * cosineScalar);
    }

    public double getAngle() {
        return armMotor.getSelectedSensorPosition() / Constants.CANCoderConstants.COUNTS_PER_DEG * Constants.ArmConstants.GEAR_RATIO;
    }

    public ArmPositions getArmPosition() {
        return armPosition;
    }

    public boolean isMotionFinished() {
        return Math.abs(getAngle() - armPosition.angle) < Constants.ArmConstants.ANGLE_TOLERANCE;
    }

    public void moveUp(double speed) {
        if (isBrakeEngaged()) {
            stop();
            return;
        }
        motorsBrake();

        armMotor.set(ControlMode.PercentOutput, Math.abs(speed));
    }

    public void moveDown(double speed) {
        if (isBrakeEngaged()) {
            stop();
            return;
        }
        motorsBrake();

        armMotor.set(ControlMode.PercentOutput, -Math.abs(speed));
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
        armMotor.setNeutralMode(NeutralMode.Brake);
    }

    private void motorsCoast() {
        armMotor.setNeutralMode(NeutralMode.Coast);
    }

    public void stop() {
        armMotor.set(0);
    }
}

