package app.UI.JavaFX.Controllers;

import app.UI.JavaFX.ViewHandlers.AppointmentViewHandler;
import app.UserData.Models.AppointmentModel;
import app.UserData.Models.CustomerModel;
import app.DataAccess.DataAccessFactory;
import app.DataAccess.Interfaces.IAppointmentData;
import app.DataAccess.Interfaces.ICustomerData;
import app.DataLocalization.LocalizationService;
import app.UI.JavaFX.AlertService;
import app.UI.JavaFX.ViewHandlers.CustomerViewHandler;
import app.Util.PropertiesService;
import app.Util.ValidationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Controller class for main application form.
 */
public class MainController
{
    private PropertiesService propertiesService;
    private LocalizationService localizationService;
    private DataAccessFactory dataAccessFactory;
    private Locale locale;
    ZoneId zoneId;
    AlertService alertService;
    ValidationService validationService;
    boolean allAppointments = true;
    boolean weeklyAppointments = false;
    boolean monthlyAppointments = false;

    @FXML Label formLabel;
    @FXML Label customerLabel;
    @FXML Button addCustomer;
    @FXML Button modifyCustomer;
    @FXML Button deleteCustomer;
    @FXML Label appointmentLabel;
    @FXML Button addAppointment;
    @FXML Button modifyAppointment;
    @FXML Button deleteAppointment;
    @FXML Button exitForm;
    @FXML RadioButton sortByAll;
    @FXML RadioButton sortByWeek;
    @FXML RadioButton sortByMonth;
    @FXML TableView customerTable;
    @FXML TableColumn customerIDColumn;
    @FXML TableColumn customerNameColumn;
    @FXML TableColumn customerAddressColumn;
    @FXML TableColumn customerZipCode;
    @FXML TableColumn customerPhoneNumber;
    @FXML TableColumn customerDivision;
    @FXML TableView appointmentTable;
    @FXML TableColumn appointmentIDColumn;
    @FXML TableColumn appointmentTitleColumn;
    @FXML TableColumn appointmentDescriptionColumn;
    @FXML TableColumn appointmentLocationColumn;
    @FXML TableColumn appointmentContactColumn;
    @FXML TableColumn appointmentTypeColumn;
    @FXML TableColumn appointmentStartColumn;
    @FXML TableColumn appointmentEndColumn;
    @FXML TableColumn appointmentCustomerIDColumn;

    /**
     * Empty constructor for main form controller.
     */
    public MainController() { }

    /**
     * Method for initializing form.
     *
     * @param propertiesService PropertiesService dependency.
     * @param localizationService LocalizationService dependency.
     * @param dataAccessFactory DataAccessFactory dependency.
     * @param locale User's locale.
     * @param zoneId User's ZoneId.
     * @param alertService AlertService dependency.
     * @param validationService ValidationService dependency.
     * @throws Exception
     */
    public void Initialize(PropertiesService propertiesService, LocalizationService localizationService,
                           DataAccessFactory dataAccessFactory, Locale locale, ZoneId zoneId,
                           AlertService alertService, ValidationService validationService) throws Exception
    {
        this.propertiesService = propertiesService;
        this.localizationService = localizationService;
        this.dataAccessFactory = dataAccessFactory;
        this.locale = locale;
        this.zoneId = zoneId;
        this.alertService = alertService;
        this.validationService = validationService;

        // Set up form
        formLabel.setText(this.localizationService.GetLocalizedMessage("appointmentmanagertitle", this.locale));
        customerLabel.setText(this.localizationService.GetLocalizedMessage("customers", this.locale));
        appointmentLabel.setText(this.localizationService.GetLocalizedMessage("appointments", this.locale));
        addCustomer.setText(this.localizationService.GetLocalizedMessage("add", this.locale));
        addAppointment.setText(this.localizationService.GetLocalizedMessage("add", this.locale));
        modifyCustomer.setText(this.localizationService.GetLocalizedMessage("modify", this.locale));
        modifyAppointment.setText(this.localizationService.GetLocalizedMessage("modify", this.locale));
        deleteCustomer.setText(this.localizationService.GetLocalizedMessage("delete", this.locale));
        deleteAppointment.setText(this.localizationService.GetLocalizedMessage("delete", this.locale));
        exitForm.setText(this.localizationService.GetLocalizedMessage("exit", this.locale));
        sortByAll.setText(this.localizationService.GetLocalizedMessage("all", this.locale));
        sortByWeek.setText(this.localizationService.GetLocalizedMessage("weekly", this.locale));
        sortByMonth.setText(this.localizationService.GetLocalizedMessage("monthly", this.locale));

        // Set up customer table
        customerIDColumn.setText(this.localizationService.GetLocalizedMessage("ID", this.locale));
        customerNameColumn.setText(this.localizationService.GetLocalizedMessage("name", this.locale));
        customerAddressColumn.setText(this.localizationService.GetLocalizedMessage("address", this.locale));
        customerZipCode.setText(this.localizationService.GetLocalizedMessage("zip", this.locale));
        customerPhoneNumber.setText(this.localizationService.GetLocalizedMessage("phone", this.locale));
        customerDivision.setText(this.localizationService.GetLocalizedMessage("div", this.locale));

        // Set up address table
        appointmentIDColumn.setText(this.localizationService.GetLocalizedMessage("ID", this.locale));
        appointmentTitleColumn.setText(this.localizationService.GetLocalizedMessage("title", this.locale));
        appointmentDescriptionColumn.setText(this.localizationService.GetLocalizedMessage("description", this.locale));
        appointmentLocationColumn.setText(this.localizationService.GetLocalizedMessage("location", this.locale));
        appointmentContactColumn.setText(this.localizationService.GetLocalizedMessage("contact", this.locale));
        appointmentTypeColumn.setText(this.localizationService.GetLocalizedMessage("type", this.locale));
        appointmentStartColumn.setText(this.localizationService.GetLocalizedMessage("start", this.locale));
        appointmentEndColumn.setText(this.localizationService.GetLocalizedMessage("end", this.locale));
        appointmentCustomerIDColumn.setText(this.localizationService.GetLocalizedMessage("customerID", this.locale));

        // Update customer and appointment tables
        UpdateTables();
    }

