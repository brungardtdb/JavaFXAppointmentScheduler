package app;

import app.DataAccess.DataAccessFactory;
import app.DataAccess.Enums.DataType;
import app.DataAccess.Interfaces.IAppointmentData;
import app.DataAccess.Interfaces.IContactData;
import app.DataLocalization.LocalizationService;
import app.UI.Enums.UserInterfaceType;
import app.UI.UIFactory;
import app.UserData.Models.AppointmentModel;
import app.UserData.Models.ContactModel;
import app.Util.PropertiesService;
import app.Util.ReportService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

///**
// * Main class for program.
// */
//public class Main
//{
//    /**
//     * Main entry point for program.
//     *
//     * @param args The default parameters for main.
//     * @throws Exception
//     */
//    public static void main(String[] args) throws Exception
//    {
//        UIFactory uiFactory = new UIFactory(UserInterfaceType.JAVAFX);
//        uiFactory.GetUserInterface(args);
//    }
//}

public class Main
{
    public static void main(String[] args) throws Exception
    {
        PropertiesService propertiesService = new PropertiesService();
        Properties projectProperties = propertiesService.GetProperties("app.properties");
        DataAccessFactory dataAccessFactory = new DataAccessFactory(DataType.MYSQL, projectProperties);
        LocalizationService localizationService = LocalizationService.getInstance();
        ReportService reportService = ReportService.getInstance();
        IAppointmentData appointmentDataService;
        IContactData contactDataService;
        List<AppointmentModel> allAppointments;
        List<ContactModel> allContacts;

        try
        {
            dataAccessFactory.ConnectToDB();
            appointmentDataService = dataAccessFactory.GetAppointmentDataService();
            contactDataService = dataAccessFactory.GetContactDataService();
            allAppointments = appointmentDataService.GetAllAppointments();
            allContacts = contactDataService.GetAllContacts();
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            dataAccessFactory.DisconnectFromDB();
        }

//        String report = reportService.AppointmentsByTypeAndMonth(allAppointments);
        String report = reportService.GetContactSchedules(allAppointments, allContacts, localizationService, Locale.getDefault());

        System.out.println(report);

    }
}