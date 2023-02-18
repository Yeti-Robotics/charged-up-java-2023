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

    private final SparkMaxPIDController intakePID;
    private final RelativeEncoder encoder;
    private final SparkMaxLimitSwitch beamBreak;
    private final SparkMaxLimitSwitch reedSwitch;

    @Inject
    public IntakeSubsystem(
            @Named(IntakeConstants.LEFT_SPARK) CANSparkMax leftSpark,
            @Named(IntakeConstants.RIGHT_SPARK) CANSparkMax rightSpark,
            @Named(IntakeConstants.INTAKE_PISTON_NAME) DoubleSolenoid intakePiston,
            @Named(IntakeConstants.INTAKE_PID) SparkMaxPIDController intakePID,
            @Named(IntakeConstants.INTAKE_ENCODER) RelativeEncoder encoder,
            @Named(IntakeConstants.INTAKE_BEAM_BREAK) SparkMaxLimitSwitch beamBreak,
            @Named(IntakeConstants.INTAKE_REED_SWITCH) SparkMaxLimitSwitch reedSwitch) {
        this.intakePiston = intakePiston;
        this.leftSpark = leftSpark;
        this.rightSpark = rightSpark;
        this.intakePID = intakePID;
        this.encoder = encoder;
        this.beamBreak = beamBreak;
        this.reedSwitch = reedSwitch;
    }

    public void setSetPoint(double setPoint) {
        intakePID.setReference(setPoint, CANSparkMax.ControlType.kVelocity,
                0, IntakeConstants.FEEDFORWARD.calculate(setPoint, IntakeConstants.MAX_ACCEL), SparkMaxPIDController.ArbFFUnits.kVoltage);
    }

    public void rollIn() {
        leftSpark.set(IntakeConstants.INTAKE_SPEED);
    }

    public void rollOut() {
        leftSpark.set(-IntakeConstants.INTAKE_SPEED);
    }

    public void stop() {
        leftSpark.stopMotor();
    }

    public void intakeClamp() {
        intakePiston.set(DoubleSolenoid.Value.kReverse); //check Forward/Reverse values
    }

    public void intakeUnclamp() {
        intakePiston.set(DoubleSolenoid.Value.kForward); //check Forward/Reverse values
    }

    public void toggleClamp() {
        intakePiston.toggle();
    }

    public double getAverageEncoder() {
        return (leftSpark.getEncoder().getVelocity() + rightSpark.getEncoder().getVelocity()) / 2;
    }

    public boolean isClamped() {
        return intakePiston.get() == DoubleSolenoid.Value.kReverse;
    }

    public boolean getBeamBreak() {
        return beamBreak.isPressed();
    }

    public double getRPM() {
        return encoder.getVelocity();
    }
}
