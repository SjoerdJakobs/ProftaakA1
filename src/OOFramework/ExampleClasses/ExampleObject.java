package OOFramework.ExampleClasses;
import OOFramework.FrameworkProgram;
import OOFramework.StandardObject;

public class ExampleObject extends StandardObject
{
    protected ExampleObject(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);
    }

    public ExampleObject(FrameworkProgram frameworkProgram, boolean usesInput, boolean usesMain, boolean usesRenderer, boolean startsActivated) {
        super(frameworkProgram, usesInput, usesMain, usesRenderer, startsActivated);

        System.out.println("lowest");

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
