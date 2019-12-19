package interfacelayer.pathfinding;

import javafx.scene.control.Button;

public class PathTile {
    private Button button;
    private boolean navigatable;

    public PathTile(Button button, boolean navigatable) {
        this.button = button;
        this.navigatable = navigatable;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public boolean isNavigatable() {
        return navigatable;
    }

    public void setNavigatable(boolean navigatable) {
        this.navigatable = navigatable;
    }



}
