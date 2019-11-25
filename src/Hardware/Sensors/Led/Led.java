package Hardware.Sensors.Led;

import TI.BoeBot;
import TI.Timer;

public class Led implements LedInterface {

    private int pin;
    private boolean usePinPower; // if true the led is powered by the pin output
    private boolean status;
    private Timer timer;

    public Led(int pin, boolean usePwmPower) throws IllegalArgumentException {
        if (pin < 0 || pin > 15)
            throw new IllegalArgumentException("LED pin must be between 0 and 15.");

        this.pin = pin;
        this.usePinPower = usePwmPower;
    }

    public void turnOn() {
        this.status = true;
        setLed(true);
    }

    public void turnOff() {
        this.status = false;
        setLed(false);
    }

    public void blink(double frequency) {
        timer = new Timer((int) Math.round(1000 / frequency));
        if (timer.timeout()) {
            this.status = !this.status;
            setLed(this.status);
        }
    }

    private void setLed(boolean status) {
        BoeBot.digitalWrite(pin, status);
    }

    public boolean getStatus() {
        return this.status;
    }

}
