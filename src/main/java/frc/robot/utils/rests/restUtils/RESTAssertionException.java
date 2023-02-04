package frc.robot.utils.rests.restUtils;

public class RESTAssertionException extends RuntimeException{
    public RESTAssertionException() {
        super("REST assertion failed.");
    }

    public RESTAssertionException(String message) {
        super(message);
    }
}
