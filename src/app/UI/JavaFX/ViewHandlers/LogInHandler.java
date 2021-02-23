package app.UI.JavaFX.ViewHandlers;

import DataAccess.DataAccessFactory;
import DataAccess.Enums.DataType;
import app.DataLocalization.LocalizationService;
import app.Util.PropertiesService;
import app.UI.JavaFX.Controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Locale;


/**
 * Class for creating the login form.
 */
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

    /**
     * Constructor for login handler class.
     *
     * @throws Exception Java.io.FileNotFoundException.
     */
    public LogInHandler() throws Exception { }

    /**
     * Method for starting application.
     *
     * @param stage The application stage.
     * @throws Exception Java.io.FileNotFoundException.
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        root = (Parent) fxmlLoader.load();
        LoginController controller =  fxmlLoader.getController();
        controller.Initialize(localizationService, dataAccessFactory, locale, zoneID, propertiesService);
        stage.setTitle(loginString);
        stage.setScene(new Scene(root, 400, 250));
        stage.show();
    }
}
