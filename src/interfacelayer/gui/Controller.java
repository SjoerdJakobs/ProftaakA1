package interfacelayer.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Controller {
    private Button[][] route;
    @FXML
    GridPane path;


    int amount = 8;
    public void initialize() {
        route = new Button[amount][amount];
        for (int x = 1; x <= amount; x++) {
            for (int y = 1; y <= amount; y++) {
                Button button = new Button(" ");
                button.setMinWidth(500/amount);
                button.setMinHeight(500/amount);
                button.setId(x + "-" + y);
                path.add(button,x,y);
                route[x-1][y-1] = button;
            }
        }

    }

}
