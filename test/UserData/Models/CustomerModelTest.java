package UserData.Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerModelTest
{
    CustomerModel customer = new CustomerModel();
    @Test
    void customerIDTest()
    {
        customer.SetCustomerID(5);
        Assertions.assertEquals(customer.GetCustomerID(), 5);
        Assertions.assertNotEquals(customer.GetCustomerID(), 50);
    }

    @Test
    void customerNameTest()
    {
        customer.SetCustomerName("Julian Assange");
        Assertions.assertEquals(customer.GetCustomerName(), "Julian Assange");
        Assertions.assertNotEquals(customer.GetCustomerName(), "John Doe");
    }

    @Test
    void customerAddressTest()
    {
        String address = "20 W 34th St, New York, NY";
        String secondAddress = "233 S Wacker Dr, Chicago, IL";
        customer.SetCustomerAddress((address));
        Assertions.assertEquals(customer.GetCustomerAddress(), address);
        Assertions.assertNotEquals(customer.GetCustomerAddress(), secondAddress);
    }

    @Test
    void postalCodeTest()
    {
        String postalCode = "60606";
        String secondPostalCode = "10112";
        customer.SetPostalCode(postalCode);
        Assertions.assertEquals(customer.GetPostalCode(), postalCode);
        Assertions.assertNotEquals(customer.GetPostalCode(), secondPostalCode);
    }

    @Test
    void phoneNumberTest()
    {
        String phoneNumber = "777-777-7777";
        String secondPhoneNumber = "555-555-5555";
        customer.SetPhoneNumber(phoneNumber);
        Assertions.assertEquals(customer.GetPhoneNumber(), phoneNumber);
        Assertions.assertNotEquals(customer.GetPhoneNumber(), secondPhoneNumber);
    }

    @Test
    void customerAppointmentTest()
    {
        AppointmentModel appointment = new AppointmentModel();
        appointment.SetAppointmentID(5);
        customer.AddAppointment(appointment);
        Assertions.assertEquals(customer.getAllAppointments().size(), 1);
        Assertions.assertNull(customer.GetAppointment(6));
        Assertions.assertNotNull(customer.GetAppointment(5));
        customer.RemoveAppointment(5);
        Assertions.assertEquals(customer.getAllAppointments().size(), 0);
    }
}
