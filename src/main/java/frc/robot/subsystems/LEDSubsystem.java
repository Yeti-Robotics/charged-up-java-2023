package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.LEDConstants;

import javax.inject.Inject;
import javax.inject.Named;


public class LEDSubsystem extends SubsystemBase {
    private final AddressableLED led;
    private final AddressableLEDBuffer ledBuffer;
    @Inject
    public LEDSubsystem(@Named(LEDConstants.LED) AddressableLED led, @Named(LEDConstants.LED_COUNT_NAME) AddressableLEDBuffer ledBuffer){
        this.ledBuffer = ledBuffer;
        this.led = led;


    }
    public void setHSV(int i, int hue, int saturation, int value) {
        ledBuffer.setHSV(i, hue, saturation, value);
    }

    public void setRGB(int i, int red, int green, int blue) {
        ledBuffer.setRGB(i, red, green, blue);
    }

    public int getBufferLength() {
        return ledBuffer.getLength();
    }

    public void sendData() {
        led.setData(ledBuffer);
    }


}

