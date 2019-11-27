package ButterCat;

import Hardware.Sensors.ultrasonicsensor.UltraSonicSensor;
import Interface.Engine;
import Interface.ObjectDetection;
import OOFramework.FrameworkProgram;
import TI.BoeBot;
import TI.Servo;

public class Main {
    public static void main(String[] args) {
        Program program = new Program();
        program.run();

//        Engine engine = new Engine(12, 13);
//
//        engine.setEngineTargetSpeed(200);
//        while(true) {
//            engine.driveForward();
//            System.out.println(engine.toString());
//            BoeBot.wait(20);
//            if (engine.getMotorRight().getServo().getPulseWidth() == 1700) engine.setEngineTargetSpeed(0);
//        }
//        UltraSonicSensor sensor = new UltraSonicSensor(7,8);
//
//        while (true) {
//            sensor.listen();
//
//            BoeBot.wait(1);
//        }
    }
}
