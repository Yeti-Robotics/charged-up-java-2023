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
    private final SparkMaxLimitSwitch forwardCarriageBeamBreak;
    private final SparkMaxLimitSwitch reverseCarriageBeamBreak;
    @Inject
    public CarriageSubsystem(
            @Named("rollerMotor") CANSparkMax rollerMotor,
            @Named("flipMotor") CANSparkMax flipMotor
    ) {
        this.rollerMotor = rollerMotor;
        this.flipMotor = flipMotor;
        this.forwardCarriageBeamBreak = rollerMotor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
        this.reverseCarriageBeamBreak = rollerMotor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
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
       return forwardCarriageBeamBreak.isPressed();
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

