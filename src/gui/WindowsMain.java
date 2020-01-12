package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortException;



public class WindowsMain extends Application {
    private Stage stage;

    public static void main(String[] args) {
        launch(WindowsMain.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("gui/stylesheet.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Buttercat");
        stage.show();

    }

    public Stage getStage() {
        return this.stage;
    }
}
