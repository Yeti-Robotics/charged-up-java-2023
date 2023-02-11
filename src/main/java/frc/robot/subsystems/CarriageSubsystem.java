package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

import javax.inject.Inject;
import javax.inject.Named;

public class CarriageSubsystem extends SubsystemBase {
    private final CANSparkMax rollerMotor;
    private final CANSparkMax flipMotor;
    private final SparkMaxLimitSwitch beamBreak;
    @Inject
    public CarriageSubsystem(
            @Named("rollerMotor") CANSparkMax rollerMotor,
            @Named("flipMotor") CANSparkMax flipMotor,
            @Named("beamBreak") SparkMaxLimitSwitch beamBreak
    ) {
        this.rollerMotor = rollerMotor;
        this.flipMotor = flipMotor;
        this.beamBreak = beamBreak;
    }

    public void carriageOut(){
        rollerMotor.set(Constants.CarriageConstants.CARRIAGE_SPEED);
    }
    public void carriageIn(){
        rollerMotor.set(-Constants.CarriageConstants.CARRIAGE_SPEED);
    }

    public void carriageStop(){
        rollerMotor.set(0);
    }

    public boolean getBeamBreak(){
       return beamBreak.isPressed();
    }

    public void flipMechanism(){
        flipMotor.set(Constants.CarriageConstants.FLIP_SPEED);
    }
    public void reverseFlipMechanism(){
        flipMotor.set(-Constants.CarriageConstants.FLIP_SPEED);
    }

    @Override

    public void periodic() {
        if (!getBeamBreak()) {
            return;
        }
        carriageStop();
        flipMechanism();
    }


}

