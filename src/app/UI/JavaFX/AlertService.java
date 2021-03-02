package app.UI.JavaFX;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.util.Optional;

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

    /**
     * Method for asking for confirmation from user.
     *
     * @param confirmationTitle The confirmation title.
     * @param confirmationHeader The confirmation header.
     * @param confirmationContent The confirmation message body.
     * @return True if "ok" was selected, otherwise false.
     */
    public boolean ShowConfirmation(String confirmationTitle, String confirmationHeader, String confirmationContent)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(confirmationTitle);
        alert.setHeaderText(confirmationHeader);
        alert.setContentText(confirmationContent);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        Optional<ButtonType> confirmation = alert.showAndWait();

        if (confirmation.get() == ButtonType.OK)
        {
            return true;
        }
        return false;
    }

}
