package app.UI.JavaFX.Controllers;

import DataAccess.DataAccessFactory;
import UserData.Models.CustomerModel;
import app.DataLocalization.LocalizationService;
import app.UI.JavaFX.AlertService;
import app.Util.PropertiesService;
import app.Util.ValidationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.time.ZoneId;
import java.util.Locale;

public class CustomerController
{
    private PropertiesService propertiesService;
    private LocalizationService localizationService;
    private DataAccess.DataAccessFactory dataAccessFactory;
    private Locale locale;
    ZoneId zoneId;
    AlertService alertService;
    ValidationService validationService;
    private MainController mainController;
    private boolean modifyingCustomer;
    private CustomerModel customer;
    @FXML Label customerLabel;
    @FXML Label customerIDLabel;
    @FXML Label customerNameLabel;
    @FXML Label customerPhoneLabel;
    @FXML Label customerZipLabel;
    @FXML Label customerAddressLabel;
    @FXML Label customerVillageLabel;
    @FXML Label customerCityLabel;
    @FXML Label customerCountryLabel;
    @FXML Label customerDivisionLabel;
    @FXML ComboBox customerCountry;
    @FXML ComboBox customerDivision;
    @FXML Button saveCustomer;
    @FXML Button cancelCustomer;
    @FXML TextField customerIDField;
    @FXML TextField customerNameField;
    @FXML TextField customerPhoneField;
    @FXML TextField customerZipField;
    @FXML TextField customerAddressField;
    @FXML TextField customerVillageField;
    @FXML TextField customerCityField;

    public CustomerController() { }

    public void Initialize(PropertiesService propertiesService, LocalizationService localizationService,
                           DataAccessFactory dataAccessFactory, Locale locale, ZoneId zoneId,
                           AlertService alertService, ValidationService validationService, MainController mainController,
                           boolean modifyingCustomer) throws Exception
    {
        this.propertiesService = propertiesService;
        this.localizationService = localizationService;
        this.dataAccessFactory = dataAccessFactory;
        this.locale = locale;
        this.zoneId = zoneId;
        this.alertService = alertService;
        this.validationService = validationService;
        this.mainController = mainController;
        this.modifyingCustomer = modifyingCustomer;

        String message = modifyingCustomer ? "modifycustomer" : "addcustomer";
        customerLabel.setText(this.localizationService.GetLocalizedMessage(message, this.locale));
        customerIDLabel.setText(this.localizationService.GetLocalizedMessage("ID", this.locale));
        customerNameLabel.setText(this.localizationService.GetLocalizedMessage("name", this.locale));
        customerAddressLabel.setText(this.localizationService.GetLocalizedMessage("address", this.locale));
        customerVillageLabel.setText(this.localizationService.GetLocalizedMessage("village", this.locale));
        customerCityLabel.setText(this.localizationService.GetLocalizedMessage("city", this.locale));
        customerZipLabel.setText(this.localizationService.GetLocalizedMessage("zip", this.locale));
        customerPhoneLabel.setText(this.localizationService.GetLocalizedMessage("phone", this.locale));
        customerCountryLabel.setText(this.localizationService.GetLocalizedMessage("selectcountry", this.locale));
        customerDivisionLabel.setText(this.localizationService.GetLocalizedMessage("selectdivision", this.locale));
        customerCountry.setPromptText(this.localizationService.GetLocalizedMessage("country", this.locale));
        customerDivision.setPromptText(this.localizationService.GetLocalizedMessage("div", this.locale));
        saveCustomer.setText(this.localizationService.GetLocalizedMessage("save", this.locale));
        cancelCustomer.setText(this.localizationService.GetLocalizedMessage("cancel", this.locale));
    }

    public void GetCustomer(CustomerModel customerModel)
    {
        this.customer = customerModel;
        this.customerIDField.setText(String.valueOf(this.customer.getCustomerID()));
        this.customerNameField.setText(this.customer.getCustomerName());
        this.customerPhoneField.setText(this.customer.getPhoneNumber());

        String customerAddress = this.customer.getCustomerAddress();
        if (customerAddress != null && !customerAddress.isEmpty())
        {
            String[] address = customerAddress.split(",", 3);
            switch (address.length)
            {
                case 3:
                    this.customerAddressField.setText(address[0]);
                    this.customerVillageField.setText(address[1]);
                    this.customerCityField.setText(address[3]);
                    break;
                case 2:
                    this.customerAddressField.setText(address[0]);
                    this.customerCityField.setText(address[1]);
                    break;
                default:
                    this.customerAddressField.setText(address[0]);
            }
        }

        this.customerZipField.setText(this.customer.getPostalCode());

    }

    public void saveCustomerData()
    {

    }

    public void handleSaveCustomer(ActionEvent actionEvent)
    {
        this.mainController.UpdateCustomerTable();
    }

    public void handleCancelCustomer(ActionEvent actionEvent)
    {
        Stage stage = (Stage) cancelCustomer.getScene().getWindow();
        stage.close();
    }
}
