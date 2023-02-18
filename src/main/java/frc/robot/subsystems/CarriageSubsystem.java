package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import javax.inject.Inject;
import javax.inject.Named;

public class CarriageSubsystem extends SubsystemBase {
    private final CANSparkMax rollerMotor;
    private final CANSparkMax flipMotor;
    private final SparkMaxPIDController flipPIDController;

    @Inject
    public CarriageSubsystem(
            @Named(Constants.CarriageConstants.ROLLER_MOTOR_NAME) CANSparkMax rollerMotor,
            @Named(Constants.CarriageConstants.FLIP_MOTOR_NAME) CANSparkMax flipMotor,
            @Named(Constants.CarriageConstants.FLIP_MOTOR_PID_NAME)SparkMaxPIDController flipPIDController
            ) {
        this.rollerMotor = rollerMotor;
        this.flipMotor = flipMotor;
        this.flipPIDController = flipPIDController;
    }

    public void carriageOut(){
        rollerMotor.set(Constants.CarriageConstants.CARRIAGE_SPEED);
    }

    public void carriageIn(){
        rollerMotor.set(-Constants.CarriageConstants.CARRIAGE_SPEED);
    }

    public double getRollerCurrent() {
        /* according to https://www.chiefdelphi.com/t/get-voltage-from-spark-max/344136/5 */
        return Math.abs(rollerMotor.getOutputCurrent());
    }

    public void rollerStop(){
        rollerMotor.stopMotor();
    }

    public void flipMechanism(){
        flipPIDController.setReference(Constants.CarriageConstants.FLIP_POSITION, CANSparkMax.ControlType.kPosition);
    }

    public void reverseFlipMechanism(){
        flipPIDController.setReference(Constants.CarriageConstants.DEFAULT_POSITION, CANSparkMax.ControlType.kPosition);
    }

    public void stopFlipMechanism() {
        flipMotor.stopMotor();
    }
}

