package Validation;

import app.Util.ValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ValidationTest
{
    ValidationService validationService = ValidationService.getInstance();

    @Test
    void FifteenMinutesTest()
    {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).plusMinutes(7);
        boolean result = validationService.ValidateUpcomingZonedDateTime(zonedDateTime);
        Assertions.assertEquals(result, true);
        zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).plusMinutes(16);
        result = validationService.ValidateUpcomingZonedDateTime(zonedDateTime);
        Assertions.assertEquals(result, false);
    }

    @Test
    void DateInCurrentWeekTest()
    {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        boolean result = validationService.ValidateZoneDateTimeInThisWeek(zonedDateTime);
        Assertions.assertTrue(result);
        ZonedDateTime lastWeek = zonedDateTime.minusWeeks(1);
        ZonedDateTime nextWeek = zonedDateTime.plusWeeks(1);
        result = validationService.ValidateZoneDateTimeInThisWeek(lastWeek);
        Assertions.assertFalse(result);
        result = validationService.ValidateZoneDateTimeInThisWeek(nextWeek);
        Assertions.assertFalse(result);
    }

    @Test
    void DateInCurrentMonthTest()
    {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        boolean result = validationService.ValidateZoneDateTimeInThisMonth(zonedDateTime);
        Assertions.assertTrue(result);
        ZonedDateTime lastMonth = zonedDateTime.minusMonths(1);
        ZonedDateTime nextMonth = zonedDateTime.plusMonths(1);
        result = validationService.ValidateZoneDateTimeInThisMonth(lastMonth);
        Assertions.assertFalse(result);
        result = validationService.ValidateZoneDateTimeInThisMonth(nextMonth);
        Assertions.assertFalse(result);
    }

    @Test
    void DateInBusinessHoursTest()
    {
        ZonedDateTime mountainTimeTooLate = ZonedDateTime.of(2021, 02, 27,21,0,0,0, ZoneId.of("America/Denver"));
        ZonedDateTime mountainTimeWeekend = ZonedDateTime.of(2021, 02, 27,12,0,0,0, ZoneId.of("America/Denver"));
        ZonedDateTime mountainInHours = ZonedDateTime.of(2021, 03, 03,12,0,0,0, ZoneId.of("America/Denver"));

        boolean lateResult = validationService.ValidateZoneDateTimeInBusinessHours(mountainTimeTooLate);
        boolean weekendResult = validationService.ValidateUpcomingZonedDateTime(mountainTimeWeekend);
        boolean businessHoursResult = validationService.ValidateZoneDateTimeInBusinessHours(mountainInHours);

        Assertions.assertFalse(lateResult);
        Assertions.assertFalse(weekendResult);
        Assertions.assertTrue(businessHoursResult);
    }


}
