package frc.robot.constants;

public final class VisionConstants {
    public static final double LIMELIGHT_HEIGHT = 11.9; //placeholder
    public static final double LIMELIGHT_X_TRANSLATION = 4.75;
    public static final double LIMELIGHT_Y_TRANSLATION = 0.0;
    public static final double LIMELIGHT_MOUNTING_ANGLE = 15.0; //placeholder

    public static final String TABLE_NAME = "table";

    public static final double CENTER_LIMIT = 0.3;

    public enum Pipeline {
        DEFAULT(0),
        CHUTE(1);

        public final int value;
        Pipeline(int value) {
            this.value = value;
        }
    }

//        public static final String VISION_PID_X = "pid controller x";
//        public static final String VISION_PID_Y = "pid controller x";
//        public static final String VISION_PID_ = "pid controller x";
}
