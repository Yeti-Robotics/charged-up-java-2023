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
    public static final class DriveConstants {

        public static final int GYRO = 1; //placeholder value

    }
    public static final class CarriageConstants {
        public static final int CARRIAGE_NEO = 1;
        public static final int[] CARRIAGE_PISTONS_SOLENOID = {2, 3};
        public static final double CARRIAGE_VOLTAGE_COMP = 12.0;
        public static final double CARRIAGE_SPEED = 0.35;
        public static final int CARRIAGE_BEAMBREAK = 0;
        public static final int CARRIAGE_LIMITSWITCH = 0;

        public static final int[] CARRIAGE_PISTON = {0,1};
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
