package DataAccessTest;

import app.DataAccess.DataAccessFactory;
import app.DataAccess.Enums.DataType;
import app.DataAccess.Interfaces.IContactData;
import app.DataAccess.Interfaces.ICountryData;
import app.UserData.Models.ContactModel;
import app.UserData.Models.CountryModel;
import app.Util.PropertiesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ContactDataServiceTest
{
    @Test
    void ContactDataServiceTest() throws Exception
    {
        PropertiesService propertiesService = new PropertiesService();
        Properties projectProperties = propertiesService.GetProperties("app.properties");
        DataAccessFactory dataAccessFactory = new DataAccessFactory(DataType.MYSQL, projectProperties);
        IContactData contactDataService;
        List<ContactModel> contacts = new ArrayList<ContactModel>();

        try
        {
            dataAccessFactory.ConnectToDB();
            contactDataService = dataAccessFactory.GetContactDataService();
            contacts = contactDataService.GetAllContacts();
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            dataAccessFactory.DisconnectFromDB();
        }

        Assertions.assertEquals(contacts.size(), 3);
    }
}
