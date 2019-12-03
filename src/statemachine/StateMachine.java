package statemachine;

import ooframework.FrameworkProgram;
import ooframework.StandardObject;

import java.util.HashMap;
import java.util.Map;

public class StateMachine extends StandardObject
{
    private Map<StateID, State> states = new HashMap<StateID, State>();

    public State currentState;

    private double deltaTime;
    public double getDeltaTime() {return deltaTime; }


    public StateMachine(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);
    }

    public StateMachine(FrameworkProgram frameworkProgram, boolean usesInput, boolean usesMain, boolean usesRenderer, boolean startsActivated) {
        super(frameworkProgram, usesInput, usesMain, usesRenderer, startsActivated);

        System.out.println("lowest");
    }

    @Override
    protected void mainLoop(double deltaTime) {
        super.mainLoop(deltaTime);
        this.deltaTime = deltaTime;
        if(currentState != null){
            //System.out.println("executeState" + currentState.stateID);
            currentState.checkForStateSwitch();
            currentState.logic();
        }
    }

    /**
     * set the currentState of the state machine and exit the former currentState
     * @param stateID the id of the state that will become the current state
     */
    public void SetState(StateID stateID)
    {
        if(!states.containsKey(stateID)) {
            throw new IllegalArgumentException("State unknown");
        }
        if(currentState != null) {
            currentState.leave();
        }
        currentState = states.get(stateID);
        currentState.enter();
    }

    /**
     * Adds a state to the list of possible states in state machine
     * @param state the state to be added
     */
    public void AddState(State state) {
        if(states.containsKey(state.stateID) || state.stateID == StateID.NullStateID)
        {
            throw new IllegalArgumentException("State unknown");
        }
        state.stateMachine = this;
        states.put( state.stateID, state );
    }
}