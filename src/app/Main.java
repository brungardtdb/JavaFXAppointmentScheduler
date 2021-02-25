package app;

import app.UI.Enums.UserInterfaceType;
import app.UI.UIFactory;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        UIFactory uiFactory = new UIFactory(UserInterfaceType.JAVAFX);
        uiFactory.GetUserInterface(args);
    }
}