package Hardware.Sensors.bluetoothreceiver;

import Interface.Engine;
import TI.SerialConnection;

public class BluetoothReceiver {
    private Engine engine;

    public BluetoothReceiver(Engine engine) {
        this.engine = engine;
    }

    SerialConnection conn = new SerialConnection();

    public void getInput() {
        if (conn.available() > 0) {
            int data = conn.readByte();
            conn.writeByte(data);
            //System.out.println("Received: " + data);

            switch (data) {
                case 119: //w
                    System.out.println("Forward");
                    engine.driveForward(200);
                    break;
                case 97: //a
                    System.out.println("Left");
                    engine.turnLeft(1);
                    break;
                case 100: //d
                    System.out.println("Right");
                    engine.turnRight(1);
                    break;
                case 115: //s
                    System.out.println("Backwards");
                    engine.driveBackward(200);
                    break;
                case 32: //space
                    System.out.println("Stop");
                    engine.stopDriving();
                    break;
            }
        }

    }
}