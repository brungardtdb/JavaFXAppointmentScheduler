package app.DataAccess.MYSQLDataServices;

import app.DataAccess.Interfaces.IContactData;
import app.UserData.Models.ContactModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages contact data in database (readonly)
 */
public class ContactDataService implements IContactData
{
    private final Connection connection;
    private final String dbName;

    /**
     * Constructor for SQL contact data service, takes SQL connection and DB name as arguments.
     *
     * @param connection The database connection.
     * @param dbName The database name.
     */
    public ContactDataService(Connection connection, String dbName)
    {
        this.connection = connection;
        this.dbName = dbName;
    }

    /**
     * Method for getting all contacts from database.
     *
     * @return A list of all contacts.
     * @throws Exception A SQL Exception.
     */
    public List<ContactModel> GetAllContacts() throws Exception
    {
        String selectAllContacts = "SELECT * " +
                "FROM " + dbName + ".contacts";

        List<ContactModel> contacts = new ArrayList<ContactModel>();

        try (var statement = connection.prepareStatement(selectAllContacts))
        {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                ContactModel contact = new ContactModel();
                contact.setContactID(resultSet.getInt("Contact_ID"));
                contact.setContactName(resultSet.getString("Contact_Name"));
                contacts.add(contact);
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
        return contacts;
    }
}
