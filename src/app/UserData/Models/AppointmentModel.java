package UserData.Models;

import UserData.Enums.AppointmentType;
import java.util.Date;

/**
 * Model for appointment data.
 */
public class AppointmentModel
{
    private int appointmentID;

    /**
     * Setter for appointment ID.
     * @param id
     */
    public void  SetAppointmentID(int id) { this.appointmentID = id; }

    /**
     * Getter for appointment ID.
     * @return Returns appointment ID.
     */
    public int GetAppointmentID() { return this.appointmentID; }

    private int contactID;

    /**
     * Setter for contact ID.
     * @param id
     */
    public void SetContactID(int id) { this.contactID = id; }

    /**
     * Getter for contact ID.
     * @return Returns contact ID.
     */
    public int GetContactID() { return this.contactID; }

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

    private int userID;

    /**
     * Setter for user ID.
     * @param id
     */
    public void SetUserId(int id) { this.userID = id; }

    /**
     * Getter for user ID.
     * @return Returns user ID.
     */
    public int GetUserID() { return this.userID; }

    private String title;

    /**
     * Setter for appointment title.
     * @param title
     */
    public void SetTitle(String title) { this.title = title; }

    /**
     * Getter for appointment title.
     * @return Returns appointment title.
     */
    public String GetTitle() { return this.title; }

    private String description;

    /**
     * Setter for appointment description.
     * @param description
     */
    public void SetDescription(String description) { this.description = description; }

    /**
     * Getter for appointment description.
     * @return Returns appointment description.
     */
    public String GetDescription() { return this.description; }

    private String location;

    /**
     * Setter for appointment location.
     * @param location
     */
    public void SetLocation(String location) { this.location = location; }

    /**
     * Getter for appointment location.
     * @return Returns appointment location.
     */
    public String GetLocation() { return this.location; }

    private AppointmentType appointmentType;

    /**
     * Setter for appointment type.
     * @param appointmentType
     */
    public void SetAppointmentType(AppointmentType appointmentType) { this.appointmentType = appointmentType; }

    /**
     * Getter for appointment type.
     * @return Returns appointment type.
     */
    public AppointmentType GetAppointmentType() { return this.appointmentType; }

    private Date startDate;

    /**
     * Setter for appointment start date.
     * @param startDate
     */
    public void SetStartDate(Date startDate) { this.startDate = startDate; }

    /**
     * Getter for appointment start date.
     * @return Returns appointment start date.
     */
    public Date GetStartDate() { return this.startDate; }

    private Date endDate;

    /**
     * Setter for appointment end date.
     * @param endDate
     */
    public void SetEndDate(Date endDate) { this.endDate = endDate; }

    /**
     * Getter for appointment end date.
     * @return returns appointment end date.
     */
    public Date GetEndDate() { return this.endDate; }
}
