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
     * @throws Exception
     */
    public int CreateCustomer(CustomerModel customer) throws Exception;

    /**
     * Gets customer from database.
     *
     * @param ID
     * @return Returns customer if found
     * @throws Exception
     */
    public CustomerModel GetCustomerByID(int ID) throws Exception;

    /**
     * Updates customer in database, overrides values with values from customer class parameter.
     *
     * @param customer
     * @return Returns true if record was successfully updated.
     * @throws Exception
     */
    public boolean UpdateCustomer(CustomerModel customer) throws Exception;

    /**
     * Deletes customer from database.
     *
     * @param ID
     * @return Returns true if record was successfully deleted.
     * @throws Exception
     */
    public boolean DeleteCustomerByID(int ID) throws Exception;
}
