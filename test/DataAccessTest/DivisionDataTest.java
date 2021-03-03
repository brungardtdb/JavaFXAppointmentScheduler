package DataAccessTest;

import app.DataAccess.DataAccessFactory;
import app.DataAccess.Enums.DataType;
import app.DataAccess.Interfaces.IDivisionData;
import app.UserData.Models.DivisionModel;
import app.Util.PropertiesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DivisionDataTest
{
    @Test
    void DivisionDataTest() throws Exception
    {
        PropertiesService propertiesService = new PropertiesService();
        Properties projectProperties = propertiesService.GetProperties("app.properties");
        DataAccessFactory dataAccessFactory = new DataAccessFactory(DataType.MYSQL, projectProperties);
        List<DivisionModel> divisions = new ArrayList<DivisionModel>();
        IDivisionData divisionDataService;


        try
        {
            dataAccessFactory.ConnectToDB();
            divisionDataService = dataAccessFactory.GetDivisionDataService();
            divisions = divisionDataService.GetAllDivisions();
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            dataAccessFactory.DisconnectFromDB();
        }

        Assertions.assertEquals(divisions.size(), 68);
    }
}
