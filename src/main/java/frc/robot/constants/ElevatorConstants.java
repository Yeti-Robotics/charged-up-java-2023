package frc.robot.constants;

import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

/*
 * All real distance measurements are in inches
 */
public final class ElevatorConstants {
    public static final String ELEVATOR_MOTOR = "elevatorMotor";
    public static final int ELEVATOR_MOTOR_ID = 9;
    public static final double ELEVATOR_SPEED = 0.2;

    public static final String ELEVATOR_MAG_SWITCH = "elevatorMagSwitch";
    public static final int MAG_SWITCH_PORT = 0;

    /*
     * Gear reduction from motor to output
     * Multiply encoder value; divide output
     */
    public static final double ELEVATOR_GEAR_RATIO = 1.0 / 7.75;
    public static final double SPROCKET_DIAMETER = 1.5;
    public static final double SPROCKET_CIRCUMFERENCE = SPROCKET_DIAMETER * Math.PI;
    public static final double ELEVATOR_DISTANCE_PER_PULSE = SPROCKET_CIRCUMFERENCE /
            (TalonFXConstants.COUNTS_PER_REV / ELEVATOR_GEAR_RATIO);

    /*
     * Number of elevator stages excluding the stationary stage
     */
    public static final int STAGES = 2;
    public static final double STAGE_EXTENSION = 23.5;
    public static final double MAX_EXTENSION = STAGE_EXTENSION * STAGES;

    public static final SupplyCurrentLimitConfiguration SUPPLY_CURRENT_LIMIT = new SupplyCurrentLimitConfiguration(
            true, 40, 50, 0.1);
    public static final StatorCurrentLimitConfiguration STATOR_CURRENT_LIMIT = new StatorCurrentLimitConfiguration(
            true, 40, 50, 0.1);

    public static final double ELEVATOR_P = 0.04;
    public static final double ELEVATOR_I = 0.015;
    public static final double ELEVATOR_D = 0.2;
    public static final double ELEVATOR_F = 0.01;
    public static final double GRAVITY_FEEDFORWARD = 0.06; //experimental value

    public static final double MAX_VELOCITY = 3.0 / ELEVATOR_DISTANCE_PER_PULSE;
    public static final double MAX_ACCEL = MAX_VELOCITY / 1.25;

    public static final double ELEVATOR_TOLERANCE = 0.10 / ELEVATOR_DISTANCE_PER_PULSE;

    public static final double ELEVATOR_REVERSE_SOFT_LIMIT = 0 / ELEVATOR_DISTANCE_PER_PULSE;
    public static final double ELEVATOR_FORWARD_SOFT_LIMIT = STAGE_EXTENSION / ELEVATOR_DISTANCE_PER_PULSE;
    public static final int SMOOTHING = 0;
    public static final double IZONE = 0.0001;

    public enum ElevatorPositions {
        DOWN(0),
        CONE_HANDOFF(6.5),
        LEVEL_TWO(20.5),
        UP(MAX_EXTENSION);

        public final double distance;
        public final double sensorUnits;

        ElevatorPositions(double distance) {
            this.distance = distance;
            this.sensorUnits = (distance / STAGES) / ELEVATOR_DISTANCE_PER_PULSE;
        }
    }
}
