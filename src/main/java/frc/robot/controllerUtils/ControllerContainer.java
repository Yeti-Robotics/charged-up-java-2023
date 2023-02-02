package frc.robot.controllerUtils;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OIConstants;
import frc.robot.utils.controllerUtils.Controller;

public class ControllerContainer {
    private final frc.robot.utils.controllerUtils.Controller[] controllers;

    public ControllerContainer() {
        controllers = new frc.robot.utils.controllerUtils.Controller[OIConstants.CONTROLLER_COUNT];

        OIConstants.CONTROLLERS.forEach((port, type) -> {
            switch (type) {
                case CUSTOM:
                    controllers[port] = new CustomController(port);
                    break;
                case XBOX:
                    controllers[port] = new CustomXboxController(port);
                    break;
            }
        });
    }

    public frc.robot.utils.controllerUtils.Controller get(int controllerNumber) {
        return controllers[controllerNumber];
    }

    public frc.robot.utils.controllerUtils.Controller[] getControllers() {
        return controllers;
    }

    private static class CustomController extends frc.robot.utils.controllerUtils.Controller {
        public CustomController(int port) {
            super(port);
        }

        @Override
        public double getLeftY() {
            return -super.getRawAxis(0);
        }

        @Override
        public double getLeftX() {
            return super.getRawAxis(1);
        }

        @Override
        public double getRightY() {
            return -super.getRawAxis(2);
        }

        @Override
        public double getRightX() {
            return super.getRawAxis(3);
        }
    }

    private static class CustomXboxController extends Controller {
        public CustomXboxController(int port) {
            super(port);
        }

        @Override
        public double getLeftY() {
            return -super.getRawAxis(XboxController.Axis.kLeftY.value);
        }

        @Override
        public double getLeftX() {
            return super.getRawAxis(XboxController.Axis.kLeftX.value);
        }

        @Override
        public double getRightY() {
            return -super.getRawAxis(XboxController.Axis.kRightY.value);
        }

        @Override
        public double getRightX() {
            return super.getRawAxis(XboxController.Axis.kRightX.value);
        }
    }
}
