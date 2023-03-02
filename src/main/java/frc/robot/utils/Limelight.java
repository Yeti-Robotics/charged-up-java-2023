package frc.robot.utils;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;

public class Limelight {

    private static NetworkTableInstance table = null;

    /**
     * Light modes for Limelight.
     *
     * @author Dan Waxman
     */

    public enum LightMode {
        eOn,
        eOff,
        eBlink
    }

    /**
     * Camera modes for Limelight.
     *
     * @author Dan Waxman
     */
    public enum CameraMode {
        eVision,
        eDriver
    }


    /**
     * Gets whether a target is detected by the Limelight.
     *
     * @return true if a target is detected, false otherwise.
     */
    public static boolean hasTarget() {
        return getValue("tv").getDouble(0) == 1;
    }

    /**
     * Horizontal offset from crosshair to target (-27 degrees to 27 degrees).
     *
     * @return tx as reported by the Limelight.
     */
    public static double getTx() {
        return getValue("tx").getDouble(0.00);
    }

    /**
     * Vertical offset from crosshair to target (-20.5 degrees to 20.5 degrees).
     *
     * @return ty as reported by the Limelight.
     */
    public static double getTy() {
        return getValue("ty").getDouble(0.00);
    }

    /**
     * Area that the detected target takes up in total camera FOV (0% to 100%).
     *
     * @return Area of target.
     */
    public static double getTa() {
        return getValue("ta").getDouble(0.00);
    }

    /**
     * Gets target skew or rotation (-90 degrees to 0 degrees).
     *
     * @return Target skew.
     */
    public static double getTs() {
        return getValue("ts").getDouble(0.00);
    }

    /**
     * Gets target latency (ms).
     *
     * @return Target latency.
     */
    public static double getTl() {
        return getValue("tl").getDouble(0.00);
    }

    public static double getTlong() {
        return getValue("tlong").getDouble(0.0);
    }

    public static double[] getBotPose() {
        return DriverStation.getAlliance() == DriverStation.Alliance.Blue ?
                getValue("botpose_wpiblue").getDoubleArray(new double[7]) :
                getValue("botpose_wpired").getDoubleArray(new double[7]);
    }

    public static Translation2d getTranslation() {
        double[] botpose = getBotPose();
        return new Translation2d(botpose[0], botpose[1]);
    }

    public static double getPitch() {
        return getBotPose()[4];
    }

    public static double getRoll() {
        return getBotPose()[3];
    }

    public static double getYaw() {
        return getBotPose()[5];
    }

    public static long getID() {
        return getValue("tid").getInteger(0);
    }

    /**
     * Sets LED mode of Limelight.
     *
     * @param mode Light mode for Limelight.
     */
    public static void setLedMode(LightMode mode) {
        getValue("ledMode").setNumber(mode.ordinal());
    }


    /**
     * Sets camera mode for Limelight.
     *
     * @param mode Camera mode for Limelight.
     */

    /**
     * Sets pipeline number (0-9 value).
     *
     * @param number Pipeline number (0-9).
     */
    public static void setPipeline(int number) {
        getValue("pipeline").setNumber(number);
    }

    private static NetworkTableEntry getValue(String key) {
        if (table == null) {
            table = NetworkTableInstance.getDefault();
        }

        return table.getTable("limelight").getEntry(key);
    }
}
