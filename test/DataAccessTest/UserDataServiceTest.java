package DataAccessTest;

import app.DataAccess.DataAccessFactory;
import app.DataAccess.Enums.DataType;
import app.DataAccess.Interfaces.IContactData;
import app.DataAccess.Interfaces.IUserData;
import app.UserData.Models.ContactModel;
import app.UserData.Models.UserModel;
import app.Util.PropertiesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDataServiceTest
{
    @Test
    void UserDataServiceTest() throws Exception
    {
        PropertiesService propertiesService = new PropertiesService();
        Properties projectProperties = propertiesService.GetProperties("app.properties");
        DataAccessFactory dataAccessFactory = new DataAccessFactory(DataType.MYSQL, projectProperties);
        IUserData userDataService;
        List<UserModel> users = new ArrayList<UserModel>();

        try
        {
            dataAccessFactory.ConnectToDB();
            userDataService = dataAccessFactory.GetUserDataService();
            users = userDataService.GetAllUsers();
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            dataAccessFactory.DisconnectFromDB();
        }

        Assertions.assertEquals(users.size(), 2);
    }
}
