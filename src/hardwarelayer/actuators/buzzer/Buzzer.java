package hardwarelayer.actuators.buzzer;

import TI.BoeBot;

public class Buzzer {

    private int pin;
    private int frequency;

    public Buzzer(int pin, int frequency) {
        this.pin = pin;
        this.frequency = frequency;
    }

    /**
     * makes this buzzer generate a sound at its frequency
     * @param time the amount of time in ms to make the sound for
     */
    public void buzz(int time) {
        BoeBot.freqOut(pin, this.frequency, time);
    }

    /**
     * mutes this buzzer
     */
    public void mute() {
        buzz(0);
    }
    
    public int getPin() { return this.pin; }

    public int getFrequency() { return this.frequency; }
    public void setFrequency(int frequency) { this.frequency = frequency; }

}