    /**
     * Method for adding a customer, opens the customer form.
     *
     * @param actionEvent The "Add" button click event for customer.
     */
    public void handleAddCustomer(ActionEvent actionEvent)
    {
        CustomerViewHandler customerViewHandler = new CustomerViewHandler(this.propertiesService, this.localizationService,
                this.dataAccessFactory, this.locale, this.zoneId, this.alertService, this.validationService,
                this, false);
        customerViewHandler.GetCustomerView();
    }

    /**
     * Method for modifying a customer, opens the customer form.
     *
     * @param actionEvent The "Modify" button click event for customer.
     * @throws Exception Java.io.FileNotFoundException.
     */
    public void handleModifyCustomer(ActionEvent actionEvent) throws Exception
    {
        CustomerModel customer = GetCustomerToModify();
        if (customer != null)
        {
            CustomerViewHandler customerViewHandler = new CustomerViewHandler(this.propertiesService, this.localizationService,
                    this.dataAccessFactory, this.locale, this.zoneId, this.alertService, this.validationService,
                    this, true);
            customerViewHandler.GetCustomer(customer);
            customerViewHandler.GetCustomerView();
            return;
        }

        // Display warning if no customer was selected
        String titleAndHeader = localizationService.GetLocalizedMessage("invalidselection", this.locale);
        String body = localizationService.GetLocalizedMessage("pleaseselectcustomer", this.locale);
        this.alertService.ShowAlert(Alert.AlertType.WARNING,titleAndHeader, titleAndHeader, body);
    }

    /**
     * Method for deleting a customer.
     *
     * @param actionEvent The "Delete" button click event for customer.
     * @throws Exception Java.io.FileNotFoundException.
     */
    public void handleDeleteCustomer(ActionEvent actionEvent) throws Exception
    {
        String titleAndHeader = this.localizationService.GetLocalizedMessage("deletecustomer", this.locale);
        String body = this.localizationService.GetLocalizedMessage("confirmdeletecustomer", this.locale);
        ICustomerData customerDataService = this.dataAccessFactory.GetCustomerDataService();

        if (alertService.ShowConfirmation(titleAndHeader, titleAndHeader, body))
        {
            CustomerModel customerToDelete = GetCustomerToModify();
            if (customerToDelete == null)
            {
                // Display warning if no customer was selected
                titleAndHeader = localizationService.GetLocalizedMessage("invalidselection", this.locale);
                body = localizationService.GetLocalizedMessage("pleaseselectcustomer", this.locale);
                this.alertService.ShowAlert(Alert.AlertType.WARNING,titleAndHeader, titleAndHeader, body);
                return;
            }

            if (customerDataService.DeleteCustomerByID(customerToDelete.getCustomerID()))
            {
                String deleteMessage = this.localizationService.GetLocalizedMessage("customerdeleteconfirmation", this.locale)
                        + "\n" + customerToDelete.getCustomerName();
                alertService.ShowAlert(Alert.AlertType.INFORMATION, titleAndHeader, titleAndHeader, deleteMessage);
                UpdateCustomerTable();
                UpdateAppointmentTable();
                return;
            }
        }
    }

