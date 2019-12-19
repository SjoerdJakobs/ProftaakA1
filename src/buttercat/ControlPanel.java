package buttercat;

import hardwarelayer.sensors.asciibutton.AsciiButton;
import hardwarelayer.sensors.bluetoothreceiver.BluetoothReceiver;
import interfacelayer.Callback;
import ooframework.FrameworkProgram;
import ooframework.StandardObject;

import java.util.ArrayList;

public class ControlPanel extends StandardObject {
    private AsciiButton WButton;
    private AsciiButton AButton;
    private AsciiButton SButton;
    private AsciiButton DButton;
    private AsciiButton SpaceButton;
    private AsciiButton EscButton;
    private AsciiButton OneButton;
    private AsciiButton TwoButton;
    private AsciiButton ThreeButton;
    private AsciiButton MButton;
    private AsciiButton QButton;
    private AsciiButton EButton;
    private AsciiButton ZButton;
    private AsciiButton CButton;
    private AsciiButton PButton;
    private ArrayList list;

    public Callback aButtonHasBeenPressed;

//    @FXML
//    private Button power;


    private BluetoothReceiver bluetoothReceiver;
    private ArrayList<AsciiButton> asciiButtons;

    protected ControlPanel(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);
    }

    protected ControlPanel(FrameworkProgram frameworkProgram, boolean usesInput, boolean usesMain, boolean usesRenderer, boolean startsActivated) {
        super(frameworkProgram, usesInput, usesMain, usesRenderer, startsActivated);
        bluetoothReceiver = new BluetoothReceiver();
        asciiButtons = new ArrayList<>();
        bluetoothReceiver.somethingHasBeenPressed = () -> {
            onAnyButtonPress();
        };
//        aButtonHasBeenPressed = () ->{
//            haha();
//        };
        WButton = new AsciiButton(119, true);
        AButton = new AsciiButton(97, true);
        SButton = new AsciiButton(115, true);
        DButton = new AsciiButton(100, true);
        SpaceButton = new AsciiButton(32);
        EscButton = new AsciiButton(27);
        OneButton = new AsciiButton(49);
        TwoButton = new AsciiButton(50);
        ThreeButton = new AsciiButton(51);
        MButton = new AsciiButton(109);
        QButton = new AsciiButton(113, true);
        EButton = new AsciiButton(101, true);
        ZButton = new AsciiButton(122, true);
        CButton = new AsciiButton(99, true);
        PButton = new AsciiButton(112, true);
        asciiButtons.add(WButton);
        asciiButtons.add(AButton);
        asciiButtons.add(SButton);
        asciiButtons.add(DButton);
        asciiButtons.add(SpaceButton);
        asciiButtons.add(EscButton);
        asciiButtons.add(OneButton);
        asciiButtons.add(TwoButton);
        asciiButtons.add(ThreeButton);
        asciiButtons.add(MButton);
        asciiButtons.add(QButton);
        asciiButtons.add(EButton);
        asciiButtons.add(ZButton);
        asciiButtons.add(CButton);
    }

    public void AsciiButton() {

    }

    private void onAnyButtonPress()
    {
        System.out.println("In onanybuttonpress");
        aButtonHasBeenPressed.run();
        System.out.println("na onanybuttonpress");

    }

//    void haha()
//    {
//        System.out.println("kut");
//    }

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

        bluetoothReceiver.checkForButtonPresses(asciiButtons);
    }

    @Override
    protected void destroy()
    {
        super.destroy();
    }

    public BluetoothReceiver getBluetoothReceiver() {
        return bluetoothReceiver;
    }

    public ArrayList<AsciiButton> getAsciiButtons() {
        return asciiButtons;
    }

    public AsciiButton getWButton() {
        System.out.println("get w");
        System.out.println(WButton.getAscii());
        return WButton;
    }

    public AsciiButton getAButton() {
        return AButton;
    }

    public AsciiButton getSButton() {
        return SButton;
    }

    public AsciiButton getDButton() {
        return DButton;
    }

    public AsciiButton getSpaceButton() {
        return SpaceButton;
    }

    public AsciiButton getEscButton() {
        return EscButton;
    }

    public AsciiButton getOneButton() {
        return OneButton;
    }

    public AsciiButton getTwoButton() {
        return TwoButton;
    }

    public AsciiButton getThreeButton() {
        return ThreeButton;
    }

    public AsciiButton getMButton() {
        return MButton;
    }

    public AsciiButton getQButton() {
        return QButton;
    }

    public AsciiButton getEButton() {
        return EButton;
    }

    public AsciiButton getZButton() {
        return ZButton;
    }

    public AsciiButton getCButton() {
        return CButton;
    }

    public AsciiButton getPButton() {
        return PButton;
    }

}