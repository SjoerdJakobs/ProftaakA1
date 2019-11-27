package ButterCat;

import ButterCat.Modules.Button;
import ButterCat.Modules.Callback;
import Hardware.Sensors.InfraRedReceiver;
import OOFramework.FrameworkProgram;
import OOFramework.StandardObject;

import java.util.ArrayList;

public class Remote extends StandardObject
{
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

    private InfraRedReceiver infraRedReceiver;
    private ArrayList<Button> buttons;

    private Button upButton = new Button(CHANNEL_PLUS);
    private Button downButton = new Button(CHANNEL_MIN);
    private Button leftButton = new Button(VOLUME_DOWN);
    private Button rightButton = new Button(VOLUME_UP);
    private Button onButton = new Button(ON);
    private Button muteButton = new Button(MUTE);
    private Button circleButton = new Button(ROUND);
    private Button squareButton = new Button(SQUARE);
    private Button triangleButton = new Button(TRIANGLE);
    private Button turn90DegreesLeftButton = new Button(TURN_LEFT);
    private Button turn90DegreesRightButton = new Button(TURN_RIGHT);
    private Button turn180DegreesLeftButton = new Button(REVERSE_LEFT);
    private Button turn180DegreesRightButton = new Button(REVERSE_RIGHT);
    private Button speedSlowButton = new Button(SPEED_ONE);
    private Button speedMediumButton = new Button(SPEED_TWO);
    private Button speedFastButton = new Button(SPEED_TRHREE);

    public Callback aButtonHasBeenPressed;

    protected Remote(FrameworkProgram frameworkProgram)
    {
        super(frameworkProgram);
    }

    public Remote(FrameworkProgram frameworkProgram, boolean usesInput, boolean usesMain, boolean usesRenderer, boolean startsActivated)
    {
        super(frameworkProgram, usesInput, usesMain, usesRenderer, startsActivated);
        infraRedReceiver = new InfraRedReceiver();
        infraRedReceiver.somethingHasBeenPressed = () -> {
            onAnyButtonPress();
        };
        buttons = new ArrayList<Button>();
        buttons.add(upButton);
        buttons.add(downButton);
        buttons.add(leftButton);
        buttons.add(rightButton);
        buttons.add(onButton);
        buttons.add(muteButton);
        buttons.add(circleButton);
        buttons.add(squareButton);
        buttons.add(triangleButton);
        buttons.add(turn90DegreesLeftButton);
        buttons.add(turn90DegreesRightButton);
        buttons.add(turn180DegreesLeftButton);
        buttons.add(turn180DegreesRightButton);
        buttons.add(speedSlowButton);
        buttons.add(speedMediumButton);
        buttons.add(speedFastButton);

        System.out.println("lowest");
    }

    private void onAnyButtonPress()
    {
        aButtonHasBeenPressed.run();
    }

    @Override
    protected void start()
    {
        super.start();
    }

    @Override
    protected void awake()
    {
        super.awake();
    }

    @Override
    protected void sleep()
    {
        super.sleep();
    }

    @Override
    protected void inputLoop(double deltaTime)
    {
        super.inputLoop(deltaTime);

        infraRedReceiver.checkForButtonPresses(buttons);
    }

    @Override
    protected void destroy()
    {
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

    public InfraRedReceiver getInfraRedReceiver()
    {
        return infraRedReceiver;
    }

    public ArrayList<Button> getButtons()
    {
        return buttons;
    }

    public Button getDownButton()
    {
        return downButton;
    }

    public Button getLeftButton()
    {
        return leftButton;
    }

    public Button getRightButton()
    {
        return rightButton;
    }

    public Button getOnButton()
    {
        return onButton;
    }

    public Button getMuteButton()
    {
        return muteButton;
    }

    public Button getCircleButton()
    {
        return circleButton;
    }

    public Button getSquareButton()
    {
        return squareButton;
    }

    public Button getTriangleButton()
    {
        return triangleButton;
    }

    public Button getTurn90DegreesLeftButton()
    {
        return turn90DegreesLeftButton;
    }

    public Button getTurn90DegreesRightButton()
    {
        return turn90DegreesRightButton;
    }

    public Button getTurn180DegreesLeftButton()
    {
        return turn180DegreesLeftButton;
    }

    public Button getTurn180DegreesRightButton()
    {
        return turn180DegreesRightButton;
    }

    public Button getSpeedSlowButton()
    {
        return speedSlowButton;
    }

    public Button getSpeedMediumButton()
    {
        return speedMediumButton;
    }

    public Button getSpeedFastButton()
    {
        return speedFastButton;
    }
}
