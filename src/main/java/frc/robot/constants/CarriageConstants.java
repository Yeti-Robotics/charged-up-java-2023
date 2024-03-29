package frc.robot.constants;

import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public final class CarriageConstants {
    public static final int ROLLER_SPARK_ID = 4; //change ID later
    public static final int FLIP_TALON_ID = 3; //update ID to talon ID
    public static final double CARRIAGE_VOLTAGE_COMP = 12.0;

    public static final SupplyCurrentLimitConfiguration FLIP_SUPPLY_CURRENT_LIMIT =
            new SupplyCurrentLimitConfiguration(true, 60, 60, 0.2);
    public static final StatorCurrentLimitConfiguration FLIP_STATOR_CURRENT_LIMIT =
            new StatorCurrentLimitConfiguration(true, 60, 60, 0.2);
    public static final double ROLLER_SPEED = .75;
    public static final double FLIP_SPEED = 0.2;
    /** TODO: Find real value */
    public static final double FLIP_RATIO = 1.0 / 21.33;
    public static final double ROLLER_RATIO = 1.0 / 21.0;

    public static final double COUNTS_TO_DEGREES = 360.0 / TalonFXConstants.COUNTS_PER_REV * FLIP_RATIO;
    public static final double LOWER_FLIP_LIMIT = 0.0 / COUNTS_TO_DEGREES;
    public static final double UPPER_FLIP_LIMIT = 170 / COUNTS_TO_DEGREES;
    public static final double FLIP_TOLERANCE = 1.0;
    public static final double FLIP_P = 0.35;
    public static final double FLIP_I = 0.000;
    public static final double FLIP_D = 0.3;
    public static final double FLIP_F = 0.0;
    public static final double FLIP_IZONE = 5.0 / COUNTS_TO_DEGREES;

    public static final double MAX_VELOCITY = 100 / COUNTS_TO_DEGREES;
    public static final double MAX_ACCEL = MAX_VELOCITY / 1.2;

    public static final String FLIP_MOTOR_NAME = "flipMotor";
    public static final String ROLLER_SPARK = "rollerMotor";
    public static final String FLIP_MOTOR_PID_NAME = "flipMotorPIDController";

    public static final double GRAVITY_FEEDFORWARD = 0.12;
    public static final int FLIP_MOTION_SMOOTHING = 0;

    public enum CarriagePositions {
        DOWN(2.0),
        CUBE(117.0),
        CHUTE(115.5),
        CUBE_STATION(123.0),
        CONE_DOUBLE(142.0),
        FLIPPED(140.0);

        public final double angle;
        public final double sensorUnits;

        CarriagePositions(double angle) {
            this.angle = angle;
            this.sensorUnits = angle / COUNTS_TO_DEGREES;
        }
    }
}
