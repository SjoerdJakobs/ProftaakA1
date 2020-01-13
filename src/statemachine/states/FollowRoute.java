package statemachine.states;

import buttercat.DriverAI;
import buttercat.Remote;
import hardwarelayer.sensors.bluetoothreceiver.BluetoothReceiver;
import interfacelayer.Engine;
import interfacelayer.LineFollowChecker;
import interfacelayer.NotificationSystem;
import interfacelayer.ObjectDetection;
import statemachine.State;
import statemachine.StateID;

import java.awt.*;
import java.util.Arrays;

public class FollowRoute extends State
{
    private Remote              remote;
    private Engine              engine;
    private LineFollowChecker   lineFollowChecker;
    private ObjectDetection     objectDetection;
    private NotificationSystem  notificationSystem;
    private BluetoothReceiver bluetoothReceiver;

    private boolean shouldGoToRemoteControl;
    private boolean currentTurning = false;
    private boolean canChangeRouteStepCounter = true;
    private boolean turnCounterHasBeenSet = false;
    private boolean repeatRoute = false;
    private boolean gotRoute = false;

    private boolean leftOnOrginLine = true;
    private boolean rightOnOrginLine = true;
    private boolean leftLinePassed1 = false;
    private boolean rightLinePassed1 = false;
    private boolean leftLinePassed2 = false;
    private boolean rightLinePassed2 = false;

    private int lastDetectedPin = 0;
    private int RouteStepCounter = 100000;
    private int turnCounter;
    //initialize with empty array
    private int[] route = new int[]{};
    private int[] tempRoute;
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

    public FollowRoute(DriverAI driverAI)
    {
        super(StateID.FollowRoute);
        this.engine =               driverAI.getEngine();
        this.lineFollowChecker =    driverAI.getLineFollowChecker();
        this.remote =               driverAI.getRemote();
        this.objectDetection =      driverAI.getObjectDetection();
        this.notificationSystem =   NotificationSystem.INSTANCE;
        this.bluetoothReceiver =    driverAI.getControlPanel().getBluetoothReceiver();
    }

    public void setStopped(boolean shouldBeStopped)
    {
        this.isStoppedAfterFullStop = shouldBeStopped;
    }

