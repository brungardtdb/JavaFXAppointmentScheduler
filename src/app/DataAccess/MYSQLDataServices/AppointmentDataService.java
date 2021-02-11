package DataAccess.MYSQLDataServices;

import DataAccess.Interfaces.IAppointmentData;
import UserData.Models.AppointmentModel;

import java.sql.Connection;

/**
 * Class that manages appointment data in SQL database.
 */
public class AppointmentDataService implements IAppointmentData
{
    private final Connection connection;
    private final String dbName;


    /**
     * Constructor for SQL appointment data service, takes SQL connection and DB name as arguments.
     *
     * @param connection
     * @param dbName
     */
    public AppointmentDataService(Connection connection, String dbName)
    {
        this.connection = connection;
        this.dbName = dbName;
    }

    /**
     * Creates appointment in database.
     *
     * @param appointment
     * @return Returns appointment ID.
     * @throws Exception
     */
    public int CreateAppointment(AppointmentModel appointment) throws Exception
    {
        return 0;
    }

    /**
     * Gets appointment from database.
     *
     * @param ID
     * @return Returns appointment if found.
     * @throws Exception
     */
    public AppointmentModel GetAppointmentByID(int ID) throws Exception
    {
        return null;
    }

    /**
     * Updates appointment in database, overrides values with values from appointment class parameter.
     *
     * @param appointment
     * @return Returns true if record was successfully updated.
     * @throws Exception
     */
    public boolean UpdateAppointment(AppointmentModel appointment) throws Exception
    {
        return false;
    }

    /**
     * Deletes appointment from database.
     *
     * @param ID
     * @return Returns true if record was successfully deleted.
     * @throws Exception
     */
    public boolean DeleteAppointmentByID(int ID) throws Exception
    {
        return false;
    }

    /**
     * Deletes all appointments for a given customer.
     *
     * @param customerID
     * @return Returns number of appointments deleted.
     */
    public int DeleteAllCustomerAppointments(int customerID)
    {
        return 0;
    }
}
