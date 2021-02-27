package UserData.Models;

import UserData.Enums.AppointmentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AppointmentModelTest
{
    AppointmentModel appointment = new AppointmentModel();

    @Test
    void AppointmentIDTest()
    {
        appointment.setAppointmentID(5);
        Assertions.assertEquals(appointment.getAppointmentID(), 5);
        Assertions.assertNotEquals(appointment.getAppointmentID(), 50);
    }

    @Test
    void ContactIDTest()
    {
        appointment.setContactID(5);
        Assertions.assertEquals(appointment.getContactID(), 5);
        Assertions.assertNotEquals(appointment.getContactID(), 50);
    }

    @Test
    void CustomerIDTest()
    {
        appointment.setCustomerID(5);
        Assertions.assertEquals(appointment.getCustomerID(), 5);
        Assertions.assertNotEquals(appointment.getCustomerID(), 50);
    }

    @Test
    void UserIDTest()
    {
        appointment.setUserID(5);
        Assertions.assertEquals(appointment.getUserID(), 5);
        Assertions.assertNotEquals(appointment.getUserID(), 50);
    }

    @Test
    void AppointmentTitleTest()
    {
        String appointmentTitle = "title1";
        String secondAppointmentTitle = "title2";
        appointment.setTitle(appointmentTitle);
        Assertions.assertEquals(appointment.getTitle(), appointmentTitle);
        Assertions.assertNotEquals(appointment.getTitle(), secondAppointmentTitle);
    }

    @Test
    void AppointmentDescriptionTest()
    {
        String appointmentDesc = "Description1";
        String secondAppDesc = "Description2";
        appointment.setDescription(appointmentDesc);
        Assertions.assertEquals(appointment.getDescription(), appointmentDesc);
        Assertions.assertNotEquals(appointment.getDescription(), secondAppDesc);
    }

    @Test
    void AppointmentLocationTest()
    {
        String address = "20 W 34th St, New York, NY";
        String secondAddress = "233 S Wacker Dr, Chicago, IL";
        appointment.setLocation(address);
        Assertions.assertEquals(appointment.getLocation(), address);
        Assertions.assertNotEquals(appointment.getLocation(), secondAddress);
    }

    @Test
    void AppointmentTypeTest()
    {
        appointment.setAppointmentType(AppointmentType.DEBRIEFING);
        Assertions.assertEquals(appointment.getAppointmentType(), AppointmentType.DEBRIEFING);
        Assertions.assertNotEquals(appointment.getAppointmentType(), AppointmentType.PLANNING);
    }

    @Test
    void StartDateTest()
    {
        ZonedDateTime startDate = ZonedDateTime.of(2018, 05, 28,12,0,0,0, ZoneId.of("UTC"));
        ZonedDateTime otherDate = ZonedDateTime.of(2018, 07, 24,12,0,0,0, ZoneId.of("UTC"));
        appointment.setStartDate(startDate);
        Assertions.assertEquals(appointment.getStartDate(), startDate);
        Assertions.assertNotEquals(appointment.getStartDate(), otherDate);
    }

    @Test
    void EndDateTest()
    {
        ZonedDateTime endDate = ZonedDateTime.of(2018, 05, 28,13,0,0,0, ZoneId.of("UTC"));
        ZonedDateTime otherDate = ZonedDateTime.of(2018, 07, 24,12,0,0,0, ZoneId.of("UTC"));
        appointment.setEndDate(endDate);
        Assertions.assertEquals(appointment.getEndDate(), endDate);
        Assertions.assertNotEquals(appointment.getEndDate(), otherDate);
    }
}
