package frc.robot.constants;

import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public final class CarriageConstants {
    public static final int ROLLER_SPARK_ID = 4; //change ID later
    public static final int FLIP_TALON_ID = 3; //update ID to talon ID
    public static final double CARRIAGE_VOLTAGE_COMP = 12.0;

    public static final SupplyCurrentLimitConfiguration FLIP_SUPPLY_CURRENT_LIMIT =
            new SupplyCurrentLimitConfiguration(true, 30, 40, 0.2);
    public static final StatorCurrentLimitConfiguration FLIP_STATOR_CURRENT_LIMIT =
            new StatorCurrentLimitConfiguration(true, 30, 40, 0.2);
    public static final double ROLLER_SPEED = 1.0;
    public static final double FLIP_SPEED = 0.2;
    /** TODO: Find real value */
    public static final double FLIP_RATIO = 1.0 / 21.33;
    public static final double ROLLER_RATIO = 1.0 / 21.0;

    public static final double COUNTS_TO_DEGREES = 360.0 / TalonFXConstants.COUNTS_PER_REV * FLIP_RATIO;
    public static final double LOWER_FLIP_LIMIT = 0.0/ COUNTS_TO_DEGREES;
    public static final double UPPER_FLIP_LIMIT = 170 / COUNTS_TO_DEGREES;
    public static final double FLIP_TOLERANCE = 1.0 / COUNTS_TO_DEGREES;
    public static final double FLIP_P = 0.12;
    public static final double FLIP_I = 0.0;
    public static final double FLIP_D = 0.5;
    public static final double FLIP_F = 0.0;

    public static final double MAX_VELOCITY =  60 / COUNTS_TO_DEGREES;
    public static final double MAX_ACCEL = MAX_VELOCITY / 1.25;

    public static final String FLIP_MOTOR_NAME = "flipMotor";
    public static final String ROLLER_SPARK = "rollerMotor";
    public static final String FLIP_MOTOR_PID_NAME = "flipMotorPIDController";

    public static final double GRAVITY_FEEDFORWARD = 0.65;

    public enum CarriagePositions {
        DOWN(0.0),
        CHUTE(165.0),
        FLIPPED(185.0);

        public final double angle;
        public final double sensorUnits;

        CarriagePositions(double angle) {
            this.angle = angle;
            this.sensorUnits = angle / COUNTS_TO_DEGREES;
        }
    }
}
