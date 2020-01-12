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

    private void init() {
        try {
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
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void sendRoute(int[] route) {
        init();
        System.out.println("port is " + port);
        System.out.println("sending route " + Arrays.toString(route));
        try {
//            try {

            serialPort.writeIntArray(route);
//            serialPort.closePort();
//            System.out.println("port closed");

        } catch (SerialPortException ex) {
            ex.printStackTrace();
        }

    }

    public void sendNumber(int number) {
        init();
        try {
            serialPort.writeInt(number);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void setPort(String port) {
        this.port = port;
        System.out.println("set port to " + port);
    }
}
