package app.UserData.Models;

import app.UserData.Enums.AppointmentType;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
    public void  setAppointmentID(int id) { this.appointmentID = id; }

    /**
     * Getter for appointment ID.
     *
     * @return The appointment ID.
     */
    public int getAppointmentID() { return this.appointmentID; }

    private int contactID;

    /**
     * Setter for contact ID.
     *
     * @param id The contact ID.
     */
    public void setContactID(int id) { this.contactID = id; }

    /**
     * Getter for contact ID.
     *
     * @return The contact ID.
     */
    public int getContactID() { return this.contactID; }

    private int customerID;

    /**
     * Setter for customer ID.
     *
     * @param id The customer ID.
     */
    public void setCustomerID(int id) { this.customerID = id; }

    /**
     * Getter for customer ID.
     *
     * @return The customer ID.
     */
    public int getCustomerID() { return this.customerID; }

    private int userID;

    /**
     * Setter for user ID.
     *
     * @param id The user ID.
     */
    public void setUserID(int id) { this.userID = id; }

    /**
     * Getter for user ID.
     *
     * @return The user ID.
     */
    public int getUserID() { return this.userID; }

    private String title;

    /**
     * Setter for appointment title.
     *
     * @param title The appointment title.
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Getter for appointment title.
     *
     * @return The appointment title.
     */
    public String getTitle() { return this.title; }

    private String description;

    /**
     * Setter for appointment description.
     *
     * @param description The appointment description.
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Getter for appointment description.
     *
     * @return The appointment description.
     */
    public String getDescription() { return this.description; }

    private String location;

    /**
     * Setter for appointment location.
     *
     * @param location The appointment location.
     */
    public void setLocation(String location) { this.location = location; }

    /**
     * Getter for appointment location.
     *
     * @return The appointment location.
     */
    public String getLocation() { return this.location; }

    private AppointmentType appointmentType;

    /**
     * Setter for appointment type.
     *
     * @param appointmentType The appointment type.
     */
    public void setAppointmentType(AppointmentType appointmentType) { this.appointmentType = appointmentType; }

    /**
     * Getter for appointment type.
     *
     * @return The appointment type.
     */
    public AppointmentType getAppointmentType() { return this.appointmentType; }

    private ZonedDateTime startDate;

    /**
     * Setter for appointment start date.
     *
     * @param zonedDateTime The appointment start date.
     */
    public void setStartDate(ZonedDateTime zonedDateTime) { this.startDate = zonedDateTime; }

    /**
     * Getter for appointment start date.
     *
     * @return The appointment start date.
     */
    public ZonedDateTime getStartDate() { return this.startDate; }

    /**
     * Method for getting start date in local date time.
     *
     * @return Local formatted date time of start date.
     */
    public String getFormattedLocalStartDate()
    {
        return GetFormattedDateTime(this.startDate.toLocalDateTime());
    }

    private ZonedDateTime endDate;


    /**
     * Setter for appointment end date.
     *
     * @param zonedDateTime The end date for appointment.
     */
    public void setEndDate(ZonedDateTime zonedDateTime) { this.endDate = zonedDateTime; }


    /**
     * Getter for appointment end date.
     *
     * @return The end date for appointment.
     */
    public ZonedDateTime getEndDate() { return this.endDate; }

    /**
     * Method for getting end date in local date time.
     *
     * @return End date in formatted local date time.
     */
    public String getFormattedLocalEndDate()
    {
        return GetFormattedDateTime(this.endDate.toLocalDateTime());
    }

    /**
     * Utility for formatting local date and time into a readable string for displaying to the end user.
     *
     * @param localDateTime The local date time for the appointment.
     * @return A formatted string containing the local date and time in a readable format.
     */
    private String GetFormattedDateTime(LocalDateTime localDateTime)
    {
        String formattedDateTime = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
        formattedDateTime += formatter.format(localDateTime.toLocalDate());
        formattedDateTime += " " + localDateTime.toLocalTime();
        return formattedDateTime;
    }
}