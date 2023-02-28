package frc.robot.constants;

public final class VisionConstants {
    public static final double X_OFFSET = -0.5;
    public static final double LIMELIGHT_HEIGHT = 11.9; //placeholder
    public static final double LIMELIGHT_X_TRANSLATION = 4.75;
    public static final double LIMELIGHT_Y_TRANSLATION = 0.0;
    public static final double LIMELIGHT_MOUNTING_ANGLE = 15.0; //placeholder

    public enum ALIGMENT_POSITION {
        HUMAN_PLAYER(0),
        LEFT(-1),
        MIDDLE(0),
        RIGHT(1);

        public double offset;
        ALIGMENT_POSITION(double offset) {
            this.offset = offset;
        }
    }

    public static final String TABLE_NAME = "table";

    public static final double CENTER_LIMIT = 0.3;

//        public static final String VISION_PID_X = "pid controller x";
//        public static final String VISION_PID_Y = "pid controller x";
//        public static final String VISION_PID_ = "pid controller x";
}
