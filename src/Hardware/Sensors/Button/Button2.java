package Hardware.Sensors.Button;

import java.security.InvalidParameterException;

public class Button2
{
    private int pin;
    private boolean isPressed;

    public Button2(int pin) throws InvalidParameterException {
        if (pin > 15)
            throw new InvalidParameterException("Pin number is too high: does not exist.");
        if (pin < 0)
            throw new InvalidParameterException("Pin number is too low: does not exist.");

        this.pin = pin;
        this.isPressed = false;
//        onButtonPress = () -> callback();
    }

    private void callback() {
        // Nothing here
    }

    public ButtonCallback onButtonPress;

    public int getPin() { return pin; }
    public void setPin(int pin) { this.pin = pin; }

    public boolean isPressed() { return isPressed; }
    public void setPressed(boolean pressed) { isPressed = pressed; }


}
