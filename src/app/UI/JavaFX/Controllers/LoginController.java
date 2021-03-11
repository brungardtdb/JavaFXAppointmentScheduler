package app.UI.JavaFX.Controllers;

import app.DataAccess.Interfaces.IUserData;
import app.UserData.Models.AppointmentModel;
import app.DataAccess.Interfaces.IAppointmentData;
import app.DataAccess.DataAccessFactory;
import app.DataLocalization.LocalizationService;
import app.UI.JavaFX.AlertService;
import app.UI.JavaFX.ViewHandlers.MainViewHandler;
import app.UserData.Models.UserModel;
import app.Util.LoggingService;
import app.Util.PropertiesService;

import app.Util.ReportService;
import app.Util.ValidationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


/**
 * Controller for login form.
 */
public class LoginController
{
    private LocalizationService localizationService;
    private DataAccessFactory dataAccessFactory;
    private PropertiesService propertiesService;
    private ValidationService validationService;
    private Locale locale;
    private ZoneId zoneID;
    private AlertService alertService;
    private LoggingService loggingService;
    private ReportService reportService;
    private List<UserModel> users;
    private UserModel user;
    private AppointmentModel upcomingUserAppointment;

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
     * @param loggingService Application logging utility.
     * @param reportService Application report utility.
     * @throws Exception Java.io.FileNotFoundException.
     */
    public void Initialize(PropertiesService propertiesService, LocalizationService localizationService,
                           DataAccessFactory dataAccessFactory, Locale locale, ZoneId zoneId,
                           AlertService alertService, ValidationService validationService, LoggingService loggingService,
                           ReportService reportService) throws Exception
    {
        this.propertiesService = propertiesService;
        this.localizationService = localizationService;
        this.dataAccessFactory = dataAccessFactory;
        this.locale = locale;
        this.zoneID = zoneId;
        this.alertService = alertService;
        this.validationService = validationService;
        this.loggingService = loggingService;
        this.reportService = reportService;

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
     * I used a stream and lambda combination here to filter through the list of
     * users looking for a matching username and password to validate the login.
     *
     * @param actionEvent The user clicks the submit button.
     * @throws Exception
     */
    public void handleSaveLogin(ActionEvent actionEvent) throws Exception
    {
        IUserData userData = this.dataAccessFactory.GetUserDataService();
        boolean loginIsValid;

        try
        {
            this.users = userData.GetAllUsers();
        }
        catch (Exception ex)
        {
            throw ex;
        }

        // Check if login credentials match a user
        // If this were a production application we would really want to hash these passwords.
        Optional<UserModel> testUser = this.users.stream().filter(x ->
            x.getUserName().equals(userNameTextField.getText()) &&
                    x.getPassword().equals(passwordField.getText())).findFirst();

        loginIsValid = testUser.isPresent();

        if (loginIsValid)
            this.user = testUser.get();

        LogAttempt(userNameTextField.getText(), passwordField.getText(), loginIsValid);

        // show error message for invalid logins
        if (!loginIsValid)
           return;


        // check for upcoming appointments
        String titleAndHeader = "";
        String content = "";
        boolean upcomingAppointment = CheckForUpcomingAppointments();

        if (upcomingAppointment)
        {
            titleAndHeader = localizationService.GetLocalizedMessage("upcomingappointment", locale);
            content = localizationService.GetLocalizedMessage("upcomingappointmentmessage", locale);
            String ID = localizationService.GetLocalizedMessage("ID", locale);
            String startDate = localizationService.GetLocalizedMessage("startdate", locale);
            String startTime = localizationService.GetLocalizedMessage("starttime", locale);
            ZonedDateTime localZDateTime = this.localizationService.ZonedDateTimeToLocal(this.upcomingUserAppointment.getStartDate(), this.zoneID);
            content += "\n" + ID + ": " + this.upcomingUserAppointment.getAppointmentID() +
                    "\n" + startDate + ": " + localZDateTime.toLocalDate() +
                    "\n" + startTime + ": " + localZDateTime.toLocalTime();
        }

        if (!upcomingAppointment)
        {
            titleAndHeader = localizationService.GetLocalizedMessage("noupcomingappointment", locale);
            content = localizationService.GetLocalizedMessage("noupcomingappointmentmessage", locale);
        }

        this.alertService.ShowAlert(Alert.AlertType.INFORMATION, titleAndHeader, titleAndHeader, content);

        // open main form and pass in dependencies
        MainViewHandler mainViewHandler = new MainViewHandler(this.propertiesService, this.localizationService, this.dataAccessFactory,
                this.locale, this.zoneID, this.alertService, this.validationService, this.loggingService, this.reportService);
        mainViewHandler.GetMainView();

        // close login form
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Button event for closing the form.
     *
     * @param actionEvent The user clicks the cancel button.
     */
    public void handleCancelLogin(ActionEvent actionEvent)
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The method for logging each login attempt.
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

        this.loggingService.LogLoginAttempt(username, password, attemptIsSuccessful, this.localizationService, this.locale);
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
        IAppointmentData appointmentData = this.dataAccessFactory.GetAppointmentDataService();
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

            if (this.validationService.ValidateUpcomingZonedDateTime(appointment.getStartDate()) &&
                    appointment.getUserID() == this.user.getUserID())
            {
                this.upcomingUserAppointment = appointment;
                return true; // Return early if we find a match
            }
        }

        return false; // Return false if no match is found
    }
}
