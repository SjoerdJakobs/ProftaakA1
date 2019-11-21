package MainPackage;

import MainPackage.Modules.Engine;

import OOFramework.FrameworkProgram;
import OOFramework.StandardObject;

public class DriverAI extends StandardObject
{
    private Engine engine;
    private Remote remote;

    protected DriverAI(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);
    }

    public DriverAI(FrameworkProgram frameworkProgram, boolean usesInput, boolean usesMain, boolean usesRenderer, boolean startsActivated, Engine engine, Remote remote) {
        super(frameworkProgram, usesInput, usesMain, usesRenderer, startsActivated);
        this.engine = engine;
        this.remote = remote;
        remote.getUpButton().onButtonPress = () -> {
            driveForward();
        };
        System.out.println("lowest");

    }

    private void driveForward()
    {
        System.out.println("drivin forward boii");
    }

    @Override
    protected void Start() {
        super.Start();
    }

    @Override
    protected void Awake() {
        super.Awake();
    }

    @Override
    protected void Sleep()
    {
        super.Sleep();
    }

    @Override
    protected void InputLoop(double deltaTime) {
        super.InputLoop(deltaTime);
    }

    @Override
    protected void MainLoop(double deltaTime) {
        super.MainLoop(deltaTime);
        engine.Drive();


    }

    @Override
    protected void Destroy() {
        super.Destroy();
    }
}
