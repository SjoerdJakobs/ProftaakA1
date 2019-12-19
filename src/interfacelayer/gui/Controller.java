package interfacelayer.gui;

import com.sun.javafx.scene.control.skin.ButtonSkin;
import interfacelayer.pathfinding.PathTile;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Controller {
    private PathTile[][] route;
    @FXML
    GridPane path;


    int amount = 8;
    public void initialize() {
        initGrid();

    }

    private void initGrid() {
        route = new PathTile[amount][amount];
        for (int x = 1; x <= amount; x++) {
            for (int y = 1; y <= amount; y++) {
                Button button = new Button(" ");
                button.setMinWidth(500/amount);
                button.setMinHeight(500/amount);
                button.setId(x + "-" + y);
                button.setSkin(new TileButtonSkin(button));
                path.add(button,x,y);
                PathTile tile = new PathTile(button, false);
                route[x-1][y-1] = tile;
            }
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
