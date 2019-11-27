package StateMachine.States;

import ButterCat.DriverAI;
import ButterCat.Modules.Engine;
import ButterCat.Remote;
import StateMachine.State;
import StateMachine.StateID;

public class ListenToRemote extends State
{
    DriverAI driverAI;
    Engine   engine;
    Remote   remote;

    private boolean shouldReturnControlToAi;
    private boolean hasAnyButtonHasBeenPressed;

    public ListenToRemote(DriverAI driverAI)
    {
        super(StateID.ListenToRemote);
        shouldReturnControlToAi = false;
        hasAnyButtonHasBeenPressed = false;
        this.driverAI = driverAI;
        this.engine = driverAI.getEngine();
        this.remote = driverAI.getRemote();
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
    }
    private void anyButtonHasBeenPressed()
    {
        hasAnyButtonHasBeenPressed = true;
    }

    private void driveForward()
    {

    }

    private void driveBackwards()
    {

    }

    private void driveRight()
    {

    }

    private void driveLeft()
    {

    }

    private void driveInCircle()
    {

    }

    private void driveInSquare()
    {

    }

    private void driveInTriangle()
    {

    }

    private void turn90DegreesLeft()
    {

    }

    private void turn90DegreesRight()
    {

    }

    private void turn180DegreesLeft()
    {

    }

    private void turn180DegreesRight()
    {

    }

    private void slowSpeed()
    {

    }

    private void mediumSpeed()
    {

    }

    private void fastSpeed()
    {

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
            //set speed to 0
        }
    }

    @Override
    protected void leave()
    {
        super.leave();
    }
}
