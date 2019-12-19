package hardwarelayer.sensors.button;

import buttercat.HelpFunctions;

public class Button2
{
    private int pin;
    private boolean isPressed;

    public Button2(int pin) {
        HelpFunctions.checkDigitalPin("Button pin", pin);

        this.pin = pin;
        this.isPressed = false;
        onButtonPress = () -> callback();
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
