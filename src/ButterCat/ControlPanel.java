package ButterCat;

import ButterCat.Modules.asciibutton.AsciiButton;
import Hardware.Sensors.bluetoothreceiver.BluetoothReceiver;

import java.util.ArrayList;

public class ControlPanel {
    private AsciiButton wButton;
    private AsciiButton aButton;
    private AsciiButton sButton;
    private AsciiButton dButton;
    private AsciiButton spaceButton;

    private BluetoothReceiver bluetoothReceiver;
    private ArrayList<AsciiButton> asciiButtons;

    public void AsciiButton() {
        wButton = new AsciiButton(119);
        aButton = new AsciiButton(97);
        sButton = new AsciiButton(115);
        dButton = new AsciiButton(100);
        spaceButton = new AsciiButton(32);
        asciiButtons.add(wButton);
        asciiButtons.add(aButton);
        asciiButtons.add(sButton);
        asciiButtons.add(dButton);
        asciiButtons.add(spaceButton);
    }

    public BluetoothReceiver getBluetoothReceiver() {
        return bluetoothReceiver;
    }

    public ArrayList<AsciiButton> getAsciiButtons() {
        return asciiButtons;
    }

    public AsciiButton getwButton() {
        return wButton;
    }

    public AsciiButton getaButton() {
        return aButton;
    }

    public AsciiButton getsButton() {
        return sButton;
    }

    public AsciiButton getdButton() {
        return dButton;
    }

    public AsciiButton getSpaceButton() {
        return spaceButton;
    }

}