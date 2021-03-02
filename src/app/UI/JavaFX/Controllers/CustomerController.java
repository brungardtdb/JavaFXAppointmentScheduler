package app.UI.JavaFX.Controllers;

import app.DataAccess.DataAccessFactory;
import app.DataAccess.Interfaces.ICountryData;
import app.DataAccess.Interfaces.ICustomerData;
import app.DataAccess.Interfaces.IDivisionData;
import app.UserData.Models.CountryModel;
import app.UserData.Models.CustomerModel;
import app.DataLocalization.LocalizationService;
import app.UI.JavaFX.AlertService;
import app.UserData.Models.DivisionModel;
import app.Util.PropertiesService;
import app.Util.ValidationService;
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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerController
{
    private PropertiesService propertiesService;
    private LocalizationService localizationService;
    private DataAccessFactory dataAccessFactory;
    private ICountryData countryDataService;
    private IDivisionData divisionDataService;
    private Locale locale;
    ZoneId zoneId;
    AlertService alertService;
    ValidationService validationService;
    private MainController mainController;
    private boolean modifyingCustomer;
    private CustomerModel customer;
    private boolean isUK;
    List<CountryModel> countries = new ArrayList<CountryModel>();
    List<DivisionModel> divisions = new ArrayList<DivisionModel>();

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
        this.countryDataService = this.dataAccessFactory.GetCountryDataService();
        this.divisionDataService = this.dataAccessFactory.GetDivisionDataService();

        // Set up form
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

        // set up combo boxes
        countries = countryDataService.GetAllCountries();
        divisions = divisionDataService.GetAllDivisions();

        customerCountry.getItems().addAll(countries.stream()
                .map(CountryModel::getCountryName)
                .collect(Collectors.toList()));

        customerCountry.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldCountry, String newCountry)
            {
                if (oldCountry != null)
                {
                    SetDivisonValues(oldCountry);
                }

                if (newCountry != null)
                {
                    SetDivisonValues(newCountry);
                }
            }
        });
    }

    public void GetCustomer(CustomerModel customerModel)
    {
        this.customer = customerModel;
        this.customerIDField.setText(String.valueOf(this.customer.getCustomerID()));
        this.customerNameField.setText(this.customer.getCustomerName());
        this.customerPhoneField.setText(this.customer.getPhoneNumber());
        GetCustomerAddress();
        this.customerZipField.setText(this.customer.getPostalCode());
        int divID = this.customer.getDivisionID();

        Optional<DivisionModel> division = this.divisions.stream()
                .filter(x -> x.getDivisionID() == divID).findFirst();

        DivisionModel currentCustomerDivision = division.get();


        Optional<CountryModel> country = this.countries.stream()
                .filter(x -> x.getCountryID() == currentCustomerDivision.getCountryID()).findFirst();

        CountryModel currentCustomerCountry = country.get();

        customerCountry.getSelectionModel().select(currentCustomerCountry.getCountryName());

        customerDivision.getSelectionModel().select(currentCustomerDivision.getDivision());
    }

    private void GetCustomerAddress()
    {
        String customerAddress = this.customer.getCustomerAddress();
        // Parse address, village, and city from address using comma delimiter
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
    }

    public void SetDivisonValues(String country)
    {
        customerDivision.getItems().clear();
        List<DivisionModel> filteredDivisions;
        switch (country)
        {
            case "U.S":
                isUK = false;
                filteredDivisions = divisions.stream()
                        .filter(x -> x.getCountryID() == 1)
                        .collect(Collectors.toList());

                customerDivision.getItems().addAll(filteredDivisions.stream()
                        .map(x -> x.getDivision())
                        .collect(Collectors.toList()));
                customerVillageField.setDisable(true);
                break;
            case "UK":
                isUK = true;
                filteredDivisions = divisions.stream()
                        .filter(x -> x.getCountryID() == 2)
                        .collect(Collectors.toList());

                customerDivision.getItems().addAll(filteredDivisions.stream()
                        .map(x -> x.getDivision())
                        .collect(Collectors.toList()));
                customerVillageField.setDisable(false);
                break;
            case "Canada":
                isUK = false;
                filteredDivisions = divisions.stream()
                        .filter(x -> x.getCountryID() == 3)
                        .collect(Collectors.toList());

                customerDivision.getItems().addAll(filteredDivisions.stream()
                        .map(x -> x.getDivision())
                        .collect(Collectors.toList()));
                customerVillageField.setDisable(true);
                break;
            default:
                customerDivision.getSelectionModel().clearSelection();
                break;
        }
    }

    private boolean ValidateFormInput()
    {
        if (modifyingCustomer && customerIDField.getText().isEmpty())
            return false;

        if (customerNameField.getText().isEmpty())
            return false;

        if (customerNameField.getText().isEmpty())
            return false;

        if (customerPhoneField.getText().isEmpty())
            return false;

        if (customerAddressField.getText().isEmpty())
            return false;

        if (customerVillageField.getText().isEmpty() && isUK)
            return false;

        if (customerCityField.getText().isEmpty())
            return false;

        if (customerZipField.getText().isEmpty())
            return false;

        if (customerCountry.getSelectionModel().isEmpty())
            return false;

        if (customerDivision.getSelectionModel().isEmpty())
            return false;

        return true;
    }

    public void SaveCustomer() throws Exception {
        CustomerModel customerData = new CustomerModel();

        customerData.setCustomerName(customerNameField.getText());
        customerData.setPhoneNumber(customerPhoneField.getText());

        String village = isUK ? customerVillageField.getText() : "";
        customerData.setCustomerAddress(this.localizationService.FormatAddress(
                customerAddressField.getText(),
                village,
                customerCityField.getText(),
                isUK
        ));

        customerData.setPostalCode(customerZipField.getText());

        Optional<DivisionModel> division = divisions.stream().filter(x ->
                x.getDivision().equals(customerDivision.getSelectionModel().getSelectedItem())).findFirst();

        DivisionModel customerDivision = division.get();

        customerData.setDivisionID(customerDivision.getDivisionID());

        ICustomerData customerDataService = this.dataAccessFactory.GetCustomerDataService();

        if (modifyingCustomer)
        {
            customerData.setCustomerID(this.customer.getCustomerID());
            customerDataService.UpdateCustomer(customerData);
            return;
        }

        customerDataService.CreateCustomer(customerData);
    }

    public void handleSaveCustomer(ActionEvent actionEvent) throws Exception
    {
        if (ValidateFormInput())
        {
            SaveCustomer();
            this.mainController.UpdateCustomerTable();
            handleCancelCustomer(actionEvent);
            return;
        }

        String headerAndTitle = localizationService.GetLocalizedMessage("invalidinput", this.locale);
        String message = localizationService.GetLocalizedMessage("allfields", this.locale);
        this.alertService.ShowAlert(Alert.AlertType.WARNING, headerAndTitle, headerAndTitle, message);

    }

    public void handleCancelCustomer(ActionEvent actionEvent)
    {
        Stage stage = (Stage) cancelCustomer.getScene().getWindow();
        stage.close();
    }
}
