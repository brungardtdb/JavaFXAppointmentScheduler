package Validation;

import app.Util.ValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ValidationTest
{
    ValidationService validationService = ValidationService.getInstance();

    @Test
    void FifteenMinutesTest()
    {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).plusMinutes(7);
        boolean result = validationService.ValidateUpcomingAppointment(zonedDateTime);
        Assertions.assertEquals(result, true);
        zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).plusMinutes(16);
        result = validationService.ValidateUpcomingAppointment(zonedDateTime);
        Assertions.assertEquals(result, false);
    }
}
