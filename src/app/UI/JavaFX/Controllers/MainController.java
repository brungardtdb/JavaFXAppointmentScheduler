package app.UI.JavaFX.Controllers;

import app.DataLocalization.LocalizationService;
import app.UI.JavaFX.AlertService;
import app.Util.PropertiesService;
import app.Util.ValidationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.ZoneId;
import java.util.Locale;

public class MainController
{
    private PropertiesService propertiesService;
    private LocalizationService localizationService;
    private DataAccess.DataAccessFactory dataAccessFactory;
    private Locale locale;
    ZoneId zoneId;
    AlertService alertService;
    ValidationService validationService;

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

    public MainController() { }

    public void Initialize(PropertiesService propertiesService, LocalizationService localizationService,
                           DataAccess.DataAccessFactory dataAccessFactory, Locale locale, ZoneId zoneId,
                           AlertService alertService, ValidationService validationService) throws Exception
    {
        this.propertiesService = propertiesService;
        this.localizationService = localizationService;
        this.dataAccessFactory = dataAccessFactory;
        this.locale = locale;
        this.zoneId = zoneId;
        this.alertService = alertService;
        this.validationService = validationService;
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
        sortByWeek.setText(this.localizationService.GetLocalizedMessage("weekly", this.locale));
        sortByMonth.setText(this.localizationService.GetLocalizedMessage("monthly", this.locale));
    }

    public void handleAddCustomer(ActionEvent actionEvent)
    {

    }

    public void handleModifyCustomer(ActionEvent actionEvent)
    {

    }

    public void handleDeleteCustomer(ActionEvent actionEvent)
    {

    }

    public void handleExitForm(ActionEvent actionEvent)
    {

    }

    public void handleAddAppointment(ActionEvent actionEvent)
    {

    }

    public void handleModifyAppointment(ActionEvent actionEvent)
    {

    }

    public void handleDeleteAppointment(ActionEvent actionEvent)
    {

    }

    public void weeklyChecked(ActionEvent actionEvent)
    {

    }

    public void monthlyChecked(ActionEvent actionEvent)
    {

    }

    public void mainFormClicked(MouseEvent mouseEvent)
    {

    }
}
