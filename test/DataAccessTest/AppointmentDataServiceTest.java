package DataAccessTest;

import UserData.Enums.AppointmentType;
import UserData.Models.AppointmentModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import DataAccess.Interfaces.IAppointmentData;

public class AppointmentDataServiceTest
{
    @Test
    void GetAppointmentTest() throws Exception
    {
        app.PropertiesService propertiesService = new app.PropertiesService();
        Properties projectProperties = propertiesService.GetProperties("app.properties");
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

        ZonedDateTime startDate = ZonedDateTime.of(2020, 05, 28,12,0,0,0, ZoneId.of("UTC"));
        ZonedDateTime endDate = ZonedDateTime.of(2020, 05, 28,13,0,0,0, ZoneId.of("UTC"));
        ZonedDateTime otherDate = ZonedDateTime.of(2020, 07, 24,12,0,0,0, ZoneId.of("UTC"));

        Assertions.assertEquals(testAppointmentModel.GetTitle(), "title");
        Assertions.assertEquals(testAppointmentModel.GetDescription(), "description");
        Assertions.assertEquals(testAppointmentModel.GetLocation(), "location");
        Assertions.assertEquals(testAppointmentModel.GetAppointmentType(), AppointmentType.PLANNING);
        Assertions.assertEquals(testAppointmentModel.GetStartDate(), startDate);
        Assertions.assertEquals(testAppointmentModel.GetEndDate(), endDate);
        Assertions.assertEquals(testAppointmentModel.GetCustomerID(), 1);
        Assertions.assertEquals(testAppointmentModel.GetUserID(), 1);
        Assertions.assertEquals(testAppointmentModel.GetContactID(), 3);
    }

    @Test
    void CreateDeleteAppointmentTest() throws Exception
    {
        app.PropertiesService propertiesService = new app.PropertiesService();
        Properties projectProperties = propertiesService.GetProperties("app.properties");
        DataAccess.DataAccessFactory dataAccessFactory = new DataAccess.DataAccessFactory(DataAccess.Enums.DataType.MYSQL, projectProperties);
        IAppointmentData appointmentDataService;
        ZonedDateTime startDate = ZonedDateTime.of(2020, 05, 28,13,0,0,0, ZoneId.of("UTC"));
        ZonedDateTime endDate = ZonedDateTime.of(2020, 07, 24,12,0,0,0, ZoneId.of("UTC"));
        int createResult;
        boolean deleteResult;
        List<AppointmentModel> appointments;

        AppointmentModel testAppointmentModel = new AppointmentModel();
        testAppointmentModel.SetTitle("Test Appointment");
        testAppointmentModel.SetDescription("Testing Testing 123...");
        testAppointmentModel.SetAppointmentType(AppointmentType.DEBRIEFING);
        testAppointmentModel.SetStartDate(startDate);
        testAppointmentModel.SetEndDate(endDate);
        testAppointmentModel.SetContactID(3);
        testAppointmentModel.SetCustomerID(1);
        testAppointmentModel.SetUserID(1);
        testAppointmentModel.SetLocation("233 S Wacker Dr, Chicago, IL");

        try
        {
            dataAccessFactory.ConnectToDB();
            appointmentDataService = dataAccessFactory.GetAppointmentDataService();
            createResult = appointmentDataService.CreateAppointment(testAppointmentModel);
            deleteResult = appointmentDataService.DeleteAppointmentByID(createResult);
            appointments = appointmentDataService.GetAllCustomerAppointments(1);
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            dataAccessFactory.DisconnectFromDB();
        }

        Assertions.assertEquals(createResult, 3);
        Assertions.assertTrue(deleteResult);
        Assertions.assertEquals(appointments.size(), 1);
    }

