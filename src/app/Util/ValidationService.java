package app.Util;

import app.DataLocalization.LocalizationService;

import java.time.*;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Properties;
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
     * Method for validating username and password in login.
     *
     * I decided to use a few lambdas here to avoid writing separate helper functions, these one-liners are also
     * pretty slick and save me a few lines of code, in addition to making the return statement of the function easy
     * to read and follow.
     *
     * @param username The username to validate.
     * @param password The password to validate.
     * @param propertiesService The properties service we are using to get properties from properties file.
     * @return True if the username and password are valid, false if invalid or exception is thrown from properties service.
     */
    public boolean ValidateUsernamePassword(String username, String password, PropertiesService propertiesService)
    {
        if (username.isEmpty() || username.isBlank() || password.isEmpty() || password.isBlank())
            return false;

        try
        {
            Properties adminProperties = propertiesService.GetProperties("app.properties");
            Predicate<String> validUserName = s -> s.toLowerCase().equals(adminProperties.getProperty("adminusername").toLowerCase());
            Predicate<String> validPassword = s -> s.equals(adminProperties.getProperty("adminpassword"));
            return validUserName.test(username) && validPassword.test(password);
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    /**
     * Method for validating if an appointment is in the next fifteen minutes
     *
     * Used a lambda function here to avoid creating a separate helper function and also for readability,
     * I feel like reading the return statement of this function makes it easy to figure out what's going on.
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
        return IsWeekDay(userZoneDateTime) && InBusinessHours(userZoneDateTime.getHour());
    }

    /**
     * Determines if ZonedDateTime is inside of a weekday.
     *
     * @param zonedDateTime ZonedDateTime to validate.
     * @return True if inside a weekday, otherwise false.
     */
    private boolean IsWeekDay(ZonedDateTime zonedDateTime)
    {
        switch (zonedDateTime.getDayOfWeek())
        {
            case MONDAY:
            case TUESDAY:
            case WEDNESDAY:
            case THURSDAY:
            case FRIDAY: return true;
            default: return false;
        }
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
}
