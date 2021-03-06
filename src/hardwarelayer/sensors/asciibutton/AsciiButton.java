package hardwarelayer.sensors.asciibutton;

import interfacelayer.Callback;

public class AsciiButton {

    private int ascii;
    private boolean continuousCallback;
    private boolean isPressed;


    public AsciiButton(int ascii) {
        this(ascii, false);
    }

    public AsciiButton(int ascii, boolean continuousCallback) {
        this.ascii = ascii;
        this.continuousCallback = continuousCallback;
        this.isPressed = false;
        onButtonPress = this::emptyCallback;
    }

    private void emptyCallback() {
        System.out.println("this callBack is empty, class: AsciiButton    line: 15");
    }

    public Callback onButtonPress;
//
//    public Callback callback() {
//        return onButtonPress;
//    }
    public boolean isContinuousCallback() {
        return continuousCallback;
    }

    public void setContinuousCallback(boolean continuousCallback) {
        this.continuousCallback = continuousCallback;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public long getAscii() {
        return ascii;
    }

    public void setAscii(int ascii) {
        this.ascii = ascii;
    }

    public Callback getOnButtonPress() {
        return this.onButtonPress;
    }
}
