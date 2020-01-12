package hardwarelayer.sensors.linefollower;

import TI.BoeBot;
import TI.Timer;
import buttercat.HelpFunctions;
import interfacelayer.Callback;

/**
 * LineFollower class for the following of a black line
 */
public class LineFollower {
    private int pin;
    private int value;

    private Callback callback;
    private Timer lineTimer;

    /**
     * makes a new LineFollower object
     * @param pin the analog pin of the line follower
     */
    public LineFollower(int pin) {
        HelpFunctions.checkAnalogPin("Boebot analog pin", pin);
        this.pin = pin;
        this.lineTimer = new Timer(1);
        this.callback = this::read;
    }

    /**
     * reads the value of the line follower
     */
    public void read() {
        if (lineTimer.timeout())
            this.value = BoeBot.analogRead(pin);
//        System.out.println(this.value);
    }

    /**
     * gets the last measured value of the line follower
     * @return the value of the line follower
     */
    public int getValue() {
        return this.value;
    }

    public Callback startReading() {
        return this.callback;
    }



}
