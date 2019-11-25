package StateMachine;

import OOFramework.FrameworkProgram;
import OOFramework.StandardObject;

import java.util.HashMap;
import java.util.Map;

public class StateMachine extends StandardObject
{
    private Map<StateID, State> states = new HashMap<StateID, State>();

    /** een verwijzing naar de huidige staat waarin we verkeren */
    private State currentState;


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
        if(currentState != null){
            currentState.CheckForStateSwitch();
            currentState.logic();
        }
    }

    public void SetState(StateID stateID) {

        /**
         *  if we dont know the state, we stop the function
         */
        if(!states.containsKey(stateID)) {
            //throw exception
            return;
        }
        /**
         * if there is already a state running, run its leave method before a new state will start
         */
        if(currentState != null) {
            currentState.leave();
        }
        /**
         * set the current state to the new state
         */
        currentState = states.get(stateID);

        /**
         * start the new state
         */
        currentState.enter();
    }


    public void AddState(StateID stateID, State state) {
        if(states.containsKey(stateID))
        {
            //throw exception
            return;
        }
        states.put( stateID, state );
    }
}