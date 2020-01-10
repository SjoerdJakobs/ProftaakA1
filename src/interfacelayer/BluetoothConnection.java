package interfacelayer;

import jssc.SerialPort;
import jssc.SerialPortException;

import java.util.Arrays;

public class BluetoothConnection {
    private String port;
    private SerialPort serialPort;

    public BluetoothConnection(String port) {
        this.port = port;
    }

    public void sendRoute(int[] route) {
        this.serialPort = new SerialPort(port);
        System.out.println("port is " + port);
        System.out.println("sending route " + Arrays.toString(route));
        try {
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.writeIntArray(route);
            serialPort.closePort();

        } catch (SerialPortException ex) {
            ex.printStackTrace();
        }

    }

    public void setPort(String port) {
        this.port = port;
        System.out.println("set port to " + port);
    }
}
