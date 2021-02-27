package DataAccess.MYSQLDataServices;

import DataAccess.Interfaces.ICustomerData;
import DataAccess.Interfaces.IAppointmentData;
import DataAccess.MYSQLDataServices.AppointmentDataService;
import UserData.Models.CustomerModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages customer data in SQL database.
 */
public class CustomerDataService implements ICustomerData
{
    private final Connection connection;
    private final String dbName;

    /**
     * Constructor for SQL customer data service, takes SQL connection and DB name as arguments.
     *
     * @param connection The database connection.
     * @param dbName The database name.
     */
    public CustomerDataService(Connection connection, String dbName)
    {
        this.connection = connection;
        this.dbName = dbName;
    }

    //region CRUD methods

    /**
     * Adds customer to database.
     *
     * @param customer The customer object. (everything must be filled out except ID)
     * @return The customer ID.
     * @throws Exception SQL Exception.
     */
    public int CreateCustomer(CustomerModel customer) throws Exception
    {
        int ID = GetNextID();
        customer.setCustomerID(ID);

        String insertCustomerQuery = "INSERT INTO " + dbName + ".customers " +
                "(`Customer_ID`, `Customer_Name`, `Address`, `Postal_Code`, `Phone`, `Division_ID`)"+
                " VALUES (?, ?, ?, ?, ?, ?)";

        try (var statement = this.connection.prepareStatement(insertCustomerQuery))
        {
            statement.setInt(1, customer.getCustomerID());
            statement.setString(2, customer.getCustomerName());
            statement.setString(3, customer.getCustomerAddress());
            statement.setString(4, customer.getPostalCode());
            statement.setString(5, customer.getPhoneNumber());
            statement.setInt(6, customer.getDivisionID());

            int result = statement.executeUpdate();

            if (result > 0)
            {
                return customer.getCustomerID();
            }

            throw new Exception("User could not be created!");

        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Gets customer from database.
     *
     * @param ID The customer ID.
     * @return The customer if found.
     * @throws Exception SQL Exception.
     */
    public CustomerModel GetCustomerByID(int ID) throws Exception
    {
        String selectCustomerQuery = "SELECT * " +
                "FROM " + dbName + ".customers " +
                "WHERE Customer_ID = ?";

        CustomerModel customer = new CustomerModel();

        try(var statement = this.connection.prepareStatement(selectCustomerQuery))
        {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                customer.setCustomerID(resultSet.getInt("Customer_ID"));
                customer.setCustomerName(resultSet.getString("Customer_Name"));
                customer.setCustomerAddress(resultSet.getString("Address"));
                customer.setPostalCode(resultSet.getString("Postal_Code"));
                customer.setPhoneNumber(resultSet.getString("Phone"));
                customer.setDivisionID(resultSet.getInt("Division_ID"));
            }

            return customer;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Updates customer in database, overrides values with values from customer class parameter.
     *
     * @param customer The customer object.
     * @return True if record was successfully updated.
     * @throws Exception SQL Exception.
     */
    public boolean UpdateCustomer(CustomerModel customer) throws Exception
    {
        String updateCustomerQuery = "UPDATE " + dbName + ".customers " +
                "SET " +
                "Customer_Name = ?, " +
                "Address = ?, " +
                "Postal_Code = ?, " +
                "Phone = ?, " +
                "Division_ID = ? " +
                "WHERE Customer_ID = ? ";

        try(var statement = connection.prepareStatement(updateCustomerQuery))
        {
            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getCustomerAddress());
            statement.setString(3, customer.getPostalCode());
            statement.setString(4, customer.getPhoneNumber());
            statement.setInt(5, customer.getDivisionID());
            statement.setInt(6, customer.getCustomerID());

            int result = statement.executeUpdate();

            return  (result > 0);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Deletes customer from database.
     *
     * @param ID The customer ID.
     * @return Returns true if record was successfully deleted.
     * @throws Exception SQL Exception.
     */
    public boolean DeleteCustomerByID(int ID) throws Exception
    {
        String deleteCustomerQuery = "DELETE " +
                "FROM " + dbName + ".customers " +
                "WHERE Customer_ID = ?";

        // All customer appointments must be deleted before customers are deleted.
        IAppointmentData appointmentDataService = new AppointmentDataService(this.connection, dbName);
        appointmentDataService.DeleteAllCustomerAppointments(ID);

        try(var statement = connection.prepareStatement(deleteCustomerQuery))
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
     * Returns all customers.
     *
     * @return A list of all customers.
     * @throws Exception
     */
    public List<CustomerModel> GetAllCustomers() throws Exception
    {
        String selectAllCustomers = "SELECT * " +
                "FROM " + dbName + ".customers";

        List<CustomerModel> customers = new ArrayList<CustomerModel>();

        try(var statement = connection.prepareStatement(selectAllCustomers))
        {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                CustomerModel customer = new CustomerModel();
                customer.setCustomerID(resultSet.getInt("Customer_ID"));
                customer.setCustomerName(resultSet.getString("Customer_Name"));
                customer.setCustomerAddress(resultSet.getString("Address"));
                customer.setPostalCode(resultSet.getString("Postal_Code"));
                customer.setPhoneNumber(resultSet.getString("Phone"));
                customer.setDivisionID(resultSet.getInt("Division_ID"));
                customers.add(customer);
            }

            return customers;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    //endregion

    // region helper methods

    /**
     * Helper method to get max customer ID from database, max ID is incremented to generate next sequential customer ID.
     * @return The next sequential customer ID.
     * @throws Exception SQL Exception.
     */
    private int GetNextID() throws Exception
    {
        /* This is kind of a hacky way to do this, but the auto-increment functionality in the database
        does not take into account objects that are being deleted and I want to avoid big jumps in IDs,
        but I also cannot make changes to the database.
        */

        String userIDQuery = "SELECT MAX(Customer_ID) FROM " + dbName + ".customers ";
        int ID = 1;

        try(var statement = this.connection.prepareStatement(userIDQuery))
        {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ID = resultSet.getInt("MAX(Customer_ID)");

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

    // endregion

}
