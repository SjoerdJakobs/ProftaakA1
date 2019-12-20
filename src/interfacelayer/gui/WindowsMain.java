package interfacelayer.gui;

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


        //TODO add saving a route under a name, adding window to give name, adding window to select route and see
        //TODO add com choose box
        Scene scene = new Scene(root);
        scene.getStylesheets().add("interfacelayer/gui/stylesheet.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Buttercat");
        stage.show();
    }
}
