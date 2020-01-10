package statemachine.states;

import TEMP.TempEngine;
import buttercat.DriverAI;
import buttercat.Remote;
import interfacelayer.LineFollowChecker;
import interfacelayer.ObjectDetection;
import statemachine.State;
import statemachine.StateID;

public class TempFollowRoute extends State
{
    private Remote remote;
    private TempEngine engine;
    private LineFollowChecker lineFollowChecker;
    private ObjectDetection objectDetection;

    private boolean shouldGoToRemoteControl;
    private boolean hasSideLine;
    private boolean isOnOrginLines;
    private boolean currentTurning = false;
    private boolean canChangeRouteStepCounter = true;
    private boolean turnCounterHasBeenSet = false;

    private int lastDetectedPin = 0;
    private int RouteStepCounter = 100000;
    private int turnCounter;
    private int[] route = new int[]{1,1,1,1};

    private Direction turn = Direction.DEFAULT ;

    public TempFollowRoute(DriverAI driverAI)
    {
        super(StateID.TempFollowRoute);
        this.engine = driverAI.getTempEngine();
        this.lineFollowChecker =  driverAI.getLineFollowChecker();
        this.remote =  driverAI.getRemote();
        this.objectDetection = driverAI.getObjectDetection();
    }

    @Override
    protected void enter()
    {
        super.enter();
        engine.SetTargetSpeed(50, 0);
        hasSideLine = true;
        remote.aButtonHasBeenPressed = () ->{
            setShouldGoToRemoteControlToTrue();};
        System.out.println("entered");
    }

    private void setShouldGoToRemoteControlToTrue()
    {
        shouldGoToRemoteControl = true;
    }
    @Override
    protected void checkForStateSwitch()
    {
        super.checkForStateSwitch();
        if (shouldGoToRemoteControl)
        {
            stateMachine.SetState(StateID.ListenToRemote);
        }
    }

    @Override
    protected void logic()
    {
        super.logic();
        //System.out.println("logic");
        if (!currentTurning)
        {
            if (lineFollowChecker.leftNoticedLine() && lineFollowChecker.midLeftNoticedLine()
                    &&  lineFollowChecker.midRightNoticedLine() && lineFollowChecker.rightNoticedLine())
            {
                //System.out.println("All lines detected.");
                engine.SetTargetSpeed(40, 0);
                hasSideLine = true;
                currentTurning = true;
                isOnOrginLines = true;
                return;
            }
            followLine();
        }
        // When turning, continue detecting a line after which the turning will stop
        if (currentTurning) {
            setTurnDirection();
            setTurnCounter();

            engine.SetTargetSpeed(40, 0);

            switch (turn)
            {
                case LEFT:
                    engine.Steer(true, 3);
                    break;
                case RIGHT:
                    engine.Steer(false, 3);
                    break;
                case TURN_AROUND_lEFT:
                    engine.Steer(true, 3);
                    break;
                case TURN_AROUND_RIGHT:
                    engine.Steer(false, 3);
                    break;
                case DEFAULT:
                    engine.SetTargetSpeed(0,0);
                    System.out.println("wrong turn value");
                    break;
            }

            if (lineFollowChecker.rightNoticedLine()||lineFollowChecker.leftNoticedLine())
            {
                //System.out.println("r/l");
                if(!isOnOrginLines)
                {
                    System.out.println("turn counter tiem");
                    System.out.println(turnCounter);
                    turnCounter = 0;
                    isOnOrginLines = true;
                }
            }
            else {
                isOnOrginLines = false;
            }

            if(turnCounter == 0) {
                if (lineFollowChecker.midLeftNoticedLine()|| lineFollowChecker.midRightNoticedLine()) {
                    currentTurning = false;
                    canChangeRouteStepCounter = true;
                    turnCounterHasBeenSet = false;
                }
            }
        }
        if(objectDetection.objectIsTooClose(100))
        {
            engine.SetTargetSpeed(0,0);
            //System.out.println(objectDetection.getDistance());
        }
        engine.drive();
    }

