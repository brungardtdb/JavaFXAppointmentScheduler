package app.DataAccess.MYSQLDataServices;

import app.DataAccess.Interfaces.IAppointmentData;
import app.UserData.Enums.AppointmentType;
import app.UserData.Models.AppointmentModel;
import app.UserData.Models.CustomerModel;
import javafx.scene.chart.XYChart;
import org.apiguardian.api.API;
import org.mockito.internal.verification.Times;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

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
     * @throws Exception SQL Exception.
     */
    public int CreateAppointment(AppointmentModel appointment) throws Exception
    {
        int ID = GetNextID();
        appointment.setAppointmentID(ID);

        String insertQuery = "INSERT INTO " + dbName + ".appointments " +
                "(`Appointment_ID`, `Title`, `Description`, `Location`, `Type`, `Start`, " +
                "`End`, `Customer_ID`, `User_ID`, `Contact_ID`)"+
               " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (var statement = this.connection.prepareStatement(insertQuery))
        {
            statement.setInt(1, appointment.getAppointmentID());
            statement.setString(2, appointment.getTitle());
            statement.setString(3, appointment.getDescription());
            statement.setString(4, appointment.getLocation());
            statement.setString(5, AppointmentTypeToString(appointment.getAppointmentType()));
            statement.setTimestamp(6, ZonedDateTimeToTimestamp(appointment.getStartDate()));
            statement.setTimestamp(7, ZonedDateTimeToTimestamp(appointment.getEndDate()));
            statement.setInt(8, appointment.getCustomerID());
            statement.setInt(9, appointment.getUserID());
            statement.setInt(10, appointment.getContactID());

            int result = statement.executeUpdate();

            if (result > 0)
            {
                return appointment.getAppointmentID();
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
     * @throws Exception SQL Exception.
     */
    public AppointmentModel GetAppointmentByID(int ID) throws Exception
    {
        String query = "SELECT * " +
                "FROM " + dbName + ".appointments " +
                "WHERE Appointment_ID = ?";

        AppointmentModel appointment = new AppointmentModel();

        try(var statement = this.connection.prepareStatement(query))
        {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                appointment.setAppointmentID(resultSet.getInt("Appointment_ID"));
                appointment.setTitle(resultSet.getString("Title"));
                appointment.setDescription(resultSet.getString("Description"));
                appointment.setLocation(resultSet.getString("Location"));
                appointment.setAppointmentType(AppointmentTypeFromString(resultSet.getString("Type")));
                appointment.setStartDate(TimestampToZonedDateTime(resultSet.getTimestamp("Start")));
                appointment.setEndDate(TimestampToZonedDateTime(resultSet.getTimestamp("End")));
                appointment.setCustomerID(resultSet.getInt("Customer_ID"));
                appointment.setUserID(resultSet.getInt("User_ID"));
                appointment.setContactID(resultSet.getInt("Contact_ID"));
            }

            return appointment;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Updates appointment in database, overrides values with values from appointment class parameter.
     *
     * @param appointment The appointment object.
     * @return True if appointment was successfully updated.
     * @throws Exception SQL Exception.
     */
    public boolean UpdateAppointment(AppointmentModel appointment) throws Exception
    {
        String updateAppointmentQuery = "UPDATE " + dbName + ".appointments " +
                "SET " +
                "Title = ?, " +
                "Description = ?, " +
                "Location = ?, " +
                "Type = ?, " +
                "Start = ?, " +
                "End = ?, " +
                "Customer_ID = ?, " +
                "User_ID = ?, " +
                "Contact_ID = ? " +
                "WHERE Appointment_ID = ?";

        try(var statement = connection.prepareStatement(updateAppointmentQuery))
        {
            statement.setString(1, appointment.getTitle());
            statement.setString(2, appointment.getDescription());
            statement.setString(3, appointment.getLocation());
            statement.setString(4, AppointmentTypeToString(appointment.getAppointmentType()));
            statement.setTimestamp(5, ZonedDateTimeToTimestamp(appointment.getStartDate()));
            statement.setTimestamp(6, ZonedDateTimeToTimestamp(appointment.getEndDate()));
            statement.setInt(7, appointment.getCustomerID());
            statement.setInt(8, appointment.getUserID());
            statement.setInt(9, appointment.getContactID());
            statement.setInt(10, appointment.getAppointmentID());

            int result = statement.executeUpdate();

            return  (result > 0);
        }
        catch (Exception ex)
        {
            throw ex;
        }
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
        String deleteAppointmentQuery = "DELETE " +
                "FROM " + dbName + ".appointments " +
                "WHERE Appointment_ID = ?";

        try(var statement = connection.prepareStatement(deleteAppointmentQuery))
        {
            statement.setInt(1, ID);
            int result = statement.executeUpdate();

            return (result > 0);
        }
        catch (Exception ex)
        {
            throw ex;
        }
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
        String deleteAppointmentsQuery = "DELETE " +
                "FROM " + dbName + ".appointments " +
                "WHERE Customer_ID = ?";

        try(var statement = connection.prepareStatement(deleteAppointmentsQuery))
        {
            statement.setInt(1, customerID);
            int result = statement.executeUpdate();
            return result;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Returns all appointments for a given customer.
     *
     * @param customerID The customer ID.
     * @return A list of customer appointments.
     * @throws Exception A SQL Exception.
     */
    public List<AppointmentModel> GetAllCustomerAppointments(int customerID) throws Exception
    {
        String selectCustomerAppointmentsQuery = "SELECT * FROM " + dbName + ".appointments " +
                "WHERE Customer_ID = ?";

        List<AppointmentModel> appointments = new ArrayList<AppointmentModel>();

        try(var statement = connection.prepareStatement(selectCustomerAppointmentsQuery))
        {
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                AppointmentModel appointment = new AppointmentModel();
                appointment.setAppointmentID(resultSet.getInt("Appointment_ID"));
                appointment.setTitle(resultSet.getString("Title"));
                appointment.setDescription(resultSet.getString("Description"));
                appointment.setLocation(resultSet.getString("Location"));
                appointment.setAppointmentType(AppointmentTypeFromString(resultSet.getString("Type")));
                appointment.setStartDate(TimestampToZonedDateTime(resultSet.getTimestamp("Start")));
                appointment.setEndDate(TimestampToZonedDateTime(resultSet.getTimestamp("End")));
                appointment.setCustomerID(resultSet.getInt("Customer_ID"));
                appointment.setUserID(resultSet.getInt("User_ID"));
                appointment.setContactID(resultSet.getInt("Contact_ID"));
                appointments.add(appointment);
            }

            return appointments;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Returns all appointments for a given contact.
     *
     * @param contactID The contact ID.
     * @return A list of appointments for the given contact.
     * @throws Exception A SQL Exception.
     */
    public List<AppointmentModel> GetAllAppointmentsByContactID(int contactID) throws Exception
    {
        String selectCustomerAppointmentsQuery = "SELECT * FROM " + dbName + ".appointments " +
                "WHERE Contact_ID = ?";

        List<AppointmentModel> appointments = new ArrayList<AppointmentModel>();

        try(var statement = connection.prepareStatement(selectCustomerAppointmentsQuery))
        {
            statement.setInt(1, contactID);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                AppointmentModel appointment = new AppointmentModel();
                appointment.setAppointmentID(resultSet.getInt("Appointment_ID"));
                appointment.setTitle(resultSet.getString("Title"));
                appointment.setDescription(resultSet.getString("Description"));
                appointment.setLocation(resultSet.getString("Location"));
                appointment.setAppointmentType(AppointmentTypeFromString(resultSet.getString("Type")));
                appointment.setStartDate(TimestampToZonedDateTime(resultSet.getTimestamp("Start")));
                appointment.setEndDate(TimestampToZonedDateTime(resultSet.getTimestamp("End")));
                appointment.setCustomerID(resultSet.getInt("Customer_ID"));
                appointment.setUserID(resultSet.getInt("User_ID"));
                appointment.setContactID(resultSet.getInt("Contact_ID"));
                appointments.add(appointment);
            }

            return appointments;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Returns all appointments.
     *
     * @return A list of all appointments.
     * @throws Exception SQL Exception.
     */
    public List<AppointmentModel> GetAllAppointments() throws Exception
    {
        String selectAppointmentsQuery = "SELECT * FROM " + dbName + ".appointments";

        List<AppointmentModel> appointments = new ArrayList<AppointmentModel>();

        try(var statement = connection.prepareStatement(selectAppointmentsQuery))
        {
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                AppointmentModel appointment = new AppointmentModel();
                appointment.setAppointmentID(resultSet.getInt("Appointment_ID"));
                appointment.setTitle(resultSet.getString("Title"));
                appointment.setDescription(resultSet.getString("Description"));
                appointment.setLocation(resultSet.getString("Location"));
                appointment.setAppointmentType(AppointmentTypeFromString(resultSet.getString("Type")));
                appointment.setStartDate(TimestampToZonedDateTime(resultSet.getTimestamp("Start")));
                appointment.setEndDate(TimestampToZonedDateTime(resultSet.getTimestamp("End")));
                appointment.setCustomerID(resultSet.getInt("Customer_ID"));
                appointment.setUserID(resultSet.getInt("User_ID"));
                appointment.setContactID(resultSet.getInt("Contact_ID"));
                appointments.add(appointment);
            }

            return appointments;
        }
        catch (Exception ex)
        {
            throw ex;
        }
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

    /**
     * Converts Java.SQL.Timestamp to ZonedDateTime.
     *
     * @param timestamp Timestamp of date to convert.
     * @return The date converted to ZonedDateTime.
     */
    private ZonedDateTime TimestampToZonedDateTime(Timestamp timestamp)
    {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.of("UTC"));
        return zonedDateTime;
    }

    /**
     * Converts ZonedDateTime to Java.SQL.Timestamp.
     *
     * @param zonedDateTime ZonedDateTime of date to convert.
     * @return The date converted to Java.SQL.Timestamp.
     */
    private java.sql.Timestamp ZonedDateTimeToTimestamp(ZonedDateTime zonedDateTime)
    {
        java.sql.Timestamp timestamp = new Timestamp(zonedDateTime.toInstant().toEpochMilli());
        return timestamp;
    }

    //endregion
}
