package interfacelayer.pathfinding;

import javafx.scene.control.Button;

public class PathTile {
    private Button button;
    private boolean navigatable;
    private int xPos;
    private int yPos;


    public PathTile(Button button, boolean navigatable, int xPos, int yPos) {
        this.button = button;
        this.navigatable = navigatable;
        setNavigatable(navigatable);
        this.xPos = xPos;
        this.yPos = yPos;
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
        System.out.println("setting " + button.getId() + " to " + navigatable);
        this.navigatable = navigatable;
        button.getStyleClass().removeAll("navigatable", "notNavigatable", "focus");

        if (navigatable) {
            button.getStyleClass().add("navigatable");
        } else {
            button.getStyleClass().add("notNavigatable");
        }
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public String toString() {
        return "tile: " + button.getId() + "| navigatable: " + this.navigatable + "| x - y: " + this.xPos + " - " + this.yPos;

    }


}
