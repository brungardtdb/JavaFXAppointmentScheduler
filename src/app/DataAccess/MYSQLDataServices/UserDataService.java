package app.DataAccess.MYSQLDataServices;

import app.DataAccess.Interfaces.IUserData;
import app.UserData.Models.UserModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages user data in SQL database.
 */
public class UserDataService implements IUserData
{
    private final Connection connection;
    private final String dbName;

    /**
     * Constructor for SQL user data service, takes SQL connection and DB name as arguments.
     *
     * @param connection The database connection.
     * @param dbName The database name.
     */
    public UserDataService(Connection connection, String dbName)
    {
        this.connection = connection;
        this.dbName = dbName;
    }

    /**
     /**
     * Method for retrieving users from the database.
     *
     * @return All of the users in the database.
     * @throws Exception A SQL Exception
     */
    public List<UserModel> GetAllUsers() throws Exception
    {
        String selectAllUsers = "SELECT * " +
                "FROM " + dbName + ".users";

        List<UserModel> users = new ArrayList<UserModel>();

        try(var statement = connection.prepareStatement(selectAllUsers))
        {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                UserModel user = new UserModel();
                user.setUserID(resultSet.getInt("User_ID"));
                user.setUserName(resultSet.getString("User_Name"));
                user.setPassword(resultSet.getString("Password"));
                users.add(user);
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }

        return users;
    }
}
