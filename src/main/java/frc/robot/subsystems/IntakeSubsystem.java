package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeConstants;

import javax.inject.Inject;
import javax.inject.Named;

public class IntakeSubsystem extends SubsystemBase {
    private final CANSparkMax spark;

    @Inject
    public IntakeSubsystem(
            @Named(IntakeConstants.SPARK) CANSparkMax spark) {
        this.spark = spark;

    }

    public void rollIn(double speed) {
        spark.set(Math.abs(speed));
    }

    public void rollOut(double speed) {
        spark.set(-Math.abs(speed));
    }

    public void stop() {
        spark.stopMotor();
    }
}