    /**
     * Method for retrieving selected customer from table.
     *
     * @return Customer if one is selected, otherwise null.
     */
    private CustomerModel GetCustomerToModify()
    {
        if (customerTable.getSelectionModel().getSelectedItem() != null)
        {
            return ((CustomerModel) customerTable.getSelectionModel().getSelectedItem());
        }
        return null;
    }

    public void handleAddAppointment(ActionEvent actionEvent)
    {
        AppointmentViewHandler appointmentViewHandler = new AppointmentViewHandler(this.propertiesService, this.localizationService,
                this.dataAccessFactory, this.locale, this.zoneId, this.alertService, this.validationService, this,  false);
        appointmentViewHandler.GetAppointmentView();
    }

    public void handleModifyAppointment(ActionEvent actionEvent) throws Exception
    {
        AppointmentModel appointment = GetAppointmentToModify();
        if (appointment != null)
        {
            AppointmentViewHandler appointmentViewHandler = new AppointmentViewHandler(this.propertiesService, this.localizationService,
                    this.dataAccessFactory, this.locale, this.zoneId, this.alertService, this.validationService, this,  true);
            appointmentViewHandler.GetAppointment(appointment);
            appointmentViewHandler.GetAppointmentView();
            return;
        }

        // Display warning if no appointment was selected
        String titleAndHeader = localizationService.GetLocalizedMessage("invalidselection", this.locale);
        String body = localizationService.GetLocalizedMessage("pleaseselectappointment", this.locale);
        this.alertService.ShowAlert(Alert.AlertType.WARNING,titleAndHeader, titleAndHeader, body);
    }

    public void handleDeleteAppointment(ActionEvent actionEvent) throws Exception
    {
        String titleAndHeader = this.localizationService.GetLocalizedMessage("deleteappointment", this.locale);
        String body = this.localizationService.GetLocalizedMessage("confirmdeleteappointment", this.locale);
        IAppointmentData appointmentDataService = this.dataAccessFactory.GetAppointmentDataService();

        if (alertService.ShowConfirmation(titleAndHeader, titleAndHeader, body))
        {
            AppointmentModel appointmentToDelete = GetAppointmentToModify();
            if (appointmentToDelete == null)
            {
                // Display warning if no appointment was selected.
                titleAndHeader = localizationService.GetLocalizedMessage("invalidselection", this.locale);
                body = localizationService.GetLocalizedMessage("pleaseselectappointment", this.locale);
                this.alertService.ShowAlert(Alert.AlertType.WARNING, titleAndHeader, titleAndHeader, body);
                return;
            }

            if (appointmentDataService.DeleteAppointmentByID(appointmentToDelete.getAppointmentID()))
            {
                String deleteMessage = this.localizationService.GetLocalizedMessage("appointmentdeleteconfirmation", this.locale)
                        + "\n" + appointmentToDelete.getAppointmentID() + " " + appointmentToDelete.getAppointmentType();
                alertService.ShowAlert(Alert.AlertType.INFORMATION, titleAndHeader, titleAndHeader, deleteMessage);
                UpdateAppointmentTable();
                return;
            }
        }
    }

    /**
     * Method for retrieving selected appointment from table.
     *
     * @return Appointment if one is selected, otherwise null.
     */
    private AppointmentModel GetAppointmentToModify()
    {
        if (appointmentTable.getSelectionModel().getSelectedItem() != null)
        {
            return ((AppointmentModel) appointmentTable.getSelectionModel().getSelectedItem());
        }
        return null;
    }

    /**
     * Re-loads tables on the main form.
     */
    public void UpdateTables()
    {
        UpdateCustomerTable();
        UpdateAppointmentTable();
    }

