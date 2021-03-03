package app.DataAccess.Interfaces;

import app.UserData.Models.CustomerModel;

import java.util.List;

/**
 * Interface for customer data CRUD operations
 */
public interface ICustomerData
{
    /**
     * Adds customer to database.
     *
     * @param customer The customer object. (everything must be filled out except ID)
     * @return The customer ID.
     * @throws Exception
     */
    public int CreateCustomer(CustomerModel customer) throws Exception;

    /**
     * Gets customer from database.
     *
     * @param ID The customer ID.
     * @return The customer if found.
     * @throws Exception
     */
    public CustomerModel GetCustomerByID(int ID) throws Exception;

    /**
     * Updates customer in database, overrides values with values from customer class parameter.
     *
     * @param customer The customer object.
     * @return True if record was successfully updated.
     * @throws Exception
     */
    public boolean UpdateCustomer(CustomerModel customer) throws Exception;

    /**
     * Deletes customer from database.
     *
     * @param ID The customer ID.
     * @return Returns true if record was successfully deleted.
     * @throws Exception
     */
    public boolean DeleteCustomerByID(int ID) throws Exception;

    /**
     * Returns all customers.
     *
     * @return A list of all customers.
     * @throws Exception
     */
    public List<CustomerModel> GetAllCustomers() throws Exception;
}
