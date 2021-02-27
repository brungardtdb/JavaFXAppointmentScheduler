package app.UI.JavaFX.Controllers;

import UserData.Models.AppointmentModel;
import app.DataLocalization.LocalizationService;
import app.UI.JavaFX.AlertService;
import app.UI.JavaFX.ViewHandlers.MainHandler;
import app.Util.PropertiesService;

import app.Util.ValidationService;
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
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.function.*;

/**
 * Controller for login form.
 */
public class LoginController
{
    private LocalizationService localizationService;
    private DataAccess.DataAccessFactory dataAccessFactory;
    private PropertiesService propertiesService;
    private ValidationService validationService;
    private Locale locale;
    private ZoneId zoneID;
    private AlertService alertService;

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
    public LoginController() throws Exception {}

    /**
     * Method for initializing form.
     *
     * @param propertiesService The service for interacting with properties files.
     * @param localizationService The service for localizing data.
     * @param dataAccessFactory The service for data access layer.
     * @param locale The user's locale.
     * @param zoneId The user's ZoneID
     * @param alertService The service for providing custom alerts to the user.
     * @param validationService The service for performing business logic validations.
     * @throws Exception Java.io.FileNotFoundException.
     */
    public void Initialize(PropertiesService propertiesService, LocalizationService localizationService,
                           DataAccess.DataAccessFactory dataAccessFactory, Locale locale, ZoneId zoneId,
                           AlertService alertService, ValidationService validationService) throws Exception
    {
        this.propertiesService = propertiesService;
        this.localizationService = localizationService;
        this.dataAccessFactory = dataAccessFactory;
        this.locale = locale;
        this.zoneID = zoneId;
        this.alertService = alertService;
        this.validationService = validationService;

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

    /**
     * Button event that handles login attempts.
     *
     * @param actionEvent A button click event.
     * @throws Exception
     */
    public void handleSaveLogin(ActionEvent actionEvent) throws Exception
    {
        boolean loginIsValid = validationService.ValidateUsernamePassword(userNameTextField.getText(),
                passwordField.getText(), this.propertiesService);

        LogAttempt(userNameTextField.getText(), passwordField.getText(), loginIsValid);

        // show error message for invalid logins
        if (!loginIsValid)
        {
           ShowLogInError();
           return;
        }

        // check for upcoming appointments
        String titleAndHeader = "";
        String content = "";
        boolean upcomingAppointment = CheckForUpcomingAppointments();

        if (upcomingAppointment)
        {
            titleAndHeader = localizationService.GetLocalizedMessage("upcomingappointment", locale);
            content = localizationService.GetLocalizedMessage("upcomingappointmentmessage", locale);
        }

        if (!upcomingAppointment)
        {
            titleAndHeader = localizationService.GetLocalizedMessage("noupcomingappointment", locale);
            content = localizationService.GetLocalizedMessage("noupcomingappointmentmessage", locale);
        }

        this.alertService.ShowAlert(Alert.AlertType.INFORMATION, titleAndHeader, titleAndHeader, content);

        // open main form and pass in dependencies
        MainHandler mainHandler = new MainHandler(this.propertiesService, this.localizationService, this.dataAccessFactory,
                this.locale, this.zoneID, this.alertService, this.validationService);
        mainHandler.GetMainView();

        // close login form
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Button event for closing the form.
     *
     * @param actionEvent A button click event.
     * @throws Exception
     */
    public void handleCancelLogin(ActionEvent actionEvent) throws Exception
    {
        this.dataAccessFactory.DisconnectFromDB();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The method for logging each login attempt.
     *
     * I used a lambda expression here to help me log to the file without creating any extra classes.
     * This may violate the Single Responsibility principle of SOLID, but I couldn't see breaking it out into it's
     * own class unless we are going to do some more sophisticated logging.
     *
     * @param username The user entry for username.
     * @param password The user entry for password.
     * @param attemptIsSuccessful Whether or not the attempt was successful.
     * @throws Exception Java.io Exception.
     */
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

    /**
     * Method for displaying an error when login is unsuccessful.
     *
     * @throws Exception Java.io.FileNotFoundException.
     */
    private void ShowLogInError() throws Exception
    {

        String titleAndHeader = localizationService.GetLocalizedMessage("invalidlogin", locale);
        String content = localizationService.GetLocalizedMessage("invalidloginerror", locale);

        this.alertService.ShowAlert(Alert.AlertType.ERROR, titleAndHeader, titleAndHeader, content);
    }

    /**
     * Method that checks for upcoming appointments (within 15 minutes)
     *
     * @return True if there is an upcoming appointment, otherwise false.
     * @throws Exception
     */
    private boolean CheckForUpcomingAppointments() throws Exception
    {
        Boolean output = false;
        DataAccess.Interfaces.IAppointmentData appointmentData = this.dataAccessFactory.GetAppointmentDataService();
        List<AppointmentModel>  appointments;
        try
        {
            appointments = appointmentData.GetAllAppointments();
        }
        catch (Exception ex)
        {
            this.dataAccessFactory.DisconnectFromDB();
            throw ex;
        }

        // Iterate through list of appointments, checking start time of each, return true if a match is found
        for (int i = 0; i < appointments.size(); i++)
        {
            AppointmentModel appointment = appointments.get(i);

            if (this.validationService.ValidateUpcomingZonedDateTime(appointment.getStartDate()))
                return true; // Return early if we find a match
        }

        // for UI testing
//        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).plusMinutes(6);
//        if (this.validationService.ValidateUpcomingAppointment(zonedDateTime))
//            return true;

        return false; // Return false if no match is found
    }
}
