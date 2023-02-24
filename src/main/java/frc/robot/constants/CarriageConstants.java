package frc.robot.constants;

public final class CarriageConstants {
    public static final int ROLLER_SPARK_ID = 4; //change ID later
    public static final int FLIP_NEO = 3; //change ID later
    public static final double CARRIAGE_VOLTAGE_COMP = 12.0;
    public static final double ROLLER_SPEED = 0.35;
    public static final double FLIP_SPEED = 0.2;
    /** TODO: Find real value */
    public static final double FLIP_RATIO = 1.0 / 21.33;
    public static final double ROLLER_RATIO = 1.0 / 21.0;

    public static final double COUNTS_TO_DEGREES = FLIP_RATIO / SparkMaxConstants.COUNTS_PER_DEG;
    public static final double LOWER_FLIP_LIMIT = 0.0;
    public static final double UPPER_FLIP_LIMIT = 170;
    public static final double FLIP_P = 0.01;
    public static final double FLIP_I = 0;
    public static final double FLIP_D = 0;
    public static final double FLIP_F = 0;

    public static final double MAX_VELOCITY = 0.25 / FLIP_RATIO;
    public static final double MAX_ACCEL = MAX_VELOCITY / 1.25;

    public static final String FLIP_MOTOR_NAME = "flipMotor";
    public static final String ROLLER_SPARK = "rollerMotor";
    public static final String FLIP_MOTOR_PID_NAME = "flipMotorPIDController";

    public static final double GRAVITY_FEEDFORWARD = 0.07;

    public enum CarriagePositions {
        DOWN(0.0),
        FLIPPED(170.0);

        public final double angle;
        public final double sensorUnits;

        CarriagePositions(double angle) {
            this.angle = angle;
            this.sensorUnits = angle / COUNTS_TO_DEGREES;
        }
    }
}
