package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import javax.inject.Named;

public class ArmSubsystem extends SubsystemBase {

    private final WPI_TalonFX motor1, motor2;
    private final WPI_CANCoder encoder;

    public ArmSubsystem(
            @Named("armMotor1") WPI_TalonFX motor1,
            @Named("armMotor2") WPI_TalonFX motor2,
            @Named("armEncoder") WPI_CANCoder encoder
            ) {
        this.motor1 = motor1;
        this.motor2 = motor2;
        this.encoder = encoder;
    }

    public void moveUp() {
        motor1.set(ControlMode.MotionMagic, Constants.ArmConstants.ARM_SPEED);
    }

    public void moveDown() {
        motor1.set(ControlMode.MotionMagic, -Constants.ArmConstants.ARM_SPEED);
    }

    public void stop() {
        motor1.set(0);
    }
}

