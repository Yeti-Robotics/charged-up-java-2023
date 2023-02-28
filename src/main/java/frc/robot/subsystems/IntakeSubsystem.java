package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeConstants;
import org.opencv.core.Mat;

import javax.inject.Inject;
import javax.inject.Named;

public class IntakeSubsystem extends SubsystemBase {
    private final CANSparkMax leftSpark;
    private final CANSparkMax rightSpark;
    private final DoubleSolenoid intakePiston;

    @Inject
    public IntakeSubsystem(
            @Named(IntakeConstants.LEFT_SPARK) CANSparkMax leftSpark,
            @Named(IntakeConstants.RIGHT_SPARK) CANSparkMax rightSpark,
            @Named(IntakeConstants.INTAKE_PISTON_NAME) DoubleSolenoid intakePiston) {
        this.intakePiston = intakePiston;
        this.leftSpark = leftSpark;
        this.rightSpark = rightSpark;

        intakeClose();
    }

    public void rollIn(double speed) {
        leftSpark.set(Math.abs(speed));
    }

    public void rollOut(double speed) {
        leftSpark.set(-Math.abs(speed));
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

    public void setCoastMode(){
        leftSpark.setIdleMode(CANSparkMax.IdleMode.kCoast);
        rightSpark.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    public void setBrakeMode(){
        leftSpark.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightSpark.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }
}
