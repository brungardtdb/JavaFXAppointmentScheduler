package app.UI.JavaFX.Controllers;

import app.DataAccess.DataAccessFactory;
import app.DataLocalization.LocalizationService;
import app.UI.JavaFX.AlertService;
import app.UserData.Models.AppointmentModel;
import app.Util.PropertiesService;
import app.Util.ValidationService;

import java.time.ZoneId;
import java.util.Locale;

public class AppointmentController
{
    private PropertiesService propertiesService;
    private LocalizationService localizationService;
    private DataAccessFactory dataAccessFactory;
    private Locale locale;
    ZoneId zoneId;
    AlertService alertService;
    ValidationService validationService;
    private MainController mainController;
    private boolean modifyingAppointment;
    private AppointmentModel appointment;

    public AppointmentController() { }

    public void Initialize(PropertiesService propertiesService, LocalizationService localizationService,
                           DataAccessFactory dataAccessFactory, Locale locale, ZoneId zoneId, AlertService alertService,
                           ValidationService validationService, MainController mainController, boolean modifyingAppointment)
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
    }


    public void GetAppointment(AppointmentModel appointment)
    {
        this.appointment = appointment;
    }

}
