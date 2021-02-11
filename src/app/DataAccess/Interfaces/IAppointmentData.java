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
     * @throws Exception
     */
    public int CreateAppointment(AppointmentModel appointment) throws Exception;

    /**
     * Gets appointment from database.
     *
     * @param ID
     * @return Returns appointment if found.
     * @throws Exception
     */
    public AppointmentModel GetAppointmentByID(int ID) throws Exception;

    /**
     * Updates appointment in database, overrides values with values from appointment class parameter.
     *
     * @param appointment
     * @return Returns true if record was successfully updated.
     * @throws Exception
     */
    public boolean UpdateAppointment(AppointmentModel appointment) throws Exception;

    /**
     * Deletes appointment from database.
     *
     * @param ID
     * @return Returns true if record was successfully deleted.
     * @throws Exception
     */
    public boolean DeleteAppointmentByID(int ID) throws Exception;

    /**
     * Deletes all appointments for a given customer.
     *
     * @param customerID
     * @return Returns number of appointments deleted.
     */
    public int DeleteAllCustomerAppointments(int customerID);
}