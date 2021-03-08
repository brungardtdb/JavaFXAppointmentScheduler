package app.UserData.Models;

/**
 * Model class for user data.
 */
public class UserModel
{
    private int userID;

    /**
     * Setter for user ID.
     *
     * @param ID The user ID.
     */
    public void setUserID(int ID)
    {
        this.userID = ID;
    }

    /**
     * Getter for the user ID.
     *
     * @return The user ID.
     */
    public int getUserID()
    {
        return this.userID;
    }

    private String userName;

    /**
     * Setter for the user name.
     *
     * @param userName The user name.
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * Getter for the user name.
     *
     * @return The user name.
     */
    public String getUserName()
    {
        return this.userName;
    }

    private String password;

    /**
     * Setter for the password.
     *
     * @param password The user password.
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Getter for the password.
     *
     * @return The user password.
     */
    public String getPassword()
    {
        return this.password;
    }
}
