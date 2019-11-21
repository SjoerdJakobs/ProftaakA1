package ButterCat;

import ButterCat.Modules.Button;
import ButterCat.Modules.Receiver;
import OOFramework.FrameworkProgram;
import OOFramework.StandardObject;

import java.util.ArrayList;

public class Remote extends StandardObject
{
    private Receiver receiver;
    private ArrayList<Button> buttons;

    private Button upButton;

    protected Remote(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);
    }

    public Remote(FrameworkProgram frameworkProgram, boolean usesInput, boolean usesMain, boolean usesRenderer, boolean startsActivated) {
        super(frameworkProgram, usesInput, usesMain, usesRenderer, startsActivated);
        receiver = new Receiver();
        buttons = new ArrayList<Button>();
        upButton = new Button(111111111111L);
        buttons.add(upButton);


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

        receiver.checkForButtonPresses(buttons);
    }

    @Override
    protected void destroy() {
        super.destroy();
    }

    public Button getUpButton()
    {
        return upButton;
    }

    public void setUpButton(Button upButton)
    {
        this.upButton = upButton;
    }
}
