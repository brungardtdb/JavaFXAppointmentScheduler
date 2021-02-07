package DataAccess.Interfaces;

import UserData.Models.CustomerModel;

/**
 * Interface for customer data CRUD operations
 */
public interface ICustomerData
{

    /**
     * Adds customer to database.
     *
     * @param customer
     * @return Returns customer ID.
     */
    public int CreateCustomer(CustomerModel customer) throws Exception;

    /**
     * Gets customer from database.
     *
     * @param customer
     * @return Returns customer if found.
     */
    public CustomerModel GetCustomer(CustomerModel customer) throws Exception;

    /**
     * Updates customer in database, overrides values with values from customer class parameter.
     *
     * @param customer
     */
    public void UpdateCustomer(CustomerModel customer) throws Exception;

    /**
     * Deletes customer from database.
     *
     * @param customer
     */
    public void DeleteCustomer(CustomerModel customer) throws Exception;
}
