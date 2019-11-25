package Hardware.Sensors.ultrasonicsensor;

import ButterCat.Modules.Callback;
import TI.BoeBot;
import TI.Timer;
//trig pin 7
//echo pin 8

/**
 * ultrasonic sensor class.
 * reads the sensor and {@link this#getDistanceToObject() saves the distance}
 */
public class UltraSonicSensor implements UltraSonicSensorInterface{

    private int echoPin;
    private int triggerPin;

    private Callback distanceCallback;

    private Timer initTimer;
    private Timer sensorTimeout;
    private int distanceToObject;

    /**
     * makes a new sensor
     *
     * @param triggerPin the pin to send the trigger signals to
     * @param echoPin    the pin to read from
     */
    public UltraSonicSensor(int triggerPin, int echoPin) throws IllegalArgumentException {
        if (triggerPin < 0 || echoPin < 0) {
            throw new IllegalArgumentException("pins can't be negative!");
        }

        this.triggerPin = triggerPin;
        this.echoPin = echoPin;
//        System.out.println("making new sensor");

        initTimer = new Timer(3);
        sensorTimeout = new Timer(30);
        distanceCallback = this::listen;
    }

    /**
     * sends a small pulse to the sensor to initialize it
     */
    private void initSensor() {
        BoeBot.digitalWrite(this.triggerPin, true);
        if (initTimer.timeout()) {
            BoeBot.digitalWrite(this.triggerPin, false);
        }
    }

    /**
     * listens for a new value from the sensor.
     */
    @Override
    public void listen() {
        initSensor();

        if (sensorTimeout.timeout()) {

            int pulse = BoeBot.pulseIn(echoPin, true, 10000);
//            System.out.println("ultrasonic pulse: " + pulse);
//            Random random = new Random();
//            int pulse = random.nextInt();
            if (pulse > 0) {
                this.distanceToObject = pulse / 58;
            }
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
}
