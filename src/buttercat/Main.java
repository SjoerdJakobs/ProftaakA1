package buttercat;

import TI.BoeBot;
import interfacelayer.Engine;

import java.util.ArrayList;
import java.util.Collections;

//Main Programm
public class Main {
    public static void main(String[] args) {

        System.out.println("hey");

        Program program = new Program();
        program.run();


    }
}

//    }
//}

//Bluetooth test
//    SerialConnection conn = new SerialConnection();
//
//while (true) {
//        if (conn.available() > 0) {
//            int data = conn.readByte();
//            conn.writeByte(data);
//            System.out.println("Received: " + data);
//        }
//            switch (data) {
//                case 119: //w
//                    System.out.println("Forward");
//                    break;
//                case 97: //a
//                    System.out.println("Left");
//                    break;
//                case 100: //d
//                    System.out.println("Right");
//                    break;
//                case 115: //s
//                    System.out.println("Backwards");
//                    break;
//                case 32: //space
//                    System.out.println("Stop");
//                    break;
//            }
//        }
//
//    }
//}


// Buzzer test
//        while (true) {
//            BoeBot.digitalWrite(9, true);
//            BoeBot.wait(1);
//            BoeBot.digitalWrite(9, false);
//            BoeBot.wait(1);
//        }


//Bluetooth test
//public class Main {
//    public static void main(String[] args) {
//        SerialConnection conn = new SerialConnection();
//
//        while (true) {
//            if (conn.available() > 0) {
//                int data = conn.readByte();
//                conn.writeByte(data);
//                System.out.println("Received: " + data);
//
//
//                switch (data) {
//                    case 119: //w
//                        System.out.println("Forward");
//                        break;
//                    case 97: //a
//                        System.out.println("Left");
//                        break;
//                    case 100: //d
//                        System.out.println("Right");
//                        break;
//                    case 115: //s
//                        System.out.println("Backwards");
//                        break;
//                    case 32: //space
//                        System.out.println("Stop");
//                        break;
//                }
//            }
//        }
//
//    }
//}


// Buzzer test
//        while (true) {
//            BoeBot.digitalWrite(9, true);
//            BoeBot.wait(1);
//            BoeBot.digitalWrite(9, false);
//            BoeBot.wait(1);
//        }
//        LineFollowChecker follower = new LineFollowChecker(program);
//        LineFollower left = new LineFollower(0);
//        LineFollower mid = new LineFollower(1);
//        LineFollower right = new LineFollower(2);
//        while (true) {
//            left.read();
//            mid.read();
//            right.read();
//
//            System.out.println("left (pin 0) : " + left.getValue());
//            System.out.println("mid (pin 1)  : " + mid.getValue());
//            System.out.println("right (pin 2): " + right.getValue());
//            System.out.println();
//
////            BoeBot.wait(10);
//        }
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
