package StateMachine.States;

import ButterCat.DriverAI;
import Interface.Engine;
import ButterCat.Remote;
import Interface.ObjectDetection;
import StateMachine.State;
import StateMachine.StateID;

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
            engine.driveForward(this.engineTargetSpeed);
            System.out.println("forward");
        }

    }

    private void driveBackwards()
    {

        engine.driveBackward(this.engineTargetSpeed * -1);

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
        engine.stopDriving();
        System.out.println("noSpeed");
    }

    private void slowSpeed()
    {
        engineTargetSpeed = 50;
        System.out.println("slowSpeed");
    }

    private void mediumSpeed()
    {
        engineTargetSpeed = 125;
        System.out.println("mediumSpeed");
    }

    private void fastSpeed()
    {
        engineTargetSpeed = 200;
        System.out.println("fastSpeed");
    }

    @Override
    protected void logic()
    {
        super.logic();
        if(hasAnyButtonHasBeenPressed)
        {
            hasAnyButtonHasBeenPressed = false;
        }
        else
        {

        }

        sumDeltaTime += stateMachine.getDeltaTime();
        if (sumDeltaTime >= 0.003) {
            sumDeltaTime = 0;
            engine.drive();
            //System.out.println("yeeeeeeeeeee");
        }
        canGoForward = true;
    }

    @Override
    protected void leave()
    {
        super.leave();
    }
}
