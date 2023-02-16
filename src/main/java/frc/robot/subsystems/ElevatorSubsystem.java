package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.ElevatorStopCommand;
import frc.robot.commands.ZeroElevatorCommand;

import javax.inject.Inject;
import javax.inject.Named;

public class ElevatorSubsystem extends SubsystemBase {
    private final WPI_TalonFX elevatorMotor;
    private final WPI_CANCoder elevatorEncoder;
    private double motionMagicTarget;

    private DigitalInput beamBreak;




    @Inject
    public ElevatorSubsystem(@Named("elevatorMotor") WPI_TalonFX elevatorMotor, @Named("elevatorBeamBreak") DigitalInput beamBreak, @Named("elevatorEncoder")WPI_CANCoder elevatorEncoder) {

        this.elevatorMotor = elevatorMotor;
        this.beamBreak = beamBreak;
        this.elevatorEncoder = elevatorEncoder;
    }

    public void elevatorUp() {
        //elevatorMotor.set(ControlMode.PercentOutput, Constants.ElevatorConstants.ELEVATOR_SPEED);
        elevatorMotor.set(ControlMode.PercentOutput, Constants.ElevatorConstants.ELEVATOR_SPEED);

    }

    public void elevatorDown() {
        elevatorMotor.set(ControlMode.PercentOutput, Constants.ElevatorConstants.ELEVATOR_SPEED);
    }

    public void elevatorStop() {
        elevatorMotor.set(ControlMode.PercentOutput, 0);
    }

    public void setMotionMagic(double setpoint) {
        motionMagicTarget = setpoint;
        elevatorMotor.set(ControlMode.MotionMagic, setpoint, DemandType.ArbitraryFeedForward, Constants.ElevatorConstants.GRAVITY_FEEDFORWARD);
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


    public double getElevatorEncoder() {
        return elevatorEncoder.getAbsolutePosition();
    }

    @Override
    public void periodic() {
        if (getBeamBreak()) {
            elevatorMotor.setSelectedSensorPosition(0);
        }
    }

    @Override
    public void setDefaultCommand(Command defaultCommand) {
        super.setDefaultCommand(new ElevatorStopCommand(this));

    }
}





