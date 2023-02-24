// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

import java.util.Map;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstants {

        public static final int FRONT_LEFT_DRIVE = 2;
        public static final int FRONT_LEFT_AZIMUTH = 1;
        public static final int FRONT_LEFT_ENCODER = 1;
        public static final boolean FRONT_LEFT_DRIVE_REVERSED = true;
        public static final double FRONT_LEFT_ENCODER_OFFSET = -18.809;
        public static final boolean FRONT_LEFT_ENCODER_REVERSED = false;

        public static final int FRONT_RIGHT_DRIVE = 4;
        public static final int FRONT_RIGHT_AZIMUTH = 3;
        public static final int FRONT_RIGHT_ENCODER = 2;
        public static final boolean FRONT_RIGHT_DRIVE_REVERSED = false;
        public static final double FRONT_RIGHT_ENCODER_OFFSET = 153.545;
        public static final boolean FRONT_RIGHT_ENCODER_REVERSED = false;

        public static final int BACK_LEFT_DRIVE = 8;
        public static final int BACK_LEFT_AZIMUTH = 7;
        public static final int BACK_LEFT_ENCODER = 4;
        public static final boolean BACK_LEFT_DRIVE_REVERSED = true;
        public static final double BACK_LEFT_ENCODER_OFFSET = 21.973;
        public static final boolean BACK_LEFT_ENCODER_REVERSED = false;

        public static final int BACK_RIGHT_DRIVE = 6;
        public static final int BACK_RIGHT_AZIMUTH = 5;
        public static final int BACK_RIGHT_ENCODER = 3;
        public static final boolean BACK_RIGHT_DRIVE_REVERSED = false;
        public static final double BACK_RIGHT_ENCODER_OFFSET = -23.730;
        public static final boolean BACK_RIGHT_ENCODER_REVERSED = false;

        public static final String FRONT_LEFT_MODULE_NAME = "front left";
        public static final String FRONT_RIGHT_MODULE_NAME = "front right";
        public static final String BACK_LEFT_MODULE_NAME = "back left";
        public static final String BACK_RIGHT_MODULE_NAME = "back right";

        public static final int GYRO = 1; //placeholder value

        public static final double DRIVE_MOTOR_P = 2.0; //placeholder from borealis
        public static final double DRIVE_MOTOR_I = 0.0; //placeholder from borealis
        public static final double DRIVE_MOTOR_D = 0.0; //placeholder from borealis
        public static final double DRIVE_MOTOR_KS = 0.743; //placeholder from borealis
        public static final double DRIVE_MOTOR_KV = 2.178; //placeholder from borealis
        public static final double DRIVE_MOTOR_KA = 0.406; //placeholder from borealis

        public static final double AZIMUTH_MOTOR_P = 5.0; //placeholder from borealis 3
        public static final double AZIMUTH_MOTOR_I = 0.0; //placeholder from borealis
        public static final double AZIMUTH_MOTOR_D = 0.00; //placeholder from borealis 0.01
        public static final double AZIMUTH_MOTOR_KS = 0.50; //placeholder from borealis 0.75
        public static final double AZIMUTH_MOTOR_KV = 0.35; //placeholder from borealis 0.7
        public static final double AZIMUTH_MOTOR_KA = 0.0; //placeholder from borealis

        public static final double DEGREES_TO_FALCON = 20.64 * 2048 / 360.0;
        public static final double SWERVE_X_REDUCTION = 1.0 / 6.75;
        public static final double WHEEL_DIAMETER = Units.inchesToMeters(4); //0.1016
        public static final double MAX_VELOCITY_METERS_PER_SECOND = 6380.0 / 60.0 * SWERVE_X_REDUCTION * WHEEL_DIAMETER * Math.PI; //placeholder
        private static final double DRIVETRAIN_WHEELBASE_METERS = Units.inchesToMeters(22.25); //PLACEHOLDER
        private static final double DRIVETRAIN_TRACKWIDTH_METERS = Units.inchesToMeters(22.25); //PLACEHOLDER
        public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = MAX_VELOCITY_METERS_PER_SECOND / Math.hypot(DriveConstants.DRIVETRAIN_TRACKWIDTH_METERS / 2, DriveConstants.DRIVETRAIN_WHEELBASE_METERS / 2); //PLACEHOLDER

        public static final SwerveDriveKinematics DRIVE_KINEMATICS =
                new SwerveDriveKinematics(
                        // Front left
                        new Translation2d(DriveConstants.DRIVETRAIN_WHEELBASE_METERS / 2.0,
                                DriveConstants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0),
                        // Front right
                        new Translation2d(DriveConstants.DRIVETRAIN_WHEELBASE_METERS / 2.0,
                                -DriveConstants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0),
                        // Back left
                        new Translation2d(-DriveConstants.DRIVETRAIN_WHEELBASE_METERS / 2.0,
                                DriveConstants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0),
                        // Back right
                        new Translation2d(-DriveConstants.DRIVETRAIN_WHEELBASE_METERS / 2.0,
                                -DriveConstants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0)
                );
    }

    public static final class CarriageConstants {
        public static final int ROLLER_SPARK_ID = 4; //change ID later
        public static final int FLIP_NEO = 3; //change ID later
        public static final double CARRIAGE_VOLTAGE_COMP = 12.0;
        public static final double ROLLER_SPEED = 0.35;
        public static final double FLIP_SPEED = 0.2;
        /** TODO: Find real value */
        public static final double FLIP_RATIO = 1.0 / 21.33;
        public static final double ROLLER_RATIO = 1.0 / 21.0;

        public static final double COUNTS_TO_DEGREES = FLIP_RATIO / SparkMaxConstants.COUNTS_PER_DEG;
        public static final double LOWER_FLIP_LIMIT = 0.0;
        public static final double UPPER_FLIP_LIMIT = 170;
        public static final double FLIP_P = 0.15;
        public static final double FLIP_I = 0.01;
        public static final double FLIP_D = 0;
        public static final double FLIP_F = 0.03;

        public static final double MAX_VELOCITY = 0.25 / FLIP_RATIO;
        public static final double MAX_ACCEL = MAX_VELOCITY / 1.25;

        public static final String FLIP_MOTOR_NAME = "flipMotor";
        public static final String ROLLER_SPARK = "rollerMotor";
        public static final String FLIP_MOTOR_PID_NAME = "flipMotorPIDController";

        public static final double GRAVITY_FEEDFORWARD = 0.07;
        public static final double PID_MIN = -0.3;
        public static final double PID_MAX = 0.3;

        public enum CarriagePositions {
            DOWN(0.0),
            FLIPPED(7.0);

            public final double angle;
            public final double sensorUnits;

            CarriagePositions(double angle) {
                this.angle = angle;
                this.sensorUnits = angle / COUNTS_TO_DEGREES;
            }
        }
    }

    public static final class ArmConstants {
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

        public static final double ENCODER_OFFSET = -168.0;
        public static final boolean ARM_ENCODER_REVERSE = true;

        /*
         * The reduction from the encoder to the arm
         * Multiply the encoder value; divide the output
         */
        public static final double GEAR_RATIO = 1.0 / (32.0 / 12.0); // ~2.6667
        // (real world output) / (gear ratio) * (CANCoder raw units) = (encoder limit in raw units)
        public static final double UPPER_LIMIT = 120.0 / GEAR_RATIO * CANCoderConstants.COUNTS_PER_DEG;
        public static final double LOWER_LIMIT = 0.0 / GEAR_RATIO * CANCoderConstants.COUNTS_PER_DEG;

        public static final double ANGLE_TOLERANCE = 1.0;

        public static final double ARM_P = 0.5;
        public static final double ARM_I = 0.00;
        public static final double ARM_D = 0.02;
        public static final double ARM_F = 0.008;
        public static final double GRAVITY_FEEDFORWARD = 0.12;
        public static final double MAX_VELOCITY = 500.0;
        public static final double MAX_ACCELERATION = MAX_VELOCITY / 1.5;
        // [0, 8]
        public static final int MOTION_SMOOTHING = 0;

        public static final double ARM_ANGLE_LIMIT = 90;

        public enum ArmPositions {
            DOWN(0.0),
            CONE_FLIP(10.0),
            SHOOT(60.0),
            UP(100.0),
            HANDOFF(120.0);

            public final double angle;
            public final double sensorUnits;

            ArmPositions(double angle) {
                this.angle = angle;
                this.sensorUnits = angle / GEAR_RATIO * CANCoderConstants.COUNTS_PER_DEG;
            }
        }
    }

    public static final class TalonFXConstants {
        public static final int COUNTS_PER_REV = 2048;
    }

    public static final class CANCoderConstants {
        public static final int COUNTS_PER_REV = 4096;
        public static final double COUNTS_PER_DEG = COUNTS_PER_REV / 360.0;
    }

    public static final class SparkMaxConstants {
        public static final double PULSES_PER_REV = 4096.0;

        public static final int HIGH_PRIORITY_MS = 20;
        public static final int MEDIUM_PRIORITY_MS = 60;
        public static final int LOW_PRIORITY_MS = 120;
        public static final int NEO_CURRENT_LIM = 30;
        public static final int NEO550_CURRENT_LIM = 20;

        public static final int SPARK_RESOLUTION = 42;
        public static final double COUNTS_PER_DEG = SPARK_RESOLUTION / 360.0;
    }

    public static final class AutoConstants {

        /**
         * Max velocity in meters per second
         */
        public static final double MAX_VELOCITY = DriveConstants.MAX_VELOCITY_METERS_PER_SECOND * 0.75;
        /**
         * Max acceleration in meters per second squared
         */
        public static final double MAX_ACCEL = DriveConstants.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND * 0.75;

        public static final double X_CONTROLLER_P = 2.1; //2.9, 2.15
        public static final double Y_CONTROLLER_P = 2.1; //2.9, 2.15
        public static final double X_CONTROLLER_D = 0;
        public static final double Y_CONTROLLER_D = 0;
        public static final double THETA_CONTROLLER_P = 3; //3
        public static final TrapezoidProfile.Constraints THETA_CONTROLLER_CONTRAINTS = //
                new TrapezoidProfile.Constraints(
                        DriveConstants.MAX_VELOCITY_METERS_PER_SECOND,
                        DriveConstants.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND);

        public static final double PITCH_SET_POINT = 0.0;
        public static final double PITCH_P = 1.0;
        public static final double PITCH_I = 0.0;
        public static final double PITCH_D = 0.0;
        public static final double PITCH_F = 0.0;
        public static final double PITCH_TOLERANCE = 2.0;
    }

    /*
     * All real distance measurements are in inches
     */
    public static final class ElevatorConstants {
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

    public static final class OIConstants {
        /*
            Map of controllers using the port number as the key to the ControllerType
         */
        public static final Map<Integer, ControllerType> CONTROLLERS = Map.of(
                0, ControllerType.CUSTOM
        );
        public static final int CONTROLLER_COUNT = CONTROLLERS.size(); //placeholder value
        public static final double DEADBAND = 0.1;
        public static final String TRANSLATION_XSUPPLIER = "translationXSupplier";
        public static final String TRANSLATION_YSUPPLIER = "translationYSupplier";
        public static final String THETA_SUPPLIER = "thetaSupplier";

        public enum ControllerType {
            CUSTOM, XBOX
        }
    }

    public static final class IntakeConstants {
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

        // 0.315 for shoot cone low
        public static final double INTAKE_SPEED = 0.2; //placeholder
        public static final double MAX_ACCEL = 3.0;

        public static final double INTAKE_VOLTAGE_COMP = 12.0;

        public static final double INTAKE_P = 0.00; //placeholder
        public static final double INTAKE_I = 0.0; //placeholder
        public static final double INTAKE_D = 0.0; //placeholder
        public static final double INTAKE_F = 0.00; //placeholder

        public static final double INTAKE_KS = 0.3;
        public static final double INTAKE_KV = 0.1;
        public static final double INTAKE_KA = 0.0;

        public static final SimpleMotorFeedforward FEEDFORWARD = new SimpleMotorFeedforward(INTAKE_KS, INTAKE_KV, INTAKE_KA); //we can use once characterized

        public static final String INTAKE_BEAM_BREAK = "intakeBeamBreak";
        public static final String INTAKE_REED_SWITCH = "intakeReedSwitch";
        public static final String INTAKE_PID = "intakePIDController";
        public static final String INTAKE_ENCODER = "intakeEncoder";
        public static final int VOLTAGE_COMP = 10;
    }

    public static final class VisionConstants {
        public static final double STOPPING_DISTANCE = 1.58333;
        public static final double LIMELIGHT_HEIGHT = 10; //placeholder

        public static final double LIMELIGHT_MOUNTING_ANGLE = 10; //placeholder

        public static final String TABLE_NAME = "table";

        public static final double CENTER_LIMIT = 0.3;

//        public static final String VISION_PID_X = "pid controller x";
//        public static final String VISION_PID_Y = "pid controller x";
//        public static final String VISION_PID_ = "pid controller x";
    }
}