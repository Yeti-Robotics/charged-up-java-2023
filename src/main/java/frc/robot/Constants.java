// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

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
    public static final class DriveConstants {

        public static final int GYRO = 1; //placeholder value
    }

    public static final class ArmConstants {
        public static final int MOTOR_1 = 10; // Left arm
        public static final int MOTOR_2 = 11; // Right arm

        public static final double ARM_SPEED = 0.1;

        public static final int ARM_ENCODER = 5;

        public static final int[] AIR_BRAKE_PORTS = {2, 3};

        public static final SupplyCurrentLimitConfiguration SUPPLY_CURRENT_LIMIT =
                new SupplyCurrentLimitConfiguration(true, 55, 65, 0.1);
        public static final StatorCurrentLimitConfiguration STATOR_CURRENT_LIMIT =
                new StatorCurrentLimitConfiguration(true, 55, 65, 0.1);

        public static final double ENCODER_OFFSET = 0.0;
        public static final boolean ARM_ENCODER_REVERSE = false;

        /*
         * The reduction from the encoder to the arm
         * Multiply the encoder value; divide the output
         */
        public static final double GEAR_RATIO = 1 / (32.0 / 12.0); // ~2.6667

        // (real world output) / (gear ratio) = (encoder limit)
        public static final double UPPER_LIMIT = 120.0 / GEAR_RATIO;
        public static final double LOWER_LIMIT = 0.0 / GEAR_RATIO;

        public static final double ARM_P = 1.0;
        public static final double ARM_I = 0.0;
        public static final double ARM_D = 0.0;
        public static final double ARM_F = 0.0;
        public static final double GRAVITY_FEEDFORWARD = 0.7;
        public static final double MAX_VELOCITY = 50.0;
        public static final double MAX_ACCELERATION = MAX_VELOCITY / 2.0;
        // [0, 8]
        public static final int MOTION_SMOOTHING = 0;

        public enum ArmPositions {
            DOWN(0 / GEAR_RATIO),
            UP(80 / GEAR_RATIO),
            HANDOFF(120 / GEAR_RATIO);

            public final double angle;
            ArmPositions(double angle) { this.angle = angle;}
        }
    }

    public static final class TalonFXConstants {
        public static final int COUNTS_PER_REV = 2048;
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
