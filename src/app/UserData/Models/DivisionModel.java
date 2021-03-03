package app.UserData.Models;

/**
 * Model class for Division data.
 */
public class DivisionModel
{
    private int divisionID;

    /**
     * Setter for the Division ID.
     *
     * @param ID The Division ID.
     */
    public void setDivisionID(int ID)
    {
        this.divisionID = ID;
    }

    /**
     * Getter for the Division ID.
     *
     * @return The Division ID.
     */
    public int getDivisionID()
    {
        return this.divisionID;
    }

    private String division;

    /**
     * Setter for the Division (name).
     *
     * @param division The Division.
     */
    public void setDivision(String division)
    {
        this.division = division;
    }

    /**
     * Getter for the Division (name).
     *
     * @return The Division.
     */
    public String getDivision()
    {
        return this.division;
    }

    int countryID;

    /**
     * Setter for the Country ID.
     *
     * @param ID The Country ID.
     */
    public void setCountryID(int ID)
    {
        this.countryID = ID;
    }

    /**
     * Getter for the Country ID.
     *
     * @return The Country ID.
     */
    public int getCountryID()
    {
        return this.countryID;
    }
}
