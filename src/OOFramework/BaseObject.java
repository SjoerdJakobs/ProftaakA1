package OOFramework;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class BaseObject
{
    /**
     * should this object be active
     */
    private final AtomicBoolean active = new AtomicBoolean(false);

    /**
     * is this object actually active
     */
    private final AtomicBoolean activated = new AtomicBoolean(false);

    /**
     * should this object be destroyed
     */
    private final AtomicBoolean shouldDestruct = new AtomicBoolean(false);

    /**
     * this is the program that contains this object
     */
    private final AtomicReference<FrameworkProgram> frameworkProgram = new AtomicReference<FrameworkProgram>();

    protected BaseObject(FrameworkProgram frameworkProgram, boolean startsActivated)
    {
        System.out.println("base");
        this.frameworkProgram.set(frameworkProgram);
        this.setShouldDestruct(false);
        this.start();

        if (startsActivated) {
            this.setActive(true);
            this.setActivated(true);
            this.awake();
        } else {
            this.setActive(false);
            this.setActivated(false);
        }

        frameworkProgram.getObjects().add(this);
    }

    /**
     * this is called when the object gets created
     * after the constructor but before the awake and the loops
     */
    protected void start()
    {

    }

    /**
     * this is called when the object gets set to active
     * if the object is created active awake will run right after the start method but before the loops
     * if the object is created inactive awake will run when the object is set to active which happens right after the program loops
     * awake will run every time the object goes from inactive to active
     */
    protected void awake()
    {

    }

    /**
     * this is called when the object gets set to inactive
     * sleep will run every time the object goes from active to inactive which happens right after the program loops
     */
    protected void sleep()
    {

    }

    protected void removeFromLists()
    {

    }

    protected void addToLists()
    {

    }

    protected void destroy()
    {
        this.removeFromLists();
    }


    //beyond this point just getters/setters

    public boolean isActive()
    {
        return active.get();
    }

    public void setActive(boolean m_active)
    {
        this.active.set(m_active);
    }

    public boolean isActivated()
    {
        return activated.get();
    }

    public void setActivated(boolean activated)
    {
        this.activated.set(activated);
    }

    public boolean shouldDestruct()
    {
        return shouldDestruct.get();
    }

    public void setShouldDestruct(boolean shouldDestruct)
    {
        this.shouldDestruct.set(shouldDestruct);
    }

    public FrameworkProgram getFrameworkProgram()
    {
        return frameworkProgram.get();
    }

    public void setFrameworkProgram(FrameworkProgram frameworkProgram)
    {
        this.frameworkProgram.set(frameworkProgram);
    }
}
