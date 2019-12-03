package statemachine;

public class State
{
    public StateID      stateID;
    public StateMachine stateMachine;

    protected State(StateID stateID)
    {
        this.stateID = stateID;
    }

    /**
     * this method gets called at the start when the state becomes the currentState in the statemachine
     */
    protected void enter()
    {

    }

    /**
     * this method gets called at the end when the state gets removed as the currentState in the statemachine
     */
    protected void leave()
    {

    }

    /**
     * this method gets called first every program loop while the state is the currentState in the statemachine
     */
    protected void checkForStateSwitch()
    {

    }

    /**
     * this method gets called second every program loop while the state is the currentState in the statemachine
     */
    protected void logic()
    {

    }
}
