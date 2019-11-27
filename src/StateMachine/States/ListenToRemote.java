package StateMachine.States;

import ButterCat.DriverAI;
import ButterCat.Modules.Engine;
import ButterCat.Remote;
import StateMachine.State;
import StateMachine.StateID;

public class ListenToRemote extends State
{
    DriverAI driverAI;
    Engine engine;
    Remote remote;

    private boolean shouldReturnControlToAi;

    public ListenToRemote(DriverAI driverAI)
    {
        super(StateID.ListenToRemote);
        shouldReturnControlToAi = false;
        this.driverAI = driverAI;
        this.engine = driverAI.getEngine();
        this.remote = driverAI.getRemote();
    }

    @Override
    protected void enter()
    {
        super.enter();
        shouldReturnControlToAi = false;
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

    }

    private void returnToAiControl()
    {
        shouldReturnControlToAi = true;
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

    private void FastSpeed()
    {

    }

    @Override
    protected void logic()
    {
        super.logic();
    }

    @Override
    protected void leave()
    {
        super.leave();
    }
}
