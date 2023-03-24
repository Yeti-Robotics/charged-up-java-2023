package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeConstants;

import javax.inject.Inject;
import javax.inject.Named;

public class IntakeSubsystem extends SubsystemBase {

    private final WPI_TalonFX intakeMotor;
    private final DoubleSolenoid intakePiston;

    @Inject
    public IntakeSubsystem(
            @Named(IntakeConstants.TALON) WPI_TalonFX intakeMotor,
            @Named(IntakeConstants.INTAKE_PISTON_NAME) DoubleSolenoid intakePiston) {
        this.intakePiston = intakePiston;
        this.intakeMotor = intakeMotor;

        intakeOpen();
    }

    public void rollIn(double speed) {
        intakeMotor.set(Math.abs(speed));
    }

    public void rollOut(double speed) {
        intakeMotor.set(-Math.abs(speed));
    }

    public void roll(double speed){
        intakeMotor.set(speed);
    }

    public void stop() {
        intakeMotor.stopMotor();
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
        intakeMotor.setNeutralMode(NeutralMode.Coast);
    }

    public void setBrakeMode(){
        intakeMotor.setNeutralMode(NeutralMode.Coast);
    }
}
