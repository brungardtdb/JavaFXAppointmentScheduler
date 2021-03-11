package app.UI.JavaFX.ViewHandlers;

import app.DataAccess.DataAccessFactory;
import app.DataLocalization.LocalizationService;
import app.UI.JavaFX.AlertService;
import app.UI.JavaFX.Controllers.MainController;
import app.Util.LoggingService;
import app.Util.PropertiesService;
import app.Util.ReportService;
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
 * Class that handles the creation of main view.
 */
public class MainViewHandler
{
    private PropertiesService propertiesService;
    private LocalizationService localizationService;
    private DataAccessFactory dataAccessFactory;
    private Locale locale;
    private ZoneId zoneId;
    private AlertService alertService;
    private ValidationService validationService;
    private LoggingService loggingService;
    private ReportService reportService;

    /**
     * Class constructor, takes dependencies and passes them to constructor of main view.
     *
     * @param propertiesService PropertiesService dependency.
     * @param localizationService LocalizationService dependency.
     * @param dataAccessFactory DataAccessFactory dependency.
     * @param locale User's locale.
     * @param zoneId User's ZoneId.
     * @param alertService AlertService dependency.
     * @param validationService ValidationService dependency.
     * @param loggingService Application logging utility.
     * @param reportService Application report utility.
     */
    public MainViewHandler(PropertiesService propertiesService, LocalizationService localizationService,
                           DataAccessFactory dataAccessFactory, Locale locale, ZoneId zoneId,
                           AlertService alertService, ValidationService validationService, LoggingService loggingService,
                           ReportService reportService)
    {
        this.propertiesService = propertiesService;
        this.localizationService = localizationService;
        this.dataAccessFactory = dataAccessFactory;
        this.locale = locale;
        this.zoneId = zoneId;
        this.alertService = alertService;
        this.validationService = validationService;
        this.loggingService = loggingService;
        this.reportService = reportService;
    }

    /**
     * Creates main application view.
     */
    public void GetMainView()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/app/UI/JavaFX/Views/MainView.fxml"));
            Parent root = null;

            root = (Parent) fxmlLoader.load();

            // Get controller and configure controller settings
            MainController mainController = fxmlLoader.getController();
            mainController.Initialize(this.propertiesService, this.localizationService, this.dataAccessFactory,
                    this.locale, this.zoneId, this.alertService, this.validationService, this.loggingService, this.reportService);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle(this.localizationService.GetLocalizedMessage("appointmentmanagertitle", this.locale));
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
