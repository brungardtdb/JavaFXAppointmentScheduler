package DataAccess.UserData.Models;

/**
 * Model for customer data.
 */
public class CustomerModel
{
    private int customerID;

    /**
     * Setter for customer ID.
     * @param id
     */
    public void SetCustomerID(int id) { this.customerID = id; }

    /**
     * Getter for customer ID.
     * @return Returns customer ID.
     */
    public int GetCustomerID() { return this.customerID; }

    private String customerName;

    /**
     * Setter for customer name.
     * @param customerName
     */
    public void SetCustomerName(String customerName) { this.customerName = customerName; }

    /**
     * Getter for customer name.
     * @return Returns customer name.
     */
    public String GetCustomerName() { return this.customerName; }

    private String customerAddress;

    /**
     * Setter for customer address.
     * @param customerAddress
     */
    public void SetCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }

    /**
     * Getter for customer address.
     * @return Returns customer address.
     */
    public String GetCustomerAddress() { return this.customerAddress; }

    private String postalCode;

    /**
     * Setter for customer postal code.
     * @param postalCode
     */
    public void SetPostalCode(String postalCode) { this.postalCode = postalCode; }

    /**
     * Getter for customer postal code.
     * @return Returns postal code.
     */
    public String GetPostalCode() { return this.postalCode; }

    private String phoneNumber;

    /**
     * Setter for customer phone number.
     * @param phoneNumber
     */
    public void SetPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    /**
     * Getter for customer phone number.
     * @return Returns customer phone number.
     */
    public String GetPhoneNumber() { return this.phoneNumber; }
}
