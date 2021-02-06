package DataAccess.SQLDataServices;

import DataAccess.Interfaces.IAppointmentData;
import UserData.Models.AppointmentModel;

import java.sql.Connection;

/**
 * Class that manages appointment data in SQL database.
 */
public class AppointmentDataService implements IAppointmentData
{
    private final Connection connection;

    /**
     * Constructor for SQL appointment data service, takes SQL connection as argument.
     *
     * @param connection
     */
    public AppointmentDataService(Connection connection)
    {
        this.connection = connection;
    }

    /**
     * Creates appointment in database.
     *
     * @param appointment
     */
    public void CreateAppointment(AppointmentModel appointment)
    {

    }

    /**
     * Gets appointment from database.
     *
     * @param appointment
     * @return Returns appointment if found.
     */
    public AppointmentModel GetAppointment(AppointmentModel appointment)
    {
        return null;
    }

    /**
     * Updates appointment in database, overrides values with values from appointment class parameter.
     *
     * @param appointment
     */
    public void UpdateAppointment(AppointmentModel appointment)
    {

    }

    /**
     * Deletes appointment from database.
     *
     * @param appointment
     */
    public void DeleteAppointment(AppointmentModel appointment)
    {

    }
}
