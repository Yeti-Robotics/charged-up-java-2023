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
import frc.robot.commands.arm.ArmUpCommand;

import javax.inject.Named;

public class ArmSubsystem extends SubsystemBase {

    private final WPI_TalonFX armMotor1;
    private final WPI_TalonFX armMotor2;
    private final WPI_CANCoder encoder;

    private final DoubleSolenoid airBrake;

    private ArmPositions armPosition = ArmPositions.UP;
    private boolean isBrakeEngaged;

    public ArmSubsystem(
            @Named(Constants.ArmConstants.ARM_MOTOR_1) WPI_TalonFX armMotor1,
            @Named(Constants.ArmConstants.ARM_MOTOR_2) WPI_TalonFX armMotor2,
            @Named(Constants.ArmConstants.ARM_ENCODER) WPI_CANCoder encoder,
            @Named(Constants.ArmConstants.AIR_BRAKE) DoubleSolenoid airBrake
            ) {
        this.armMotor1 = armMotor1;
        this.armMotor2 = armMotor2;
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

        armMotor1.set(ControlMode.MotionMagic, position.sensorUnits, DemandType.ArbitraryFeedForward, Constants.ArmConstants.GRAVITY_FEEDFORWARD * cosineScalar);
    }

    public double getAngle() {
        return armMotor1.getSelectedSensorPosition() / Constants.CANCoderConstants.COUNTS_PER_DEG * Constants.ArmConstants.GEAR_RATIO;
    }

    public ArmPositions getArmPosition() {
        return armPosition;
    }

    public boolean isMotionFinished() {
        return Math.abs(getAngle() - armPosition.angle) < Constants.ArmConstants.ANGLE_TOLERANCE;
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

    private void motorsBrake() {
        armMotor1.setNeutralMode(NeutralMode.Brake);
        armMotor2.setNeutralMode(NeutralMode.Brake);
    }

    private void motorsCoast() {
        armMotor1.setNeutralMode(NeutralMode.Coast);
        armMotor2.setNeutralMode(NeutralMode.Coast);
    }

    public void stop() {
        armMotor1.set(0);
    }
}

