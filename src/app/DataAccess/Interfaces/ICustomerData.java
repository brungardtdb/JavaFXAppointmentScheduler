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
    public int CreateCustomer(CustomerModel customer);

    /**
     * Gets customer from database.
     *
     * @param customer
     * @return Returns customer if found.
     */
    public CustomerModel GetCustomer(CustomerModel customer);

    /**
     * Updates customer in database, overrides values with values from customer class parameter.
     *
     * @param customer
     */
    public void UpdateCustomer(CustomerModel customer);

    /**
     * Deletes customer from database.
     *
     * @param customer
     */
    public void DeleteCustomer(CustomerModel customer);
}
