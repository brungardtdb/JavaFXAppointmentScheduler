package DataAccessTest;

import app.DataAccess.DataAccessFactory;
import app.DataAccess.Enums.DataType;
import app.DataAccess.Interfaces.ICountryData;
import app.DataAccess.Interfaces.ICountryData;
import app.UserData.Models.CountryModel;
import app.Util.PropertiesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CountryDataTest
{
    @Test
    void CountryDataTest() throws Exception
    {
        PropertiesService propertiesService = new PropertiesService();
        Properties projectProperties = propertiesService.GetProperties("app.properties");
        DataAccessFactory dataAccessFactory = new DataAccessFactory(DataType.MYSQL, projectProperties);
        ICountryData countryDataService;
        List<CountryModel> countries = new ArrayList<CountryModel>();

        try
        {
            dataAccessFactory.ConnectToDB();
            countryDataService = dataAccessFactory.GetCountryDataService();
            countries = countryDataService.GetAllCountries();
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            dataAccessFactory.DisconnectFromDB();
        }

        Assertions.assertEquals(countries.size(), 3);
    }
}
