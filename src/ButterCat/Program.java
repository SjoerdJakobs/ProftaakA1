package ButterCat;

import ButterCat.Modules.Engine;
import OOFramework.FrameworkProgram;
import Interface.ObjectDetection;
import StateMachine.StateMachine;

public class Program extends FrameworkProgram
{
    Program()
    {

    }

    @Override
    protected void start() {
        super.start();
        Engine engine = new Engine();
        Remote remote = new Remote(this,true,false,false,true);
        StateMachine stateMachine = new StateMachine(this);
        ObjectDetection objectDetection = new ObjectDetection(this);
        DriverAI driverAI = new DriverAI(this,true,false,false,true,engine,remote,stateMachine);
    }

    @Override
    protected void addToLoop() {
        super.addToLoop();

    }

    @Override
    protected void exitProgram() {
        super.exitProgram();
    }
}
