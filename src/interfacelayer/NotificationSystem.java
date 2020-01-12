package interfacelayer;


import TI.BoeBot;
import hardwarelayer.actuators.buzzer.Buzzer;
import hardwarelayer.actuators.led.Led;
import hardwarelayer.actuators.led.NeoLed;

import java.awt.*;

public enum  NotificationSystem {
    INSTANCE;
    // TODO: add select led, RgbLed and buzzer functions here.
    Led greenLed = new Led(5, true);
    //    RgbLed rgbLed = new RgbLed(2, 1, 0, 0, 0, 0, true);
    Buzzer buzzer = new Buzzer(2, 2000);
    NeoLed neoLed1 = new NeoLed(0, Color.white);

    public NeoLed getNeoLed1()
    {
        return neoLed1;
    }

    public NeoLed getNeoLed2()
    {
        return neoLed2;
    }

    public NeoLed getNeoLed3()
    {
        return neoLed3;
    }

    public NeoLed getNeoLed4()
    {
        return neoLed4;
    }

    public NeoLed getNeoLed5()
    {
        return neoLed5;
    }

    public NeoLed getNeoLed6()
    {
        return neoLed6;
    }

    NeoLed neoLed2 = new NeoLed(1, Color.white);
    NeoLed neoLed3 = new NeoLed(2, Color.white);
    NeoLed neoLed4 = new NeoLed(3, Color.white);
    NeoLed neoLed5 = new NeoLed(4, Color.white);
    NeoLed neoLed6 = new NeoLed(5, Color.white);

    boolean buzzerIsOn = false;
    double sumDeltaTimeForBuzzer = 0;



    //implement this in the driveForward method
    public void backwards() {
        neoLed1.turnOn();
        neoLed2.turnOn();
        neoLed3.turnOn();
        neoLed4.turnOff();
        neoLed5.turnOff();
        neoLed6.turnOff();
    }


    //implement this in the driveBackwards method
    public void forward() {
        neoLed1.turnOff();
        neoLed2.turnOff();
        neoLed3.turnOff();
        neoLed4.turnOn();
        neoLed5.turnOn();
        neoLed6.turnOn();
    }

    //implement this in the turnLeft method
    public void left() {
        neoLed1.turnOff();
        neoLed2.turnOff();
        neoLed3.turnOn();
        neoLed4.turnOn();
        neoLed5.turnOff();
        neoLed6.turnOff();
    }

    //implement this in the turnRight method
    public void right() {
        neoLed1.turnOn();
        neoLed2.turnOff();
        neoLed3.turnOff();
        neoLed4.turnOff();
        neoLed5.turnOff();
        neoLed6.turnOn();
    }

    //implement this in the objectDetection method
    public void objectDetected() {
        neoLed1.setColor(Color.yellow);
        neoLed2.setColor(Color.yellow);
        neoLed3.setColor(Color.yellow);
        neoLed4.setColor(Color.yellow);
        neoLed5.setColor(Color.yellow);
        neoLed6.setColor(Color.yellow);

    }

    //implement this in the remote class in the powerButton method
    public void remoteControll() {
        neoLed1.setColor(Color.blue);
        neoLed2.setColor(Color.blue);
        neoLed3.setColor(Color.blue);
        neoLed4.setColor(Color.blue);
        neoLed5.setColor(Color.blue);
        neoLed6.setColor(Color.blue);
    }

    public void turnLedsOn(){
        neoLed1.turnOn();
        neoLed2.turnOn();
        neoLed3.turnOn();
        neoLed4.turnOn();
        neoLed5.turnOn();
        neoLed6.turnOn();
    }

    public void turnLedsOff(){
        neoLed1.turnOff();
        neoLed2.turnOff();
        neoLed3.turnOff();
        neoLed4.turnOff();
        neoLed5.turnOff();
        neoLed6.turnOff();
    }

    /**
     * Create a buzzer sound using the wait function
     * @param frequency The frequency in Hz
     * @param waitTime The duration for which the buzzer will buzz in milliseconds
     */
    public void makeSound(int frequency, int waitTime) {
        if (buzzer.getFrequency() != frequency) buzzer.setFrequency(frequency);
        buzzer.buzz(waitTime);
    }

    /**
     * Create a buzzer sound using deltaTime
     * @param frequency The frequency in Hz.
     * @param deltaTime The difference between now and the previous time the function was called
     */
    public void makeSound(int frequency, double deltaTime) {
        sumDeltaTimeForBuzzer += deltaTime;
        if (buzzerIsOn) {
            buzzerIsOn = false;
            BoeBot.digitalWrite(buzzer.getPin(), true);
        }
        else {
            buzzerIsOn = true;
            BoeBot.digitalWrite(buzzer.getPin(), false);
        }
    }

    public void noRemoteControl() {
        neoLed1.setColor(Color.RED);
        neoLed2.setColor(Color.RED);
        neoLed3.setColor(Color.RED);
        neoLed4.setColor(Color.RED);
        neoLed5.setColor(Color.RED);
        neoLed6.setColor(Color.RED);
        turnLedsOn();
    }

    public void leftLineFollower(Color rgb) {
        neoLed4.setColor(rgb);
        neoLed4.turnOn();
        neoLed5.turnOff();
        neoLed6.turnOff();
    }

    public void midLineFollower(Color rgb) {
        neoLed5.setColor(rgb);
        neoLed5.turnOn();
        neoLed4.turnOff();
        neoLed6.turnOff();
    }

    public void rightLineFollower(Color rgb) {
        neoLed6.setColor(rgb);
        neoLed6.turnOn();
        neoLed4.turnOff();
        neoLed5.turnOff();
    }

    public void midRightLineFollower(Color rgb) {
        neoLed5.setColor(rgb);
        neoLed6.setColor(rgb);
        neoLed6.turnOn();
        neoLed5.turnOn();
        neoLed4.turnOff();

    }

    public void midLeftLineFollower(Color rgb) {
        neoLed5.setColor(rgb);
        neoLed4.setColor(rgb);
        neoLed4.turnOn();
        neoLed5.turnOn();
        neoLed6.turnOff();

    }

    public void allLineFollowers(Color rgb) {
        neoLed1.setColor(rgb);
        neoLed2.setColor(rgb);
        neoLed3.setColor(rgb);
        neoLed4.setColor(rgb);
        neoLed5.setColor(rgb);
        neoLed6.setColor(rgb);
        neoLed1.turnOn();
        neoLed2.turnOn();
        neoLed3.turnOn();
        neoLed4.turnOn();
        neoLed5.turnOn();
        neoLed6.turnOn();
    }

}
