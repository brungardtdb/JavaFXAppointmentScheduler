package app.UserData.Models;

/**
 * Model for Country data.
 */
public class CountryModel
{
    private int countryID;

    /**
     * Setter for Country ID.
     *
     * @param ID The Country ID.
     */
    public void setCountryID(int ID)
    {
        this.countryID = ID;
    }

    /**
     * Getter for Country ID.
     *
     * @return The Country ID.
     */
    public int getCountryID()
    {
        return this.countryID;
    }

    private String countryName;

    /**
     * Setter for Country Name.
     *
     * @param countryName The name of the country.
     */
    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    /**
     * Getter for the Country Name.
     *
     * @return The name of the country.
     */
    public String getCountryName()
    {
        return this.countryName;
    }
}
