package app.UI.JavaFX.ViewHandlers;

import app.DataAccess.DataAccessFactory;
import app.UserData.Models.CustomerModel;
import app.DataLocalization.LocalizationService;
import app.UI.JavaFX.AlertService;
import app.UI.JavaFX.Controllers.CustomerController;
import app.UI.JavaFX.Controllers.MainController;
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
 * Class that handles the creation of the customer view.
 */
public class CustomerViewHandler
{
    private PropertiesService propertiesService;
    private LocalizationService localizationService;
    private DataAccessFactory dataAccessFactory;
    private Locale locale;
    ZoneId zoneId;
    AlertService alertService;
    ValidationService validationService;
    private MainController mainController;
    private boolean modifyingCustomer;
    private CustomerModel customer;

    /**
     * Class constructor, takes dependencies and passes them to constructor of customer view.
     *
     * @param propertiesService PropertiesService dependency.
     * @param localizationService LocalizationService dependency.
     * @param dataAccessFactory DataAccessFactory dependency.
     * @param locale User's locale.
     * @param zoneId User's ZoneId.
     * @param alertService AlertService dependency.
     * @param validationService ValidationService dependency.
     * @param mainController Controller for main application view.
     * @param modifyingCustomer Indicates if we are modifying an existing customer.
     */
    public CustomerViewHandler(PropertiesService propertiesService, LocalizationService localizationService,
                               DataAccessFactory dataAccessFactory, Locale locale, ZoneId zoneId,
                               AlertService alertService, ValidationService validationService, MainController mainController,
                               boolean modifyingCustomer)
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
    }

    /**
     * Method for passing in existing customer when we are modifying a customer.
     *
     * @param customerModel The customer to be modified.
     */
    public void GetCustomer(CustomerModel customerModel)
    {
        this.customer = customerModel;
    }

    /**
     * Creates customer view.
     */
    public void GetCustomerView()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/app/UI/JavaFX/Views/CustomerView.fxml"));
            Parent root = null;

            root = (Parent) fxmlLoader.load();

            // Get controller and configure controller settings
            CustomerController customerController = fxmlLoader.getController();
            customerController.Initialize(this.propertiesService, this.localizationService, this.dataAccessFactory,
                    this.locale, this.zoneId, this.alertService, this.validationService, this.mainController,
                    this.modifyingCustomer);

            if (modifyingCustomer)
                customerController.GetCustomer(this.customer);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            String message = modifyingCustomer ? "modifycustomer" : "addcustomer";
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
