package DataAccessTest;

import UserData.Enums.AppointmentType;
import UserData.Models.AppointmentModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

import DataAccess.Interfaces.IAppointmentData;

public class AppointmentDataServiceTest
{
    @Test
    void GetAppointmentTest() throws Exception
    {

        app.PropertiesService propertiesService = new app.PropertiesService();
        Properties projectProperties = propertiesService.GetProperties();
        DataAccess.DataAccessFactory dataAccessFactory = new DataAccess.DataAccessFactory(DataAccess.Enums.DataType.MYSQL, projectProperties);
        IAppointmentData appointmentDataService;
        AppointmentModel testAppointmentModel;

        try
        {
            dataAccessFactory.ConnectToDB();
            appointmentDataService = dataAccessFactory.GetAppointmentDataService();
            testAppointmentModel = appointmentDataService.GetAppointmentByID(1);
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            dataAccessFactory.DisconnectFromDB();
        }

//        Date startDate = DateFormat.getDateInstance().parse("2020-05-28 12:00:00");
//        Date endDate = DateFormat.getDateInstance().parse("2020-05-28 13:00:00");

        Assertions.assertEquals(testAppointmentModel.GetTitle(), "title");
        Assertions.assertEquals(testAppointmentModel.GetDescription(), "description");
        Assertions.assertEquals(testAppointmentModel.GetLocation(), "location");
        Assertions.assertEquals(testAppointmentModel.GetAppointmentType(), AppointmentType.PLANNING);
//        Assertions.assertEquals(testAppointmentModel.GetStartDate(), startDate);
//        Assertions.assertEquals(testAppointmentModel.GetEndDate(), endDate);
        Assertions.assertEquals(testAppointmentModel.GetCustomerID(), 1);
        Assertions.assertEquals(testAppointmentModel.GetUserID(), 1);
        Assertions.assertEquals(testAppointmentModel.GetContactID(), 3);
    }
}
