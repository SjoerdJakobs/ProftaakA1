package ButterCat;

import Interface.Engine;
import OOFramework.FrameworkProgram;
import Interface.ObjectDetection;
import StateMachine.StateMachine;

public class Program extends FrameworkProgram
{
    Program()
    {

    }

    private Engine          engine;
    private Remote          remote;
    private StateMachine    stateMachine;
    private ObjectDetection objectDetection;
    private DriverAI        driverAI;

    @Override
    protected void start() {
        super.start();

        engine = new Engine(15, 14);
        remote = new Remote(this,true,false,false,true);
        stateMachine = new StateMachine(this);
        objectDetection = new ObjectDetection(this);
        driverAI = new DriverAI(this,true,false,false,true,engine,remote,stateMachine,objectDetection);
    }

    @Override
    protected void addToLoop() {
        super.addToLoop();

    }

    @Override
    protected void exitProgram() {
        super.exitProgram();
    }

    public Engine getEngine()
    {
        return engine;
    }

    public Remote getRemote()
    {
        return remote;
    }

    public StateMachine getStateMachine()
    {
        return stateMachine;
    }

    public ObjectDetection getObjectDetection()
    {
        return objectDetection;
    }

    public DriverAI getDriverAI()
    {
        return driverAI;
    }
}
