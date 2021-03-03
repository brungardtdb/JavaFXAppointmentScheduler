package app.UserData.Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerModelTest
{
    CustomerModel customer = new CustomerModel();
    @Test
    void CustomerIDTest()
    {
        customer.setCustomerID(5);
        Assertions.assertEquals(customer.getCustomerID(), 5);
        Assertions.assertNotEquals(customer.getCustomerID(), 50);
    }

    @Test
    void CustomerNameTest()
    {
        customer.setCustomerName("Julian Assange");
        Assertions.assertEquals(customer.getCustomerName(), "Julian Assange");
        Assertions.assertNotEquals(customer.getCustomerName(), "John Doe");
    }

    @Test
    void CustomerAddressTest()
    {
        String address = "20 W 34th St, New York, NY";
        String secondAddress = "233 S Wacker Dr, Chicago, IL";
        customer.setCustomerAddress((address));
        Assertions.assertEquals(customer.getCustomerAddress(), address);
        Assertions.assertNotEquals(customer.getCustomerAddress(), secondAddress);
    }

    @Test
    void PostalCodeTest()
    {
        String postalCode = "60606";
        String secondPostalCode = "10112";
        customer.setPostalCode(postalCode);
        Assertions.assertEquals(customer.getPostalCode(), postalCode);
        Assertions.assertNotEquals(customer.getPostalCode(), secondPostalCode);
    }

    @Test
    void PhoneNumberTest()
    {
        String phoneNumber = "777-777-7777";
        String secondPhoneNumber = "555-555-5555";
        customer.setPhoneNumber(phoneNumber);
        Assertions.assertEquals(customer.getPhoneNumber(), phoneNumber);
        Assertions.assertNotEquals(customer.getPhoneNumber(), secondPhoneNumber);
    }

    @Test
    void CustomerAppointmentTest()
    {
        AppointmentModel appointment = new AppointmentModel();
        appointment.setAppointmentID(5);
        customer.addAppointment(appointment);
        Assertions.assertEquals(customer.getAllAppointments().size(), 1);
        Assertions.assertNull(customer.getAppointment(6));
        Assertions.assertNotNull(customer.getAppointment(5));
        customer.removeAppointment(5);
        Assertions.assertEquals(customer.getAllAppointments().size(), 0);
    }
}
