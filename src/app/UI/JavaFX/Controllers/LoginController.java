package app.UI.JavaFX.Controllers;

import app.DataLocalization.LocalizationService;
import app.Util.PropertiesService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Properties;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Controller for login form.
 */
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

    /**
     * Constructor for login controller form.
     *
     * @throws Exception
     */
    public LoginController() throws Exception { }

    /**
     * Method for initializing form.
     *
     * I would prefer to use constructor injection here, but I couldn't find a great way to do that with JavaFX.
     *
     * @param localizationService The service used for data localization.
     * @param dataAccessFactory The service used to access database.
     * @param locale The user's locale.
     * @param zoneID The user's zone ID.
     * @param propertiesService The service used for fetching properties files.
     * @throws Exception Java.io.FileNotFoundException.
     */
    public void Initialize(LocalizationService localizationService, DataAccess.DataAccessFactory dataAccessFactory,
    Locale locale, ZoneId zoneID, PropertiesService propertiesService) throws Exception
    {
        this.localizationService = localizationService;
        this.dataAccessFactory = dataAccessFactory;
        this.locale = locale;
        this.zoneID = zoneID;
        this.propertiesService = propertiesService;

        // Set up user interface
        String localizedZoneID = localizationService.GetLocalizedMessage("zoneid", this.locale);
        zoneIDLabel.setText(String.valueOf(localizedZoneID + ": " + this.zoneID));
        logInLabel.setText(localizationService.GetLocalizedMessage("loginTitle", this.locale));
        userNameLabel.setText(localizationService.GetLocalizedMessage("username", this.locale));
        passwordLabel.setText(localizationService.GetLocalizedMessage("password", this.locale));
        String saveButtonText = localizationService.GetLocalizedMessage("submit", this.locale);
        String cancelButtonText = localizationService.GetLocalizedMessage("cancel", this.locale);
        saveButton.setText(saveButtonText);
        cancelButton.setText(cancelButtonText);
    }

    public void handleSaveLogin(ActionEvent actionEvent) throws Exception
    {
        boolean loginIsValid = LoginIsValid(userNameTextField.getText(), passwordField.getText());
        LogAttempt(userNameTextField.getText(), passwordField.getText(), loginIsValid);

        if (loginIsValid)
        {
            // TODO - OPEN MAIN WINDOW
        }
    }

    public void handleCancelLogin(ActionEvent actionEvent)
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private boolean LoginIsValid(String username, String password)
    {
        if (username.isEmpty() || username.isBlank() || password.isEmpty() || password.isBlank())
            return false;

        try
        {
            Properties adminProperties = this.propertiesService.GetProperties("app.properties");
            Predicate<String> validUserName = s -> s.toLowerCase().equals(adminProperties.getProperty("adminusername").toLowerCase());
            Predicate<String> validPassword = s -> s.equals(adminProperties.getProperty("adminpassword"));
            return validUserName.test(username) && validPassword.test(password);
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    private void LogAttempt(String username, String password, boolean attemptIsSuccessful) throws Exception
    {
        if (!attemptIsSuccessful)
            ShowLogInError();

        String message = "--login attempt at " + ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")) + "--\n" +
                "Username: " + username + " Password: " + password + " Successful Login: " + attemptIsSuccessful  + "\n";

        File file = new File("./login_activity.txt");
        FileWriter fileWriter;

        Predicate<FileWriter> writeToFile = x -> {
            try {
                x.write(message);
                x.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        };

        fileWriter = file.exists() ? new FileWriter(file, true) : new FileWriter(file);

        if (!writeToFile.test(fileWriter))
            throw new Exception(localizationService.GetLocalizedMessage("logerror", this.locale));
    }

    private void ShowLogInError()
    {
        // TODO  - show error when login fails
    }
}
