package interfacelayer.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PathVisualiser extends Application {
    private int amount;

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("pathLayout.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("interfacelayer/gui/stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("Buttercat");
        stage.show();

    }
}
