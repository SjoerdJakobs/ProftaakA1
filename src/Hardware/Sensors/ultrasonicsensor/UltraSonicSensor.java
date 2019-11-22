package Hardware.Sensors.ultrasonicsensor;

import ButterCat.Modules.Callback;
import OOFramework.FrameworkProgram;
import OOFramework.StandardObject;
import TI.BoeBot;
import TI.Timer;

import java.util.Random;
//trig pin 7
//echo pin 8

public class UltraSonicSensor {

    private int echoPin;
    private int triggerPin;

    public Callback distanceCallback;

    Timer timer = new Timer(1);
    private int cm;

    public UltraSonicSensor(int triggerPin, int echoPin) {
        this.triggerPin = triggerPin;
        this.echoPin = echoPin;
        System.out.println("making new sensor");

        distanceCallback = this::listen;
    }

    private void initSensor() {
        BoeBot.digitalWrite(this.triggerPin, true);
        if (timer.timeout()) {
            BoeBot.digitalWrite(this.echoPin, false);
        }
    }

    public void listen() {
        initSensor();

        int pulse = BoeBot.pulseIn(1, true, 10000);
//        System.out.println("ultrasonic pulse: " + pulse);
//        Random random = new Random();
//        int pulse = random.nextInt();
        this.cm = pulse / 58;
//        System.out.println("distance in cm: " + cm);

    }

    public int getCm() {
        return this.cm;
    }
}
