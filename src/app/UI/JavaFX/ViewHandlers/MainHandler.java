package app.UI.JavaFX.ViewHandlers;

import UserData.Models.AppointmentModel;
import app.DataLocalization.LocalizationService;
import app.UI.JavaFX.AlertService;
import app.UI.JavaFX.Controllers.MainController;
import app.Util.PropertiesService;
import app.Util.ValidationService;
import com.sun.javafx.scene.control.Properties;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.bytebuddy.asm.Advice;

import java.awt.*;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Locale;

public class MainHandler
{
    private PropertiesService propertiesService;
    private LocalizationService localizationService;
    private DataAccess.DataAccessFactory dataAccessFactory;
    private Locale locale;
    ZoneId zoneId;
    AlertService alertService;
    ValidationService validationService;

    public MainHandler(PropertiesService propertiesService, LocalizationService localizationService,
                       DataAccess.DataAccessFactory dataAccessFactory, Locale locale, ZoneId zoneId,
                       AlertService alertService, ValidationService validationService)
    {
        this.propertiesService = propertiesService;
        this.localizationService = localizationService;
        this.dataAccessFactory = dataAccessFactory;
        this.locale = locale;
        this.zoneId = zoneId;
        this.alertService = alertService;
        this.validationService = validationService;
    }

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
                    this.locale, this.zoneId, this.alertService, this.validationService);

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
