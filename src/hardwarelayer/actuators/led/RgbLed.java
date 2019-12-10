package hardwarelayer.actuators.led;

import buttercat.HelpFunctions;
import TI.PWM;
import TI.Timer;

public class RgbLed implements LedInterface{

    private PWM redLed;
    private PWM greenLed;
    private PWM blueLed;

    private int redValue;
    private int greenValue;
    private int blueValue;

    private boolean status;

    private Timer timer;

    /**
     * Initialise the RGB pins without its values.
     * @param redPin The RGB LED pin of the red diode.
     * @param greenPin The RGB LED pin of the green diode.
     * @param bluePin The RGB LED pin of the blue diode.
     */
    public RgbLed(int redPin, int greenPin, int bluePin) {
        HelpFunctions.checkDigitalPin("Red RGB pin", redPin);
        HelpFunctions.checkDigitalPin("Green RGB pin", greenPin);
        HelpFunctions.checkDigitalPin("Blue RGB pin", bluePin);

        this.redLed = new PWM(redPin, 0);
        this.greenLed = new PWM(greenPin, 0);
        this.blueLed = new PWM(bluePin, 0);

        this.status = false;

        this.redValue = 0;
        this.greenValue = 0;
        this.blueValue = 0;
    }

    /**
     * Initialise the RGB pins with its values without turning the LED on.
     * @param redPin The RGB LED pin of the red diode.
     * @param greenPin The RGB LED pin of the green diode.
     * @param bluePin The RGB LED pin of the blue diode.
     * @param redValue The red light value of the red diode.
     * @param greenValue The green light value of the green diode.
     * @param blueValue The blue light value of the blue diode.
     */
    public RgbLed(int redPin, int greenPin, int bluePin, int redValue, int greenValue, int blueValue) {
        HelpFunctions.checkDigitalPin("Red RGB pin", redPin);
        HelpFunctions.checkDigitalPin("Green RGB pin", greenPin);
        HelpFunctions.checkDigitalPin("Blue RGB pin", bluePin);

        this.redLed = new PWM(redPin, 0);
        this.greenLed = new PWM(greenPin, 0);
        this.blueLed = new PWM(bluePin, 0);

        this.status = false;

        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
    }

    /**
     * Initialise the RGB pins with its values with the option to turn the LED on.
     * @param redPin The RGB LED pin of the red diode.
     * @param greenPin The RGB LED pin of the green diode.
     * @param bluePin The RGB LED pin of the blue diode.
     * @param redValue The red light value of the red diode.
     * @param greenValue The green light value of the green diode.
     * @param blueValue The blue light value of the blue diode.
     * @param status Determines whether the LED will be ON or OFF.
     */
    public RgbLed(int redPin, int greenPin, int bluePin, int redValue, int greenValue, int blueValue, boolean status) {
        HelpFunctions.checkDigitalPin("Red RGB pin", redPin);
        HelpFunctions.checkDigitalPin("Green RGB pin", greenPin);
        HelpFunctions.checkDigitalPin("Blue RGB pin", bluePin);

        this.redLed = new PWM(redPin, 0);
        this.greenLed = new PWM(greenPin, 0);
        this.blueLed = new PWM(bluePin, 0);

        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;

        this.status = status;
        update(redValue, greenValue, blueValue);
    }

    /**
     * Turn the RGB LED on.
     */
    public void turnOn() {
        this.status = true;
        update(this.redValue, this.greenValue, this.blueValue);
    }

    /**
     * Turn the RGB LED off.
     */
    public void turnOff() {
        update(0,0,0);
        this.status = false;
    }

    /**
     * Let the RGB LED blink with a frequency
     * @param frequency The frequency of the RGB LED.
     */
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

    /**
     * Set the colour of the RGB LED regardless of whether the RGB LED is turned on.
     * @param redValue The red light value of the red diode.
     * @param greenValue The green light value of the green diode.
     * @param blueValue The blue light value of the blue diode.
     */
    public void setColour(int redValue, int greenValue, int blueValue) {
        HelpFunctions.checkValue("Red pin value", redValue, 0, 255);
        HelpFunctions.checkValue("Green pin value", greenValue, 0, 255);
        HelpFunctions.checkValue("Blue pin value", blueValue, 0, 255);

        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;

        update(redValue, greenValue, blueValue);
    }

    /**
     * Update the colour values of the RGB diodes ONLY if the RGB LED has status = true
     * @param redValue The red light value of the red diode.
     * @param greenValue The green light value of the green diode.
     * @param blueValue The blue light value of the blue diode.
     */
    private void update(int redValue, int greenValue, int blueValue) {
        if (status) {
            redLed.update(redValue);
            greenLed.update(greenValue);
            blueLed.update(blueValue);
        }
    }

    public boolean getStatus() { return this.status; }
    public int getRedValue() { return this.redValue; }
    public int getGreenValue() { return this.greenValue; }
    public int getBlueValue() { return this.blueValue; }
}
