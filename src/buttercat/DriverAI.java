package buttercat;

import interfacelayer.Engine;

import interfacelayer.LineFollowChecker;
import interfacelayer.ObjectDetection;
import ooframework.FrameworkProgram;
import ooframework.StandardObject;

import statemachine.*;
import statemachine.states.*;

public class DriverAI extends StandardObject
{
    private StateMachine stateMachine;
    private Engine engine;
    private Remote remote;
    private ObjectDetection objectDetection;
    private LineFollowChecker lineFollowChecker;

    public DriverAI(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);
    }

    public DriverAI(FrameworkProgram frameworkProgram,
                    boolean usesInput, boolean usesMain,
                    boolean usesRenderer, boolean startsActivated,
                    Engine engine, Remote remote, StateMachine stateMachine, ObjectDetection objectDetection, LineFollowChecker lineFollowChecker) {

        super(frameworkProgram, usesInput, usesMain, usesRenderer, startsActivated);
        this.stateMachine = stateMachine;
        this.engine = engine;
        this.remote = remote;
        this.objectDetection = objectDetection;
        this.lineFollowChecker = lineFollowChecker;

        MakeStates();
        stateMachine.SetState(StateID.ListenToRemote);
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

    public ObjectDetection getObjectDetection()
    {
        return objectDetection;
    }

    public LineFollowChecker getLineFollowChecker() {
        return lineFollowChecker;
    }
}