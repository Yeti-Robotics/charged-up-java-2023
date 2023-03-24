// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.constants;

import com.ctre.phoenix.WPI_CallbackHelper;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import org.opencv.core.Mat;

import java.util.Map;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public class DriveConstants {

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
    public static final double DRIVE_MOTOR_F = 0.0;

    public static final double AZIMUTH_MOTOR_P = 5.0; //placeholder from borealis 3
    public static final double AZIMUTH_MOTOR_I = 0.0; //placeholder from borealis
    public static final double AZIMUTH_MOTOR_D = 0.00; //placeholder from borealis 0.01
    public static final double AZIMUTH_MOTOR_F = 0.0;
    public static final double AZIMUTH_MAX_VEL = Math.toDegrees(Math.PI * 3) * CANCoderConstants.COUNTS_PER_DEG;
    public static final double AZIMUTH_MAX_ACCEL = Math.toDegrees(Math.PI * 6) * CANCoderConstants.COUNTS_PER_DEG;
    public static final int AZIMUTH_MOTION_SMOOTHING = 0;
    public static final double AZIMUTH_TOLERANCE = 0.5 * CANCoderConstants.COUNTS_PER_DEG;

    public static final double SWERVE_X_REDUCTION = 1.0 / 6.75;
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(4); //0.1016
    public static final double WHEEL_CIRCUMFERENCE =  WHEEL_DIAMETER * Math.PI;

    public static final double FRAME_PERIMETER = 27.0;
    private static final double DRIVETRAIN_WHEELBASE_METERS = Units.inchesToMeters(22.25); //PLACEHOLDER
    private static final double DRIVETRAIN_TRACKWIDTH_METERS = Units.inchesToMeters(22.25); //PLACEHOLDER

    public static final double MAX_VELOCITY_METERS_PER_SECOND = 6380.0 / 60.0 * SWERVE_X_REDUCTION * WHEEL_CIRCUMFERENCE; //placeholder
    public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = MAX_VELOCITY_METERS_PER_SECOND / Math.hypot(DriveConstants.DRIVETRAIN_TRACKWIDTH_METERS / 2, DriveConstants.DRIVETRAIN_WHEELBASE_METERS / 2); //PLACEHOLDER

    public static final double COUNTS_TO_METERS = TalonFXConstants.COUNTS_PER_REV * SWERVE_X_REDUCTION / WHEEL_CIRCUMFERENCE;

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

