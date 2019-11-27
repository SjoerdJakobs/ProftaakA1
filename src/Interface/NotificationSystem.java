package Interface;


import Hardware.Sensors.Buzzer.Buzzer;
import Hardware.Sensors.Led.Led;
import Hardware.Sensors.Led.NeoLed;

import java.awt.*;

public class NotificationSystem {
    // TODO: add select Led, RgbLed and Buzzer functions here.
    Led greenLed = new Led(5, true);
    //    RgbLed rgbLed = new RgbLed(2, 1, 0, 0, 0, 0, true);
    Buzzer buzzer = new Buzzer(9, 2000);
    NeoLed neoLed1 = new NeoLed(0, Color.white);
    NeoLed neoLed2 = new NeoLed(1, Color.white);
    NeoLed neoLed3 = new NeoLed(2, Color.white);
    NeoLed neoLed4 = new NeoLed(3, Color.white);
    NeoLed neoLed5 = new NeoLed(4, Color.white);
    NeoLed neoLed6 = new NeoLed(5, Color.white);

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
        buzzer.buzz(1000);
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

}
