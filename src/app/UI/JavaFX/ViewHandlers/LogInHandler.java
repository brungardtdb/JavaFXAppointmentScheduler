package app.UI.JavaFX.ViewHandlers;

import app.DataAccess.DataAccessFactory;
import app.DataAccess.Enums.DataType;
import app.DataLocalization.LocalizationService;
import app.UI.JavaFX.AlertService;
import app.Util.PropertiesService;
import app.UI.JavaFX.Controllers.LoginController;
import app.Util.ValidationService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.bytebuddy.asm.Advice;

import java.time.ZoneId;
import java.util.Locale;
import java.util.Properties;


/**
 * Class for creating the login form.
 */
public class LogInHandler extends Application
{
    PropertiesService propertiesService = new PropertiesService();
    LocalizationService localizationService =  LocalizationService.getInstance();
    DataAccessFactory dataAccessFactory =  new DataAccessFactory(DataType.MYSQL, propertiesService.GetProperties("app.properties"));
//    Locale locale = new Locale("fr"); //used for testing
//    ZoneId zoneId = ZoneId.of("America/Montreal"); // used for testing
    Locale locale = Locale.getDefault();
    ZoneId zoneId = ZoneId.systemDefault();
    AlertService alertService =  new AlertService();
    ValidationService validationService = ValidationService.getInstance();
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
        try
        {
            this.dataAccessFactory.ConnectToDB();
            String loginString = LocalizationService.getInstance().GetLocalizedMessage("loginTitle", this.locale);

            root = (Parent) fxmlLoader.load();
            LoginController controller =  fxmlLoader.getController();
            controller.Initialize(propertiesService, localizationService, dataAccessFactory, locale,
                    zoneId, alertService, validationService);
            stage.setTitle(loginString);
            stage.setScene(new Scene(root, 400, 250));
            stage.show();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
}
