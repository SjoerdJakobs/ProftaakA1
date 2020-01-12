package hardwarelayer.sensors.ultrasonicsensor;

import TI.BoeBot;
import TI.Timer;
import buttercat.HelpFunctions;
import interfacelayer.Callback;
//trig pin 7
//echo pin 8

/**
 * ultrasonic sensor class.
 * reads the sensor and {@link this#getDistanceToObject() saves the distance}
 */
public class UltraSonicSensor {

    private int echoPin;
    private int triggerPin;

    private Callback distanceCallback;

    private Timer sensorTimeout;
    private int distanceToObject;

    /**
     * makes a new sensor
     *
     * @param triggerPin the pin to send the trigger signals to
     * @param echoPin    the pin to read from
     */
    public UltraSonicSensor(int triggerPin, int echoPin) {
        HelpFunctions.checkDigitalPin("Ultrasonic sensor trigger pin", triggerPin);
        HelpFunctions.checkDigitalPin("Ultrasonic sensor echo pin", echoPin);

        this.triggerPin = triggerPin;
        this.echoPin = echoPin;

        sensorTimeout = new Timer(50);
        distanceCallback = this::listen;

    }

    /**
     * sends a small pulse to the sensor to initialize it
     */
    private void initSensor() {
        BoeBot.digitalWrite(this.triggerPin, true);
        BoeBot.uwait(1);
        BoeBot.digitalWrite(this.triggerPin, false);
    }

    /**
     * listens for a new value in millimeters from the sensor.
     */
    public void listen() {
        if (sensorTimeout.timeout()) {
            initSensor();
            int pulse = BoeBot.pulseIn(echoPin, true, 10000);
            //System.out.println("ultrasonic pulse: " + pulse);

            if (pulse > 100) {
                this.distanceToObject = (int) (pulse / 5.82);
            }
//            System.out.println(getDistanceToObject());
        }
    }

    /**
     * gets the current distance the sensor is reading
     *
     * @return the current distance in distanceToObject as an <code>int</code>
     */
    public int getDistanceToObject() {
        return this.distanceToObject;
    }

    public Callback onDistanceCallback() {
        return this.distanceCallback;
    }

    public String toString() {
        return "Ultrasonic sensor distance: " + getDistanceToObject() + " mm";
    }
}
