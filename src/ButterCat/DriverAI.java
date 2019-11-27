package ButterCat;

import ButterCat.Modules.Engine;

import OOFramework.FrameworkProgram;
import OOFramework.StandardObject;

import StateMachine.*;
import StateMachine.States.*;

public class DriverAI extends StandardObject
{
    private StateMachine stateMachine;
    private Engine engine;
    private Remote remote;

    public DriverAI(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);
    }

    public DriverAI(FrameworkProgram frameworkProgram,
                    boolean usesInput, boolean usesMain,
                    boolean usesRenderer, boolean startsActivated,
                    Engine engine, Remote remote, StateMachine stateMachine) {

        super(frameworkProgram, usesInput, usesMain, usesRenderer, startsActivated);
        this.stateMachine = stateMachine;
        this.engine = engine;
        this.remote = remote;

        MakeStates();
        stateMachine.SetState(StateID.SearchForStartPoint);
        //System.out.println("the state should be started");
    }

    void MakeStates() {
        stateMachine.AddState(new FollowRoute(this));
        stateMachine.AddState(new GetRoute(this));
        stateMachine.AddState(new ListenToRemote(this));
        stateMachine.AddState(new SearchForStartPoint(this));
    }

    @Override
    protected void mainLoop(double deltaTime) {
        super.mainLoop(deltaTime);
        engine.drive();
    }

    @Override
    protected void destroy() {
        super.destroy();
    }

    public StateMachine getStateMachine()
    {
        return stateMachine;
    }

    public void setStateMachine(StateMachine stateMachine)
    {
        this.stateMachine = stateMachine;
    }

    public Engine getEngine()
    {
        return engine;
    }

    public void setEngine(Engine engine)
    {
        this.engine = engine;
    }

    public Remote getRemote()
    {
        return remote;
    }

    public void setRemote(Remote remote)
    {
        this.remote = remote;
    }
}
