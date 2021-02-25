package app.Util;

import app.DataLocalization.LocalizationService;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    public boolean ValidateUpcomingAppointment(ZonedDateTime zonedDateTime)
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
}
