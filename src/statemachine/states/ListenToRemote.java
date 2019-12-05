package statemachine.states;

import buttercat.DriverAI;
import interfacelayer.Engine;
import buttercat.Remote;
import interfacelayer.ObjectDetection;

import interfacelayer.NotificationSystem;

import statemachine.State;
import statemachine.StateID;

public class ListenToRemote extends State
{
    DriverAI driverAI;
    Engine   engine;
    Remote   remote;
    ObjectDetection objectDetection;

    private boolean shouldReturnControlToAi;
    private boolean hasAnyButtonHasBeenPressed;
    private boolean canGoForward;
    private double sumDeltaTime = 0;
    private int engineTargetSpeed = 125;
    private NotificationSystem notificationSystem;
    private int objectDetectionDistance = 80; // in millimeters

//    private boolean on;

    private boolean squareActive;


    public ListenToRemote(DriverAI driverAI)
    {
        super(StateID.ListenToRemote);
        shouldReturnControlToAi = false;
        hasAnyButtonHasBeenPressed = false;
        canGoForward = true;
        this.driverAI = driverAI;
        this.engine = driverAI.getEngine();
        this.remote = driverAI.getRemote();
        this.objectDetection = driverAI.getObjectDetection();
        this.notificationSystem = NotificationSystem.INSTANCE;
//        on = false;
    }

    @Override
    protected void enter()
    {
        super.enter();
        shouldReturnControlToAi = false;
        hasAnyButtonHasBeenPressed = true;
        setButtons();
    }

    @Override
    protected void checkForStateSwitch()
    {
        super.checkForStateSwitch();

        if (shouldReturnControlToAi)
        {
            stateMachine.SetState(StateID.GetRoute);
        }
    }

    private void setButtons()
    {
        remote.getUpButton().onButtonPress      = () ->{driveForward();};
        remote.getDownButton().onButtonPress    = () ->{driveBackwards();};
        remote.getLeftButton().onButtonPress    = () ->{driveLeft();};
        remote.getRightButton().onButtonPress   = () ->{driveRight();};
        remote.getOnButton().onButtonPress      = () ->{returnToAiControl();};
        remote.getTurn90DegreesLeftButton().onButtonPress   = () ->{turn90DegreesLeft();};
        remote.getTurn90DegreesRightButton().onButtonPress  = () ->{turn90DegreesRight();};
        remote.getTurn180DegreesLeftButton().onButtonPress  = () ->{turn180DegreesLeft();};
        remote.getTurn180DegreesRightButton().onButtonPress = () ->{turn180DegreesRight();};
        remote.getSpeedOffButton().onButtonPress    = () ->{noSpeed();};
        remote.getSpeedSlowButton().onButtonPress   = () ->{slowSpeed();};
        remote.getSpeedMediumButton().onButtonPress = () ->{mediumSpeed();};
        remote.getSpeedFastButton().onButtonPress   = () ->{fastSpeed();};
        remote.getCircleButton().onButtonPress      = () ->{driveInCircle();};
        remote.getSquareButton().onButtonPress      = () ->{driveInSquare();};
        remote.getTriangleButton().onButtonPress    = () ->{driveInTriangle();};
        remote.aButtonHasBeenPressed = () ->{anyButtonHasBeenPressed();};
    }

    private void returnToAiControl()
    {
        shouldReturnControlToAi = true;
        System.out.println("on/off");
    }
    private void anyButtonHasBeenPressed()
    {
        hasAnyButtonHasBeenPressed = true;
    }

    public void driveForward()
    {
        if(canGoForward) {
            engine.turnStop();
            engine.driveForward(this.engineTargetSpeed);
            System.out.println("forward");
        }

    }

    private void driveBackwards()
    {
        engine.turnStop();
        engine.driveBackward(this.engineTargetSpeed);

        System.out.println("backwards");
    }

    private void driveRight()
    {
        if(canGoForward) {
            engine.turnRight(0.6);
            System.out.println("right");
        }
    }

    private void driveLeft()
    {
        if(canGoForward) {
            engine.turnLeft(0.6);
            System.out.println("left");
        }
    }


    private void driveInCircle()
    {
        if(canGoForward) {
            engine.driveCircle(1, this.engineTargetSpeed);
            System.out.println("circle");
        }
    }

    private void driveInSquare()
    {
        squareActive = true;
        if(canGoForward) {
            engine.driveSquare(1, this.engineTargetSpeed);
            System.out.println("square");
        }
    }

    private void driveInTriangle()
    {
        if(canGoForward) {
            engine.driveTriangle(1, this.engineTargetSpeed);
            System.out.println("triangle");
        }
    }

    private void turn90DegreesLeft()
    {
        if(canGoForward) {
            System.out.println("turn90DegreesLeft");
        }
    }

    private void turn90DegreesRight()
    {
        if(canGoForward) {
            System.out.println("turn90DegreesRight");
        }
    }

    private void turn180DegreesLeft()
    {
        System.out.println("turn180DegreesLeft");
    }

    private void turn180DegreesRight()
    {
        System.out.println("turn180DegreesRight");
    }

    private void noSpeed()
    {
        engine.driveStop();
        System.out.println("noSpeed");
    }

    private void slowSpeed()
    {
        this.engineTargetSpeed = 50;
        engine.setEngineTargetSpeed(this.engineTargetSpeed);
        System.out.println("slowSpeed");
    }

    private void mediumSpeed()
    {
        this.engineTargetSpeed = 125;
        engine.setEngineTargetSpeed(this.engineTargetSpeed);
        System.out.println("mediumSpeed");
    }

    private void fastSpeed()
    {
        this.engineTargetSpeed = 200;
        engine.setEngineTargetSpeed(this.engineTargetSpeed);
        System.out.println("fastSpeed");
    }

    @Override
    protected void logic()
    {
        super.logic();
        canGoForward = !objectDetection.objectIsTooClose(this.objectDetectionDistance);
        if(hasAnyButtonHasBeenPressed)
        {
            notificationSystem.remoteControll();
            hasAnyButtonHasBeenPressed = false;
        }

        if (squareActive) {
            driveInSquare();
        }

        // Each 0.001s the engine will update the servo motors' speed.
        sumDeltaTime += stateMachine.getDeltaTime();
        if (sumDeltaTime >= 0.001) {

            sumDeltaTime = 0;
            engine.drive();
        }

        // Slow down if an object is detected and stops at the distance objectDetectionDistance
        canGoForward = !objectDetection.objectIsTooClose(this.objectDetectionDistance);
        if (canGoForward) {
            if (objectDetection.objectIsTooClose(this.objectDetectionDistance + 200)) {
                engine.setEngineTargetSpeed(this.engineTargetSpeed - this.engineTargetSpeed /
                        (this.engineTargetSpeed * (objectDetection.getDistance() / 200)));
            }
        }

        //
        if (!canGoForward && engine.getOriginalTargetSpeed() > 0) {
            notificationSystem.objectDetected();
            engine.emergencyBrake();
            System.out.println("noticed object on " + objectDetection.getDistance());
            notificationSystem.makeSound();
//            engine.getMotorLeft().updateInstantPulse(1500);
//            engine.getMotorRight().updateInstantPulse(1500);
        }
    }

    @Override
    protected void leave()
    {
        super.leave();
        engine.emergencyBrake();
    }
}
