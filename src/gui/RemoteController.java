package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

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

    public void initialize() {
        initButtons();
        setSkins();
    }

    private void initButtons() {
        forwardButton.setOnAction(e -> {
            //TODO send drive forward signal
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
    }

    private void setSkins() {
        forwardButton.setSkin(new GUIButtonSkin(forwardButton));
        backwardButton.setSkin(new GUIButtonSkin(backwardButton));
        leftButton.setSkin(new GUIButtonSkin(leftButton));
        rightButton.setSkin(new GUIButtonSkin(rightButton));
        stopButton.setSkin(new GUIButtonSkin(stopButton));
        uTurnButton.setSkin(new GUIButtonSkin(uTurnButton));
    }

}
