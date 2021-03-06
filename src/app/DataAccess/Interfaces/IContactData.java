package app.DataAccess.Interfaces;

import app.UserData.Models.ContactModel;

import java.util.List;

/**
 * Interface for reading contact data (readonly)
 */
public interface IContactData
{
    /**
     * Method for getting all contacts from database.
     *
     * @return A list of all contacts.
     * @throws Exception
     */
    public List<ContactModel> GetAllContacts() throws Exception;
}
