package hardwarelayer.sensors.bluetoothreceiver;


import TI.SerialConnection;
import hardwarelayer.sensors.asciibutton.AsciiButton;
import interfacelayer.Callback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BluetoothReceiver {

    public Callback somethingHasBeenPressed;
    public SerialConnection conn;

    public ArrayList<Integer> route;
    private int[] routeAsArray;
    private boolean routeIsEntered = false;

    public BluetoothReceiver() {
        this.conn = new SerialConnection();
        this.route = new ArrayList<>();
    }

    public void checkForButtonPresses(ArrayList<AsciiButton> asciibuttons) {

        if (conn.available() > 0) {
            int data = conn.readByte();
            System.out.println("Received: " + data);
            System.out.println("Next line");
            conn.writeByte(data);

            if (data > 0 && data != 250) {
                for (AsciiButton asciiButton : asciibuttons) {
                    if (asciiButton.getAscii() == data) {
                        if (!asciiButton.isPressed()) {
                            asciiButton.setPressed(true);
                            asciiButton.onButtonPress.run();
                            somethingHasBeenPressed.run();
                        } else if (asciiButton.isContinuousCallback()) {
                            asciiButton.onButtonPress.run();
                        }
                    } else if (asciiButton.isPressed()) {
                        asciiButton.setPressed(false);
                    }
                }
            } else if (data == 250) {
                data = conn.readByte();
                while (data != 251) {
                    this.route.add(data);
                    System.out.println(this.route);
                    data = conn.readByte();
                }
            } else {
                for (AsciiButton asciiButton : asciibuttons) {
                    asciiButton.setPressed(false);
                }
                data = -1;
            }
        }
        if (!route.isEmpty()) {
            this.routeAsArray = convertToArray(route);
        }
        routeIsEntered = true;
    }

    private int[] convertToArray(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next();
        }
        return ret;
    }

    public boolean isRouteEntered() {
        return this.routeIsEntered;
    }

    public int[] getRouteAsArray() {
        return this.routeAsArray;
    }

    public SerialConnection getConn() {
        return conn;
    }

    public ArrayList<Integer> getRoute() {
        return this.route;
    }
}
