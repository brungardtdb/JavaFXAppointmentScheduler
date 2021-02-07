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


    /**
     * Adds customer to database.
     *
     * @param customer
     * @return Returns Customer ID.
     */
    public int CreateCustomer(CustomerModel customer) throws Exception
    {
        String insertQuery = "INSERT INTO " + dbName + ".customers " +
                "(`Customer_Name`, `Address`, `Postal_Code`, `Phone`, `Division_ID`)"+
                " VALUES (\'"+ customer.GetCustomerName() +
                "\', \'" + customer.GetCustomerAddress() +
                "\', \'" + customer.GetPostalCode() +
                "\', \'" + customer.GetPhoneNumber() + "\', " +
                customer.GetDivisionID() + ")";

        String userIDQuery = "SELECT Customer_ID FROM " + dbName + ".customers " +
                "WHERE Customer_Name = \'" + customer.GetCustomerName() + "\' " +
                "AND Address = \'" + customer.GetCustomerAddress() + "\' " +
                "AND Postal_Code = \'" + customer.GetPostalCode() + "\' " +
                "AND Phone = \'" + customer.GetPhoneNumber() + "\' " +
                "AND Division_ID = " + customer.GetDivisionID();

        try (var statement = this.connection.prepareStatement(insertQuery))
        {
            int result = statement.executeUpdate();
        }
        try(var statement = this.connection.prepareStatement(userIDQuery))
        {
            int ID;
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                customer.SetCustomerID(resultSet.getInt("Customer_ID"));
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }

        return customer.GetCustomerID();
    }

    /**
     * Gets customer from database.
     *
     * @param customer
     * @return Returns customer if found.
     */
    public CustomerModel GetCustomer(CustomerModel customer) throws Exception
    {
        return null;
    }

    /**
     * Updates customer in database, overrides values with values from customer class parameter.
     *
     * @param customer
     */
    public void UpdateCustomer(CustomerModel customer) throws Exception
    {

    }

    /**
     * Deletes customer from database.
     *
     * @param customer
     */
    public void DeleteCustomer(CustomerModel customer) throws Exception
    {

    }
}
