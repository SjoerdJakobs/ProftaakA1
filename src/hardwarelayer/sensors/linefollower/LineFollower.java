package hardwarelayer.sensors.linefollower;

import buttercat.HelpFunctions;
import buttercat.modules.Callback;
import TI.BoeBot;

public class LineFollower {
    private int pin;
    private int value;

    private Callback callback;

    public LineFollower(int pin) {
        HelpFunctions.checkAnalogPin("Boebot analog pin", pin);
        this.pin = pin;

        this.callback = this::read;
    }

    private void read() {
        this.value = BoeBot.analogRead(pin);
    }


}
