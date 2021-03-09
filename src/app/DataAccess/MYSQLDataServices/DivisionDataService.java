package app.DataAccess.MYSQLDataServices;

import app.DataAccess.Interfaces.IDivisionData;
import app.UserData.Models.DivisionModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages division data in database (readonly)
 */
public class DivisionDataService implements IDivisionData
{
    private final Connection connection;
    private final String dbName;

    /**
     * Constructor for SQL division data service, takes SQL connection and DB name as arguments.
     *
     * @param connection The database connection.
     * @param dbName The database name.
     */
    public DivisionDataService(Connection connection, String dbName)
    {
        this.connection = connection;
        this.dbName = dbName;
    }

    /**
     * Method for getting divisions from database.
     *
     * @return A list of all available divisions.
     * @throws Exception A SQL Exception.
     */
    public List<DivisionModel> GetAllDivisions() throws Exception
    {
        String selectAllDivisions = "SELECT * " +
                "FROM " + dbName + ".first_level_divisions";

        List<DivisionModel> divisions = new ArrayList<DivisionModel>();

        try(var statement = connection.prepareStatement(selectAllDivisions))
        {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                DivisionModel division = new DivisionModel();
                division.setDivisionID(resultSet.getInt("Division_ID"));
                division.setDivision(resultSet.getString("Division"));
                division.setCountryID(resultSet.getInt("COUNTRY_ID"));
                divisions.add(division);
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
        return divisions;
    }
}
