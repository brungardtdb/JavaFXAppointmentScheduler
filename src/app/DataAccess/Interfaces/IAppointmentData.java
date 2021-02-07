package DataAccess.Interfaces;

import UserData.Models.AppointmentModel;

/**
 * Interface for appointment data CRUD operations
 */
public interface IAppointmentData
{

    /**
     * Creates appointment in database.
     *
     * @param appointment
     * @return Returns appointment ID.
     */
    public int CreateAppointment(AppointmentModel appointment);

    /**
     * Gets appointment from database.
     *
     * @param appointment
     * @return Returns appointment if found.
     */
    public AppointmentModel GetAppointment(AppointmentModel appointment);

    /**
     * Updates appointment in database, overrides values with values from appointment class parameter.
     *
     * @param appointment
     */
    public void UpdateAppointment(AppointmentModel appointment);

    /**
     * Deletes appointment from database.
     *
     * @param appointment
     */
    public void DeleteAppointment(AppointmentModel appointment);
}