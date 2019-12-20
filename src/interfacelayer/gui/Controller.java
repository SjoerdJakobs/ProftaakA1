package interfacelayer.gui;

import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private ArrayList<String> commands;
    private ListView<String> commandList;
    private ArrayList<String> routes;
    private ListView<String> routesList;

    @FXML
    Button leftButton;
    @FXML
    Button rightButton;
    @FXML
    Button forwardButton;
    @FXML
    AnchorPane rootPane;
    @FXML
    TextField nameField;
    @FXML
    Button saveButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init");
        this.commands = new ArrayList<>();
        this.commands.add("Add your commands using the buttons!");

        this.routes = new ArrayList<>();
        this.routes.add("Your saved routes will be shown here!");

        initCommandsList();
        initRoutesList();
        setButtons();
        setCommandsListLayout();

    }

    private void initRoutesList() {
        ListView<String> routesList = new ListView<String>();
        this.routesList = routesList;
        rootPane.getChildren().addAll(routesList);
        routesList.setItems(FXCollections.observableList(routes));
    }

    private void initCommandsList() {
        ListView<String> commandList = new ListView<String>();
        this.commandList = commandList;
        rootPane.getChildren().add(commandList);
        commandList.setItems(FXCollections.observableList(commands));
    }

    private void setButtons() {
        leftButton.setOnAction(e -> {
            System.out.println("adding " + "Left");
            checkForTestValue();
            this.commands.add("Left");
//            System.out.println(commands.toString());
            commandList.refresh();
        });
        forwardButton.setOnAction( e -> {
            System.out.println("adding " + "Forward");
            checkForTestValue();
            this.commands.add("Forward");
//            System.out.println(commands.toString());
            commandList.refresh();
        });
        rightButton.setOnAction(e -> {
            System.out.println("adding " + "Right");
            checkForTestValue();
            this.commands.add("Right");
//            System.out.println(commands.toString());
            commandList.refresh();
        });
        saveButton.setOnAction(e -> {

        });

        leftButton.setSkin(new GUIButtonSkin(leftButton));
        forwardButton.setSkin(new GUIButtonSkin(forwardButton));
        rightButton.setSkin(new GUIButtonSkin(rightButton));
        saveButton.setSkin(new GUIButtonSkin(saveButton));
    }

    private void checkForTestValue() {
        this.commands.remove("Add your commands using the buttons!");
        this.routes.remove("Your saved routes will be shown here!");
    }
    private void setCommandsListLayout() {
        this.commandList.setPrefWidth(318);
        this.commandList.setPrefHeight(507);
        this.commandList.setLayoutX(34);
        this.commandList.setLayoutY(200);
        this.routesList.setPrefWidth(318);
        this.routesList.setPrefHeight(507);
        this.routesList.setLayoutX(34 + 318 + 300);
        this.routesList.setLayoutY(200);

    }

    private class GUIButtonSkin extends ButtonSkin {

        public GUIButtonSkin(Button control) {
            super(control);

            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(130));
            scaleUp.setNode(control);
            scaleUp.setToX(1.14);
            scaleUp.setToY(1.14);

            control.setOnMouseEntered(e -> scaleUp.play());

            ScaleTransition scaleDownPress = new ScaleTransition(Duration.millis(50));
            scaleDownPress.setNode(control);
            scaleDownPress.setToX(1.06);
            scaleDownPress.setToY(1.06);
            control.setOnMousePressed(e -> scaleDownPress.play());

//            control.setOnMouseClicked(scaleDownPress.playFromStart(););

            ScaleTransition scaleUpPress = new ScaleTransition(Duration.millis(50));
            scaleUpPress.setNode(control);
            scaleUpPress.setToX(1.14);
            scaleUpPress.setToY(1.14);
            control.setOnMouseReleased(e -> scaleUpPress.play());

            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(130));
            scaleDown.setNode(control);
            scaleDown.setToX(1);
            scaleDown.setToY(1);

            control.setOnMouseExited(e -> scaleDown.play());
        }
    }
}
