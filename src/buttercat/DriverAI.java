package buttercat;

import interfacelayer.Engine;
import interfacelayer.LineFollowChecker;
import interfacelayer.ObjectDetection;
import ooframework.FrameworkProgram;
import ooframework.StandardObject;
import statemachine.StateID;
import statemachine.StateMachine;
import statemachine.states.FollowRoute;
import statemachine.states.GetRoute;
import statemachine.states.ListenToRemote;
import statemachine.states.ListentoControlPanel;

public class DriverAI extends StandardObject
{
    private StateMachine stateMachine;
    private Engine engine;


    private buttercat.Remote remote;
    private ObjectDetection objectDetection;
    private LineFollowChecker lineFollowChecker;
    private buttercat.ControlPanel controlPanel;
    private FollowRoute followRoute;

    public DriverAI(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);
    }

    public DriverAI(FrameworkProgram frameworkProgram,
                    boolean usesInput, boolean usesMain,
                    boolean usesRenderer, boolean startsActivated,
                    Engine engine, buttercat.Remote remote, StateMachine stateMachine, ObjectDetection objectDetection, LineFollowChecker lineFollowChecker, buttercat.ControlPanel controlPanel) {

        super(frameworkProgram, usesInput, usesMain, usesRenderer, startsActivated);
        this.stateMachine = stateMachine;
        this.engine = engine;
        this.remote = remote;
        this.objectDetection = objectDetection;
        this.lineFollowChecker = lineFollowChecker;
        this.controlPanel = controlPanel;

        MakeStates();
        stateMachine.SetState(StateID.ListenToControlPanel);
    }

    void MakeStates() {
        this.followRoute = new FollowRoute(this);
        stateMachine.AddState(this.followRoute);
        stateMachine.AddState(new GetRoute(this));
        stateMachine.AddState(new ListenToRemote(this));
        stateMachine.AddState(new ListentoControlPanel(this));
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

    public buttercat.Remote getRemote()
    {
        return remote;
    }

    public void setRemote(buttercat.Remote remote)
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

    public buttercat.ControlPanel getControlPanel() {
        return controlPanel;
    }

    public void setObjectDetection(ObjectDetection objectDetection)
    {
        this.objectDetection = objectDetection;
    }

    public void setLineFollowChecker(LineFollowChecker lineFollowChecker)
    {
        this.lineFollowChecker = lineFollowChecker;
    }

    public void setControlPanel(buttercat.ControlPanel controlPanel)
    {
        this.controlPanel = controlPanel;
    }

    public FollowRoute getFollowRoute() {
        return this.followRoute;
    }
}
