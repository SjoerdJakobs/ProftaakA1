package Hardware.Sensors.Led;

import ButterCat.Modules.Callback;
import TI.BoeBot;
import TI.Timer;

import java.awt.*;

public class NeoLed implements LedInterface {
    private int pin;
    private Color color;
    private Timer timer;
    private boolean turnedOn;

    /**
     * creates a new NEOLED. The color will initially be set to black, which means off
     * @param pin the pin of the LED (0 to 5)
     */
    public NeoLed(int pin) {
        if (pin < 0 || pin > 5) {
            throw new IllegalArgumentException("The NEOPixel LED can only have a pin number from 0 to 5");
        }
        this.pin = pin;
        this.color = Color.BLACK;
        this.turnedOn = false;
    }

    /**
     * sets the color of the led
     * @param color the color to give this led.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * turns this led on.
     */
    @Override
    public void turnOn() {
        BoeBot.rgbSet(pin, this.color);
        BoeBot.rgbShow();
        this.turnedOn = true;
    }

    /**
     * turns this led off.
     */
    @Override
    public void turnOff() {
        BoeBot.rgbSet(pin, Color.BLACK);
        BoeBot.rgbShow();
        this.turnedOn = false;
    }

    /**
     * makes this led blink with the given frequency
     * @param frequency the frequency to blink with
     */
    @Override
    public void blink(double frequency) {
        timer = new Timer((int) Math.round(1000 / frequency));
        if (timer.timeout()) {
            turnOn();
        } else {
            turnOff();
        }
    }

    /**
     * gets if the led is turned on or not
     * @return true if the led is turned on, false if not.
     */
    @Override
    public boolean getStatus() {
        return this.turnedOn;
    }
}
