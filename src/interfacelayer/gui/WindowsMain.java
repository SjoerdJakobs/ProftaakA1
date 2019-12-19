package interfacelayer.gui;

import interfacelayer.gui.grid.PathVisualiser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowsMain extends Application{
    public static void main(String[] args) {
        launch(WindowsMain.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));


        Scene scene = new Scene(root);
        scene.getStylesheets().add("interfacelayer/gui/stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("Buttercat");
        stage.show();
    }
}
