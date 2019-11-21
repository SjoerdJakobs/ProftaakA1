package OOFramework;

import java.util.ArrayList;
import java.util.Iterator;

public class FrameworkProgram
{
    private boolean run = false;

    private ArrayList<BaseObject> objects = new ArrayList<BaseObject>();
    private ArrayList<StandardObject> inputObjects = new ArrayList<StandardObject>();
    private ArrayList<StandardObject> mainObjects = new ArrayList<StandardObject>();
    private ArrayList<StandardObject> renderObjects = new ArrayList<StandardObject>();

    private double deltaTime = 0;

    public FrameworkProgram()
    {

    }

    public void run() {

        start();

        long lastTime = System.nanoTime();

        run = true;
        while (run) {

            /**
             * calculate deltatime
             */
            long time = System.nanoTime();
            deltaTime = ((double)(time - lastTime) / 1000_000_000);//delta time in seconds
            lastTime = time;

            //uncomment to print the deltatime in seconds
            //String s = String.format("%.5f", deltaTime);
            //System.out.println(s);

            addToLoop();

            for (StandardObject object : inputObjects) {
                object.inputLoop(deltaTime);
            }
            for (StandardObject object : mainObjects) {
                object.mainLoop(deltaTime);
            }
            for (StandardObject object : renderObjects) {
                object.renderLoop(deltaTime);
            }

            Iterator<BaseObject> it = objects.iterator();
            while (it.hasNext()) {
                BaseObject bo = it.next();
                if (bo.shouldDestruct()) {
                    bo.destroy();
                    it.remove();
                }
                else if(bo.isActive() && !bo.isActivated())
                {
                    bo.addToLists();
                    bo.setActivated(true);
                    bo.awake();
                }
                else if(!bo.isActive() && bo.isActivated())
                {
                    bo.removeFromLists();
                    bo.setActivated(false);
                    bo.sleep();
                }
            }
        }
    }

    protected void start()
    {

    }

    protected void addToLoop()
    {

    }

    protected void exitProgram()
    {
        run = false;
    }

    public boolean isRun()
    {
        return run;
    }

    public ArrayList<BaseObject> getObjects()
    {
        return objects;
    }

    public ArrayList<StandardObject> getInputObjects()
    {
        return inputObjects;
    }

    public ArrayList<StandardObject> getMainObjects()
    {
        return mainObjects;
    }

    public ArrayList<StandardObject> getRenderObjects()
    {
        return renderObjects;
    }
}

