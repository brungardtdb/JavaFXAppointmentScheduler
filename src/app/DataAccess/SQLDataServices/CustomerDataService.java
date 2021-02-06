package DataAccess.SQLDataServices;

import DataAccess.Interfaces.ICustomerData;
import UserData.Models.CustomerModel;

import java.sql.Connection;

/**
 * Class that manages customer data in SQL database.
 */
public class CustomerDataService implements ICustomerData
{
    private final Connection connection;

    /**
     * Constructor for SQL customer data service, takes SQL connection as argument.
     *
     * @param connection
     */
    public CustomerDataService(Connection connection)
    {
        this.connection = connection;
    }


    /**
     * Adds customer to database.
     *
     * @param customer
     */
    public void CreateCustomer(CustomerModel customer) {

    }

    /**
     * Gets customer from database.
     *
     * @param customer
     * @return Returns customer if found.
     */
    public CustomerModel GetCustomer(CustomerModel customer) {
        return null;
    }

    /**
     * Updates customer in database, overrides values with values from customer class parameter.
     *
     * @param customer
     */
    public void UpdateCustomer(CustomerModel customer) {

    }

    /**
     * Deletes customer from database.
     *
     * @param customer
     */
    public void DeleteCustomer(CustomerModel customer) {

    }
}
