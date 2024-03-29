package frc.robot.constants;

import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

public final class ArmConstants {
    public static final String ARM_MOTOR = "armMotor";
    public static final int ARM_MOTOR_ID = 11;

    public static final String ARM_ENCODER = "armEncoder";
    public static final int ARM_ENCODER_ID = 5;

    public static final String AIR_BRAKE = "airBrake";
    public static final int[] AIR_BRAKE_PORTS = {2, 3};

    public static final SupplyCurrentLimitConfiguration SUPPLY_CURRENT_LIMIT =
            new SupplyCurrentLimitConfiguration(true, 55, 65, 0.1);
    public static final StatorCurrentLimitConfiguration STATOR_CURRENT_LIMIT =
            new StatorCurrentLimitConfiguration(true, 55, 65, 0.1);

    public static final double ENCODER_OFFSET = -162.598;
    public static final boolean ARM_ENCODER_REVERSE = false;

    /*
     * The reduction from the encoder to the arm
     * Multiply the encoder value; divide the output
     */
    public static final double GEAR_RATIO = 1.0 / (32.0 / 12.0); // ~2.6667
    // (real world output) / (gear ratio) * (CANCoder raw units) = (encoder limit in raw units)
    public static final double UPPER_LIMIT = 100.0 / GEAR_RATIO * CANCoderConstants.COUNTS_PER_DEG;
    public static final double LOWER_LIMIT = -20.0 / GEAR_RATIO * CANCoderConstants.COUNTS_PER_DEG;

    public static final double ANGLE_TOLERANCE = 1.0;

    public static final double ARM_P = 0.6;
    public static final double ARM_I = 0.0;
    public static final double ARM_D = 0.005;
    public static final double ARM_F = 0.0;
    public static final double GRAVITY_FEEDFORWARD = 0.05;
    public static final double MAX_VELOCITY = 5500; //600
    public static final double MAX_ACCELERATION = MAX_VELOCITY / 1.25;

    // [0, 8]
    public static final int MOTION_SMOOTHING = 0;

    public enum ArmPositions {
        DOWN(-20.0),
        CONE_FLIP(21.0),
        SHOOT(65.0), //67 60
        PORTAL(70.0),
        UP(85.0),
        LOW(35.0),
        HANDOFF(60.0);

        public final double angle;
        public final double sensorUnits;

        ArmPositions(double angle) {
            this.angle = angle;
            this.sensorUnits = angle / GEAR_RATIO * CANCoderConstants.COUNTS_PER_DEG;
        }
    }
}
