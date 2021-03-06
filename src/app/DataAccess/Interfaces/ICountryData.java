package app.DataAccess.Interfaces;

import app.UserData.Models.CountryModel;

import java.util.List;

/**
 * Interface for reading country data (readonly)
 */
public interface ICountryData
{
    /**
     * Method for getting countries from database.
     *
     * @return A list of all available countries.
     * @throws Exception
     */
    public List<CountryModel> GetAllCountries() throws Exception;
}
