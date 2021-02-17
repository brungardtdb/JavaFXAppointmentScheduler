package DataAccess.Interfaces;

import UserData.Models.AppointmentModel;

import java.util.List;

/**
 * Interface for appointment data CRUD operations
 */
public interface IAppointmentData
{

    /**
     * Creates appointment in database.
     *
     * @param appointment The appointment object.
     * @return The appointment ID.
     * @throws Exception
     */
    public int CreateAppointment(AppointmentModel appointment) throws Exception;

    /**
     * Gets appointment from database.
     *
     * @param ID The appointment ID.
     * @return The appointment object if found.
     * @throws Exception
     */
    public AppointmentModel GetAppointmentByID(int ID) throws Exception;

    /**
     * Updates appointment in database, overrides values with values from appointment class parameter.
     *
     * @param appointment The appointment object.
     * @return True if appointment was successfully updated.
     * @throws Exception
     */
    public boolean UpdateAppointment(AppointmentModel appointment) throws Exception;

    /**
     * Deletes appointment from database.
     *
     * @param ID The appointment ID.
     * @return True if the appointment was successfully deleted.
     * @throws Exception
     */
    public boolean DeleteAppointmentByID(int ID) throws Exception;

    /**
     * Deletes all appointments for a given customer.
     *
     * @param customerID The customer ID.
     * @return The number of appointment records that were deleted.
     * @throws Exception
     */
    public int DeleteAllCustomerAppointments(int customerID) throws Exception;

    /**
     * Returns all appointments for a given customer.
     *
     * @param customerID The customer ID.
     * @return A list of customer appointments.
     * @throws Exception
     */
    public List<AppointmentModel> GetAllCustomerAppointments(int customerID) throws Exception;

    /**
     * Returns all appointments for a given contact.
     *
     * @param contactID The contact ID.
     * @return A list of appointments for the given contact.
     * @throws Exception
     */
    public List<AppointmentModel> GetAllAppointmentsByContactID(int contactID) throws Exception;
}