package app.DataAccess.MYSQLDataServices;

import app.DataAccess.Interfaces.ICountryData;
import app.UserData.Models.CountryModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages country data in database (readonly)
 */
public class CountryDataService implements ICountryData
{
    private final Connection connection;
    private final String dbName;

    /**
     * Constructor for SQL country data service, takes SQL connection and DB name as arguments.
     *
     * @param connection The database connection.
     * @param dbName The database name.
     */
    public CountryDataService(Connection connection, String dbName)
    {
        this.connection = connection;
        this.dbName = dbName;
    }


    /**
     * Method for getting countries from database.
     *
     * @return A list of all available countries.
     * @throws Exception A SQL Exception.
     */
    public List<CountryModel> GetAllCountries() throws Exception
    {
        String selectAllCountries = "SELECT * " +
                "FROM " + dbName + ".countries";

        List<CountryModel> countries = new ArrayList<CountryModel>();

        try(var statement = connection.prepareStatement(selectAllCountries))
        {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                CountryModel country = new CountryModel();
                country.setCountryID(resultSet.getInt("Country_ID"));
                country.setCountryName(resultSet.getString("Country"));
                countries.add(country);
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
        return countries;
    }
}
