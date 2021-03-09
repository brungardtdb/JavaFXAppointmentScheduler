package app.UI.JavaFX.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller class for the report view.
 */
public class ReportController
{
    @FXML Label reportTitle;
    @FXML Label reportContent;

    /**
     * Empty constructor for the report controller.
     */
    public ReportController() { }

    /**
     * Method to initialize the form.
     *
     * @param title The title for the report.
     * @param report The report content.
     */
    public void Initialize(String title, String report)
    {
        reportTitle.setText(title);
        reportContent.setText(report);
    }
}
