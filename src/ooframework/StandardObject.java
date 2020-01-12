package ooframework;

public abstract class StandardObject extends BaseObject
{
    private boolean usesInput;
    private boolean usesMain;
    private boolean usesRenderer;

    protected StandardObject(FrameworkProgram frameworkProgram)
    {
        this(frameworkProgram, false, true, false, true);
    }

    protected StandardObject(FrameworkProgram frameworkProgram, boolean usesInput, boolean usesMain, boolean usesRenderer, boolean startsActivated)
    {
        super(frameworkProgram, startsActivated);
        this.usesInput = usesInput;
        this.usesMain = usesMain;
        this.usesRenderer = usesRenderer;
        this.addToLists();
        //System.out.println("runnable");

    }

    protected void inputLoop(double deltaTime)
    {

    }

    protected void mainLoop(double deltaTime)
    {

    }

    protected void renderLoop(double deltaTime)
    {

    }

    @Override
    protected void removeFromLists()
    {
        super.removeFromLists();
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
    protected void addToLists()
    {
        super.addToLists();
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
    protected void destroy()
    {
        super.destroy();
    }


    //beyond this point just getters/setters

    public boolean usesInput()
    {
        return usesInput;
    }

    public void setUsesInput(boolean usesInput)
    {
        this.usesInput = usesInput;
    }

    public boolean usesMain()
    {
        return usesMain;
    }

    public void setUsesMain(boolean usesMain)
    {
        this.usesMain = usesMain;
    }

    public boolean usesRenderer()
    {
        return usesRenderer;
    }

    public void setUsesRenderer(boolean usesRenderer)
    {
        this.usesRenderer = usesRenderer;
    }
}

