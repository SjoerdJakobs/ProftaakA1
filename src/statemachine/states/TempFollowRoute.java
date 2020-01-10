package statemachine.states;

import TEMP.TempEngine;
import buttercat.DriverAI;
import buttercat.Remote;
import interfacelayer.LineFollowChecker;
import interfacelayer.NotificationSystem;
import interfacelayer.ObjectDetection;
import statemachine.State;
import statemachine.StateID;

public class TempFollowRoute extends State
{
    private Remote              remote;
    private TempEngine          engine;
    private LineFollowChecker   lineFollowChecker;
    private ObjectDetection     objectDetection;
    private NotificationSystem  notificationSystem;

    private boolean shouldGoToRemoteControl;
    private boolean currentTurning = false;
    private boolean canChangeRouteStepCounter = true;
    private boolean turnCounterHasBeenSet = false;
    private boolean repeatRoute = true;

    private boolean leftOnOrginLine = true;
    private boolean rightOnOrginLine = true;
    private boolean leftLinePassed1 = false;
    private boolean rightLinePassed1 = false;
    private boolean leftLinePassed2 = false;
    private boolean rightLinePassed2 = false;

    private int lastDetectedPin = 0;
    private int RouteStepCounter = 100000;
    private int turnCounter;
    private int[] route = new int[]{2,1,0,1,0,1,1,2,2,1,1,1};
    //private int[] route = new int[]{2,1,2,1};
    //private int[] route = new int[]{2,2,2,2};
    //private int[] route = new int[]{1,1,1,1};
    //private int[] route = new int[]{0,0,0,0};

    private int driveSpeed = 80;
    private int currentDriveSpeed;
    private int brakeDistance = 250;
    private int stopThreshold = 70;

    private boolean delayHasBeenSet = false;
    private boolean inDelay = false;
    private double turnDelay = 1;
    private double turnDelayCounter;

    boolean isStoppedAfterFullStop = false;

    private Direction turn = Direction.DEFAULT ;

    public TempFollowRoute(DriverAI driverAI)
    {
        super(StateID.TempFollowRoute);
        this.engine =               driverAI.getTempEngine();
        this.lineFollowChecker =    driverAI.getLineFollowChecker();
        this.remote =               driverAI.getRemote();
        this.objectDetection =      driverAI.getObjectDetection();
        this.notificationSystem =   NotificationSystem.INSTANCE;
    }

