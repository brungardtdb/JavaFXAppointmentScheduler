package app.UI.JavaFX;

import DataAccess.DataAccessFactory;
import DataAccess.Enums.DataType;
import app.DataLocalization.LocalizationService;
import app.PropertiesService;
import app.UI.JavaFX.Controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Locale;


public class LogInHandler extends Application
{
    private final PropertiesService propertiesService = new PropertiesService();
    private final LocalizationService localizationService = LocalizationService.getInstance();
    private final DataAccess.DataAccessFactory dataAccessFactory = new DataAccessFactory(DataType.MYSQL, propertiesService.GetProperties("app.properties"));
    Locale locale = new Locale("fr"); //used for testing
    ZoneId zoneID = ZoneId.of("America/Montreal");
//    Locale locale = Locale.getDefault();
//    ZoneId zoneID = ZoneId.systemDefault();
    String loginString = LocalizationService.getInstance().GetLocalizedMessage("loginTitle", locale);
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/app/UI/JavaFX/Views/LoginView.fxml"));
    Parent root = null;

    public LogInHandler() throws Exception { }

    @Override
    public void start(Stage stage) throws Exception
    {
        //Parent root = FXMLLoader.load(getClass().getResource("/app/UI/JavaFX/Views/LoginView.fxml"));
        root = (Parent) fxmlLoader.load();
        LoginController controller =  fxmlLoader.getController();
        controller.SetLocalization(localizationService);
        controller.SetDataAccessFactory(dataAccessFactory);
        controller.SetLocale(locale);
        controller.SetZoneID(zoneID);
        controller.SetPropertiesService(propertiesService);
        controller.Initialize();
        stage.setTitle(loginString);
        stage.setScene(new Scene(root, 400, 250));
        stage.show();
    }
}
