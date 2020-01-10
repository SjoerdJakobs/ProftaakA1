package gui;

import interfacelayer.BluetoothConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final String PLACEHOLDER = "Add your route using the buttons!";
    private boolean editing;
    private int selectedRouteIndex;

    public static String COM_PORT;

    private BluetoothConnection bluetoothConnection;

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
    @FXML
    ComboBox<String> comBox;
    @FXML
    Button stopOverButton;
    @FXML
    Button resumeButton;

    RemoteController remoteController;
    public Controller() {

    }

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

        bluetoothConnection = new BluetoothConnection(COM_PORT);
        remoteController = new RemoteController();

        makeTemp();
        initCommandsList();
        initRoutesList();
        setButtons();
        initComs();
        setCommandsListLayout();

    }

    private void initComs() {
        comBox.getItems().add("COM0");
        comBox.getItems().add("COM1");
        comBox.getItems().add("COM2");
        comBox.getItems().add("COM3");
        comBox.getItems().add("COM4");
        comBox.getItems().add("COM5");
        comBox.getItems().add("COM6");
        comBox.getItems().add("COM7");
        comBox.getItems().add("COM8");
        comBox.getItems().add("COM9");

        comBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                COM_PORT = newValue;
                bluetoothConnection.setPort(COM_PORT);
            }
        });

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
     * sets up the ListView for the route
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
//            System.out.println(route.toString());
            commandList.refresh();
        });
        forwardButton.setOnAction(e -> {
            System.out.println("adding " + "Forward");
            checkForTestValue();
            this.commands.add("Forward");
//            System.out.println(route.toString());
            commandList.refresh();
        });
        rightButton.setOnAction(e -> {
            System.out.println("adding " + "Right");
            checkForTestValue();
            this.commands.add("Right");
//            System.out.println(route.toString());
            commandList.refresh();
        });
        stopOverButton.setOnAction(e -> {
            System.out.println("adding stop");
            checkForTestValue();
            this.commands.add("Stop");
            commandList.refresh();
        });
        saveButton.setOnAction(e -> {
            checkForTestValue();
            Route route = new Route(nameField.getText());
            route.setCommands(this.commands);

//            System.out.println("route: " + this.route);
//            System.out.println("routes: " + this.routes);
//            System.out.println("route route: " + route.getCommands());
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
            remoteController.passConnection(bluetoothConnection);

            if (COM_PORT == null || COM_PORT.isEmpty()) {
                RouteAlert alert = new RouteAlert(Alert.AlertType.WARNING, "No COM port set", "Please select a COM port!");
                alert.showAndWait();
                return;
            }
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
            if (COM_PORT == null || COM_PORT.isEmpty()) {
                RouteAlert alert = new RouteAlert(Alert.AlertType.WARNING, "No COM port set", "Please select a COM port!");
                alert.showAndWait();
                return;
            }
            Route selected = routesList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int[] instructions = selected.routeToNumbers();

                System.out.println(Arrays.toString(instructions));
                bluetoothConnection.sendRoute(instructions);
            } else {
                RouteAlert alert = new RouteAlert(Alert.AlertType.WARNING, "No route selected","Please select the route you want to execute!");
                alert.showAndWait();
            }

        });

        resumeButton.setOnAction(e -> {
            System.out.println("resume kanker");
            //TODO implement resuming of route
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
        stopOverButton.setSkin(new GUIButtonSkin(stopOverButton));
        resumeButton.setSkin(new GUIButtonSkin(resumeButton));
    }

    public BluetoothConnection getBluetoothConnection() {
        return bluetoothConnection;
    }
}
