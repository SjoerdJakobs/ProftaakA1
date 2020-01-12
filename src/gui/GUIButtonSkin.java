package gui;

import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

/**
 * Button skin class for adding animations
 */
class GUIButtonSkin extends ButtonSkin {

    /**
     * make a new button skin and assign the animations to it
     *
     * @param control the button to add the animations to
     */
    GUIButtonSkin(Button control) {
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