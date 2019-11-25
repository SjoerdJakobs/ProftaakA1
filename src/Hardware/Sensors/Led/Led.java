package Hardware.Sensors.Led;

import ButterCat.HelpFunctions;
import TI.BoeBot;
import TI.Timer;

public class Led implements LedInterface {

    private int pin;
    private boolean usePinPower; // if true the led is powered by the pin output
    private boolean status;
    private Timer timer;

    /**
     * Initialise the LED and its hardware configuration.
     * @param pin The LED pin.
     * @param usePinPower Whether the LED is powered by the pin
     */
    public Led(int pin, boolean usePinPower) {
        HelpFunctions.checkDigitalPin("LED pin", pin);

        this.pin = pin;
        this.usePinPower = usePinPower;
    }

    /**
     * Turn the LED on.
     */
    public void turnOn() {
        this.status = true;
        setLed();
    }

    /**
     * Turn the LED off.
     */
    public void turnOff() {
        this.status = false;
        setLed();
    }

    /**
     * Let the RGB LED blink with a frequency
     * @param frequency The frequency of the RGB LED.
     */
    public void blink(double frequency) {
        timer = new Timer((int) Math.round(1000 / frequency));
        if (timer.timeout()) {
            this.status = !this.status;
            setLed();
        }
    }

    /**
     * Turn the LED on or off based on its status.
     */
    private void setLed() {
        BoeBot.digitalWrite(pin, this.status);
    }

    public boolean getStatus() {
        return this.status;
    }

}
