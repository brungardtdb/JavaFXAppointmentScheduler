package DataAccess.MYSQLDataServices;

import DataAccess.Interfaces.IAppointmentData;
import UserData.Enums.AppointmentType;
import UserData.Models.AppointmentModel;
import UserData.Models.CustomerModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

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
     * @param connection The database connection.
     * @param dbName The database name.
     */
    public AppointmentDataService(Connection connection, String dbName)
    {
        this.connection = connection;
        this.dbName = dbName;
    }

    //region CRUD methods

    /**
     * Creates appointment in database.
     *
     * @param appointment The appointment object.
     * @return The appointment ID.
     * @throws Exception
     */
    public int CreateAppointment(AppointmentModel appointment) throws Exception
    {
        int ID = GetNextID();
        appointment.SetCustomerID(ID);

        String insertQuery = "INSERT INTO " + dbName + ".appointments " +
                "(`Appointment_ID`, `Title`, `Description`, `Location`, `Type`, `Start`, " +
                "`End`, `Customer_ID`, `User_ID`, `Contact_ID`)"+
                " VALUES (\'"+ appointment.GetAppointmentID() +
                "\', \'" + appointment.GetTitle() +
                "\', \'" + appointment.GetDescription() +
                "\', \'" + appointment.GetLocation() +
                "\', \'" + AppointmentTypeToString(appointment.GetAppointmentType()) +
                "\', \'" + appointment.GetStartDate() +
                "\', \'" + appointment.GetEndDate() +
                "\', \'" + appointment.GetCustomerID() +
                "\', \'" + appointment.GetUserID() +
                appointment.GetContactID() + ")";

        try (var statement = this.connection.prepareStatement(insertQuery))
        {
            int result = statement.executeUpdate();

            if (result > 0)
            {
                return appointment.GetAppointmentID();
            }

            throw new Exception("Appointment could not be created!");

        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Gets appointment from database.
     *
     * @param ID The appointment ID.
     * @return The appointment object if found.
     * @throws Exception
     */
    public AppointmentModel GetAppointmentByID(int ID) throws Exception
    {
        String query = "SELECT * " +
                "FROM " + dbName + ".appointments " +
                "WHERE Appointment_ID = " + ID;

        AppointmentModel appointment = new AppointmentModel();

        try(var statement = this.connection.prepareStatement(query))
        {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                appointment.SetAppointmentID(resultSet.getInt("Appointment_ID"));
                appointment.SetTitle(resultSet.getString("Title"));
                appointment.SetDescription(resultSet.getString("Description"));
                appointment.SetLocation(resultSet.getString("Location"));
                appointment.SetAppointmentType(AppointmentTypeFromString(resultSet.getString("Type")));
                appointment.SetStartDate(resultSet.getDate("Start"));
                appointment.SetEndDate(resultSet.getDate("End"));
                appointment.SetCustomerID(resultSet.getInt("Customer_ID"));
                appointment.SetUserID(resultSet.getInt("User_ID"));
                appointment.SetContactID(resultSet.getInt("Contact_ID"));
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }

        return appointment;
    }

    /**
     * Updates appointment in database, overrides values with values from appointment class parameter.
     *
     * @param appointment The appointment object.
     * @return True if appointment was successfully updated.
     * @throws Exception
     */
    public boolean UpdateAppointment(AppointmentModel appointment) throws Exception
    {
        return false;
    }

    /**
     * Deletes appointment from database.
     *
     * @param ID The appointment ID.
     * @return True if the appointment was successfully deleted.
     * @throws Exception
     */
    public boolean DeleteAppointmentByID(int ID) throws Exception
    {
        return false;
    }

    /**
     * Deletes all appointments for a given customer.
     *
     * @param customerID The customer ID.
     * @return The number of appointment records that were deleted.
     * @throws Exception
     */
    public int DeleteAllCustomerAppointments(int customerID) throws Exception
    {
        return 0;
    }

    /**
     * Returns all appointments for a given customer.
     *
     * @param customerID The customer ID.
     * @return A list of customer appointments.
     * @throws Exception
     */
    public List<AppointmentModel> GetAllCustomerAppointments(int customerID) throws Exception
    {
        return null;
    }

    //endregion

    //region Helper methods

    /**
     * Helper method to get max appointment ID from database, max ID is incremented to generate next sequential appointment ID.
     *
     * @return The next sequential appointment ID.
     * @throws Exception SQL Exception.
     */
    private int GetNextID() throws Exception
    {
        /* This is kind of a hacky way to do this, but the auto-increment functionality in the database
        does not take into account objects that are being deleted and I want to avoid big jumps in IDs,
        but I also cannot make changes to the database.
        */

        String appointmentIDQuery = "SELECT MAX(Appointment_ID) FROM " + dbName + ".appointments ";
        int ID = 1;

        try(var statement = this.connection.prepareStatement(appointmentIDQuery))
        {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ID = resultSet.getInt("MAX(Appointment_ID)");

                if (resultSet.wasNull())
                {
                    ID = 1;
                    return ID;
                }
                ID++;
                break;
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
        return ID;
    }

    /**
     * Converts appointment type to String.
     *
     * @param type Type of appointment. -(Enum)
     * @return Type of appointment. -(String)
     */
    private String AppointmentTypeToString(AppointmentType type)
    {
        switch (type)
        {
            case PLANNING: return "Planning Session";
            case DEBRIEFING: return "De-Briefing";
            default: return null;
        }
    }

    /**
     * Converts String to appointment type.
     *
     * @param type Type of appointment. -(String)
     * @return The type of appointment. -(Enum)
     */
    private AppointmentType AppointmentTypeFromString(String type)
    {
        if (type.toUpperCase().equals("PLANNING SESSION"))
        {
            return AppointmentType.PLANNING;
        }

        if (type.toUpperCase().equals("DE-BRIEFING"))
        {
            return AppointmentType.DEBRIEFING;
        }

        return  null;
    }
    //endregion
}
