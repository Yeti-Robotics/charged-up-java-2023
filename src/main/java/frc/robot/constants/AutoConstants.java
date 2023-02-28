package frc.robot.constants;

import edu.wpi.first.math.trajectory.TrapezoidProfile;

public final class AutoConstants {

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