    private void setTurnCounter()
    {
        if(!turnCounterHasBeenSet)
        {
            turnCounterHasBeenSet = true;
            switch (turn)
            {
                case FORWARD:
                    turnCounter = 0;
                    break;
                case LEFT:
                    turnCounter = 1;
                    break;
                case RIGHT:
                    turnCounter = 1;
                    break;
                case TURN_AROUND_lEFT:
                    turnCounter = 2;
                    break;
                case TURN_AROUND_RIGHT:
                    turnCounter = 2;
                    break;
                case DEFAULT:
                    System.out.println("wrong turn value");
                    break;
            }
        }
    }

    private void setTurnDirection()
    {
        if (RouteStepCounter < route.length - 1 && canChangeRouteStepCounter) {
            RouteStepCounter++;
            canChangeRouteStepCounter = false;
            System.out.println(RouteStepCounter);
        } else if (canChangeRouteStepCounter) {
            RouteStepCounter = 0;
            canChangeRouteStepCounter = false;
            System.out.println(RouteStepCounter);
        }
        else
        {
            return;
        }

        switch (route[RouteStepCounter])
        {
            case 0 :
                turn = Direction.FORWARD;
                break;
            case 1 :
                turn = Direction.LEFT;
                break;
            case 2 :
                turn = Direction.RIGHT;
                break;
            case 3 :
                turn = Direction.TURN_AROUND_lEFT;
                break;
            case 4 :
                turn = Direction.TURN_AROUND_RIGHT;
                break;
        }

    }

    private void followLine()
    {
        if (lineFollowChecker.midLeftNoticedLine() && lineFollowChecker.midRightNoticedLine()) {
            //System.out.println("pin 1 and 2");
            engine.Steer(true, 0);
            engine.SetTargetSpeed(500, 0);
        } else if (lineFollowChecker.leftNoticedLine() && lineFollowChecker.midLeftNoticedLine()) {
            //System.out.println("pin 0 and 1");
            engine.Steer(false, 0.8);
            engine.SetTargetSpeed(200, 0);
        } else if (lineFollowChecker.midRightNoticedLine() && lineFollowChecker.rightNoticedLine()) {
            //System.out.println("pin 2 and 3");
            engine.Steer(true, 0.8);
            engine.SetTargetSpeed(200, 0);
        } else if (lineFollowChecker.midRightNoticedLine()) {
            //System.out.println("pin 2");
            engine.Steer(true, 0.70);
            engine.SetTargetSpeed(200, 0);
            lastDetectedPin = 2;
        } else if (lineFollowChecker.midLeftNoticedLine()) {
            //System.out.println("pin 1");
            engine.Steer(false, 0.70);
            engine.SetTargetSpeed(200, 0);
            lastDetectedPin = 1;
        } else if (lineFollowChecker.rightNoticedLine()) {
            //System.out.println("pin 3");
            engine.Steer(true, 1.6);
            engine.SetTargetSpeed(200, 0);
            lastDetectedPin = 3;
        } else if (lineFollowChecker.leftNoticedLine()) {
            //System.out.println("pin 0");
            engine.Steer(false, 1.6);
            engine.SetTargetSpeed(200, 0);
            lastDetectedPin = 0;
        } else if (lastDetectedPin == 2 || lastDetectedPin == 3) {
            //System.out.println("last pin 2 or 3");
            engine.Steer(true, 1.2);
            engine.SetTargetSpeed(200, 0);
        } else if (lastDetectedPin == 1 || lastDetectedPin == 0) {
            //System.out.println("last pin 1 or 0");
            engine.Steer(false, 1.2);
            engine.SetTargetSpeed(200, 0);
        }

    }

    @Override
    protected void leave()
    {
        super.leave();
    }

}

enum Direction
{
    RIGHT,
    LEFT,
    FORWARD,
    TURN_AROUND_lEFT,
    TURN_AROUND_RIGHT,
    DEFAULT;
}