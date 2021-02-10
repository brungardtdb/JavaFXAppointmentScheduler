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

import java.util.Properties;

import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.when;

public class CustomerDataServiceTest
{
    @Test
    void GetUserTest() throws Exception
    {

        app.PropertiesService propertiesService = new app.PropertiesService();
        Properties projectProperties = propertiesService.GetProperties();
        DataAccessFactory dataAccessFactory = new DataAccessFactory(DataType.SQL, projectProperties);
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
    void UpdateUserTest() throws Exception
    {
        app.PropertiesService propertiesService = new app.PropertiesService();
        Properties projectProperties = propertiesService.GetProperties();
        DataAccessFactory dataAccessFactory = new DataAccessFactory(DataType.SQL, projectProperties);
        ICustomerData customerDataService;
        CustomerModel testCustomerModel = new CustomerModel();

        testCustomerModel.SetCustomerID(1);
        testCustomerModel.SetPhoneNumber("869-908-1875");
        testCustomerModel.SetPostalCode("01291");
        testCustomerModel.SetCustomerAddress("1919 Boardwalk");
        testCustomerModel.SetCustomerName("Dr. Daddy Warbucks");
        testCustomerModel.SetDivisionID(29);
        boolean result;
        CustomerModel customerResult;

        try
        {
            dataAccessFactory.ConnectToDB();
            customerDataService = dataAccessFactory.GetCustomerDataService();
            result = customerDataService.UpdateCustomer(testCustomerModel);
            customerResult = customerDataService.GetCustomerByID(1);
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
    }

    @Test
    void CreateUserTest() throws Exception
    {
        app.PropertiesService propertiesService = new app.PropertiesService();
        Properties projectProperties = propertiesService.GetProperties();
        DataAccessFactory dataAccessFactory = new DataAccessFactory(DataType.SQL, projectProperties);
        ICustomerData customerDataService;
        CustomerModel testCustomerModel = new CustomerModel();

        testCustomerModel.SetPhoneNumber("555-555-5555");
        testCustomerModel.SetPostalCode("84015");
        testCustomerModel.SetCustomerAddress("233 S Wacker Dr, Chicago, IL");
        testCustomerModel.SetCustomerName("John Doe");
        testCustomerModel.SetDivisionID(103);
        int result;

        try
        {
            dataAccessFactory.ConnectToDB();
            customerDataService = dataAccessFactory.GetCustomerDataService();
            result = customerDataService.CreateCustomer(testCustomerModel);
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            dataAccessFactory.DisconnectFromDB();
        }

        Assertions.assertEquals(result, 4);
        Assertions.assertNotNull(result);
    }
}
