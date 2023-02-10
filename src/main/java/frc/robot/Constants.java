// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.ElevatorSubsystem;

import javax.swing.text.Element;
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

    public static final class ElevatorConstants {

        public static final int ELEVATOR_MOTOR = 11;
        public static final double ELEVATOR_SPEED = .4;

        public static final double ELEVATOR_GEAR_RATIO = 7.75/12;
        public static final double ELEVATOR_HEIGHT_RATIO = 32/12;

        public static final double ELEVATOR_PULSES_PER_REVOLUTION = 2048;
        public static final double SPROCKET_CIRCUMFERENCE = 1.5 * Math.PI;
        public static final double ELEVATOR_DISTANCE_PER_PULSE = ELEVATOR_PULSES_PER_REVOLUTION / ELEVATOR_GEAR_RATIO;

        public static final int[] ELEVATOR_PISTON = {0, 1};



        public static final double ELEVATOR_P = 0.1;
        public static final double ELEVATOR_I = 0.1;
        public static final double ELEVATOR_D = 0.1;
        public static final double ELEVATOR_F = 0.1;

        public static final double ELEVATOR_MAX_VELOCITY = 58472.2;
        public static final double ELEVATOR_CRUISING_VELOCITY = ELEVATOR_MAX_VELOCITY / 1.25;
        public static final double ELEVATOR_CRUISING_ACCELERATION = ELEVATOR_MAX_VELOCITY / 1.25;

        public static final int BEAM_BREAK = 5;
        public static final int ELEVATOR_TOLERANCE = (int) (0.25 / Constants.ElevatorConstants.ELEVATOR_DISTANCE_PER_PULSE);

        public static final int ELEVATOR_CURRENT_LIMIT = 15;


    }
    public static final class TalonFXConstants {

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