    /**
     * Re-loads the customer table on the main form.
     */
    public void UpdateCustomerTable()
    {
        try
        {
            ICustomerData customerData = dataAccessFactory.GetCustomerDataService();
            ArrayList<CustomerModel> customerModelArrayList = (ArrayList<CustomerModel>) customerData.GetAllCustomers();
            ObservableList<CustomerModel> customers = FXCollections.observableArrayList(customerModelArrayList);

            customerIDColumn.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("customerID"));
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("customerName"));
            customerAddressColumn.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("customerAddress"));
            customerZipCode.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("postalCode"));
            customerPhoneNumber.setCellValueFactory(new PropertyValueFactory<CustomerModel, String >("phoneNumber"));
            customerDivision.setCellValueFactory(new PropertyValueFactory<CustomerModel, String>("divisionID"));

            customerTable.setItems(customers);
            customerTable.getSelectionModel().clearSelection();
            customerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            customerTable.refresh();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Re-loads the appointment table on the main form.
     */
    public void UpdateAppointmentTable()
    {        
        try 
        {
            IAppointmentData appointmentData = dataAccessFactory.GetAppointmentDataService();
            ArrayList<AppointmentModel> appointmentModelArrayList = (ArrayList<AppointmentModel>) appointmentData.GetAllAppointments();
            ArrayList<AppointmentModel> updatedAppointments = new ArrayList<AppointmentModel>();

            if (allAppointments)
                updatedAppointments = LocalizeAppointmentDates(appointmentModelArrayList);
            if (weeklyAppointments)
                updatedAppointments = FilterAppointmentsByWeek(LocalizeAppointmentDates(appointmentModelArrayList));
            if (monthlyAppointments)
                updatedAppointments = FilterAppointmentsByMonth(LocalizeAppointmentDates(appointmentModelArrayList));

            ObservableList<AppointmentModel> appointments = FXCollections.observableArrayList(updatedAppointments);

            appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("appointmentID"));
            appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("title"));
            appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("description"));
            appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("location"));
            appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("contactID"));
            appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("appointmentType"));
            appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("startDate"));
            appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("endDate"));
            appointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<AppointmentModel, String>("customerID"));

            appointmentTable.setItems(appointments);
            appointmentTable.getSelectionModel().clearSelection();
            appointmentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            appointmentTable.refresh();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
       
    }

    /**
     * Localizes appointment dates from database from UTC to local time zone.
     *
     * @param appointments Appointments from database.
     * @return Returns appointments with updated start and end dates.
     */
    private ArrayList<AppointmentModel> LocalizeAppointmentDates(ArrayList<AppointmentModel> appointments)
    {
        for (AppointmentModel appointment: appointments)
        {
            ZonedDateTime start = appointment.getStartDate();
            ZonedDateTime end = appointment.getEndDate();
            appointment.setStartDate(this.localizationService.ZonedDateTimeToLocal(start, this.zoneId));
            appointment.setEndDate(this.localizationService.ZonedDateTimeToLocal(end, this.zoneId));
        }
        return appointments;
    }

    /**
     * Filters out appointments that aren't in the current week.
     *
     * I decided to use a stream and lambda expression here to make things
     * a bit more concise, allowing me to return the filtered list in a single statement.
     *
     * @param appointments Appointments from database.
     * @return All appointments in the current week.
     */
    private ArrayList<AppointmentModel> FilterAppointmentsByWeek(ArrayList<AppointmentModel> appointments)
    {
        return (ArrayList<AppointmentModel>) appointments.stream().filter(
                x -> this.validationService.ValidateZoneDateTimeInThisWeek(x.getStartDate())
                && this.validationService.ValidateZoneDateTimeInThisWeek(x.getEndDate())).collect(Collectors.toList());
    }

    /**
     * Filters out appointments that aren't in the current month.
     *
     * I decided to use a stream and lambda expression here to make things
     * a bit more concise, allowing me to return the filtered list in a single statement.
     *
     * @param appointments
     * @return
     */
    private ArrayList<AppointmentModel> FilterAppointmentsByMonth(ArrayList<AppointmentModel> appointments)
    {
        return (ArrayList<AppointmentModel>) appointments.stream().filter(
                x -> this.validationService.ValidateZoneDateTimeInThisMonth(x.getStartDate())
                && this.validationService.ValidateZoneDateTimeInThisMonth(x.getEndDate())).collect(Collectors.toList());
    }

    /**
     * RadioButton event to display all appointments.
     *
     * @param actionEvent Selecting the "All" RadioButton on the main form.
     */
    public void allChecked(ActionEvent actionEvent)
    {
        allAppointments = true;
        weeklyAppointments = false;
        monthlyAppointments = false;

        UpdateAppointmentTable();
    }

    /**
     * RadioButton event to display all appointments in the current week.
     *
     * @param actionEvent Selecting the "By Week" RadioButton on the main form.
     */
    public void weeklyChecked(ActionEvent actionEvent)
    {
        allAppointments = false;
        weeklyAppointments = true;
        monthlyAppointments = false;

        UpdateAppointmentTable();
    }

    /**
     * RadioButton event to display all appointments in the current month.
     *
     * @param actionEvent Selecting the "By Month" RadioButton on the main form.
     */
    public void monthlyChecked(ActionEvent actionEvent)
    {
        allAppointments = false;
        weeklyAppointments = false;
        monthlyAppointments = true;

        UpdateAppointmentTable();
    }

    /**
     * Clears the table selection when the main form is clicked.
     *
     * @param mouseEvent Selecting the main form.
     */
    public void mainFormClicked(MouseEvent mouseEvent)
    {
        customerTable.getSelectionModel().clearSelection();
        appointmentTable.getSelectionModel().clearSelection();
    }

    /**
     * Closes the application.
     *
     * @param actionEvent The user selects the exit button.
     */
    @FXML void handleExitForm(ActionEvent actionEvent)
    {
        Stage stage = (Stage) exitForm.getScene().getWindow();
        stage.close();
    }
}