    @Override
    protected void enter()
    {
        super.enter();
        notificationSystem.setColor(Color.green);
        currentDriveSpeed = driveSpeed;
        engine.sJSetTargetSpeed(driveSpeed, 0);
        shouldGoToRemoteControl = false;
        remote.aButtonHasBeenPressed = this::setShouldGoToRemoteControlToTrue;
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
        //check if a route has been entered
        if (!gotRoute)
        if (bluetoothReceiver.isRouteEntered()) {
            //if so, get the route
            tempRoute = bluetoothReceiver.getRouteAsArray();
            //check if the route is different from the route we already have
            if (!Arrays.equals(tempRoute,route)){
                this.route = tempRoute;
                gotRoute = true;
            }
        }
        if (!currentTurning)
        {
            if (lineFollowChecker.leftNoticedLine() && lineFollowChecker.midLeftNoticedLine()
                    &&  lineFollowChecker.midRightNoticedLine() && lineFollowChecker.rightNoticedLine())
            {
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
                    engine.sJSteer(true, 3);
                    break;
                case RIGHT:
                    engine.sJSteer(false, 3);
                    break;
                case TURN_AROUND_lEFT:
                    engine.sJSteer(true, 3);
                    break;
                case TURN_AROUND_RIGHT:
                    engine.sJSteer(false, 3);
                    break;
                case DEFAULT:
                    engine.sJSetTargetSpeed(0,0);
                    System.out.println("wrong turn value");
                    break;
            }

            if(turn != Direction.FORWARD)
            {
                currentDriveSpeed = driveSpeed / 2;
                engine.sJSetTargetSpeed(driveSpeed / 2, 0);
            }

            if(lineFollowChecker.leftNoticedLine())
            {
                if(!leftOnOrginLine)
                {
                    leftOnOrginLine = true;
                    leftLinePassed1 = true;
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

            }
        }
        if(objectDetection.objectIsTooClose(brakeDistance))
        {
            if(objectDetection.objectIsTooClose(stopThreshold))
            {
                engine.sJSetTargetSpeed(0,0);
                isStoppedAfterFullStop = true;
            }
            else {
                double speedDuringBraking = Math.pow((double) objectDetection.getDistance() / brakeDistance,3);
                currentDriveSpeed = (int)(driveSpeed * speedDuringBraking);
                engine.sJSetTargetSpeed((int)(driveSpeed*speedDuringBraking), 0);
                if(currentDriveSpeed < driveSpeed/2 && inDelay)
                {
                    turnDelayCounter += stateMachine.getDeltaTime()/2;
                }
            }
        }
        if(isStoppedAfterFullStop)
        {
            engine.sJSetTargetSpeed(0,0);
        }
        engine.sJDrive();
    }

    private void setDelay()
    {
        setDelay(0.3d);
    }

    private void setDelay(double baseDelay)
    {
        turnDelay = (double)driveSpeed / currentDriveSpeed + baseDelay;
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
        } else if (canChangeRouteStepCounter && repeatRoute) {
            RouteStepCounter = 0;
            canChangeRouteStepCounter = false;
        }
        else
        {
            isStoppedAfterFullStop = true;
            RouteStepCounter = 0;
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
            case 5 :
                turn = Direction.STOP;
                isStoppedAfterFullStop = true;
                canChangeRouteStepCounter = true;
                setTurnDirection();
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
            notificationSystem.getNeoLed1().turnOn();
            notificationSystem.getNeoLed3().turnOn();
            engine.sJSteer(true, 0);
            engine.sJSetTargetSpeed(driveSpeed, 0);
        } else if (lineFollowChecker.leftNoticedLine() && lineFollowChecker.midLeftNoticedLine()) {
            notificationSystem.getNeoLed1().turnOn();
            notificationSystem.getNeoLed4().turnOn();
            engine.sJSteer(false, 0.9);
            engine.sJSetTargetSpeed((int)(driveSpeed*0.5), 0);
        } else if (lineFollowChecker.midRightNoticedLine() && lineFollowChecker.rightNoticedLine()) {
            notificationSystem.getNeoLed3().turnOn();
            notificationSystem.getNeoLed6().turnOn();
            engine.sJSteer(true, 0.9);
            engine.sJSetTargetSpeed((int)(driveSpeed*0.5), 0);
        } else if (lineFollowChecker.midRightNoticedLine()) {
            notificationSystem.getNeoLed1().turnOn();
            engine.sJSteer(true, 0.30);
            engine.sJSetTargetSpeed((int)(driveSpeed*0.9), 0);
            lastDetectedPin = 2;
        } else if (lineFollowChecker.midLeftNoticedLine()) {
            notificationSystem.getNeoLed3().turnOn();
            engine.sJSteer(false, 0.30);
            engine.sJSetTargetSpeed((int)(driveSpeed*0.9), 0);
            lastDetectedPin = 1;
        } else if (lineFollowChecker.rightNoticedLine()) {
            notificationSystem.getNeoLed6().turnOn();
            engine.sJSteer(true, 1.6);
            engine.sJSetTargetSpeed((int)(driveSpeed*0.4), 0);
            lastDetectedPin = 3;
        } else if (lineFollowChecker.leftNoticedLine()) {
            notificationSystem.getNeoLed4().turnOn();
            engine.sJSteer(false, 1.6);
            engine.sJSetTargetSpeed((int)(driveSpeed*0.4), 0);
            lastDetectedPin = 0;
        } else if (lastDetectedPin == 2 || lastDetectedPin == 3) {
            notificationSystem.getNeoLed1().turnOn();
            notificationSystem.getNeoLed6().turnOn();
            engine.sJSteer(true, 0.80);
            engine.sJSetTargetSpeed((int)(driveSpeed*0.75), 0);
        } else if (lastDetectedPin == 1 || lastDetectedPin == 0) {
            notificationSystem.getNeoLed3().turnOn();
            notificationSystem.getNeoLed4().turnOn();
            engine.sJSteer(false, 0.80);
            engine.sJSetTargetSpeed((int)(driveSpeed*0.75), 0);
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
    STOP,
    TURN_AROUND_lEFT,
    TURN_AROUND_RIGHT,
    DEFAULT;
}