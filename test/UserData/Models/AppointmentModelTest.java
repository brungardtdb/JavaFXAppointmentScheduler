package UserData.Models;

import UserData.Enums.AppointmentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
        appointment.SetUserId(5);
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
        Date startDate = new Date(System.currentTimeMillis());
        Date otherDate = new GregorianCalendar(2018, Calendar.JULY, 24).getTime();
        appointment.SetStartDate(startDate);
        Assertions.assertEquals(appointment.GetStartDate(), startDate);
        Assertions.assertNotEquals(appointment.GetStartDate(), otherDate);
    }

    @Test
    void EndDateTest()
    {
        Date endDate = new Date(System.currentTimeMillis());
        Date otherDate = new GregorianCalendar(2018, Calendar.JULY, 24).getTime();
        appointment.SetEndDate(endDate);
        Assertions.assertEquals(appointment.GetEndDate(), endDate);
        Assertions.assertNotEquals(appointment.GetEndDate(), otherDate);
    }
}
