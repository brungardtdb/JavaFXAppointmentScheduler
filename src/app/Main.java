package app;

import app.UI.Enums.UserInterfaceType;
import app.UI.UIFactory;

/**
 * Main class for program.
 */
public class Main
{
    /**
     * Main entry point for program.
     *
     * @param args The default parameters for main.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        UIFactory uiFactory = new UIFactory(UserInterfaceType.JAVAFX);
        uiFactory.GetUserInterface(args);
    }
}