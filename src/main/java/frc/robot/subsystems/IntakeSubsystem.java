package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import javax.inject.Named;

public class IntakeSubsystem extends SubsystemBase {
    private final CANSparkMax intakeSpark1;
    private  final CANSparkMax intakeSpark2;
    private final DoubleSolenoid intakePiston;

    public IntakeSubsystem(
            @Named ("intake piston") DoubleSolenoid intakePiston,
            @Named ("intakeSpark1") CANSparkMax intakeSpark1,
            @Named ("intakeSpark2") CANSparkMax intakeSpark2)
    {

        this.intakePiston = intakePiston;
        this.intakeSpark1 = intakeSpark1;
        this.intakeSpark2 = intakeSpark2;

        intakeSpark2.follow(intakeSpark1, true);
    }

    public void rollIn(){
        intakeSpark1.set(Constants.IntakeConstants.INTAKE_SPEED);
    }

    public void roll(double speed){
        intakeSpark1.set(speed);
    }

    public void rollOut(){
        intakeSpark1.set(-Constants.IntakeConstants.INTAKE_SPEED);
    }


    public void intakeClamp(){
        intakePiston.set(DoubleSolenoid.Value.kForward); //check Forward/Reverse values
    }

    public void intakeUnClamp(){
        intakePiston.set(DoubleSolenoid.Value.kReverse); //check Forward/Reverse values
    }

}
