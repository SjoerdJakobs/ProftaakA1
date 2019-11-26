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

    // all buttons with their addresses that are used on the remote
    public static final long CHANNEL_PLUS = 000010010000L;
    public static final long CHANNEL_MIN = 100010010000L;
    public static final long VOLUME_UP = 010010010000L;
    public static final long VOLUME_DOWN = 110010010000L;
    public static final long ON = 101010010000L;
    public static final long MUTE = 001010010000L;
    public static final long ROUND = 101010000000L;
    public static final long SQUARE = 101100111000L;
    public static final long TRIANGLE = 011100111000L;
    public static final long TURN_RIGHT = 101001010000L;
    public static final long TURN_LEFT = 000100111000L;
    public static final long REVERSE_LEFT = 001100010000L;
    public static final long REVERSE_RIGHT = 101001010000L;
    public static final long SPEED_ONE = 000000010000L;
    public static final long SPEED_TWO = 100000010000L;
    public static final long SPEED_TRHREE = 010000010000L;

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
