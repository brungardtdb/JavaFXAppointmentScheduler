package DataAccess.MYSQLDataServices;

import DataAccess.Interfaces.ICustomerData;
import UserData.Models.CustomerModel;

import java.sql.Connection;
import java.sql.ResultSet;

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
     * @param connection
     * @param dbName
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
     * @param customer
     * @return
     * @throws Exception Throws SQL Exception.
     */
    public int CreateCustomer(CustomerModel customer) throws Exception
    {
        int ID = GetNextID();
        customer.SetCustomerID(ID);

        String insertQuery = "INSERT INTO " + dbName + ".customers " +
                "(`Customer_ID`, `Customer_Name`, `Address`, `Postal_Code`, `Phone`, `Division_ID`)"+
                " VALUES (\'"+ customer.GetCustomerID() +
                "\', \'" + customer.GetCustomerName() +
                "\', \'" + customer.GetCustomerAddress() +
                "\', \'" + customer.GetPostalCode() +
                "\', \'" + customer.GetPhoneNumber() + "\', " +
                customer.GetDivisionID() + ")";

        try (var statement = this.connection.prepareStatement(insertQuery))
        {
            int result = statement.executeUpdate();

            if (result > 0)
            {
                return customer.GetCustomerID();
            }
            else
            {
                throw new Exception("User could not be created!");
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Gets customer from database.
     *
     * @param ID
     * @return Returns customer if found
     * @throws Exception Throws SQL Exception.
     */
    public CustomerModel GetCustomerByID(int ID) throws Exception
    {
        String query = "SELECT * " +
                "FROM " + dbName + ".customers " +
                "WHERE Customer_ID = " + ID;

        CustomerModel customer = new CustomerModel();

        try(var statement = this.connection.prepareStatement(query))
        {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                customer.SetCustomerID(resultSet.getInt("Customer_ID"));
                customer.SetCustomerName(resultSet.getString("Customer_Name"));
                customer.SetCustomerAddress(resultSet.getString("Address"));
                customer.SetPostalCode(resultSet.getString("Postal_Code"));
                customer.SetPhoneNumber(resultSet.getString("Phone"));
                customer.SetDivisionID(resultSet.getInt("Division_ID"));
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }

        return customer;
    }

    /**
     * Updates customer in database, overrides values with values from customer class parameter.
     *
     * @param customer
     * @return Returns true if record was successfully updated.
     * @throws Exception Throws SQL Exception.
     */
    public boolean UpdateCustomer(CustomerModel customer) throws Exception
    {
        String updateQuery = "UPDATE " + dbName + ".customers " +
                "SET " +
                "Customer_Name = \'" + customer.GetCustomerName() + "\', " +
                "Address = \'" + customer.GetCustomerAddress() + "\', " +
                "Postal_Code = \'" + customer.GetPostalCode() + "\', " +
                "Phone = \'" + customer.GetPhoneNumber() + "\', " +
                "Division_ID = \'" + customer.GetDivisionID() + "\' " +
                "WHERE Customer_ID = " + customer.GetCustomerID();

        try(var statement = connection.prepareStatement(updateQuery))
        {
            int result = statement.executeUpdate();

            return  (result > 0);
        }
    }

    /**
     * Deletes customer from database.
     *
     * @param ID
     * @throws Exception
     */
    public boolean DeleteCustomerByID(int ID) throws Exception
    {
        String deleteQuery = "DELETE " +
                "FROM " + dbName + ".customers " +
                "WHERE Customer_ID = " + ID;

        try(var statement = connection.prepareStatement(deleteQuery))
        {
            int result = statement.executeUpdate();

            return (result > 0);
        }
    }

    //endregion

    // region helper methods

    /**
     * Helper method to get max customer ID from database, max ID is incremented to generate next sequential customer ID.
     * @return Returns next sequential customer ID.
     * @throws Exception Throws SQL Exception.
     */
    private int GetNextID() throws Exception
    {
        String userIDQuery = "SELECT MAX(Customer_ID) FROM " + dbName + ".customers ";
        int ID = 1;

        try(var statement = this.connection.prepareStatement(userIDQuery))
        {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ID = resultSet.getInt("MAX(Customer_ID)");

                if (resultSet.wasNull()) {
                    ID = 1;
                } else {
                    ID++;
                }
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
