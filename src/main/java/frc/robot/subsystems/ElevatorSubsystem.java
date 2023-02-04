package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import javax.inject.Inject;
import javax.inject.Named;

public class ElevatorSubsystem extends SubsystemBase {
    private final WPI_TalonFX elevatorMotor;
    private double motionMagicTarget;

    private DigitalInput beamBreak;

    @Inject
    public ElevatorSubsystem(@Named("elevatorMotor") WPI_TalonFX elevatorMotor, @Named("beamBreak") DigitalInput beamBreak) {

        this.elevatorMotor = elevatorMotor;
        this.beamBreak = beamBreak;
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

    public boolean getBeamBreak() {
        return beamBreak.get();
    }

    public int convertInchesToCounts(double inches){
        return (int) (inches / Constants.ElevatorConstants.ELEVATOR_DISTANCE_PER_PULSE);
    }

    @Override
    public void periodic() {
        if (getBeamBreak()) {
            elevatorMotor.setSelectedSensorPosition(0);
        }
    }
}





