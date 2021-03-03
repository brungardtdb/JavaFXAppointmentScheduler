package DataAccessTest;

import app.DataAccess.DataAccessFactory;
import app.DataAccess.Enums.DataType;
import app.DataAccess.Interfaces.IAppointmentData;
import app.DataAccess.Interfaces.ICustomerData;
import app.UserData.Models.AppointmentModel;
import app.UserData.Models.CustomerModel;
import app.Util.PropertiesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;

public class CustomerDataServiceTest
{
    @Test
    void GetCustomerTest() throws Exception
    {

        PropertiesService propertiesService = new PropertiesService();
        Properties projectProperties = propertiesService.GetProperties("app.properties");
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

        Assertions.assertEquals(testCustomerModel.getCustomerName(), "Daddy Warbucks");
        Assertions.assertEquals(testCustomerModel.getCustomerAddress(), "1919 Boardwalk");
        Assertions.assertEquals(testCustomerModel.getPostalCode(), "01291");
        Assertions.assertEquals(testCustomerModel.getPhoneNumber(), "869-908-1875");
        Assertions.assertEquals(testCustomerModel.getDivisionID(), 29);
    }

    @Test
    void UpdateCustomerTest() throws Exception
    {
        PropertiesService propertiesService = new PropertiesService();
        Properties projectProperties = propertiesService.GetProperties("app.properties");
        DataAccessFactory dataAccessFactory = new DataAccessFactory(DataType.MYSQL, projectProperties);
        ICustomerData customerDataService;

        CustomerModel testCustomerModel = new CustomerModel();
        testCustomerModel.setCustomerID(1);
        testCustomerModel.setPhoneNumber("869-908-1875");
        testCustomerModel.setPostalCode("01291");
        testCustomerModel.setCustomerAddress("1919 Boardwalk");
        testCustomerModel.setCustomerName("Dr. Daddy Warbucks");
        testCustomerModel.setDivisionID(29);

        CustomerModel customerNameFix = new CustomerModel();
        customerNameFix.setCustomerID(testCustomerModel.getCustomerID());
        customerNameFix.setCustomerName("Daddy Warbucks");
        customerNameFix.setPhoneNumber(testCustomerModel.getPhoneNumber());
        customerNameFix.setPostalCode(testCustomerModel.getPostalCode());
        customerNameFix.setCustomerAddress(testCustomerModel.getCustomerAddress());
        customerNameFix.setDivisionID(testCustomerModel.getDivisionID());

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
        Assertions.assertEquals(customerResult.getCustomerName(), "Dr. Daddy Warbucks");
        Assertions.assertTrue(nameFixResult);
    }

    @Test
    void CreateDeleteUserTest() throws Exception
    {
        PropertiesService propertiesService = new PropertiesService();
        Properties projectProperties = propertiesService.GetProperties("app.properties");
        DataAccessFactory dataAccessFactory = new DataAccessFactory(DataType.MYSQL, projectProperties);
        ICustomerData customerDataService;
        IAppointmentData appointmentDataService;
        CustomerModel testCustomerModel = new CustomerModel();

        testCustomerModel.setPhoneNumber("555-555-5555");
        testCustomerModel.setPostalCode("84015");
        testCustomerModel.setCustomerAddress("233 S Wacker Dr, Chicago, IL");
        testCustomerModel.setCustomerName("John Doe");
        testCustomerModel.setDivisionID(103);
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
            testAppointment.setCustomerID(4);
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
        PropertiesService propertiesService = new PropertiesService();
        Properties projectProperties = propertiesService.GetProperties("app.properties");
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
        Assertions.assertEquals(testCustomerModel.getCustomerName(), "Daddy Warbucks");
    }
}
