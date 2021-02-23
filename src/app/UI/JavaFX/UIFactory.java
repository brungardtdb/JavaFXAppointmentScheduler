package app.UI.JavaFX;

import app.UI.Enums.UserInterfaceType;
import app.UI.JavaFX.ViewHandlers.LogInHandler;
import javafx.application.Application;

/**
 * Factory class for UI creation.
 */
public class UIFactory
{
    private UserInterfaceType userInterfaceType;
    private String springException = "Spring is not yet implemented!";

    /**
     * Constructor for UI Factory class.
     *
     * @param userInterfaceType An enum specifying the UI type to be created.
     */
    public UIFactory(UserInterfaceType userInterfaceType)
    {
        this.userInterfaceType = userInterfaceType;
    }

    /**
     * Method that handles user interface creation.
     *
     * @param args Args passed in from main method, used to start specified application.
     * @throws Exception Throws exception if unsupported UI type is specified.
     */
    public void GetUserInterface(String[] args) throws Exception
    {
        switch (userInterfaceType)
        {
            case JAVAFX:
                try
                {
                    Application.launch(LogInHandler.class, args);
                }
                catch (Exception ex)
                {
                    throw ex;
                }
                break;
            case SPRING:
                throw new UnsupportedOperationException(springException);
            default:
                throw new UnsupportedOperationException();
        }
    }
}
