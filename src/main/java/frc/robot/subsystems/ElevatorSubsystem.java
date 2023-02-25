package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.ElevatorConstants.ElevatorPositions;

import javax.inject.Inject;
import javax.inject.Named;

public class ElevatorSubsystem extends SubsystemBase {
    private final WPI_TalonFX elevatorMotor;
    private ElevatorPositions position;

    private final DigitalInput magSwitch;

    @Inject
    public ElevatorSubsystem(
            @Named(ElevatorConstants.ELEVATOR_MOTOR) WPI_TalonFX elevatorMotor,
            @Named(ElevatorConstants.ELEVATOR_MAG_SWITCH) DigitalInput magSwitch) {
        this.elevatorMotor = elevatorMotor;
        this.magSwitch = magSwitch;

        if (getMagSwitch()) {
            zeroEncoder();
        }
    }

    public void elevatorUp() {
        elevatorMotor.set(ControlMode.PercentOutput, ElevatorConstants.ELEVATOR_SPEED);
    }

    public void elevatorDown() {
        elevatorMotor.set(ControlMode.PercentOutput, -ElevatorConstants.ELEVATOR_SPEED);
    }

    public boolean isDown(){
        return getElevatorEncoder() <= ElevatorConstants.ELEVATOR_TOLERANCE;
    }

    public void stop() {
        elevatorMotor.set(ControlMode.PercentOutput, 0);
    }

    public void setPosition(ElevatorPositions setpoint) {
        position = setpoint;
        elevatorMotor.set(ControlMode.MotionMagic, position.sensorUnits, DemandType.ArbitraryFeedForward, ElevatorConstants.GRAVITY_FEEDFORWARD);
    }

    public ElevatorPositions getPosition() {
        return position;
    }

    public boolean motionFinished() {
        return Math.abs(elevatorMotor.getSelectedSensorPosition() - position.sensorUnits) <= ElevatorConstants.ELEVATOR_TOLERANCE;
    }

    public boolean getMagSwitch() {
        return !magSwitch.get();
    }

    public double convertInchesToCounts(double inches) {
        return inches / ElevatorConstants.ELEVATOR_DISTANCE_PER_PULSE;
    }

    public double getElevatorEncoder() {
        return elevatorMotor.getSelectedSensorPosition();
    }

    public void zeroEncoder() {
        elevatorMotor.setSelectedSensorPosition(0.0);
    }

    @Override
    public void periodic() {
        if (getMagSwitch() && position == ElevatorPositions.DOWN) {
            zeroEncoder();
        }
    }
}





