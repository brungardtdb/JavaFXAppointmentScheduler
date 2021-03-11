package app.Util;

import java.time.*;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.function.Predicate;

/**
 * Class for handling appointment validation for business logic
 */
public class ValidationService
{
    private static ValidationService validationService = new ValidationService();

    /**
     * Private constructor for class.
     */
    private ValidationService(){}

    /**
     * Method for getting single instance of validation service.
     *
     * @return Validation Service.
     */
    public static ValidationService getInstance()
    {
        return validationService;
    }

    /**
     * Method for validating if an appointment is in the next fifteen minutes
     *
     * Used a lambda function here to avoid creating a separate helper function and also for readability,
     * I feel like reading the return statement of this function makes it easy to figure out what's going on.
     * The function returns compares the ZonedDateTime in milliseconds to detect if it is within fifteen minutes
     * of the current time.
     *
     * @param zonedDateTime The appointment time.
     * @return True if appointment is in fifteen or less, otherwise false.
     */
    public boolean ValidateUpcomingZonedDateTime(ZonedDateTime zonedDateTime)
    {
        // Lambda function to check if appointment start time is between now and fifteen minutes from now
        Predicate<ZonedDateTime> inFifteenMinutes = z ->
        {
            return z.toInstant().toEpochMilli() > ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"))
                    .toInstant().toEpochMilli() &&
                    z.toInstant().toEpochMilli() < ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"))
                    .plusMinutes(15).toInstant().toEpochMilli();
        };

        return inFifteenMinutes.test(zonedDateTime);
    }

    /**
     * Method for validating ZonedDateTime is in current week.
     *
     * @param zonedDateTime ZonedDateTime to validate.
     * @return True if in current week, otherwise false.
     */
    public boolean ValidateZoneDateTimeInThisWeek(ZonedDateTime zonedDateTime)
    {
        ZoneId zoneId = ZoneId.of("US/Eastern");
        Locale locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
        ZonedDateTime userZoneDateTime = ZonedDateTime.ofInstant(zonedDateTime.toInstant(), zoneId);
        ZonedDateTime now = ZonedDateTime.ofInstant(Instant.now(), zoneId);
        return now.get(WeekFields.of(locale).weekOfYear()) == userZoneDateTime.get(WeekFields.of(locale).weekOfYear());
    }

    /**
     * Method for validating ZonedDateTime is in current month.
     *
     * @param zonedDateTime ZonedDateTime to validate.
     * @return True if in current month, otherwise false.
     */
    public boolean ValidateZoneDateTimeInThisMonth(ZonedDateTime zonedDateTime)
    {
        ZoneId zoneId = ZoneId.of("US/Eastern");
        ZonedDateTime userZoneDateTime = ZonedDateTime.ofInstant(zonedDateTime.toInstant(), zoneId);
        ZonedDateTime now = ZonedDateTime.ofInstant(Instant.now(), zoneId);
        return now.getMonth() == userZoneDateTime.getMonth();
    }

    /**
     * Method for validating ZonedDateTime is inside of business hours.
     *
     * @param zonedDateTime ZonedDateTime to validate.
     * @return True if inside business hours, otherwise false.
     */
    public boolean ValidateZoneDateTimeInBusinessHours(ZonedDateTime zonedDateTime)
    {
        ZoneId zoneId = ZoneId.of("US/Eastern");
        ZonedDateTime userZoneDateTime = ZonedDateTime.ofInstant(zonedDateTime.toInstant(), zoneId);
        return InBusinessHours(userZoneDateTime.getHour());
    }

    /**
     * Evaluates if a ZonedDateTime is between 8am and 10pm EST.
     *
     * @param hours Hours to evaluate.
     * @return True if within 8am and 10pm EST, otherwise false.
     */
    private boolean InBusinessHours(int hours)
    {
        return 8 <= hours && hours <= 22;
    }

    /**
     * Method to check if one ZonedDateTime exists within two others.
     *
     * @param timeToCompare ZonedDateTime to check.
     * @param startTime ZonedDateTime that marks beginning of window for comparison.
     * @param endTime ZonedDateTime that marks end of window for comparison.
     * @return True if time to compare is between start and finish times.
     */
    public boolean TimeOverlaps(ZonedDateTime timeToCompare, ZonedDateTime startTime, ZonedDateTime endTime)
    {
        return ((timeToCompare.toInstant().toEpochMilli() > startTime.toInstant().toEpochMilli() &&
                timeToCompare.toInstant().toEpochMilli() < endTime.toInstant().toEpochMilli()));
    }
}
