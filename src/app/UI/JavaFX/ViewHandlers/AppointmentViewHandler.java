package app.UI.JavaFX.ViewHandlers;

import app.DataAccess.DataAccessFactory;
import app.DataLocalization.LocalizationService;
import app.UI.JavaFX.AlertService;
import app.UI.JavaFX.Controllers.AppointmentController;
import app.UI.JavaFX.Controllers.MainController;
import app.UserData.Models.AppointmentModel;
import app.Util.LoggingService;
import app.Util.PropertiesService;
import app.Util.ValidationService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.ZoneId;
import java.util.Locale;

/**
 * Class that handles the creation of the appointment view.
 */
public class AppointmentViewHandler
{
    private PropertiesService propertiesService;
    private LocalizationService localizationService;
    private DataAccessFactory dataAccessFactory;
    private Locale locale;
    private ZoneId zoneId;
    private AlertService alertService;
    private ValidationService validationService;
    private LoggingService loggingService;
    private MainController mainController;
    private boolean modifyingAppointment;
    private AppointmentModel appointment;

    /**
     * Class constructor, takes dependencies and passes them to constructor of appointment view.
     *
     * @param propertiesService PropertiesService dependency.
     * @param localizationService LocalizationService dependency.
     * @param dataAccessFactory DataAccessFactory dependency.
     * @param locale User's locale.
     * @param zoneId User's ZoneId.
     * @param alertService AlertService dependency.
     * @param validationService ValidationService dependency.
     * @param mainController Controller for main application view.
     * @param modifyingAppointment Indicates if we are modifying an existing appointment.
     * @param loggingService Application logging utility.
     */
    public AppointmentViewHandler(PropertiesService propertiesService, LocalizationService localizationService,
                                  DataAccessFactory dataAccessFactory, Locale locale, ZoneId zoneId, AlertService alertService,
                                  ValidationService validationService, MainController mainController, boolean modifyingAppointment,
                                  LoggingService loggingService)
    {
        this.propertiesService = propertiesService;
        this.localizationService = localizationService;
        this.dataAccessFactory = dataAccessFactory;
        this.locale = locale;
        this.zoneId = zoneId;
        this.alertService = alertService;
        this.validationService = validationService;
        this.mainController = mainController;
        this.modifyingAppointment = modifyingAppointment;
        this.loggingService = loggingService;
    }

    /**
     * Method for passing in existing appointment when we are modifying an appointment.
     *
     * @param appointment The appointment to be modified.
     */
    public void GetAppointment(AppointmentModel appointment){ this.appointment = appointment; }

    /**
     * Creates the appointment view.
     */
    public void GetAppointmentView()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/app/UI/JavaFX/Views/AppointmentView.fxml"));
            Parent root = null;

            root = (Parent) fxmlLoader.load();

            // Get controller and configure controller settings
            AppointmentController appointmentController = fxmlLoader.getController();
            appointmentController.Initialize(this.propertiesService, this.localizationService, this.dataAccessFactory,
                    this.locale, this.zoneId, this.alertService, this.validationService, this.mainController,
                    this.modifyingAppointment, this.loggingService);

            if (modifyingAppointment)
                appointmentController.GetAppointment(this.appointment);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);

            String message = modifyingAppointment ? "modifyappointment" : "addappointment";
            stage.setTitle(this.localizationService.GetLocalizedMessage(message, this.locale));
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
