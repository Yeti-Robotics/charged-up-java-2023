// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{
    public static final class SparkMaxConstants {
        public static final double PULSES_PER_REV = 4096.0;
    }
    public static final class DriveConstants {

        public static final int GYRO = 1; //placeholder value

    }

    public static final class CarriageConstants {
        public static final int ROLLER_NEO = 4; //change ID later
        public static final int FLIP_NEO = 3; //change ID later
        public static final double CARRIAGE_VOLTAGE_COMP = 12.0;
        public static final double CARRIAGE_SPEED = 0.2;
        public static final double FLIP_SPEED = 0.5;
        /** TODO: Find real value */
        public static final double STOP_ROLLER_CURRENT_DELTA = 10;
        public static final double FLIP_POSITION = 0.5;
        public static final double DEFAULT_POSITION = 0.0;
        public static final double FLIP_RATIO = 1.0 / 40.0;
        public static final double ROLLER_RATIO = 1.0 / 21.0;

        public static final double FLIP_DEGREES_TO_COUNTS = FLIP_RATIO * SparkMaxConstants.PULSES_PER_REV / 360.0;
        public static final double FLIP_P = 0.005;
        public static final double FLIP_I = 0;
        public static final double FLIP_D = 0;
        public static final double FLIP_F = 0;

        public static final String FLIP_MOTOR_NAME = "flipMotor";
        public static final String ROLLER_MOTOR_NAME = "rollerMotor";
        public static final String FLIP_MOTOR_PID_NAME = "flipMotorPIDController";
    }


    public static final class VisionConstants{
        public static final double STOPPING_DISTANCE = 1.58333;
        public static final double LIMELIGHT_HEIGHT = 10; //placeholder

        public static final double LIMELIGHT_MOUNTING_ANGLE = 10; //placeholder


        public static final String TABLE_NAME = "table";
    }
    public static final class OIConstants {

        public static final Map<Integer, ControllerType> CONTROLLERS = Map.of(
                0, ControllerType.CUSTOM
        );
        public static final int XBOX_PORT = 0; //placeholder value
        public static final int CONTROLLER_COUNT = 1; //placeholder value

        public enum ControllerType {
            CUSTOM, XBOX
        }
    }
}
