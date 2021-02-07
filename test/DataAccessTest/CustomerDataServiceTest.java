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

import static org.mockito.Mockito.when;

public class CustomerDataServiceTest
{
    @Test
    void CreateUserTest() throws Exception
    {

        app.PropertiesService propertiesService = new app.PropertiesService();
        Properties projectProperties = propertiesService.GetProperties();
        DataAccessFactory dataAccessFactory = new DataAccessFactory(DataType.SQL, projectProperties);
        ICustomerData customerDataService = dataAccessFactory.GetCustomerDataService();
        CustomerModel testCustomerModel = new CustomerModel();

        testCustomerModel.SetPhoneNumber("555-555-5555");
        testCustomerModel.SetPostalCode("84015");
        testCustomerModel.SetCustomerAddress("233 S Wacker Dr, Chicago, IL");
        testCustomerModel.SetCustomerName("John Doe");
        int result;

        try
        {
            dataAccessFactory.ConnectToDB();
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
        System.out.println(result);

    }
}
