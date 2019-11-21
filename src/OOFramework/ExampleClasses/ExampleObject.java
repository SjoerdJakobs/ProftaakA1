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
    }

    @Override
    protected void RenderLoop(double deltaTime) {
        super.RenderLoop(deltaTime);
    }

    @Override
    protected void Destroy() {
        super.Destroy();
    }
}
