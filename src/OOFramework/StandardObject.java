package OOFramework;

public class StandardObject extends BaseObject
{
    private boolean usesInput;
    private boolean usesMain;
    private boolean usesRenderer;

    protected StandardObject(FrameworkProgram frameworkProgram)
    {
        this(frameworkProgram, true, true, true, true);
    }

    protected StandardObject(FrameworkProgram frameworkProgram, boolean usesInput, boolean usesMain, boolean usesRenderer, boolean startsActivated)
    {
        super(frameworkProgram, startsActivated);
        this.usesInput = usesInput;
        this.usesMain = usesMain;
        this.usesRenderer = usesRenderer;
        this.AddToLists();
        System.out.println("runnable");

    }

    protected void InputLoop(double deltaTime)
    {

    }

    protected void MainLoop(double deltaTime)
    {

    }

    protected void RenderLoop(double deltaTime)
    {

    }

    @Override
    protected void RemoveFromLists()
    {
        super.RemoveFromLists();
        if (usesInput) {
            getFrameworkProgram().getInputObjects().remove(this);
        }
        if (usesMain) {
            getFrameworkProgram().getMainObjects().remove(this);
        }
        if (usesRenderer) {
            getFrameworkProgram().getRenderObjects().remove(this);
        }
    }

    @Override
    protected void AddToLists()
    {
        super.AddToLists();
        System.out.println("add2");
        if (usesInput) {
            getFrameworkProgram().getInputObjects().add(this);
        }
        if (usesMain) {
            getFrameworkProgram().getMainObjects().add(this);
        }
        if (usesRenderer) {
            getFrameworkProgram().getRenderObjects().add(this);
        }
    }

    @Override
    protected void Destroy()
    {
        super.Destroy();
    }


    //beyond this point just getters/setters

    public boolean UsesInput()
    {
        return usesInput;
    }

    public void setUsesInput(boolean usesInput)
    {
        this.usesInput = usesInput;
    }

    public boolean UsesMain()
    {
        return usesMain;
    }

    public void setUsesMain(boolean usesMain)
    {
        this.usesMain = usesMain;
    }

    public boolean UsesRenderer()
    {
        return usesRenderer;
    }

    public void setUsesRenderer(boolean usesRenderer)
    {
        this.usesRenderer = usesRenderer;
    }
}

