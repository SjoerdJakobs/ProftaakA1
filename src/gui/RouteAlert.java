package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

/**
 * Alert class for showing alerts
 */
class RouteAlert extends Alert {

    /**
     * make a new alert and assign a css class to it
     *
     * @param alertType the type of alert
     * @param title     the alert window title
     * @param message   the alert message
     */
    RouteAlert(AlertType alertType, String title, String message) {
        super(alertType);
        this.setTitle(title);
        this.setContentText(message);
        this.setHeaderText("HEY! YOU!");

        DialogPane dialogPane = this.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("stylesheet.css").toExternalForm());
    }
}