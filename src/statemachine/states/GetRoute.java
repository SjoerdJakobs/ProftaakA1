package statemachine.states;

import buttercat.DriverAI;
import interfacelayer.Engine;
import buttercat.Remote;
import statemachine.State;
import statemachine.StateID;

public class GetRoute extends State
{
    DriverAI driverAI;
    Engine engine;
    Remote remote;

    private boolean shouldGoToRemoteControl;

    public GetRoute(DriverAI driverAI)
    {
        super(StateID.GetRoute);
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
