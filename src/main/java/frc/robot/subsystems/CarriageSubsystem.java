package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.commands.AutoActuateCarriageCommand;

import javax.inject.Inject;
import javax.inject.Named;

public class CarriageSubsystem extends SubsystemBase {
    private final CANSparkMax carriageNeo;
    private final DoubleSolenoid carriagePiston;
    private final DigitalInput limitSwitch;
    private final SparkMaxLimitSwitch beamBreak;
    @Inject
    public CarriageSubsystem(
            @Named("sideNeo") CANSparkMax sideNeo,
            @Named("carriage piston") DoubleSolenoid carriagePiston,
            @Named("limitSwitch") DigitalInput limitSwitch,
            @Named("beamBreak") SparkMaxLimitSwitch beamBreak
    ) {
        this.carriageNeo = sideNeo;
        this.carriagePiston = carriagePiston;
        this.limitSwitch = limitSwitch;
        this.beamBreak = beamBreak;


    }
    public void carriageOpen(){
        carriagePiston.set(DoubleSolenoid.Value.kReverse);
    }
    public void carriageClose(){
        carriagePiston.set(DoubleSolenoid.Value.kForward);
    }
    public void carriageOut(){
        carriageNeo.set(Constants.CarriageConstants.CARRIAGE_SPEED);
    }
    public void carriageIn(){
        carriageNeo.set(-Constants.CarriageConstants.CARRIAGE_SPEED);
    }

    public boolean getBeamBreak(){
       return beamBreak.isLimitSwitchEnabled();
    }


    public void setDefaultCommand(AutoActuateCarriageCommand autoActuateCarriageCommand) {
        setDefaultCommand(autoActuateCarriageCommand);
    }
}

