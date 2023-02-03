package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import javax.inject.Inject;
import javax.inject.Named;

public class ElevatorSubsystem extends SubsystemBase {
    private final WPI_TalonFX elevatorMotor;
    private double motionMagicTarget;

    @Inject
    public ElevatorSubsystem(@Named("elevatorMotor") WPI_TalonFX elevatorMotor) {

        this.elevatorMotor = elevatorMotor;
    }

    public void elevatorUp() {
        elevatorMotor.set(ControlMode.PercentOutput, Constants.ElevatorConstants.ELEVATOR_SPEED);

    }

    public void elevatorDown() {
        elevatorMotor.set(ControlMode.PercentOutput, -Constants.ElevatorConstants.ELEVATOR_SPEED);
    }

    public void elevatorStop() {
        elevatorMotor.set(ControlMode.PercentOutput, 0);
    }

    public void setMotionMagic(double setpoint) {
        motionMagicTarget = setpoint;

    }
    public boolean motionMagicOnTarget() {
        return Math.abs(elevatorMotor.getSelectedSensorPosition() - motionMagicTarget) <= Constants.ElevatorConstants.ELEVATOR_TOLERANCE;
        }

    }





