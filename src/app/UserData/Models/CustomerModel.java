package UserData.Models;

import java.util.ArrayList;

/**
 * Model for customer data.
 */
public class CustomerModel
{
    private int customerID;

    /**
     * Setter for customer ID.
     *
     * @param id
     */
    public void SetCustomerID(int id) { this.customerID = id; }

    /**
     * Getter for customer ID.
     *
     * @return Returns customer ID.
     */
    public int GetCustomerID() { return this.customerID; }

    private String customerName;

    /**
     * Setter for customer name.
     *
     * @param customerName
     */
    public void SetCustomerName(String customerName) { this.customerName = customerName; }

    /**
     * Getter for customer name.
     *
     * @return Returns customer name.
     */
    public String GetCustomerName() { return this.customerName; }

    private String customerAddress;

    /**
     * Setter for customer address.
     *
     * @param customerAddress
     */
    public void SetCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }

    /**
     * Getter for customer address.
     *
     * @return Returns customer address.
     */
    public String GetCustomerAddress() { return this.customerAddress; }

    private String postalCode;

    /**
     * Setter for customer postal code.
     *
     * @param postalCode
     */
    public void SetPostalCode(String postalCode) { this.postalCode = postalCode; }

    /**
     * Getter for customer postal code.
     *
     * @return Returns postal code.
     */
    public String GetPostalCode() { return this.postalCode; }

    private String phoneNumber;

    /**
     * Setter for customer phone number.
     *
     * @param phoneNumber
     */
    public void SetPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    /**
     * Getter for customer phone number.
     *
     * @return Returns customer phone number.
     */
    public String GetPhoneNumber() { return this.phoneNumber; }

    private ArrayList<AppointmentModel> appointments = new ArrayList<AppointmentModel>();

    /**
     * Adds appointment to customer appointments.
     *
     * @param appointment
     */
    public void AddAppointment(AppointmentModel appointment) { appointments.add(appointment); }

    /**
     * Gets appointment based on ID.
     * I decided to use the stream and lambda combination in this method
     * because it was more succinct and readable, avoiding the need to loop through
     * the collection with some kind of foreach loop. I'm pretty comfortable with Linq in C#
     * and this syntax is a close match for Java.
     *
     * @param id
     * @return Returns appointment or null if no appointment is found.
     */
    public AppointmentModel GetAppointment(int id)
    {
        try
        {
            return appointments.stream()
                    .filter(x -> x.GetAppointmentID() == id)
                    .findFirst()
                    .get();
        } catch (Exception ex)
        {
            return null;
        }
    }

    /**
     * Removes appointment from appointments by ID if appointment is found.
     * Another example of a lambda being more succinct, here I was able to remove
     * the appointment using a single line instead of an if statement nested inside of a loop.
     *
     * @param id
     */
    public void RemoveAppointment(int id)
    {
        appointments.removeIf(x -> x.GetAppointmentID() == id);
    }

    /**
     * Method for getting customer appointments.
     *
     * @return Returns list of customer appointments.
     */
    public ArrayList<AppointmentModel> getAllAppointments()
    {
        return this.appointments;
    }
}
