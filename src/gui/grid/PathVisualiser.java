package gui.grid;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PathVisualiser extends Application {
    private int amount;
    public static Stage stage;

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));


        Scene scene = new Scene(root);
        scene.getStylesheets().add("gui/grid/stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("Buttercat");
        stage.show();

        PathVisualiser.stage = stage;
    }
}
