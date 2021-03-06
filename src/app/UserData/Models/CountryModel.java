package app.UserData.Models;

/**
 * Model for country data.
 */
public class CountryModel
{
    private int countryID;

    /**
     * Setter for country ID.
     *
     * @param ID The country ID.
     */
    public void setCountryID(int ID)
    {
        this.countryID = ID;
    }

    /**
     * Getter for country ID.
     *
     * @return The country ID.
     */
    public int getCountryID()
    {
        return this.countryID;
    }

    private String countryName;

    /**
     * Setter for country Name.
     *
     * @param countryName The name of the country.
     */
    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    /**
     * Getter for the country Name.
     *
     * @return The name of the country.
     */
    public String getCountryName()
    {
        return this.countryName;
    }
}
