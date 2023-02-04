package frc.robot.utils.controllerUtils;

public enum POVDirections {
        UP(0),
        DOWN(90),
        LEFT(180),
        RIGHT(270);

        public final int value;

        POVDirections(int value) {
            this.value = value;
        }
}