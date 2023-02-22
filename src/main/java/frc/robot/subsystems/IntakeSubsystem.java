package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

import javax.inject.Inject;
import javax.inject.Named;

public class IntakeSubsystem extends SubsystemBase {
    private final CANSparkMax leftSpark;
    private final CANSparkMax rightSpark;
    private final DoubleSolenoid intakePiston;
    private final SparkMaxLimitSwitch beamBreak;
    private final SparkMaxLimitSwitch reedSwitch;

    @Inject
    public IntakeSubsystem(
            @Named(IntakeConstants.LEFT_SPARK) CANSparkMax leftSpark,
            @Named(IntakeConstants.RIGHT_SPARK) CANSparkMax rightSpark,
            @Named(IntakeConstants.INTAKE_PISTON_NAME) DoubleSolenoid intakePiston,
            @Named(IntakeConstants.INTAKE_BEAM_BREAK) SparkMaxLimitSwitch beamBreak,
            @Named(IntakeConstants.INTAKE_REED_SWITCH) SparkMaxLimitSwitch reedSwitch) {
        this.intakePiston = intakePiston;
        this.leftSpark = leftSpark;
        this.rightSpark = rightSpark;
        this.beamBreak = beamBreak;
        this.reedSwitch = reedSwitch;

        intakeClose();
    }

    public void rollIn() {
        leftSpark.set(IntakeConstants.INTAKE_SPEED);
    }

    public void rollOut() {
        leftSpark.set(-IntakeConstants.INTAKE_SPEED);
    }

    public void roll(double speed){
        leftSpark.set(speed);
    }

    public void stop() {
        leftSpark.stopMotor();
    }

    public void intakeClose() {
        intakePiston.set(DoubleSolenoid.Value.kReverse); //check Forward/Reverse values
    }

    public void intakeOpen() {
        intakePiston.set(DoubleSolenoid.Value.kForward); //check Forward/Reverse values
    }

    public void toggle() {
        intakePiston.toggle();
    }

    public boolean isClosed() {
        return intakePiston.get() == DoubleSolenoid.Value.kReverse;
    }

    public boolean getBeamBreak() {
        return beamBreak.isPressed();
    }

    public boolean isCube() {
        return reedSwitch.isPressed();
    }

    public void setCoastMode(){
        leftSpark.setIdleMode(CANSparkMax.IdleMode.kCoast);
        rightSpark.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    public void setBrakeMode(){
        leftSpark.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightSpark.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }
}
