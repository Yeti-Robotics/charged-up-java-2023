package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;

import javax.inject.Named;

public class IntakeSubsystem extends SubsystemBase {
    private final CANSparkMax intakeSpark1;
    private  final CANSparkMax intakeSpark2;
    private final DoubleSolenoid intakePiston;

    private final SparkMaxPIDController intakePID;



    public IntakeSubsystem(
            @Named("intake spark 1") CANSparkMax intakeSpark1,
            @Named ("intake spark 2") CANSparkMax intakeSpark2,
            @Named ("intake piston") DoubleSolenoid intakePiston){

        this.intakePiston = intakePiston;
        this.intakeSpark1 = intakeSpark1;
        this.intakeSpark2 = intakeSpark2;


       intakePID = intakeSpark1.getPIDController();
       intakePID.setP(IntakeConstants.INTAKE_P);
       intakePID.setD(IntakeConstants.INTAKE_D);
       intakePID.setFF(IntakeConstants.INTAKE_F);
    }

    public void rollIn(){
        intakeSpark1.set(IntakeConstants.INTAKE_SPEED);
    }

    public void roll(double speed){
        intakeSpark1.set(speed);
    }

    public void rollOut(){
        intakeSpark1.set(-IntakeConstants.INTAKE_SPEED);
    }


    public void intakeClamp(){
        intakePiston.set(DoubleSolenoid.Value.kForward); //check Forward/Reverse values
    }

    public void intakeUnClamp(){
        intakePiston.set(DoubleSolenoid.Value.kReverse); //check Forward/Reverse values
    }

}
