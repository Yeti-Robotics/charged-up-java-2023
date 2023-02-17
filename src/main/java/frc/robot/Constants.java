// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;

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
        public static final int INTAKE_SPARK_1 = 1;
        public static final int INTAKE_SPARK_2 = 2;

        public static final String INTAKE_SPARK_1_NAME = "intake spark 1";
        public static final String INTAKE_SPARK_2_NAME = "intake spark 2";
        public static final String INTAKE_PISTON_NAME = "intake piston";
        public static final String INTAKE_BEAM_BREAK_NAME = "intake beam break";



        public static final double INTAKE_SPEED = 0.35; //placeholder

        public static final double INTAKE_VOLTAGE_COMP = 12.0;

        public static final double INTAKE_P = 0.5; //placeholder
        public static final double INTAKE_I = 0.0; //placeholder
        public static final double INTAKE_D = 0.0; //placeholder
        public static final double INTAKE_F = 0.1; //placeholder

        public static final double WHEEL_DIAMETER = 4; //PLACEHOLDER

        public static final double INTAKE_RATIO = 6; //PLACEHOLDER

        public static final double INTAKE_KS = 0.5;
        public static final double INTAKE_KV = 0.5;
        public static final double INTAKE_KA = 0.5;

        public static final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(INTAKE_KS, INTAKE_KV, INTAKE_KA); //we can use once characterized

    }
}
