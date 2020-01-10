package gui;

import buttercat.ControlPanel;
import buttercat.DriverAI;
import buttercat.Program;
import interfacelayer.BluetoothConnection;
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
    Button speed1Button;
    @FXML
    Button speed2Button;
    @FXML
    Button speed3Button;
    @FXML
    Button muteButton;
    @FXML
    Button powerButton;

    BluetoothConnection connection;


    public void initialize() {
        initButtons();
        setSkins();
    }

    private void initButtons() {
        int[] number = new int[2];
        forwardButton.setOnAction(e -> {
            number[0] = 119;
            number[1] = 119;
            connection.sendRoute(number);
        });

        leftButton.setOnAction(e -> {
            number[0] = 97;
            number[1] = 97;
            connection.sendRoute(number);
        });

        rightButton.setOnAction(e -> {
            number[0] = 100;
            number[1] = 100;
            connection.sendRoute(number);
        });

        backwardButton.setOnAction(e -> {
            number[0] = 115;
            number[1] = 115;
            connection.sendRoute(number);
        });

        stopButton.setOnAction(e -> {
            number[0] = 32;
            number[1] = 32;
            connection.sendRoute(number);
        });

        speed1Button.setOnAction(e -> {
            number[0] = 49;
            number[1] = 49;
            connection.sendRoute(number);
        });

        speed2Button.setOnAction(e -> {
            number[0] = 50;
            number[1] = 50;
            connection.sendRoute(number);
        });

        speed3Button.setOnAction(e -> {
            number[0] = 51;
            number[1] = 51;
            connection.sendRoute(number);
        });

        muteButton.setOnAction(e -> {
            number[0] = 109;
            number[1] = 109;
            connection.sendRoute(number);
        });

        powerButton.setOnAction(e -> {
            number[0] = 29;
            number[1] = 29;
            connection.sendRoute(number);
        });
    }

    private void setSkins() {
        forwardButton.setSkin(new GUIButtonSkin(forwardButton));
        backwardButton.setSkin(new GUIButtonSkin(backwardButton));
        leftButton.setSkin(new GUIButtonSkin(leftButton));
        rightButton.setSkin(new GUIButtonSkin(rightButton));
        stopButton.setSkin(new GUIButtonSkin(stopButton));
        speed1Button.setSkin(new GUIButtonSkin(speed1Button));
        speed2Button.setSkin(new GUIButtonSkin(speed2Button));
        speed3Button.setSkin(new GUIButtonSkin(speed3Button));
        muteButton.setSkin(new GUIButtonSkin(muteButton));
        powerButton.setSkin(new GUIButtonSkin(powerButton));
    }

    public void passConnection(BluetoothConnection connection) {
        this.connection = connection;
    }

}