    @Override
    protected void enter()
    {
        super.enter();
        currentDriveSpeed = driveSpeed;
        engine.SetTargetSpeed(driveSpeed, 0);
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
                //engine.SetTargetSpeed(0, 0);
                setDelay();
                currentTurning = true;
                inDelay = true;
            }
            followLine();
        }
        else if(inDelay)
        {
            if(turnDelayCounter <= turnDelay)
            {
                turnDelayCounter+=stateMachine.getDeltaTime();
                inDelay = false;
                turnDelayCounter = 0;
                System.out.println("resetDelay");
            }
            followLine();
        }else if (currentTurning) {
            setTurnDirection();
            setTurnCounter();

            switch (turn)
            {
                case FORWARD:
                    leftLinePassed2 = true;
                    rightLinePassed2 = true;
                    break;
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

            if(turn != Direction.FORWARD)
            {
                currentDriveSpeed = driveSpeed / 2;
                engine.SetTargetSpeed(driveSpeed / 2, 0);
            }

            if(lineFollowChecker.leftNoticedLine())
            {
                if(!leftOnOrginLine)
                {
                    leftOnOrginLine = true;
                    leftLinePassed1 = true;
                    //System.out.println("done turning left");
                    //turnCounter = 0;
                }
            }
            else {
                if (leftLinePassed1) {
                    leftLinePassed2 = true;
                }
                leftOnOrginLine = false;
            }

            if(lineFollowChecker.rightNoticedLine())
            {
                if(!rightOnOrginLine)
                {
                    rightOnOrginLine = true;
                    rightLinePassed1 = true;
                    //System.out.println("done turning right");
                    //turnCounter = 0;
                }
            }
            else
            {
                if(rightLinePassed1)
                {
                    rightLinePassed2 = true;
                }
                rightOnOrginLine = false;
            }

            if(turn == Direction.FORWARD || (turn == Direction.RIGHT && leftLinePassed2) || (rightLinePassed2 && turn == Direction.LEFT)) {
                //if (lineFollowChecker.midLeftNoticedLine()|| lineFollowChecker.midRightNoticedLine()) {
                currentTurning = false;
                canChangeRouteStepCounter = true;
                turnCounterHasBeenSet = false;
                leftOnOrginLine = true;
                rightOnOrginLine = true;
                leftLinePassed1 = false;
                rightLinePassed1 = false;
                leftLinePassed2 = false;
                rightLinePassed2 = false;
                delayHasBeenSet = false;
                //}
            }
        }
        if(objectDetection.objectIsTooClose(brakeDistance))
        {
            if(objectDetection.objectIsTooClose(stopThreshold))
            {
                engine.SetTargetSpeed(0,0);
                isStoppedAfterFullStop = true;
            }
            else {
                double speedDuringBraking = Math.pow((double) objectDetection.getDistance() / brakeDistance,3);
                currentDriveSpeed = (int)(driveSpeed * speedDuringBraking);
                engine.SetTargetSpeed((int)(driveSpeed*speedDuringBraking), 0);
                if(currentDriveSpeed < driveSpeed/2 && inDelay)
                {
                    turnDelayCounter += stateMachine.getDeltaTime()/2;
                }
                //System.out.println(driveSpeed);
                //System.out.println(speedDuringBraking);
            }
        }
        if(isStoppedAfterFullStop)
        {
            //engine.SetTargetSpeed(0,0);
        }
        engine.drive();
    }

    private void setDelay()
    {
        setDelay(0.3d);
    }

    private void setDelay(double baseDelay)
    {
        turnDelay = (double)driveSpeed / currentDriveSpeed + baseDelay;
        System.out.println(turnDelay);
        delayHasBeenSet = true;
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
        } else if (canChangeRouteStepCounter && repeatRoute) {
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
        notificationSystem.getNeoLed1().turnOff();
        notificationSystem.getNeoLed2().turnOff();
        notificationSystem.getNeoLed3().turnOff();
        notificationSystem.getNeoLed4().turnOff();
        notificationSystem.getNeoLed5().turnOff();
        notificationSystem.getNeoLed6().turnOff();
        if (lineFollowChecker.midLeftNoticedLine() && lineFollowChecker.midRightNoticedLine()) {
            //System.out.println("pin 1 and 2");
            notificationSystem.getNeoLed1().turnOn();
            notificationSystem.getNeoLed3().turnOn();
            engine.Steer(true, 0);
            engine.SetTargetSpeed(driveSpeed, 0);
        } else if (lineFollowChecker.leftNoticedLine() && lineFollowChecker.midLeftNoticedLine()) {
            //System.out.println("pin 0 and 1");
            notificationSystem.getNeoLed1().turnOn();
            notificationSystem.getNeoLed4().turnOn();
            engine.Steer(false, 0.9);
            engine.SetTargetSpeed((int)(driveSpeed*0.5), 0);
        } else if (lineFollowChecker.midRightNoticedLine() && lineFollowChecker.rightNoticedLine()) {
            //System.out.println("pin 2 and 3");
            notificationSystem.getNeoLed3().turnOn();
            notificationSystem.getNeoLed6().turnOn();
            engine.Steer(true, 0.9);
            engine.SetTargetSpeed((int)(driveSpeed*0.5), 0);
        } else if (lineFollowChecker.midRightNoticedLine()) {
            //System.out.println("pin 2");
            notificationSystem.getNeoLed1().turnOn();
            engine.Steer(true, 0.30);
            engine.SetTargetSpeed((int)(driveSpeed*0.9), 0);
            lastDetectedPin = 2;
        } else if (lineFollowChecker.midLeftNoticedLine()) {
            //System.out.println("pin 1");
            notificationSystem.getNeoLed3().turnOn();
            engine.Steer(false, 0.30);
            engine.SetTargetSpeed((int)(driveSpeed*0.9), 0);
            lastDetectedPin = 1;
        } else if (lineFollowChecker.rightNoticedLine()) {
            //System.out.println("pin 3");
            notificationSystem.getNeoLed6().turnOn();
            engine.Steer(true, 1.6);
            engine.SetTargetSpeed((int)(driveSpeed*0.4), 0);
            lastDetectedPin = 3;
        } else if (lineFollowChecker.leftNoticedLine()) {
            //System.out.println("pin 0");
            notificationSystem.getNeoLed4().turnOn();
            engine.Steer(false, 1.6);
            engine.SetTargetSpeed((int)(driveSpeed*0.4), 0);
            lastDetectedPin = 0;
        } else if (lastDetectedPin == 2 || lastDetectedPin == 3) {
            //System.out.println("last pin 2 or 3");
            notificationSystem.getNeoLed1().turnOn();
            notificationSystem.getNeoLed6().turnOn();
            engine.Steer(true, 0.80);
            engine.SetTargetSpeed((int)(driveSpeed*0.75), 0);
        } else if (lastDetectedPin == 1 || lastDetectedPin == 0) {
            //System.out.println("last pin 1 or 0");
            notificationSystem.getNeoLed3().turnOn();
            notificationSystem.getNeoLed4().turnOn();
            engine.Steer(false, 0.80);
            engine.SetTargetSpeed((int)(driveSpeed*0.75), 0);
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