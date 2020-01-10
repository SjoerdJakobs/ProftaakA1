package buttercat;

import TEMP.TempEngine;
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



    private TempEngine tempEngine;
    private Remote remote;
    private ObjectDetection objectDetection;
    private LineFollowChecker lineFollowChecker;
    private ControlPanel controlPanel;

    public DriverAI(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);
    }

    public DriverAI(FrameworkProgram frameworkProgram,
                    boolean usesInput, boolean usesMain,
                    boolean usesRenderer, boolean startsActivated,
                    Engine engine, TempEngine tempEngine, Remote remote, StateMachine stateMachine, ObjectDetection objectDetection, LineFollowChecker lineFollowChecker, ControlPanel controlPanel) {

        super(frameworkProgram, usesInput, usesMain, usesRenderer, startsActivated);
        this.stateMachine = stateMachine;
        this.engine = engine;
        this.tempEngine = tempEngine;
        this.remote = remote;
        this.objectDetection = objectDetection;
        this.lineFollowChecker = lineFollowChecker;
        this.controlPanel = controlPanel;

        MakeStates();
        stateMachine.SetState(StateID.TempFollowRoute);
        //System.out.println("the state should be started");
    }

    void MakeStates() {
        stateMachine.AddState(new FollowRoute(this));
        stateMachine.AddState(new TempFollowRoute(this));
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

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public TempEngine getTempEngine()
    {
        return tempEngine;
    }

    public void setTempEngine(TempEngine tempEngine)
    {
        this.tempEngine = tempEngine;
    }

    public void setObjectDetection(ObjectDetection objectDetection)
    {
        this.objectDetection = objectDetection;
    }

    public void setLineFollowChecker(LineFollowChecker lineFollowChecker)
    {
        this.lineFollowChecker = lineFollowChecker;
    }

    public void setControlPanel(ControlPanel controlPanel)
    {
        this.controlPanel = controlPanel;
    }
}
