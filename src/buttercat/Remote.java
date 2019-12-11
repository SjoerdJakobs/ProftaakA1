package buttercat;

import hardwarelayer.sensors.button.Button;
import interfacelayer.Callback;
import hardwarelayer.sensors.infraredreceiver.InfraRedReceiver;
import ooframework.FrameworkProgram;
import ooframework.StandardObject;

import java.util.ArrayList;

public class Remote extends StandardObject
{
    // all buttons with their addresses that are used on the remote
    public static final long CHANNEL_PLUS = 10010000L;//000010010000L
    public static final long CHANNEL_MIN = 100010010000L;//100010010000L
    public static final long VOLUME_UP = 10010010000L;//010010010000L
    public static final long VOLUME_DOWN = 110010010000L;//110010010000L
    public static final long ON = 101010010000L;//101010010000L
    public static final long MUTE = 1010010000L;//001010010000L
    public static final long ROUND = 101010000000L;//101010000000L
    public static final long SQUARE = 101100111000L;//101100111000L
    public static final long TRIANGLE = 11100111000L;//011100111000L
    public static final long TURN_RIGHT = 101001010000L;//101001010000L
    public static final long TURN_LEFT = 100111000L;//000100111000L
    public static final long REVERSE_LEFT = 1100010000L;//001100010000L
    public static final long REVERSE_RIGHT = 101001010000L;//101001010000L
    public static final long SPEED_ZERO = 100100010000L;//100100010000L
    public static final long SPEED_ONE = 10000L;//000000010000L
    public static final long SPEED_TWO = 100000010000L;//100000010000L
    public static final long SPEED_TRHREE = 10000010000L;//010000010000L

    private InfraRedReceiver infraRedReceiver;
    private ArrayList<Button> buttons;

    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;
    private Button onButton;
    private Button muteButton;
    private Button circleButton;
    private Button squareButton;
    private Button triangleButton;
    private Button turn90DegreesLeftButton;
    private Button turn90DegreesRightButton;
    private Button turn180DegreesLeftButton;
    private Button turn180DegreesRightButton;
    private Button speedOffButton;
    private Button speedSlowButton;
    private Button speedMediumButton;
    private Button speedFastButton;

    public Callback aButtonHasBeenPressed;

    protected Remote(FrameworkProgram frameworkProgram)
    {
        super(frameworkProgram);
    }

    public Remote(FrameworkProgram frameworkProgram, boolean usesInput, boolean usesMain, boolean usesRenderer, boolean startsActivated)
    {
        super(frameworkProgram, usesInput, usesMain, usesRenderer, startsActivated);
        infraRedReceiver = new InfraRedReceiver(1);
        infraRedReceiver.somethingHasBeenPressed = () -> {
            onAnyButtonPress();
        };
        buttons = new ArrayList<Button>();
        upButton = new Button(CHANNEL_PLUS, true);
        downButton = new Button(CHANNEL_MIN, true);
        leftButton = new Button(VOLUME_DOWN, true);
        rightButton = new Button(VOLUME_UP, true);
        onButton = new Button(ON);
        muteButton = new Button(MUTE);
        circleButton = new Button(ROUND);
        squareButton = new Button(SQUARE);
        triangleButton = new Button(TRIANGLE);
        turn90DegreesLeftButton = new Button(TURN_LEFT, true);
        turn90DegreesRightButton = new Button(TURN_RIGHT, true);
        turn180DegreesLeftButton = new Button(REVERSE_LEFT, true);
        turn180DegreesRightButton = new Button(REVERSE_RIGHT, true);
        speedOffButton = new Button(SPEED_ZERO);
        speedSlowButton = new Button(SPEED_ONE);
        speedMediumButton = new Button(SPEED_TWO);
        speedFastButton = new Button(SPEED_TRHREE);
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
        buttons.add(speedOffButton);
        buttons.add(speedSlowButton);
        buttons.add(speedMediumButton);
        buttons.add(speedFastButton);
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

        infraRedReceiver.checkForButtonPresses(buttons, deltaTime);
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

    public Button getSpeedOffButton()
    {
        return speedOffButton;
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
