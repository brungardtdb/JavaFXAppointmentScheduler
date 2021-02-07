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
     * onstructor for SQL appointment data service, takes SQL connection and DB name as arguments.
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
     * @return Returns Appointment ID.
     */
    public int CreateAppointment(AppointmentModel appointment) throws Exception
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets appointment from database.
     *
     * @param appointment
     * @return Returns appointment if found.
     */
    public AppointmentModel GetAppointment(AppointmentModel appointment) throws Exception
    {
        return null;
    }

    /**
     * Updates appointment in database, overrides values with values from appointment class parameter.
     *
     * @param appointment
     */
    public void UpdateAppointment(AppointmentModel appointment) throws Exception
    {

    }

    /**
     * Deletes appointment from database.
     *
     * @param appointment
     */
    public void DeleteAppointment(AppointmentModel appointment) throws Exception
    {

    }
}
