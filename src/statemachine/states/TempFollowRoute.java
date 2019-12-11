package statemachine.states;

import TEMP.TempEngine;
import TI.BoeBot;
import buttercat.DriverAI;
import interfacelayer.LineFollowChecker;
import statemachine.State;
import statemachine.StateID;

public class TempFollowRoute extends State
{

    public TempFollowRoute(DriverAI driverAI)
    {
        super(StateID.TempFollowRoute);

        this.engine = driverAI.getTempEngine();
        this.lineFollowChecker =  driverAI.getLineFollowChecker();
    }

    private boolean left;
    private boolean right;
    private boolean forward;

    int turn = 2;
    int turnCounter = 0;

    private boolean hasLine;
    private TempEngine engine;
    private LineFollowChecker lineFollowChecker;
    private boolean currentTurning = false;

    int lastDetectedPin = 0;
    boolean hasline = false;

    @Override
    protected void enter()
    {
        super.enter();
        engine.SetTargetSpeed(80, 0);
        hasLine = true;
        System.out.println("entered");
        // write code here
    }

    @Override
    protected void checkForStateSwitch()
    {
        super.checkForStateSwitch();
    }

    @Override
    protected void logic()
    {
        super.logic();

        if (!currentTurning) {

            if (lineFollowChecker.leftNoticedLine() && lineFollowChecker.midLeftNoticedLine()&&  lineFollowChecker.midRightNoticedLine() && lineFollowChecker.rightNoticedLine()) {
                System.out.println("All lines detected.");
                engine.SetTargetSpeed(100, 0);

                hasLine = true;
                currentTurning = true;
                return;
            }

            if(lineFollowChecker.midLeftNoticedLine()&&  lineFollowChecker.midRightNoticedLine())
            {
                System.out.println("pin 1 and 2");
                engine.Steer(true, 0);
                engine.SetTargetSpeed(500, 0);
            }
            else if(lineFollowChecker.leftNoticedLine() && lineFollowChecker.midLeftNoticedLine())
            {
                System.out.println("pin 0 and 1");
                engine.Steer(true, 0.8);

                engine.SetTargetSpeed(200, 0);
            }
            else if(lineFollowChecker.midRightNoticedLine() && lineFollowChecker.rightNoticedLine())
            {
                System.out.println("pin 2 and 3");
                engine.Steer(false, 0.8);
                engine.SetTargetSpeed(200, 0);
            }
            else if(lineFollowChecker.midRightNoticedLine())
            {
                System.out.println("pin 2");
                engine.Steer(false, 0.70);
                engine.SetTargetSpeed(200, 0);
                lastDetectedPin = 2;
            }
            else if(lineFollowChecker.midLeftNoticedLine())
            {
                System.out.println("pin 1");
                engine.Steer(true, 0.70);
                engine.SetTargetSpeed(200, 0);
                lastDetectedPin = 1;
            }
            else if(lineFollowChecker.rightNoticedLine())
            {
                System.out.println("pin 3");
                engine.Steer(false, 1.6);
                engine.SetTargetSpeed(200, 0);
                lastDetectedPin = 3;
            }
            else if(lineFollowChecker.leftNoticedLine())
            {
                System.out.println("pin 0");
                engine.Steer(true, 1.6);
                engine.SetTargetSpeed(200, 0);
                lastDetectedPin = 0;
            }
            else if(lastDetectedPin == 2||lastDetectedPin == 3)
            {
                System.out.println("last pin 2 or 3");
                engine.Steer(false, 1.2);
                engine.SetTargetSpeed(200, 0);
            }
            else if(lastDetectedPin == 1 || lastDetectedPin == 0)
            {
                System.out.println("last pin 1 or 0");
                engine.Steer(true, 1.2);
                engine.SetTargetSpeed(200, 0);
            }

        }


        // When turning, continue detecting a line after which the turning will stop
        if (currentTurning) {

            engine.SetTargetSpeed(100, 0);
            if(turn == 2)
            {
                engine.Steer(false, 3);
            }
            else if(turn == 3)
            {
                engine.Steer(true, 3);
            }
            else if(turn == 0)
            {
                engine.Steer(false, 3);
            }
            if (lineFollowChecker.rightNoticedLine()||lineFollowChecker.leftNoticedLine())
            {
                System.out.println("hasline");
                if(!hasLine)
                {
                    System.out.println("addcount");
                    if(turn == 0) {
                        turn = 2;
                    }
                    else
                    {
                        turnCounter = 1;
                    }
                }
                hasLine = true;
            }
            else {
                hasLine = false;
            }

            if(turnCounter == 1) {
                if (lineFollowChecker.midLeftNoticedLine()|| lineFollowChecker.midRightNoticedLine()) {

                    System.out.println("wedone");
                    currentTurning = false;
                    turnCounter = 0;
                }
            }
        }

        engine.drive();

        // Neo LEDs


    }

    @Override
    protected void leave()
    {
        super.leave();
    }
}