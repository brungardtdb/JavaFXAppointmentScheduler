package DataAccessTest;

import DataAccess.DataAccessFactory;
import DataAccess.Enums.DataType;
import DataAccess.Interfaces.IAppointmentData;
import DataAccess.Interfaces.ICustomerData;
import DataAccess.MYSQLDataServices.CustomerDataService;
import UserData.Models.AppointmentModel;
import UserData.Models.CustomerModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.when;

public class CustomerDataServiceTest
{
    @Test
    void GetCustomerTest() throws Exception
    {

        app.PropertiesService propertiesService = new app.PropertiesService();
        Properties projectProperties = propertiesService.GetProperties();
        DataAccessFactory dataAccessFactory = new DataAccessFactory(DataType.MYSQL, projectProperties);
        ICustomerData customerDataService;
        CustomerModel testCustomerModel;

        try
        {
            dataAccessFactory.ConnectToDB();
            customerDataService = dataAccessFactory.GetCustomerDataService();
            testCustomerModel = customerDataService.GetCustomerByID(1);
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            dataAccessFactory.DisconnectFromDB();
        }

        Assertions.assertEquals(testCustomerModel.GetCustomerName(), "Daddy Warbucks");
        Assertions.assertEquals(testCustomerModel.GetCustomerAddress(), "1919 Boardwalk");
        Assertions.assertEquals(testCustomerModel.GetPostalCode(), "01291");
        Assertions.assertEquals(testCustomerModel.GetPhoneNumber(), "869-908-1875");
        Assertions.assertEquals(testCustomerModel.GetDivisionID(), 29);
    }

    @Test
    void UpdateCustomerTest() throws Exception
    {
        app.PropertiesService propertiesService = new app.PropertiesService();
        Properties projectProperties = propertiesService.GetProperties();
        DataAccessFactory dataAccessFactory = new DataAccessFactory(DataType.MYSQL, projectProperties);
        ICustomerData customerDataService;

        CustomerModel testCustomerModel = new CustomerModel();
        testCustomerModel.SetCustomerID(1);
        testCustomerModel.SetPhoneNumber("869-908-1875");
        testCustomerModel.SetPostalCode("01291");
        testCustomerModel.SetCustomerAddress("1919 Boardwalk");
        testCustomerModel.SetCustomerName("Dr. Daddy Warbucks");
        testCustomerModel.SetDivisionID(29);

        CustomerModel customerNameFix = new CustomerModel();
        customerNameFix.SetCustomerID(testCustomerModel.GetCustomerID());
        customerNameFix.SetCustomerName("Daddy Warbucks");
        customerNameFix.SetPhoneNumber(testCustomerModel.GetPhoneNumber());
        customerNameFix.SetPostalCode(testCustomerModel.GetPostalCode());
        customerNameFix.SetCustomerAddress(testCustomerModel.GetCustomerAddress());
        customerNameFix.SetDivisionID(testCustomerModel.GetDivisionID());

        boolean result;
        boolean nameFixResult;
        CustomerModel customerResult;

        try
        {
            dataAccessFactory.ConnectToDB();
            customerDataService = dataAccessFactory.GetCustomerDataService();
            result = customerDataService.UpdateCustomer(testCustomerModel);
            customerResult = customerDataService.GetCustomerByID(1);
            nameFixResult = customerDataService.UpdateCustomer(customerNameFix);
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            dataAccessFactory.DisconnectFromDB();
        }

        Assertions.assertTrue(result);
        Assertions.assertEquals(customerResult.GetCustomerName(), "Dr. Daddy Warbucks");
        Assertions.assertTrue(nameFixResult);
    }

    @Test
    void CreateDeleteUserTest() throws Exception
    {
        app.PropertiesService propertiesService = new app.PropertiesService();
        Properties projectProperties = propertiesService.GetProperties();
        DataAccessFactory dataAccessFactory = new DataAccessFactory(DataType.MYSQL, projectProperties);
        ICustomerData customerDataService;
        IAppointmentData appointmentDataService;
        CustomerModel testCustomerModel = new CustomerModel();

        testCustomerModel.SetPhoneNumber("555-555-5555");
        testCustomerModel.SetPostalCode("84015");
        testCustomerModel.SetCustomerAddress("233 S Wacker Dr, Chicago, IL");
        testCustomerModel.SetCustomerName("John Doe");
        testCustomerModel.SetDivisionID(103);
        int createResult;
        boolean deleteResult;
        AppointmentModel testAppointment;
        List<AppointmentModel> appointments;

        try
        {
            dataAccessFactory.ConnectToDB();
            customerDataService = dataAccessFactory.GetCustomerDataService();
            appointmentDataService = dataAccessFactory.GetAppointmentDataService();
            createResult = customerDataService.CreateCustomer(testCustomerModel);
            testAppointment = appointmentDataService.GetAppointmentByID(1);
            testAppointment.SetCustomerID(4);
            appointmentDataService.CreateAppointment(testAppointment);
            appointmentDataService.CreateAppointment(testAppointment);
            appointmentDataService.CreateAppointment(testAppointment);
            deleteResult = customerDataService.DeleteCustomerByID(createResult);
            appointments = appointmentDataService.GetAllCustomerAppointments(createResult);
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            dataAccessFactory.DisconnectFromDB();
        }

        Assertions.assertEquals(createResult, 4);
        Assertions.assertNotNull(createResult);
        Assertions.assertTrue(deleteResult);
        Assertions.assertEquals(appointments.size(), 0);
    }

    @Test
    void GetAllCustomersTest() throws Exception
    {
        app.PropertiesService propertiesService = new app.PropertiesService();
        Properties projectProperties = propertiesService.GetProperties();
        DataAccessFactory dataAccessFactory = new DataAccessFactory(DataType.MYSQL, projectProperties);
        ICustomerData customerDataService;
        CustomerModel testCustomerModel;
        List<CustomerModel> customers;

        try
        {
            dataAccessFactory.ConnectToDB();
            customerDataService = dataAccessFactory.GetCustomerDataService();
            customers = customerDataService.GetAllCustomers();
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            dataAccessFactory.DisconnectFromDB();
        }

        Assertions.assertEquals(customers.size(), 3);
        testCustomerModel = customers.get(0);
        Assertions.assertEquals(testCustomerModel.GetCustomerName(), "Daddy Warbucks");
    }
}
