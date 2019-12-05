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
    private boolean muted = false;

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
        remote.getMuteButton().onButtonPress    = () ->{muteBuzzer();};
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
        this.shouldReturnControlToAi = true;
        System.out.println("on/off");
    }
    private void anyButtonHasBeenPressed()
    {
        this.hasAnyButtonHasBeenPressed = true;
    }

    public void muteBuzzer() {
        this.muted = !this.muted;
    }

    public void driveForward()
    {
        if(canGoForward) {
            this.engine.turnStop();
            this.engine.driveForward(this.engineTargetSpeed);
            System.out.println("forward");
        }

    }

    private void driveBackwards()
    {
        this.engine.turnStop();
        this.engine.driveBackward(this.engineTargetSpeed);

        System.out.println("backwards");
    }

    private void driveRight()
    {
        if(this.canGoForward) {
            this.engine.turnRight(0.6);
            System.out.println("right");
        }
    }

    private void driveLeft()
    {
        if(this.canGoForward) {
            this.engine.turnLeft(0.6);
            System.out.println("left");
        }
    }


    private void driveInCircle()
    {
        if(this.canGoForward) {
            this.engine.driveCircle(1, this.engineTargetSpeed);
            System.out.println("circle");
        }
    }

    private void driveInSquare()
    {
        this.squareActive = true;
        if(this.canGoForward) {
            this.engine.driveSquare(1, this.engineTargetSpeed);
            System.out.println("square");
        }
    }

    private void driveInTriangle()
    {
        if(this.canGoForward) {
            this.engine.driveTriangle(1, this.engineTargetSpeed);
            System.out.println("triangle");
        }
    }

    private void turn90DegreesLeft()
    {
        if(this.canGoForward) {
            System.out.println("turn90DegreesLeft");
        }
    }

    private void turn90DegreesRight()
    {
        if(this.canGoForward) {
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
        this.engine.driveStop();
        System.out.println("noSpeed");
    }

    private void slowSpeed()
    {
        this.engineTargetSpeed = 50;
        this.engine.setEngineTargetSpeed(this.engineTargetSpeed);
        System.out.println("slowSpeed");
    }

    private void mediumSpeed()
    {
        this.engineTargetSpeed = 125;
        this.engine.setEngineTargetSpeed(this.engineTargetSpeed);
        System.out.println("mediumSpeed");
    }

    private void fastSpeed()
    {
        this.engineTargetSpeed = 200;
        this.engine.setEngineTargetSpeed(this.engineTargetSpeed);
        System.out.println("fastSpeed");
    }

    @Override
    protected void logic()
    {
        super.logic();

        // 
        this.canGoForward = !this.objectDetection.objectIsTooClose(this.objectDetectionDistance);
        if(this.hasAnyButtonHasBeenPressed)
        {
            this.notificationSystem.remoteControll();
            this.hasAnyButtonHasBeenPressed = false;
        }

        // TODO: Drive a square (does not work)
        if (this.squareActive) {
            driveInSquare();
        }

        // Each 0.001s the engine will update the servo motors' speed.
        this.sumDeltaTime += this.stateMachine.getDeltaTime();
        if (this.sumDeltaTime >= 0.001) {

            this.sumDeltaTime = 0;
            this.engine.drive();
        }

        // Slow down if an object is detected and stops at the distance objectDetectionDistance
        this.canGoForward = !this.objectDetection.objectIsTooClose(this.objectDetectionDistance);
        if (this.canGoForward) {
            if (this.objectDetection.objectIsTooClose(this.objectDetectionDistance + 200)) {
                this.engine.setEngineTargetSpeed(this.engineTargetSpeed - this.engineTargetSpeed /
                        (this.engineTargetSpeed * (this.objectDetection.getDistance() / 200)));
            }
        }

        // If can go forward and wants to go forward (targetSpeed > 0) than stop immediately and make sound if not muted
        if (!this.canGoForward && this.engine.getOriginalTargetSpeed() > 0) {
            this.notificationSystem.objectDetected();
            this.engine.emergencyBrake();
            System.out.println("noticed object on " + this.objectDetection.getDistance());
            if (!this.muted) this.notificationSystem.makeSound(1000, this.stateMachine.getDeltaTime());
        }
    }

    @Override
    protected void leave()
    {
        super.leave();
        engine.emergencyBrake();
    }
}
