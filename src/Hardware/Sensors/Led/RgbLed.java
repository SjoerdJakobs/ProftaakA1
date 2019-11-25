package Hardware.Sensors.Led;

import TI.PWM;
import TI.Timer;

public class RgbLed {

    private PWM redLed;
    private PWM greenLed;
    private PWM blueLed;

    private int redValue;
    private int greenValue;
    private int blueValue;

    private boolean status;

    private Timer timer;

    public RgbLed(int redPin, int greenPin, int bluePin, boolean status) {
        checkPin("Red RGB pin", redPin);
        checkPin("Green RGB pin", greenPin);
        checkPin("Blue RGB pin", bluePin);

        this.redLed = new PWM(redPin, 0);
        this.greenLed = new PWM(greenPin, 0);
        this.blueLed = new PWM(bluePin, 0);

        this.status = status;

        this.redValue = 0;
        this.greenValue = 0;
        this.blueValue = 0;
    }

    public RgbLed(int redPin, int greenPin, int bluePin) {
        checkPin("Red RGB pin", redPin);
        checkPin("Green RGB pin", greenPin);
        checkPin("Blue RGB pin", bluePin);

        this.redLed = new PWM(redPin, 0);
        this.greenLed = new PWM(greenPin, 0);
        this.blueLed = new PWM(bluePin, 0);

        this.status = false;

        this.redValue = 0;
        this.greenValue = 0;
        this.blueValue = 0;
    }

    public RgbLed(int redPin, int greenPin, int bluePin, boolean status, int redValue, int greenValue, int blueValue) {
        checkPin("Red RGB pin", redPin);
        checkPin("Green RGB pin", greenPin);
        checkPin("Blue RGB pin", bluePin);

        this.redLed = new PWM(redPin, 0);
        this.greenLed = new PWM(greenPin, 0);
        this.blueLed = new PWM(bluePin, 0);

        this.status = status;

        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
    }

    public RgbLed(int redPin, int greenPin, int bluePin, int redValue, int greenValue, int blueValue) {
        checkPin("Red RGB pin", redPin);
        checkPin("Green RGB pin", greenPin);
        checkPin("Blue RGB pin", bluePin);

        this.redLed = new PWM(redPin, 0);
        this.greenLed = new PWM(greenPin, 0);
        this.blueLed = new PWM(bluePin, 0);

        this.status = false;

        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
    }

    private void checkPin(String argumentName, int argumentValue ) throws IllegalArgumentException {
        if (argumentValue < 0 || argumentValue > 15)
            throw new IllegalArgumentException(argumentName + " value must be between 0 and 15.");
    }

    private void checkValue(String argumentName, int argumentValue) throws IllegalArgumentException {
        if (argumentValue < 0 || argumentValue > 255)
            throw new IllegalArgumentException(argumentName + " value must be between 0 and 255.");
    }

    public void turnOn() {
        this.status = true;
        update(this.redValue, this.greenValue, this.blueValue);
    }

    public void turnOff() {
        this.status = false;
        update(0,0,0);
    }

    public void blink(double frequency) {
        timer = new Timer((int) Math.round(1000 / frequency));
        if (timer.timeout()) {
            this.status = !this.status;
            if (status)
                update(this.redValue, this.greenValue, this.blueValue);
            else
                update(0,0,0);
        }
    }

    public void setColour(int redValue, int greenValue, int blueValue) {
        checkValue("Red pin value", redValue);
        checkValue("Green pin value", greenValue);
        checkValue("Blue pin value", blueValue);

        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;

        if (status) update(redValue, greenValue, blueValue);
    }

    private void update(int redValue, int greenValue, int blueValue) {
        redLed.update(redValue);
        greenLed.update(greenValue);
        blueLed.update(blueValue);
    }

    public boolean getStatus() { return this.status; }
    public int getRedValue() { return this.redValue; }
    public int getGreenValue() { return this.greenValue; }
    public int getBlueValue() { return this.blueValue; }
}
