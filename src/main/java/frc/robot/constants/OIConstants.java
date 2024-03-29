package frc.robot.constants;

import java.util.Map;

public final class OIConstants {
    /*
        Map of controllers using the port number as the key to the ControllerType
     */
    public static final Map<Integer, ControllerType> CONTROLLERS = Map.of(
            0, ControllerType.CUSTOM
    );
    public static final int CONTROLLER_COUNT = CONTROLLERS.size(); //placeholder value
    public static final double DEADBAND = 0.05;
    public static final String TRANSLATION_XSUPPLIER = "translationXSupplier";
    public static final String TRANSLATION_YSUPPLIER = "translationYSupplier";
    public static final String THETA_SUPPLIER = "thetaSupplier";

    public enum ControllerType {
        CUSTOM, XBOX
    }

    public enum ButtonMode {
        PRIMARY, AUTO_ALIGN
    }
}
