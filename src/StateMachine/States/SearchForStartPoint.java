package StateMachine.States;

import ButterCat.DriverAI;
import Interface.Engine;
import ButterCat.Remote;
import StateMachine.State;
import StateMachine.StateID;

public class SearchForStartPoint extends State
{
    DriverAI driverAI;
    Engine engine;
    Remote remote;

    private boolean shouldGoToRemoteControl;

    public SearchForStartPoint(DriverAI driverAI)
    {
        super(StateID.SearchForStartPoint);
        shouldGoToRemoteControl = false;
        this.driverAI = driverAI;
        this.engine = driverAI.getEngine();
        this.remote = driverAI.getRemote();
    }

    @Override
    protected void enter()
    {
        super.enter();
        remote.aButtonHasBeenPressed = () ->{
            setShouldGoToRemoteControlToTrue();};
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
}
