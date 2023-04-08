package frc.robot.constants;

import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

public final class WristConstants {
    public static final String WRIST_MOTOR = "wristMotor";
    public static final int WRIST_MOTOR_ID = 12; //PLACEHOLDER VALUE

    public static final String WRIST_ENCODER = "wristEncoder";
    public static final int WRIST_ENCODER_ID = 5; //PLACEHOLDER VALUE

    public static final SupplyCurrentLimitConfiguration SUPPLY_CURRENT_LIMIT =
            new SupplyCurrentLimitConfiguration(true, 55, 65, 0.1);
    public static final StatorCurrentLimitConfiguration STATOR_CURRENT_LIMIT =
            new StatorCurrentLimitConfiguration(true, 55, 65, 0.1);

    //PLACEHOLDER VALUES FOR SUPPLYCURRENTLIMIT

    public static final double ENCODER_OFFSET = -170.0; //PLACEHOLDER VALUE
    public static final boolean WRIST_ENCODER_REVERSE = true;

    /*
     * The reduction from the encoder to the wrist
     * Multiply the encoder value; divide the output
     */

    public static final double UPPER_LIMIT = 120.0 / CANCoderConstants.COUNTS_PER_DEG; //PLACEHOLDER VALUE
    public static final double LOWER_LIMIT = 2.0 / CANCoderConstants.COUNTS_PER_DEG; //PLACEHOLDER VALUE

    public static final double ANGLE_TOLERANCE = 1.0; //PLACEHOLDER VALUE

    public static final double WRIST_P = 0.9; //PLACEHOLDER VALUE
    public static final double WRIST_I = 0.00; //PLACEHOLDER VALUE
    public static final double WRIST_D = 0.005; //PLACEHOLDER VALUE
    public static final double WRIST_F = 0.01; //PLACEHOLDER VALUE
    public static final double GRAVITY_FEEDFORWARD = 0.08; //PLACEHOLDER VALUE
    public static final double MAX_VELOCITY = 1500.0; //PLACEHOLDER VALUE
    public static final double MAX_ACCELERATION = MAX_VELOCITY / 1.25; //PLACEHOLDER VALUE

    // [0, 8]
    public static final int MOTION_SMOOTHING = 0; //PLACEHOLDER VALUE

    public enum WristPositions {

        SCORE_CUBE_LOW(30.0),
        SCORE_CUBE_MID(80.0),
        SCORE_CUBE_HIGH(90.0),
        SCORE_CONE_LOW(0.0),
        SCORE_CONE_MID(10.0),
        SCORE_CONE_HIGH(0),
        INTAKE_CONE_DOUBLE(90),
        INTAKE_CONE_SINGLE(135),
        INTAKE_CUBE_DOUBLE(170),
        INTAKE_CUBE_SINGLE(180),
        INTAKE_CONE_TIPPED_GROUND(90.0),
        INTAKE_CONE_UPRIGHT_GROUND(0.0),
        INTAKE_CUBE_GROUND(180.0);




        public final double angle;
        public final double sensorUnits;

        WristPositions(double angle) {
            this.angle = angle;
            this.sensorUnits = angle / CANCoderConstants.COUNTS_PER_DEG;
        }
    }
}
