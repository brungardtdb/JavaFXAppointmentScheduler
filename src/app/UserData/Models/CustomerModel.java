package app.UserData.Models;

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
     * @param id The customer ID
     */
    public void setCustomerID(int id) { this.customerID = id; }

    /**
     * Getter for customer ID.
     *
     * @return Returns customer ID.
     */
    public int getCustomerID() { return this.customerID; }

    private String customerName;

    /**
     * Setter for customer name.
     *
     * @param customerName The customer name.
     */
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    /**
     * Getter for customer name.
     *
     * @return Returns customer name.
     */
    public String getCustomerName() { return this.customerName; }

    private String customerAddress;

    /**
     * Setter for customer address.
     *
     * @param customerAddress The customer address.
     */
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }

    /**
     * Getter for customer address.
     *
     * @return Returns customer address.
     */
    public String getCustomerAddress() { return this.customerAddress; }

    private String postalCode;

    /**
     * Setter for customer postal code.
     *
     * @param postalCode The customer postal code.
     */
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    /**
     * Getter for customer postal code.
     *
     * @return Returns postal code.
     */
    public String getPostalCode() { return this.postalCode; }

    private String phoneNumber;

    /**
     * Setter for customer phone number.
     *
     * @param phoneNumber The customer phone number.
     */
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    /**
     * Getter for customer phone number.
     *
     * @return Returns customer phone number.
     */
    public String getPhoneNumber() { return this.phoneNumber; }

    private ArrayList<AppointmentModel> appointments = new ArrayList<AppointmentModel>();

    /**
     * Adds appointment to customer appointments.
     *
     * @param appointment A new customer appointment.
     */
    public void addAppointment(AppointmentModel appointment) { appointments.add(appointment); }

    /**
     * Gets appointment based on ID.
     * The lambda in this method filters the list, searching for a matching ID.
     * I decided to use the stream and lambda combination in this method
     * because it was more succinct and readable, avoiding the need to loop through
     * the collection with some kind of foreach loop.
     *
     * @param id The appointment ID.
     * @return Returns appointment or null if no appointment is found.
     */
    public AppointmentModel getAppointment(int id)
    {
        try
        {
            return appointments.stream()
                    .filter(x -> x.getAppointmentID() == id)
                    .findFirst()
                    .get();
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    /**
     * Removes appointment from appointments by ID if appointment is found.
     * The lambda in this method filters through the appointments, and removes the appointment if an ID match is found.
     * Another example of a lambda being more succinct, here I was able to remove
     * the appointment using a single line instead of an if statement nested inside of a loop.
     *
     * @param id The appointment ID.
     */
    public void removeAppointment(int id)
    {
        appointments.removeIf(x -> x.getAppointmentID() == id);
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

    private int divisionID;

    /**
     * Setter for division ID.
     *
     * @param ID The division ID.
     */
    public void setDivisionID(int ID)
    {
        this.divisionID = ID;
    }

    /**
     * Getter for division ID.
     *
     * @return Returns division ID.
     */
    public int getDivisionID()
    {
        return this.divisionID;
    }
}
