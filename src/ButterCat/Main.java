package ButterCat;

import Hardware.Sensors.ultrasonicsensor.UltraSonicSensor;
import Interface.Engine;
import Interface.ObjectDetection;
import OOFramework.FrameworkProgram;
import TI.BoeBot;
import TI.Servo;

public class Main {
    public static void main(String[] args) {

        System.out.println("hey");
        /*while (true) {
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
        }*/
        Program program = new Program();
        program.run();

    }
}
