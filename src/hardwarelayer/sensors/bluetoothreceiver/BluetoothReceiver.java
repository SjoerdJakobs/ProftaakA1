package hardwarelayer.sensors.bluetoothreceiver;


import TI.SerialConnection;
import hardwarelayer.sensors.asciibutton.AsciiButton;

import java.util.ArrayList;

public class BluetoothReceiver {

    SerialConnection conn = new SerialConnection();

    public void checkForButtonPresses(ArrayList<AsciiButton> asciibuttons) {
        int data = conn.readByte();
        conn.writeByte(data);
        //System.out.println("Received: " + data);
        if (data != -1) {
            for (AsciiButton asciiButton : asciibuttons) {
                if (asciiButton.getAscii() == data) {
                    if (!asciiButton.isPressed()) {
                        asciiButton.setPressed(true);
                        asciiButton.onButtonPress.run();
                    } else if (asciiButton.isPressed() && asciiButton.isContinuousCallback()) {
                        asciiButton.onButtonPress.run();
                    }
                } else if (asciiButton.isPressed()) {
                    asciiButton.setPressed(false);
                }
            }
        }
        else{
            for (AsciiButton asciiButton: asciibuttons) {
                asciiButton.setPressed(false);
            }
            data = -1;
        }
    }
}


//    private Engine engine;
//
//    public BluetoothReceiver(Engine engine) {
//        this.engine = engine;
//    }
//
//    SerialConnection conn = new SerialConnection();
//
//    public void getInput() {
//        if (conn.available() > 0) {
//            int data = conn.readByte();
//            conn.writeByte(data);
//            //System.out.println("Received: " + data);
//
//            switch (data) {
//                case 119: //w
//                    System.out.println("Forward");
//                    engine.driveForward(200);
//                    break;
//                case 97: //a
//                    System.out.println("Left");
//                    engine.turnLeft(1);
//                    break;
//                case 100: //d
//                    System.out.println("Right");
//                    engine.turnRight(1);
//                    break;
//                case 115: //s
//                    System.out.println("Backwards");
//                    engine.driveBackward(200);
//                    break;
//                case 32: //space
//                    System.out.println("Stop");
//                    engine.stopDriving();
//                    break;
//            }
//        }
//
//    }
//}