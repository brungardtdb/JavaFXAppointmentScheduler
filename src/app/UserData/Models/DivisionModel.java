package app.UserData.Models;

/**
 * Model class for division data.
 */
public class DivisionModel
{
    private int divisionID;

    /**
     * Setter for the division ID.
     *
     * @param ID The division ID.
     */
    public void setDivisionID(int ID)
    {
        this.divisionID = ID;
    }

    /**
     * Getter for the division ID.
     *
     * @return The division ID.
     */
    public int getDivisionID()
    {
        return this.divisionID;
    }

    private String division;

    /**
     * Setter for the division (name).
     *
     * @param division The division.
     */
    public void setDivision(String division)
    {
        this.division = division;
    }

    /**
     * Getter for the division (name).
     *
     * @return The division.
     */
    public String getDivision()
    {
        return this.division;
    }

    int countryID;

    /**
     * Setter for the country ID.
     *
     * @param ID The country ID.
     */
    public void setCountryID(int ID)
    {
        this.countryID = ID;
    }

    /**
     * Getter for the country ID.
     *
     * @return The country ID.
     */
    public int getCountryID()
    {
        return this.countryID;
    }
}
