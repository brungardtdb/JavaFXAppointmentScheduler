package app.UI.JavaFX.Controllers;

import app.DataLocalization.LocalizationService;
import app.PropertiesService;
import com.sun.javafx.scene.control.Properties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.ZoneId;
import java.util.Locale;

public class LoginController
{
    private LocalizationService localizationService;
    private DataAccess.DataAccessFactory dataAccessFactory;
    private PropertiesService propertiesService;
    private Locale locale;
    private ZoneId zoneID;
    @FXML private  Label logInLabel;
    @FXML private Label zoneIDLabel;
    @FXML private Label userNameLabel;
    @FXML private Label passwordLabel;
    @FXML private TextField userNameTextField;
    @FXML private PasswordField passwordField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    public LoginController() throws Exception { }

    public void SetLocalization(LocalizationService localizationService)
    {
        this.localizationService = localizationService;
    }

    public void SetDataAccessFactory(DataAccess.DataAccessFactory dataAccessFactory)
    {
        this.dataAccessFactory = dataAccessFactory;
    }

    public void SetLocale(Locale locale)
    {
        this.locale = locale;
    }

    public void SetZoneID(ZoneId zoneID) throws Exception
    {
        this.zoneID = zoneID;
        String localizedZoneID = localizationService.GetLocalizedMessage("zoneid", this.locale);
        zoneIDLabel.setText(String.valueOf(localizedZoneID + ": " + this.zoneID));
    }

    public void SetPropertiesService(PropertiesService propertiesService)
    {
        this.propertiesService = propertiesService;
    }

    public void Initialize() throws Exception
    {
        logInLabel.setText(localizationService.GetLocalizedMessage("loginTitle", this.locale));
        userNameLabel.setText(localizationService.GetLocalizedMessage("username", this.locale));
        passwordLabel.setText(localizationService.GetLocalizedMessage("password", this.locale));
        String saveButtonText = localizationService.GetLocalizedMessage("submit", this.locale);
        String cancelButtonText = localizationService.GetLocalizedMessage("cancel", this.locale);
        saveButton.setText(saveButtonText);
        cancelButton.setText(cancelButtonText);
    }

    public void handleSaveLogin(ActionEvent actionEvent)
    {
        System.out.println("Hello from save");
    }

    public void handleCancelLogin(ActionEvent actionEvent)
    {
        System.out.println("Hello from cancel.");
    }
}
