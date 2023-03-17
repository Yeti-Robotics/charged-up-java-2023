package frc.robot.constants;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.util.Units;

public final class IntakeConstants {
    public static final String LEFT_SPARK = "intake spark 1";
    public static final int LEFT_SPARK_ID = 1;
    public static final String RIGHT_SPARK = "intake spark 2";
    public static final int RIGHT_SPARK_ID = 2;

    public static final String INTAKE_PISTON_NAME = "intake piston";
    public static final int[] INTAKE_PISTON = {4, 5};

    public static final double WHEEL_DIAMETER = Units.inchesToMeters(4.0); // 0.1016
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
    public static final double GEAR_RATIO = 1.0 / 7.0;
    public static final double VELOCITY_CONVERSION = WHEEL_CIRCUMFERENCE * GEAR_RATIO;

    public static final double INTAKE_SPEED = 0.3; //placeholder
    public static final double SHOOT_MID_SPEED = 0.35;
    public static final double SHOOT_HIGH_SPEED = 0.9;

    public static final double INTAKE_P = 0.00; //placeholder
    public static final double INTAKE_I = 0.0; //placeholder
    public static final double INTAKE_D = 0.0; //placeholder
    public static final double INTAKE_F = 0.00; //placeholder

    public static final double INTAKE_KS = 0.3;
    public static final double INTAKE_KV = 0.1;
    public static final double INTAKE_KA = 0.0;

    public static final int VOLTAGE_COMP = 10;
}
