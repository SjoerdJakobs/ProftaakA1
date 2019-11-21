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
    protected void Start() {
        super.Start();
        Engine engine = new Engine();
        Remote remote = new Remote(this,true,true,true,true);
        DriverAI driverAI = new DriverAI(this,true,false,false,true,engine,remote);
    }

    @Override
    protected void AddToLoop() {
        super.AddToLoop();

    }

    @Override
    protected void ExitProgram() {
        super.ExitProgram();
    }
}