    @Test
    void UpdateAppointmentTest() throws Exception
    {
        app.PropertiesService propertiesService = new app.PropertiesService();
        Properties projectProperties = propertiesService.GetProperties("app.properties");
        DataAccess.DataAccessFactory dataAccessFactory = new DataAccess.DataAccessFactory(DataAccess.Enums.DataType.MYSQL, projectProperties);
        IAppointmentData appointmentDataService;
        AppointmentModel testAppointmentModel;
        AppointmentModel secondTestAppointmentModel;
        boolean updateResult;

        try
        {
            dataAccessFactory.ConnectToDB();
            appointmentDataService = dataAccessFactory.GetAppointmentDataService();
            testAppointmentModel = appointmentDataService.GetAppointmentByID(1);
            testAppointmentModel.SetAppointmentType(AppointmentType.DEBRIEFING);
            updateResult = appointmentDataService.UpdateAppointment(testAppointmentModel);
            secondTestAppointmentModel = appointmentDataService.GetAppointmentByID(1);
            testAppointmentModel.SetAppointmentType(AppointmentType.PLANNING);
            appointmentDataService.UpdateAppointment(testAppointmentModel);
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            dataAccessFactory.DisconnectFromDB();
        }

        Assertions.assertEquals(secondTestAppointmentModel.GetAppointmentType(), AppointmentType.DEBRIEFING);
        Assertions.assertTrue(updateResult);
    }

    @Test
    void GetDeleteAllCustomerAppointmentsTest() throws Exception
    {
        app.PropertiesService propertiesService = new app.PropertiesService();
        Properties projectProperties = propertiesService.GetProperties("app.properties");
        DataAccess.DataAccessFactory dataAccessFactory = new DataAccess.DataAccessFactory(DataAccess.Enums.DataType.MYSQL, projectProperties);
        IAppointmentData appointmentDataService;
        AppointmentModel testAppointmentModel;
        List<AppointmentModel> customerAppointments;
        int deletedAppointments;

        try
        {
            dataAccessFactory.ConnectToDB();
            appointmentDataService = dataAccessFactory.GetAppointmentDataService();
            testAppointmentModel = appointmentDataService.GetAppointmentByID(1);
            testAppointmentModel.SetCustomerID(3);
            appointmentDataService.CreateAppointment(testAppointmentModel);
            appointmentDataService.CreateAppointment(testAppointmentModel);
            appointmentDataService.CreateAppointment(testAppointmentModel);
            customerAppointments = appointmentDataService.GetAllCustomerAppointments(3);
            deletedAppointments = appointmentDataService.DeleteAllCustomerAppointments(3);
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            dataAccessFactory.DisconnectFromDB();
        }

        Assertions.assertEquals(customerAppointments.size(), 3);
        Assertions.assertEquals(deletedAppointments, 3);
    }

    @Test
    void GetAppointmentsByContactIDTest() throws Exception
    {
        app.PropertiesService propertiesService = new app.PropertiesService();
        Properties projectProperties = propertiesService.GetProperties("app.properties");
        DataAccess.DataAccessFactory dataAccessFactory = new DataAccess.DataAccessFactory(DataAccess.Enums.DataType.MYSQL, projectProperties);
        IAppointmentData appointmentDataService;
        AppointmentModel testAppointmentModel;
        List<AppointmentModel> contactAppointments;
        int firstAppointmentID;
        int secondAppointmentID;
        int thirdAppointmentID;

        try
        {
            dataAccessFactory.ConnectToDB();
            appointmentDataService = dataAccessFactory.GetAppointmentDataService();
            testAppointmentModel = appointmentDataService.GetAppointmentByID(1);
            testAppointmentModel.SetContactID(1);
            firstAppointmentID = appointmentDataService.CreateAppointment(testAppointmentModel);
            secondAppointmentID = appointmentDataService.CreateAppointment(testAppointmentModel);
            thirdAppointmentID = appointmentDataService.CreateAppointment(testAppointmentModel);
            contactAppointments = appointmentDataService.GetAllAppointmentsByContactID(1);
            appointmentDataService.DeleteAppointmentByID(firstAppointmentID);
            appointmentDataService.DeleteAppointmentByID(secondAppointmentID);
            appointmentDataService.DeleteAppointmentByID(thirdAppointmentID);
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            dataAccessFactory.DisconnectFromDB();
        }

        Assertions.assertEquals(contactAppointments.size(), 3);
    }
}
