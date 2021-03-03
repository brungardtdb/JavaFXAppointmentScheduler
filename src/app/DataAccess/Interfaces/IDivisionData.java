package app.DataAccess.Interfaces;

import app.UserData.Models.DivisionModel;

import java.util.List;

/**
 * Interface for reading Division data (readonly)
 */
public interface IDivisionData
{
    /**
     * Method for getting Divisions from database.
     *
     * @return A list of all available divisions.
     * @throws Exception
     */
    public List<DivisionModel> GetAllDivisions() throws Exception;
}
