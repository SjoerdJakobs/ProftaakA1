package MainPackage;

import MainPackage.Modules.Button;
import MainPackage.Modules.Receiver;
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

        System.out.println("button = " + upButton.getAddress());
        System.out.println("button = " + upButton.isPressed());
        System.out.println("button in list = " + buttons.get(0).getAddress());
        System.out.println("button in list = " + buttons.get(0).isPressed());

        //receiver.CheckInt(UpButton);
        receiver.checkForButtonPresses(buttons);

        System.out.println("button after method = " + upButton.getAddress());
        System.out.println("button after method = " + upButton.isPressed());
        System.out.println("button in list after method = " + buttons.get(0).getAddress());
        System.out.println("button in list after method = " + buttons.get(0).isPressed());
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

    double counter = 0;

    @Override
    protected void inputLoop(double deltaTime) {
        super.inputLoop(deltaTime);

        counter += deltaTime;
        if(counter >= 1)
        {
            counter = 0;
            upButton.onButtonPress.run();
        }
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
