package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.LEDConstants;

import javax.inject.Inject;
import javax.inject.Named;


public class LEDSubsystem extends SubsystemBase {
    public enum PieceTarget {
        CONE,
        CUBE,
        NONE
    }

    private final AddressableLED led;
    private final AddressableLEDBuffer ledBuffer;
    private PieceTarget pieceTarget = PieceTarget.NONE;

    @Inject
    public LEDSubsystem(AddressableLED led){
        this.ledBuffer = new AddressableLEDBuffer(LEDConstants.LED_COUNT);
        this.led = led;
    }

    public PieceTarget getPieceTarget() {
        return pieceTarget;
    }

    public void setPieceTarget(PieceTarget pieceTarget) {
        this.pieceTarget = pieceTarget;
    }

    public void setHSV(int i, int hue, int saturation, int value) {
        ledBuffer.setHSV(i, hue, saturation, value);
    }

    public void setRGB(int i, int red, int green, int blue) {
        // Green and blue are swapped cause we use ws2811, wpi assumes ws2812
        ledBuffer.setRGB(i, red, blue, green);
    }

    public int getBufferLength() {
        return ledBuffer.getLength();
    }

    public void sendData() {
        led.setData(ledBuffer);
    }

    public void setSolidRGB(int r, int g, int b) {
        for (int i = 0; i < getBufferLength(); i++) {
            setRGB(i, r, g, b);
        }
        sendData();
    }

    public void setYetiBlue() {
        setSolidRGB(20, 120, 255);
    }

    public void setConeYellow() {
        setPieceTarget(PieceTarget.CONE);
        setSolidRGB(255, 100, 0);
    }

    public void setCubePurple() {
        setPieceTarget(PieceTarget.CUBE);
        setSolidRGB(115, 0, 255);
    }
}

