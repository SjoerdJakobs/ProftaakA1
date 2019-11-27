package ButterCat;

import Hardware.Sensors.ultrasonicsensor.UltraSonicSensor;
import Interface.Engine;
import Interface.ObjectDetection;
import OOFramework.FrameworkProgram;
import TI.BoeBot;

import TI.Timer;

public class Main {
    public static void main(String[] args) {
        //Program program = new Program();
        //program.run();

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


    }
}
