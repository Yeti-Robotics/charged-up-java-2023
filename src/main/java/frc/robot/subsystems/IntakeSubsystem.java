package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeConstants;

import javax.inject.Inject;
import javax.inject.Named;

public class IntakeSubsystem extends SubsystemBase {

    private final WPI_TalonFX talon;
    private final DoubleSolenoid intakePiston;

    @Inject
    public IntakeSubsystem(
            @Named(IntakeConstants.TALON) WPI_TalonFX talon,
            @Named(IntakeConstants.INTAKE_PISTON_NAME) DoubleSolenoid intakePiston) {
        this.intakePiston = intakePiston;
        this.talon = talon;

        intakeOpen();
    }

    public void rollIn(double speed) {
        talon.set(Math.abs(speed));
    }

    public void rollOut(double speed) {
        talon.set(-Math.abs(speed));
    }

    public void roll(double speed){
        talon.set(speed);
    }

    public void stop() {
        talon.stopMotor();
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
        talon.setNeutralMode(NeutralMode.Coast);
    }

    public void setBrakeMode(){
        talon.setNeutralMode(NeutralMode.Coast);
    }
}
