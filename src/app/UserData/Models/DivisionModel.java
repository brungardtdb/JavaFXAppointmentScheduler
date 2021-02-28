package app.UserData.Models;

public class DivisionModel
{
    private int divisionID;

    public void setDivisionID(int ID)
    {
        this.divisionID = ID;
    }

    public int getDivisionID()
    {
        return this.divisionID;
    }

    private String division;

    public void setDivision(String division)
    {
        this.division = division;
    }

    public String getDivision()
    {
        return this.division;
    }

    int countryID;

    public void setCountryID(int ID)
    {
        this.countryID = ID;
    }

    public int getCountryID()
    {
        return this.countryID;
    }
}
