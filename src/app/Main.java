package app;

import app.UI.JavaFX.LogInHandler;
import javafx.application.Application;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        try
        {
            Application.launch(LogInHandler.class, args);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
}