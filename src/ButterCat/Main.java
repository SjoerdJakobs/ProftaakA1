package ButterCat;

import Hardware.Sensors.ultrasonicsensor.UltraSonicSensor;
import Interface.Engine;
import Interface.ObjectDetection;
import OOFramework.FrameworkProgram;
import TI.BoeBot;

import TI.SerialConnection;
import TI.Timer;
import jssc.SerialPort;
import jssc.SerialPortException;


public class Main {
    public static void main(String[] args) {
//        SerialPort serialPort = new SerialPort("COM6");
//        try {
//            serialPort.openPort();
//            serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//            serialPort.writeString("HelloWorld");
//
//            byte[] buffer = serialPort.readBytes(10);
//
//            for ( int count = 0; count < 10; count ++) System.out.println(buffer[count] + "-");
//            serialPort.closePort();
//        } catch (SerialPortException ex) {
//            System.out.println(ex);
//        }


        SerialConnection conn = new SerialConnection();

        Engine engine = new Engine(14, 15);

        while (true) {
            if (conn.available() > 0) {
                int data = conn.readByte();
                conn.writeByte(data);
                System.out.println("Received: " + data);
                switch (data) {
                    case 119:
                        System.out.println("Forward");
                        engine.driveForward(200);
                        break;
                    case 97:
                        System.out.println("Left");
                        engine.turnLeft(1);
                        break;
                    case 100:
                        System.out.println("Right");
                        engine.turnRight(1);
                        break;
                    case 115:
                        System.out.println("Backwards");
                        engine.driveBackward(200);
                        break;
                    case 32:
                        System.out.println("Stop");
                        engine.stopDriving();
                        break;
                }
            }
            BoeBot.wait(10);
        }


//
//        System.out.println("hey");
//        Program program = new Program();
//        program.run();
        /*
        Engine engine = new Engine(12, 13);
        // driehoek 1240
        // vierkant 930
        engine.driveForward(200);
        Timer shapeTimer = new Timer(1000);
        Timer turnTimer = new Timer(5);
        int squareCounter = 0;
        boolean needsToTurn = false;
        while (true) {
            engine.drive();
//            engine.driveSquare();
//            System.out.println(engine.toString());
//            BoeBot.wait(15);

            BoeBot.wait(1);
        }


        while (true) {
            int pulseLen = BoeBot.pulseIn(1, false, 6000);
            long number = 0;
            if (pulseLen > 2000) {
                int lengtes[] = new int[12];
                for (int i = 0; i < 12; i++) {
                    lengtes[i] = BoeBot.pulseIn(1, false, 20000);
                }
                for (int i = 0; i < 12; i++) {
                    System.out.print(lengtes[i]+", ");
                }
                System.out.println("");
                //System.out.println(number);
            }
        }
        Program program = new Program();
        program.run();
        */
    }
}
