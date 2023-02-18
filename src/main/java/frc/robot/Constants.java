// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.util.Units;

import javax.inject.Singleton;
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

    public static final class SparkConstants{
        public static final int SPARK_PERIODMS = 250;
        public static final int CURRENT_LIM = 40;

        public static final int SPARK_RESOLUTION = 4096;
    }
    public static final class DriveConstants {

        public static final int GYRO = 1; //placeholder value

    }


    public static final class VisionConstants{
        public static final double STOPPING_DISTANCE = 1.58333;
        public static final double LIMELIGHT_HEIGHT = 10; //placeholder

        public static final double LIMELIGHT_MOUNTING_ANGLE = 10; //placeholder


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
    public static final class IntakeConstants{
        public static final int[] INTAKE_PISTON = {4, 5};
        public static final int LEFT_SPARK_ID = 1;
        public static final int RIGHT_SPARK_ID = 2;

        public static final String LEFT_SPARK = "intake spark 1";
        public static final String RIGHT_SPARK = "intake spark 2";
        public static final String INTAKE_PISTON_NAME = "intake piston";
        public static final String INTAKE_BEAM_BREAK_NAME = "intake beam break";

        public static final double WHEEL_DIAMETER = Units.inchesToMeters(4.0); //PLACEHOLDER
        public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
        public static final double GEAR_RATIO = 1.0 / 7.0;
        public static final double VELOCITY_CONVERSION = WHEEL_CIRCUMFERENCE * GEAR_RATIO;

        public static final double INTAKE_SPEED = 0.35; //placeholder
        public static final double MAX_ACCEL = 3.0;

        public static final double INTAKE_VOLTAGE_COMP = 12.0;

        public static final double INTAKE_P = 0.5; //placeholder
        public static final double INTAKE_I = 0.0; //placeholder
        public static final double INTAKE_D = 0.0; //placeholder
        public static final double INTAKE_F = 0.1; //placeholder

        public static final double INTAKE_KS = 0.5;
        public static final double INTAKE_KV = 0.5;
        public static final double INTAKE_KA = 0.5;

        public static final SimpleMotorFeedforward FEEDFORWARD = new SimpleMotorFeedforward(INTAKE_KS, INTAKE_KV, INTAKE_KA); //we can use once characterized

        public static final String INTAKE_BEAM_BREAK = "intakeBeamBreak";
        public static final String INTAKE_REED_SWITCH = "intakeReedSwitch";
        public static final String INTAKE_PID = "intakePIDController";
        public static final String INTAKE_ENCODER = "intakeEncoder";
    }
}
