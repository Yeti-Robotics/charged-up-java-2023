package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.AutoActuateCarriageCommand;

import javax.inject.Inject;
import javax.inject.Named;

public class CarriageSubsystem extends SubsystemBase {
    private final CANSparkMax sideNeo;
    private final DoubleSolenoid piston;
    private final DigitalInput limitSwitch;
    private final SparkMaxLimitSwitch beamBreak;
    @Inject
    public CarriageSubsystem(
            @Named("sideNeo") CANSparkMax sideNeo,
            @Named("piston") DoubleSolenoid piston,
            @Named("limitSwitch") DigitalInput limitSwitch,
            @Named("beamBreak") SparkMaxLimitSwitch beamBreak
    ) {
        this.sideNeo = sideNeo;
        this.piston = piston;
        this.limitSwitch = limitSwitch;
        this.beamBreak = beamBreak;


    }
    public void carriageOpen(){
        piston.set(DoubleSolenoid.Value.kReverse);
    }
    public void carriageClose(){
        piston.set(DoubleSolenoid.Value.kForward);
    }
    public void carriageOut(){
        sideNeo.set(Constants.CarriageConstants.CARRIAGE_SPEED);
    }
    public void carriageIn(){
        sideNeo.set(-Constants.CarriageConstants.CARRIAGE_SPEED);
    }

    public boolean getBeamBreak(){
       return beamBreak.isLimitSwitchEnabled();
    }


    public void setDefaultCommand(AutoActuateCarriageCommand autoActuateCarriageCommand) {
        setDefaultCommand(autoActuateCarriageCommand);
    }
}

