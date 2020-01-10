package interfacelayer;

import jssc.SerialPort;
import jssc.SerialPortException;

import java.util.Arrays;

public class BluetoothConnection {
    private String port;
    private SerialPort serialPort;
    private boolean portIsOpen;

    public BluetoothConnection(String port) {
        this.port = port;
        portIsOpen = true;
    }

    public void sendRoute(int[] route) {
        System.out.println("port is " + port);
        System.out.println("sending route " + Arrays.toString(route));
        try {
//            try {
            if (portIsOpen) {
                this.serialPort = new SerialPort(port);
                serialPort.openPort();
                portIsOpen = false;
            }
//            } catch (SerialPortException e1) {
//                System.out.println("port busy!");
//            }
            serialPort.setParams(SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.writeIntArray(route);
//            serialPort.closePort();
//            System.out.println("port closed");

        } catch (SerialPortException ex) {
            ex.printStackTrace();
        }

    }

    public void setPort(String port) {
        this.port = port;
        System.out.println("set port to " + port);
    }
}
