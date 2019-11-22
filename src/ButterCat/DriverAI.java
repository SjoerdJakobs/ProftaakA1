package ButterCat;

import ButterCat.Modules.Engine;

import OOFramework.FrameworkProgram;
import OOFramework.StandardObject;

public class DriverAI extends StandardObject
{
    private Engine engine;
    private Remote remote;

    public DriverAI(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);
    }

    public DriverAI(FrameworkProgram frameworkProgram, boolean usesInput, boolean usesMain, boolean usesRenderer, boolean startsActivated, Engine engine, Remote remote) {
        super(frameworkProgram, usesInput, usesMain, usesRenderer, startsActivated);
        this.engine = engine;
        this.remote = remote;
        System.out.println("lowest");

    }

    private void driveForward()
    {

    }

    @Override
    protected void start() {
        super.start();
    }

    @Override
    protected void awake() {
        super.awake();
    }

    @Override
    protected void sleep()
    {
        super.sleep();
    }

    @Override
    protected void inputLoop(double deltaTime) {
        super.inputLoop(deltaTime);
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
}
