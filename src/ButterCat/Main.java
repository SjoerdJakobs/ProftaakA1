package ButterCat;

import Interface.Engine;
import TI.BoeBot;
import TI.Servo;

public class Main
{
    public static void main(String[] args)
    {
        //Program program = new Program();
        //program.run();

        Engine engine = new Engine(12, 13);

        engine.setEngineTargetSpeed(-200);
        while(true) {
            engine.driveForward();
            System.out.println(engine.toString());
            BoeBot.wait(20);
        }
    }
}
