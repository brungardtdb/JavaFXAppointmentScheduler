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
        appointment.SetAppointmentID(5);
        Assertions.assertEquals(appointment.GetAppointmentID(), 5);
        Assertions.assertNotEquals(appointment.GetAppointmentID(), 50);
    }

    @Test
    void ContactIDTest()
    {
        appointment.SetContactID(5);
        Assertions.assertEquals(appointment.GetContactID(), 5);
        Assertions.assertNotEquals(appointment.GetContactID(), 50);
    }

    @Test
    void CustomerIDTest()
    {
        appointment.SetCustomerID(5);
        Assertions.assertEquals(appointment.GetCustomerID(), 5);
        Assertions.assertNotEquals(appointment.GetCustomerID(), 50);
    }

    @Test
    void UserIDTest()
    {
        appointment.SetUserID(5);
        Assertions.assertEquals(appointment.GetUserID(), 5);
        Assertions.assertNotEquals(appointment.GetUserID(), 50);
    }

    @Test
    void AppointmentTitleTest()
    {
        String appointmentTitle = "title1";
        String secondAppointmentTitle = "title2";
        appointment.SetTitle(appointmentTitle);
        Assertions.assertEquals(appointment.GetTitle(), appointmentTitle);
        Assertions.assertNotEquals(appointment.GetTitle(), secondAppointmentTitle);
    }

    @Test
    void AppointmentDescriptionTest()
    {
        String appointmentDesc = "Description1";
        String secondAppDesc = "Description2";
        appointment.SetDescription(appointmentDesc);
        Assertions.assertEquals(appointment.GetDescription(), appointmentDesc);
        Assertions.assertNotEquals(appointment.GetDescription(), secondAppDesc);
    }

    @Test
    void AppointmentLocationTest()
    {
        String address = "20 W 34th St, New York, NY";
        String secondAddress = "233 S Wacker Dr, Chicago, IL";
        appointment.SetLocation(address);
        Assertions.assertEquals(appointment.GetLocation(), address);
        Assertions.assertNotEquals(appointment.GetLocation(), secondAddress);
    }

    @Test
    void AppointmentTypeTest()
    {
        appointment.SetAppointmentType(AppointmentType.DEBRIEFING);
        Assertions.assertEquals(appointment.GetAppointmentType(), AppointmentType.DEBRIEFING);
        Assertions.assertNotEquals(appointment.GetAppointmentType(), AppointmentType.PLANNING);
    }

    @Test
    void StartDateTest()
    {
        ZonedDateTime startDate = ZonedDateTime.of(2018, 05, 28,12,0,0,0, ZoneId.of("UTC"));
        ZonedDateTime otherDate = ZonedDateTime.of(2018, 07, 24,12,0,0,0, ZoneId.of("UTC"));
        appointment.SetStartDate(startDate);
        Assertions.assertEquals(appointment.GetStartDate(), startDate);
        Assertions.assertNotEquals(appointment.GetStartDate(), otherDate);
    }

    @Test
    void EndDateTest()
    {
        ZonedDateTime endDate = ZonedDateTime.of(2018, 05, 28,13,0,0,0, ZoneId.of("UTC"));
        ZonedDateTime otherDate = ZonedDateTime.of(2018, 07, 24,12,0,0,0, ZoneId.of("UTC"));
        appointment.SetEndDate(endDate);
        Assertions.assertEquals(appointment.GetEndDate(), endDate);
        Assertions.assertNotEquals(appointment.GetEndDate(), otherDate);
    }
}
