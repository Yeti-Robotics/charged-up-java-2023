package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

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

    public void moveUp(double speed) {
        motor1.set(ControlMode.PercentOutput, Math.abs(speed));
    }

    public void moveDown(double speed) {
        motor1.set(ControlMode.PercentOutput, -Math.abs(speed));
    }

    public void engageBrake() {
        airBrake.set(DoubleSolenoid.Value.kForward);
    }

    public void disengageBrake() {
        airBrake.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggleBrake() {
        airBrake.toggle();
    }

    public void stop() {
        motor1.set(0);
    }
}

