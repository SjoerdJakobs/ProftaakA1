package interfacelayer.gui;

import com.sun.javafx.scene.control.skin.ButtonSkin;
import interfacelayer.pathfinding.PathTile;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import jssc.SerialPort;
import jssc.SerialPortException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class Controller {
    private PathTile[][] route;
    @FXML
    GridPane path;
    @FXML
    TextField gridSizeField;
    @FXML
    Button saveButton;
    @FXML
    Button helpButton;


    int amount = 8;

    public void initialize() {
        initGrid();
        saveButton.setSkin(new TileButtonSkin(saveButton));


        gridSizeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //make sure the user can only enter numbers
                if (!newValue.matches("\\d*")) {
                    gridSizeField.setText(newValue.replaceAll("[^\\d]", ""));
                }

                if (!gridSizeField.getText().isEmpty() || !(gridSizeField.getText() == null))
                    if (!gridSizeField.getText().isEmpty())
                        amount = Integer.parseInt(gridSizeField.getText());

                initGrid();
            }
        });

        saveButton.setOnAction(event -> gridToString());
        helpButton.setOnAction(event -> {
            try {
                showHelp();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void showHelp() throws IOException {
        Parent help = FXMLLoader.load(getClass().getResource("pathLayout.fxml"));
        Scene helpScene = new Scene(help);
        Stage helpWindow = new Stage();
        helpWindow.setTitle("Help");
        helpWindow.setScene(helpScene);

        helpWindow.initModality(Modality.WINDOW_MODAL);
        helpWindow.initOwner(PathVisualiser.stage);

        helpWindow.setX(PathVisualiser.stage.getX() + 100);
        helpWindow.setY(PathVisualiser.stage.getY() + 100);

        helpWindow.show();

    }

    private void initGrid() {
        path.getChildren().clear();
        route = new PathTile[amount][amount];
        for (int x = 1; x <= amount; x++) {
            for (int y = 1; y <= amount; y++) {
                Button button = new Button(" ");
                button.setMinWidth(500 / amount);
                button.setMinHeight(500 / amount);
                button.setId(x + "-" + y);
                button.setSkin(new TileButtonSkin(button));
                path.add(button, x, y);
                PathTile tile = new PathTile(button, false, x, y);
                addAction(tile);
                route[y - 1][x - 1] = tile;
            }
        }
    }

    private void addAction(PathTile tile) {
        Button button = tile.getButton();
        button.setOnAction(e -> {
            System.out.println(button.getId() + " clicked");
            System.out.println("tile is " + tile.isNavigatable());
            tile.setNavigatable(!tile.isNavigatable());
        });

    }

    public void gridToString() {
        for (int i = 0; i < route.length; i++) {
            for (int j = 0; j < route[0].length; j++) {
                System.out.print(route[i][j]);
            }
            System.out.println();
        }
    }


    private class TileButtonSkin extends ButtonSkin {

        public TileButtonSkin(Button control) {
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
