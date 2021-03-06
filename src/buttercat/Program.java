package buttercat;

import interfacelayer.Engine;
import interfacelayer.LineFollowChecker;
import interfacelayer.ObjectDetection;
import ooframework.FrameworkProgram;
import statemachine.StateMachine;

public class Program extends FrameworkProgram {
    Program() {

    }

    private Engine engine;
    private Remote remote;
    private StateMachine stateMachine;
    private ObjectDetection objectDetection;
    private DriverAI driverAI;
    private LineFollowChecker lineFollowChecker;
    private ControlPanel controlPanel;

    @Override
    protected void start() {
        super.start();

        engine = new Engine(15, 14);
        remote = new Remote(this, true, false, false, true);
        stateMachine = new StateMachine(this);
        objectDetection = new ObjectDetection(this);
        lineFollowChecker = new LineFollowChecker(this);
        controlPanel = new ControlPanel(this,true,false,false,false, stateMachine);
        driverAI = new DriverAI(this, true, false, false, true, engine, remote, stateMachine, objectDetection, lineFollowChecker, controlPanel);
    }

    @Override
    protected void addToLoop() {
        super.addToLoop();

    }

    @Override
    protected void exitProgram() {
        super.exitProgram();
    }

    public Engine getEngine() {
        return engine;
    }

    public Remote getRemote() {
        return remote;
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public ObjectDetection getObjectDetection() {
        return objectDetection;
    }

    public DriverAI getDriverAI() {
        return driverAI;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }
}
