package frc.robot.constants;

import edu.wpi.first.math.util.Units;

public final class IntakeConstants {
    public static final String SPARK = "intake spark 1";
    public static final int SPARK_ID = 1;

    public static final double WHEEL_DIAMETER = Units.inchesToMeters(4.0); // 0.1016
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
    public static final double GEAR_RATIO = 1.0 / 7.0;
    public static final double VELOCITY_CONVERSION = WHEEL_CIRCUMFERENCE * GEAR_RATIO;

    public static final double INTAKE_SPEED = 0.27; //placeholder



    public static final int VOLTAGE_COMP = 10;
}
