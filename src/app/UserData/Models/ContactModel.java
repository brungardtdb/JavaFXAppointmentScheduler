package app.UserData.Models;

/**
 * Model for contact data.
 */
public class ContactModel
{
    private int contactID;

    /**
     * Setter for contact ID.
     *
     * @param ID The contact ID.
     */
    public void setContactID(int ID)
    {
        this.contactID = ID;
    }

    /**
     * Getter for contact ID.
     *
     * @return The contact ID.
     */
    public int getContactID()
    {
        return this.contactID;
    }

    private String contactName;

    /**
     * Setter for contact name.
     *
     * @param name The contact name.
     */
    public void setContactName(String name)
    {
        this.contactName = name;
    }

    /**
     * Getter for contact name.
     *
     * @return The contact name.
     */
    public String getContactName()
    {
        return this.contactName;
    }
}
