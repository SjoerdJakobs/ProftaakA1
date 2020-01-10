package statemachine.states;

import buttercat.ControlPanel;
import buttercat.DriverAI;
import interfacelayer.Engine;
import buttercat.Remote;
import statemachine.State;
import statemachine.StateID;

public class SearchForStartPoint extends State
{
    DriverAI driverAI;
    Engine engine;
    Remote remote;
    ControlPanel controlPanel;

    private boolean shouldGoToRemoteControl;
    private boolean shouldGotoControlPanelControl;

    public SearchForStartPoint(DriverAI driverAI)
    {
        super(StateID.SearchForStartPoint);
        shouldGoToRemoteControl = false;
        shouldGotoControlPanelControl = false;
        this.driverAI = driverAI;
        this.engine = driverAI.getEngine();
        this.remote = driverAI.getRemote();
        this.controlPanel = driverAI.getControlPanel();
    }

    @Override
    protected void enter()
    {
        super.enter();
        remote.aButtonHasBeenPressed = () ->{
            setShouldGoToRemoteControlToTrue();};
        controlPanel.aButtonHasBeenPressed = () ->{
            setShouldGoToControlPanelControlToTrue();};
    }

    private void setShouldGoToRemoteControlToTrue()
    {
        shouldGoToRemoteControl = true;
    }

    private void setShouldGoToControlPanelControlToTrue()
    {
        System.out.println("setshouldtocontrol");
        shouldGotoControlPanelControl = true;
    }

    @Override
    protected void checkForStateSwitch()
    {
        super.checkForStateSwitch();
        if (shouldGoToRemoteControl)
        {
            stateMachine.SetState(StateID.ListenToRemote);
        }
        else if (shouldGotoControlPanelControl)
        {
            stateMachine.SetState(StateID.ListenToControlPanel);
        }
    }
}
