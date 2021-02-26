package app.UI.JavaFX;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

/**
 * Service for sending alerts to the user.
 */
public class AlertService
{
    /**
     * Method for sending alerts to the user.
     *
     * @param alertType The type of alert.
     * @param alertTitle The title of the alert.
     * @param alertHeader The alert header.
     * @param alertContent The content message of the alert.
     */
    public static void ShowAlert(Alert.AlertType alertType, String alertTitle, String alertHeader, String alertContent)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.setContentText(alertContent);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
