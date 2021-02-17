package UserData.Models;

import UserData.Enums.AppointmentType;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Model for appointment data.
 */
public class AppointmentModel
{
    private int appointmentID;

    /**
     * Setter for appointment ID.
     *
     * @param id The appointment ID.
     */
    public void  SetAppointmentID(int id) { this.appointmentID = id; }

    /**
     * Getter for appointment ID.
     *
     * @return The appointment ID.
     */
    public int GetAppointmentID() { return this.appointmentID; }

    private int contactID;

    /**
     * Setter for contact ID.
     *
     * @param id The contact ID.
     */
    public void SetContactID(int id) { this.contactID = id; }

    /**
     * Getter for contact ID.
     *
     * @return The contact ID.
     */
    public int GetContactID() { return this.contactID; }

    private int customerID;

    /**
     * Setter for customer ID.
     *
     * @param id The customer ID.
     */
    public void SetCustomerID(int id) { this.customerID = id; }

    /**
     * Getter for customer ID.
     *
     * @return The customer ID.
     */
    public int GetCustomerID() { return this.customerID; }

    private int userID;

    /**
     * Setter for user ID.
     *
     * @param id The user ID.
     */
    public void SetUserID(int id) { this.userID = id; }

    /**
     * Getter for user ID.
     *
     * @return The user ID.
     */
    public int GetUserID() { return this.userID; }

    private String title;

    /**
     * Setter for appointment title.
     *
     * @param title The appointment title.
     */
    public void SetTitle(String title) { this.title = title; }

    /**
     * Getter for appointment title.
     *
     * @return The appointment title.
     */
    public String GetTitle() { return this.title; }

    private String description;

    /**
     * Setter for appointment description.
     *
     * @param description The appointment description.
     */
    public void SetDescription(String description) { this.description = description; }

    /**
     * Getter for appointment description.
     *
     * @return The appointment description.
     */
    public String GetDescription() { return this.description; }

    private String location;

    /**
     * Setter for appointment location.
     *
     * @param location The appointment location.
     */
    public void SetLocation(String location) { this.location = location; }

    /**
     * Getter for appointment location.
     *
     * @return The appointment location.
     */
    public String GetLocation() { return this.location; }

    private AppointmentType appointmentType;

    /**
     * Setter for appointment type.
     *
     * @param appointmentType The appointment type.
     */
    public void SetAppointmentType(AppointmentType appointmentType) { this.appointmentType = appointmentType; }

    /**
     * Getter for appointment type.
     *
     * @return The appointment type.
     */
    public AppointmentType GetAppointmentType() { return this.appointmentType; }

    private ZonedDateTime startDate;


    /**
     * Setter for appointment start date.
     *
     * @param zonedDateTime The appointment start date.
     */
    public void SetStartDate(ZonedDateTime zonedDateTime) { this.startDate = zonedDateTime; }

    /**
     * Getter for appointment start date.
     *
     * @return The appointment start date.
     */
    public ZonedDateTime GetStartDate() { return this.startDate; }

    private ZonedDateTime endDate;


    /**
     * Setter for appointment end date.
     *
     * @param zonedDateTime The end date for appointment.
     */
    public void SetEndDate(ZonedDateTime zonedDateTime) { this.endDate = zonedDateTime; }


    /**
     * Getter for appointment end date.
     *
     * @return The end date for appointment.
     */
    public ZonedDateTime GetEndDate() { return this.endDate; }
}
