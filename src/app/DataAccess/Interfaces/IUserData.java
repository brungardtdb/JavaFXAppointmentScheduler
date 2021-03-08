package app.DataAccess.Interfaces;

import app.UserData.Models.UserModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for reading the user data. (readonly)
 */
public interface IUserData
{

    /**
     * Method for retrieving users from the database.
     *
     * @return All of the users in the database.
     * @throws Exception
     */
    public List<UserModel> GetAllUsers() throws Exception;
}
