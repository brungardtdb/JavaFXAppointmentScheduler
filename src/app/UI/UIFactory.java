package app.UI;

import app.UI.Enums.UserInterfaceType;
import app.UI.JavaFX.ViewHandlers.LogInViewHandler;
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
     * Shout out to this post on StackOverflow for showing me how to launch the application from a
     * factory method outside of main: https://stackoverflow.com/questions/34597594/javafx-starting-application-from-different-class
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
                    Application.launch(LogInViewHandler.class, args);
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
