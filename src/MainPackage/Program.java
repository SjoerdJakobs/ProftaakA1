package MainPackage;

import MainPackage.Modules.Engine;
import OOFramework.FrameworkProgram;

public class Program extends FrameworkProgram
{
    boolean state = true;

    Program()
    {

    }

    @Override
    protected void start() {
        super.start();
        Engine engine = new Engine();
        Remote remote = new Remote(this,true,true,true,true);
        DriverAI driverAI = new DriverAI(this,true,false,false,true,engine,remote);
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
