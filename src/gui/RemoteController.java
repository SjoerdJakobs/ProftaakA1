package gui;

import buttercat.ControlPanel;
import buttercat.DriverAI;
import buttercat.Program;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ooframework.FrameworkProgram;

import java.awt.*;

public class RemoteController {
    @FXML
    Button forwardButton;
    @FXML
    Button backwardButton;
    @FXML
    Button leftButton;
    @FXML
    Button rightButton;
    @FXML
    Button stopButton;
    @FXML
    Button uTurnButton;
    @FXML
    Button speed1Button;
    @FXML
    Button speed2Button;
    @FXML
    Button speed3Button;
    @FXML
    Button muteButton;
    @FXML
    Button powerButton;


    public void initialize() {
        initButtons();
        setSkins();
    }

    private void initButtons() {

        forwardButton.setOnAction(e -> {
            //TODO send forward signal
        });

        leftButton.setOnAction(e -> {
            //TODO send turn left signal
        });

        rightButton.setOnAction(e -> {
            //TODO send turn right signal
        });

        backwardButton.setOnAction(e -> {
            //TODO send drive backwards signal
        });

        stopButton.setOnAction(e -> {
            //TODO send stop signal
        });

        uTurnButton.setOnAction(e -> {
            //TODO send u turn signal
        });

        speed1Button.setOnAction(e -> {
            //TODO send speed 1 signal
        });

        speed2Button.setOnAction(e -> {
            //TODO send speed 2 signal
        });

        speed3Button.setOnAction(e -> {
            //TODO send speed 3 signal
        });

        muteButton.setOnAction(e -> {
            //TODO send mute singal
        });

        powerButton.setOnAction(e -> {
            //TODO send power on/off signal
        });
    }

    private void setSkins() {
        forwardButton.setSkin(new GUIButtonSkin(forwardButton));
        backwardButton.setSkin(new GUIButtonSkin(backwardButton));
        leftButton.setSkin(new GUIButtonSkin(leftButton));
        rightButton.setSkin(new GUIButtonSkin(rightButton));
        stopButton.setSkin(new GUIButtonSkin(stopButton));
        uTurnButton.setSkin(new GUIButtonSkin(uTurnButton));
        speed1Button.setSkin(new GUIButtonSkin(speed1Button));
        speed2Button.setSkin(new GUIButtonSkin(speed2Button));
        speed3Button.setSkin(new GUIButtonSkin(speed3Button));
        muteButton.setSkin(new GUIButtonSkin(muteButton));
        powerButton.setSkin(new GUIButtonSkin(powerButton));
    }

}
