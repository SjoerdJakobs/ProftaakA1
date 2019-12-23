package gui;

import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The controller class for the GUI elements.
 */
public class Controller implements Initializable {
    private ArrayList<String> commands;
    private ListView<String> commandList;
    private ObservableList<Route> routes;
    private ListView<Route> routesList;

    private Route temp;
    private final String PLACEHOLDER = "Add your commands using the buttons!";
    private boolean editing;
    private int selectedRouteIndex;

    //fxml gui buttons
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
    @FXML
    Button undoButton;
    @FXML
    Button editButton;
    @FXML
    Button deleteButton;
    @FXML
    Button manualButton;
    @FXML
    Button executeButton;

    /**
     * initializes the GUI elements
     *
     * @param location  the location of the FXML file
     * @param resources the bundle of resources to work with
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init");
        this.commands = new ArrayList<>();
        this.commands.add(PLACEHOLDER);
        this.routes = FXCollections.observableArrayList();
        editing = false;

        makeTemp();
        initCommandsList();
        initRoutesList();
        setButtons();
        setCommandsListLayout();

    }

    /**
     * sets up the ListView for the routes
     */
    private void initRoutesList() {
        ListView<Route> routesList = new ListView<>(routes);
        this.routesList = routesList;
        this.routesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        rootPane.getChildren().addAll(routesList);
    }

    /**
     * sets up the ListView for the commands
     */
    private void initCommandsList() {
        ListView<String> commandList = new ListView<String>();
        this.commandList = commandList;
        this.commandList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        rootPane.getChildren().add(commandList);
        commandList.setItems(FXCollections.observableList(commands));
    }

    /**
     * sets all the event handlers for the buttons
     */
    private void setButtons() {
        leftButton.setOnAction(e -> {
            System.out.println("adding " + "Left");
            checkForTestValue();
            this.commands.add("Left");
//            System.out.println(commands.toString());
            commandList.refresh();
        });
        forwardButton.setOnAction(e -> {
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
            checkForTestValue();
            Route route = new Route(nameField.getText());
            route.setCommands(this.commands);

//            System.out.println("commands: " + this.commands);
//            System.out.println("routes: " + this.routes);
//            System.out.println("route commands: " + route.getCommands());
//            System.out.println("valid: " + route.isValid());

            if (nameField.getText().isEmpty()) {
                RouteAlert alert = new RouteAlert(Alert.AlertType.WARNING, "No name", "Please give your route a name!");
                alert.showAndWait();
            } else if (!route.isValid()) {
                RouteAlert alert = new RouteAlert(Alert.AlertType.WARNING, "Empty route", "You tried to add an empty route."
                        + "\nPlease add a route with instructions!");
                alert.showAndWait();
            } else {
                if (editing) {
                    this.routes.remove(selectedRouteIndex);
                }
                this.routes.remove(this.temp);
                this.routes.add(route);
                this.commands.clear();
                this.nameField.setText("");
                this.commands.add(PLACEHOLDER);
                this.commandList.refresh();
//                System.out.println("routes after add: " + this.routes);
                editing = false;
            }
        });
        undoButton.setOnAction(e -> {
            if (!this.commands.isEmpty()) {
                this.commands.remove(this.commands.size() - 1);
                this.commandList.refresh();
            }
        });
        editButton.setOnAction(e -> {
            displayContentsOfRoute();

        });

        deleteButton.setOnAction(e -> {
            Route selected = routesList.getSelectionModel().getSelectedItem();
            if (selected != null)
                this.routes.remove(selected);
            if (this.routes.isEmpty()) makeTemp();
            routesList.refresh();
        });

        manualButton.setOnAction(e -> {
            Stage stage = new Stage();
            try {
                Parent control = FXMLLoader.load(getClass().getResource("remotecontrol.fxml"));
                Scene scene = new Scene(control);
                scene.getStylesheets().add("gui/stylesheet.css");
                stage.setScene(scene);
                stage.setTitle("Manual control");
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

        executeButton.setOnAction(e -> {
            Route selected = routesList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int[] instructions = selected.routeToNumbers();
                System.out.println(Arrays.toString(instructions));
                //TODO send via bluetooth to boebot
            } else {
                RouteAlert alert = new RouteAlert(Alert.AlertType.WARNING, "No route selected","Please select the route you want to execute!");
                alert.showAndWait();
            }

        });

        setSkins();
    }

    /**
     * displays the contents of the selected {@link Route route}
     */
    public void displayContentsOfRoute() {
        Route selected = routesList.getSelectionModel().getSelectedItem();
        if (selected == null || !selected.isValid()) return;
//        System.out.println("selected route: " + selected);
//        System.out.println("command list: " + selected.getCommands());
        nameField.setText(selected.getName());
        selectedRouteIndex = routesList.getSelectionModel().getSelectedIndex();
        this.commands = new ArrayList<>(selected.getCommands());
        this.commandList.setItems(FXCollections.observableList(this.commands));
        this.commandList.refresh();
        editing = true;
    }

    private void openNewWindow() {
        //TODO open new window

    }

    /**
     * removes the placeholder command
     */
    private void checkForTestValue() {
        this.commands.remove(PLACEHOLDER);
    }

    /**
     * sets the layout for the command lists
     */
    private void setCommandsListLayout() {
        this.commandList.setPrefWidth(318);
        this.commandList.setPrefHeight(507);
        this.commandList.setLayoutX(34);
        this.commandList.setLayoutY(200);
        this.routesList.setPrefWidth(350);
        this.routesList.setPrefHeight(507);
        this.routesList.setLayoutX(2 + 318 + 300);
        this.routesList.setLayoutY(200);

    }

    /**
     * makes a temporary {@link Route route} to add as placeholder
     */
    private void makeTemp() {
        this.temp = new Route("Your saved routes will be shown here!");
        this.routes.add(temp);
    }

    /**
     * sets all the skins (animations) for the buttons
     */
    private void setSkins() {
        leftButton.setSkin(new GUIButtonSkin(leftButton));
        forwardButton.setSkin(new GUIButtonSkin(forwardButton));
        rightButton.setSkin(new GUIButtonSkin(rightButton));
        saveButton.setSkin(new GUIButtonSkin(saveButton));
        undoButton.setSkin(new GUIButtonSkin(undoButton));
        editButton.setSkin(new GUIButtonSkin(editButton));
        deleteButton.setSkin(new GUIButtonSkin(deleteButton));
        manualButton.setSkin(new GUIButtonSkin(manualButton));
        executeButton.setSkin(new GUIButtonSkin(executeButton));
    }
}
