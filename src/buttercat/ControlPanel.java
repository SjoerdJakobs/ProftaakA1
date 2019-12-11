package buttercat;

import hardwarelayer.sensors.asciibutton.AsciiButton;
import hardwarelayer.sensors.bluetoothreceiver.BluetoothReceiver;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

//    @FXML
//    private Button power;


    private BluetoothReceiver bluetoothReceiver;
    private ArrayList<AsciiButton> asciiButtons;

    protected ControlPanel(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);
    }

    protected ControlPanel(FrameworkProgram frameworkProgram, boolean usesInput, boolean usesMain, boolean usesRenderer, boolean startsActivated) {
        super(frameworkProgram, usesInput, usesMain, usesRenderer, startsActivated);
    }

    public void AsciiButton() {
        WButton = new AsciiButton(119);
        AButton = new AsciiButton(97);
        SButton = new AsciiButton(115);
        DButton = new AsciiButton(100);
        SpaceButton = new AsciiButton(32);
        EscButton = new AsciiButton(27);
        OneButton = new AsciiButton(49);
        TwoButton = new AsciiButton(50);
        ThreeButton = new AsciiButton(51);
        MButton = new AsciiButton(109);
        QButton = new AsciiButton(113);
        EButton = new AsciiButton(101);
        ZButton = new AsciiButton(122);
        CButton = new AsciiButton(99);
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

    public BluetoothReceiver getBluetoothReceiver() {
        return bluetoothReceiver;
    }

    public ArrayList<AsciiButton> getAsciiButtons() {
        return asciiButtons;
    }

    public AsciiButton getWButton() {
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
}