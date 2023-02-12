package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import javax.inject.Named;

public class ArmSubsystem extends SubsystemBase {

    private final WPI_TalonFX motor1, motor2;
    private final CANCoder armEncoder;

    public ArmSubsystem(
            @Named("armMotor1") WPI_TalonFX motor1,
            @Named("armMotor2") WPI_TalonFX motor2,
            @Named("armEncoder")CANCoder armEncoder
            ) {
        this.motor1 = motor1;
        this.motor2 = motor2;
        this.armEncoder = armEncoder;

    }

    public void moveUp() {
        motor1.set(ControlMode.MotionMagic, Constants.ArmConstants.ARM_SPEED);
    }

    public void moveDown() {
        motor1.set(ControlMode.MotionMagic, -Constants.ArmConstants.ARM_SPEED);
    }

    public void move(double speed){
        motor1.set(ControlMode.MotionMagic, speed);
    }

    public void stopMove() {
        motor1.set(0);


    }
}

