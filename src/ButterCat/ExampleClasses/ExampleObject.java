package ButterCat.ExampleClasses;
import OOFramework.FrameworkProgram;
import OOFramework.StandardObject;

public class ExampleObject extends StandardObject
{
    public ExampleObject(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);
    }

    public ExampleObject(FrameworkProgram frameworkProgram, boolean usesInput, boolean usesMain, boolean usesRenderer, boolean startsActivated) {
        super(frameworkProgram, usesInput, usesMain, usesRenderer, startsActivated);

        System.out.println("lowest");

    }


    /**
     * this is called when the object gets created
     * after the constructor but before the awake and the loops
     */
    @Override
    protected void start() {
        super.start();
    }

    /**
     * this is called when the object gets set to active
     * if the object is created active awake will run right after the start method but before the loops
     * if the object is created inactive awake will run when the object is set to active which happens right after the program loops
     * awake will run every time the object goes from inactive to active
     */
    @Override
    protected void awake() {
        super.awake();
    }

    /**
     * this is called when the object gets set to inactive
     * sleep will run every time the object goes from active to inactive which happens right after the program loops
     */
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
    }

    @Override
    protected void renderLoop(double deltaTime) {
        super.renderLoop(deltaTime);
    }

    @Override
    protected void destroy() {
        super.destroy();
    }
}
