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
        System.out.println("button = " + upButton.IsPressed());
        System.out.println("button in list = " + buttons.get(0).getAddress());
        System.out.println("button in list = " + buttons.get(0).IsPressed());

        //receiver.CheckInt(UpButton);
        receiver.CheckForButtonPresses(buttons);

        System.out.println("button after method = " + upButton.getAddress());
        System.out.println("button after method = " + upButton.IsPressed());
        System.out.println("button in list after method = " + buttons.get(0).getAddress());
        System.out.println("button in list after method = " + buttons.get(0).IsPressed());
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

    double counter = 0;

    @Override
    protected void InputLoop(double deltaTime) {
        super.InputLoop(deltaTime);

        counter += deltaTime;
        if(counter >= 1)
        {
            counter = 0;
            upButton.onButtonPress.run();
        }
        receiver.CheckForButtonPresses(buttons);
    }

    @Override
    protected void Destroy() {
        super.Destroy();
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
