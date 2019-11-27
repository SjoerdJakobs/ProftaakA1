package ButterCat;

import Interface.Engine;
import TI.BoeBot;

public class Main
{
    public static void main(String[] args)
    {
        //Program program = new Program();
        //program.run();

        Engine engine = new Engine(14, 15);

        engine.driveForward();
        int status = 0;

        while(true) {
            engine.drive();
            System.out.println(engine.toString());
            BoeBot.wait(15);

            if (engine.getMotorRight().getServo().getPulseWidth() == 1700 && status == 0) {
                engine.turnRight(1);
                status++;
                BoeBot.wait(3000);
            }
            if (engine.getMotorRight().getServo().getPulseWidth() == 1500 && status == 1) {
                engine.noTurn();
                status++;
                BoeBot.wait(3000);
            }
            if (engine.getMotorRight().getServo().getPulseWidth() == 1700 && status == 2) {
                engine.setEngineTargetSpeed(100);
                status++;
                BoeBot.wait(3000);
            }
            if (engine.getMotorRight().getServo().getPulseWidth() == 1600 && status == 3) {
                engine.turnLeft(0.5);
                status++;
                BoeBot.wait(3000);
            }


        }

    }
}
