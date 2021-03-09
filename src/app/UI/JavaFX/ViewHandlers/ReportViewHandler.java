package app.UI.JavaFX.ViewHandlers;

import app.UI.JavaFX.Controllers.ReportController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ReportViewHandler
{
    private String reportTitle;
    private String reportContent;

    /**
     * Constructor for report view handler.
     *
     * @param reportTitle Title of the report.
     * @param reportContent The report to display.
     */
    public ReportViewHandler(String reportTitle, String reportContent)
    {
        this.reportTitle = reportTitle;
        this.reportContent = reportContent;
    }

    /**
     * Gets the view that will display the report.
     */
    public void GetReportView()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/app/UI/JavaFX/Views/ReportView.fxml"));
            Parent root = null;

            root = (Parent) fxmlLoader.load();
            ReportController reportController = fxmlLoader.getController();
            reportController.Initialize(this.reportTitle, this.reportContent);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle(this.reportTitle);
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
