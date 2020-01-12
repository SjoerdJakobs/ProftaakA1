package statemachine.states;

import buttercat.ControlPanel;
import buttercat.DriverAI;
import buttercat.Remote;
import interfacelayer.Engine;
import statemachine.State;
import statemachine.StateID;

public class GetRoute extends State {
    DriverAI driverAI;
    Engine engine;
    Remote remote;
    ControlPanel controlPanel;

    private boolean shouldGoToRemoteControl;
    private boolean shouldGoToControlPanel;

    public GetRoute(DriverAI driverAI) {
        super(StateID.GetRoute);
        shouldGoToRemoteControl = false;
        shouldGoToControlPanel = false;
        this.driverAI = driverAI;
        this.engine = driverAI.getEngine();
        this.remote = driverAI.getRemote();
        this.controlPanel = driverAI.getControlPanel();
    }

    @Override
    protected void enter() {
        super.enter();
        remote.aButtonHasBeenPressed = () -> {
            setShouldGoToRemoteControlToTrue();
        };
        controlPanel.aButtonHasBeenPressed = () -> {
            setShouldGoToControlPanelControlTrue();
        };
    }

    private void setShouldGoToRemoteControlToTrue() {
        shouldGoToRemoteControl = true;
    }

    private void setShouldGoToControlPanelControlTrue(){
        shouldGoToControlPanel = true;
    }

    @Override
    protected void checkForStateSwitch() {
        super.checkForStateSwitch();
        if (shouldGoToRemoteControl) {
            stateMachine.SetState(StateID.ListenToRemote);
        } else if (shouldGoToControlPanel) {
            stateMachine.SetState(StateID.ListenToControlPanel);
        }
    }
}
