package app.UI.JavaFX.Controllers;

import app.DataAccess.DataAccessFactory;
import app.DataAccess.Interfaces.IAppointmentData;
import app.DataAccess.Interfaces.IContactData;
import app.DataAccess.Interfaces.ICustomerData;
import app.DataLocalization.LocalizationService;
import app.UI.JavaFX.AlertService;
import app.UserData.Enums.AppointmentType;
import app.UserData.Models.AppointmentModel;
import app.UserData.Models.ContactModel;
import app.UserData.Models.CountryModel;
import app.UserData.Models.CustomerModel;
import app.Util.PropertiesService;
import app.Util.ValidationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

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
    private ArrayList<ContactModel> contacts;
    private ArrayList<CustomerModel> customers;

    private final Map<String, Integer> AvailableHours = new HashMap<String, Integer>(){{
        put("00:00", 0);
        put("01:00", 1);
        put("02:00", 2);
        put("03:00", 3);
        put("04:00", 4);
        put("05:00", 5);
        put("06:00", 6);
        put("07:00", 7);
        put("08:00", 8);
        put("09:00", 9);
        put("10:00", 10);
        put("11:00", 11);
        put("12:00", 12);
        put("13:00", 13);
        put("14:00", 14);
        put("15:00", 15);
        put("16:00", 16);
        put("17:00", 17);
        put("18:00", 18);
        put("19:00", 19);
        put("20:00", 20);
        put("21:00", 21);
        put("22:00", 22);
        put("23:00", 23);
    }};

    @FXML Label appointmentLabel;
    @FXML Label appointmentIDLabel;
    @FXML Label appointmentTitleLabel;
    @FXML Label appointmentDescriptionLabel;
    @FXML Label appointmentLocationLabel;
    @FXML Label appointmentContactLabel;
    @FXML Label appointmentTypeLabel;
    @FXML Label appointmentStartDateLabel;
    @FXML Label appointmentStartTimeLabel;
    @FXML Label appointmentEndDateLabel;
    @FXML Label appointmentEndTimeLabel;
    @FXML Label appointmentCustomerLabel;
    @FXML TextField appointmentIDField;
    @FXML TextField appointmentTitleField;
    @FXML TextField appointmentDescriptionField;
    @FXML TextField appointmentLocationField;
    @FXML ComboBox appointmentContact;
    @FXML ComboBox appointmentType;
    @FXML DatePicker appointmentStartDate;
    @FXML ComboBox appointmentStartTime;
    @FXML DatePicker appointmentEndDate;
    @FXML ComboBox appointmentEndTime;
    @FXML ComboBox appointmentCustomer;
    @FXML Button saveAppointment;
    @FXML Button cancelAppointment;

    public AppointmentController() { }

    public void Initialize(PropertiesService propertiesService, LocalizationService localizationService,
                           DataAccessFactory dataAccessFactory, Locale locale, ZoneId zoneId, AlertService alertService,
                           ValidationService validationService, MainController mainController, boolean modifyingAppointment) throws Exception
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

        String message = modifyingAppointment ? "modifyappointment" : "addappointment";
        appointmentLabel.setText(this.localizationService.GetLocalizedMessage(message, this.locale));
        appointmentIDLabel.setText(this.localizationService.GetLocalizedMessage("ID", this.locale));
        appointmentTitleLabel.setText(this.localizationService.GetLocalizedMessage("title", this.locale));
        appointmentDescriptionLabel.setText(this.localizationService.GetLocalizedMessage("description", this.locale));
        appointmentLocationLabel.setText(this.localizationService.GetLocalizedMessage("location", this.locale));
        appointmentContactLabel.setText(this.localizationService.GetLocalizedMessage("contact", this.locale));
        appointmentTypeLabel.setText(this.localizationService.GetLocalizedMessage("type", this.locale));
        appointmentStartDateLabel.setText(this.localizationService.GetLocalizedMessage("startdate", this.locale));
        appointmentStartTimeLabel.setText(this.localizationService.GetLocalizedMessage("starttime", this.locale));
        appointmentEndDateLabel.setText(this.localizationService.GetLocalizedMessage("enddate", this.locale));
        appointmentEndTimeLabel.setText(this.localizationService.GetLocalizedMessage("endtime", this.locale));
        appointmentCustomerLabel.setText(this.localizationService.GetLocalizedMessage("customer", this.locale));
        appointmentIDField.setDisable(true);

        IContactData contactDataService = this.dataAccessFactory.GetContactDataService();
        this.contacts = (ArrayList<ContactModel>) contactDataService.GetAllContacts();
        appointmentContact.getItems().addAll(contacts.stream()
        .map(ContactModel::getContactName)
        .collect(Collectors.toList()));

        appointmentType.getItems().addAll(
                AppointmentType.PLANNING.toString(),
                AppointmentType.DEBRIEFING.toString()
        );

        appointmentStartTime.getItems().addAll(Arrays.stream(AvailableHours.keySet().toArray()).sorted()
        .collect(Collectors.toList()));

        appointmentEndTime.getItems().addAll(Arrays.stream(AvailableHours.keySet().toArray()).sorted()
        .collect(Collectors.toList()));

        ICustomerData customerDataService = this.dataAccessFactory.GetCustomerDataService();
        this.customers = (ArrayList<CustomerModel>) customerDataService.GetAllCustomers();
        appointmentCustomer.getItems().addAll(customers.stream()
        .map(CustomerModel::getCustomerName)
        .collect(Collectors.toList()));

        saveAppointment.setText(this.localizationService.GetLocalizedMessage("save", this.locale));
        cancelAppointment.setText(this.localizationService.GetLocalizedMessage("cancel", this.locale));
    }


    public void GetAppointment(AppointmentModel appointment)
    {
        this.appointment = appointment;

        appointmentIDField.setText(String.valueOf(appointment.getAppointmentID()));
        appointmentTitleField.setText(appointment.getTitle());
        appointmentDescriptionField.setText(appointment.getDescription());
        appointmentLocationField.setText(appointment.getLocation());

        Optional<ContactModel> contact = this.contacts.stream().filter(x ->
                x.getContactID() == this.appointment.getContactID()).findFirst();

        ContactModel matchingContact = contact.get();

        appointmentContact.getSelectionModel().select(matchingContact.getContactName());

        appointmentType.getSelectionModel().select(this.appointment.getAppointmentType().toString());

        ZonedDateTime startDate = this.appointment.getStartDate();
        ZonedDateTime endDate = this.appointment.getEndDate();

        appointmentStartDate.setValue(startDate.toLocalDate());

        appointmentStartTime.getSelectionModel().select(AvailableHours.entrySet().stream()
        .filter(x -> x.getValue() == startDate.getHour()).findFirst().get().getKey());

        appointmentEndDate.setValue(endDate.toLocalDate());
        appointmentEndTime.getSelectionModel().select(AvailableHours.entrySet().stream()
        .filter(x -> x.getValue() == endDate.getHour()).findFirst().get().getKey());

        Optional<CustomerModel> customer = this.customers.stream()
                .filter(x -> x.getCustomerID() == this.appointment.getCustomerID()).findFirst();

        CustomerModel matchingCustomer = customer.get();

        appointmentCustomer.getSelectionModel().select(matchingCustomer.getCustomerName());
    }

    public boolean ValidateFormInput()
    {
        if (modifyingAppointment && appointmentIDField.getText().isEmpty())
            return false;

        if (appointmentTitleField.getText().isEmpty())
            return false;

        if (appointmentDescriptionField.getText().isEmpty())
            return false;

        if (appointmentLocationField.getText().isEmpty())
            return false;

        if (appointmentContact.getSelectionModel().isEmpty())
            return false;

        if (appointmentType.getSelectionModel().isEmpty())
            return false;

        if (appointmentStartDate.getEditor().getText().isEmpty())
            return false;

        if (appointmentStartTime.getSelectionModel().isEmpty())
            return false;

        if (appointmentEndDate.getEditor().getText().isEmpty())
            return false;

        if (appointmentEndTime.getSelectionModel().isEmpty())
            return false;

        if (appointmentCustomer.getSelectionModel().isEmpty())
            return false;

        return true;
    }

    public void SaveAppointment(ZonedDateTime startDate, ZonedDateTime endDate) throws Exception
    {
        AppointmentModel appointmentData = new AppointmentModel();

        if (modifyingAppointment)
            appointmentData.setAppointmentID(this.appointment.getAppointmentID());

        appointmentData.setTitle(appointmentTitleField.getText());
        appointmentData.setDescription(appointmentDescriptionField.getText());
        appointmentData.setLocation(appointmentLocationField.getText());

        // Get contact
        Optional<ContactModel> matchingContact = this.contacts.stream().filter(x ->
                x.getContactName().equals(appointmentContact.getSelectionModel().getSelectedItem().toString())).findFirst();
        appointmentData.setContactID(matchingContact.get().getContactID());

        // Get appointment type
        if (appointmentType.getSelectionModel().getSelectedItem().toString().equals(AppointmentType.PLANNING.toString()))
            appointmentData.setAppointmentType(AppointmentType.PLANNING);

        if (appointmentType.getSelectionModel().getSelectedItem().toString().equals(AppointmentType.DEBRIEFING.toString()))
            appointmentData.setAppointmentType(AppointmentType.DEBRIEFING);

        // Get dates
        appointmentData.setStartDate(startDate);
        appointmentData.setEndDate(endDate);

        // Get customer
        Optional<CustomerModel> matchingCustomer = this.customers.stream().filter(x ->
                x.getCustomerName().equals(appointmentCustomer.getSelectionModel().getSelectedItem().toString())).findFirst();

        appointmentData.setCustomerID(matchingCustomer.get().getCustomerID());
        IAppointmentData appointmentDataService = this.dataAccessFactory.GetAppointmentDataService();

        // Default user ID to admin, the user ID was not required on the form, but this is a foreign key constraint.
        appointmentData.setUserID(2);

        if (modifyingAppointment)
            appointmentDataService.UpdateAppointment(appointmentData);

        if (!modifyingAppointment)
            appointmentDataService.CreateAppointment(appointmentData);
    }

    public void handleSaveAppointment(ActionEvent actionEvent) throws Exception
    {
        String titleAndHeader;
        String message;
        ZonedDateTime startDate;
        ZonedDateTime endDate;

        // Validate form input
        if (!ValidateFormInput())
        {
            titleAndHeader = this.localizationService.GetLocalizedMessage("invalidinput", this.locale);
            message = this.localizationService.GetLocalizedMessage("allfields", this.locale);
            this.alertService.ShowAlert(Alert.AlertType.WARNING, titleAndHeader, titleAndHeader, message);
            return;
        }

        startDate = AppointmentZonedDateTime(appointmentStartDate.getValue(),
                AvailableHours.get(appointmentStartTime.getSelectionModel().getSelectedItem().toString()));

        endDate = AppointmentZonedDateTime(appointmentEndDate.getValue(),
                AvailableHours.get(appointmentEndTime.getSelectionModel().getSelectedItem().toString()));

        // Validate appointment time
        if (!(this.validationService.ValidateZoneDateTimeInBusinessHours(startDate) &&
                this.validationService.ValidateZoneDateTimeInBusinessHours(endDate)))
        {
            titleAndHeader = this.localizationService.GetLocalizedMessage("invalidinput", this.locale);
            message = this.localizationService.GetLocalizedMessage("appointmentbusinesshours", this.locale);
            this.alertService.ShowAlert(Alert.AlertType.WARNING, titleAndHeader, titleAndHeader, message);
            return;
        }

        SaveAppointment(startDate,endDate);
        this.mainController.UpdateAppointmentTable();
        handleCancelAppointment(actionEvent);
    }

    public ZonedDateTime AppointmentZonedDateTime(LocalDate localDate, int hour)
    {
        return ZonedDateTime.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(),
                hour, 0, 0, 0, this.zoneId);
    }

    public void handleCancelAppointment(ActionEvent actionEvent)
    {
        Stage stage = (Stage) cancelAppointment.getScene().getWindow();
        stage.close();
    }
}
