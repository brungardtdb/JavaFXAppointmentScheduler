package app.Util;

import app.DataLocalization.LocalizationService;

import java.io.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.function.Predicate;

/**
 * Utility class for logging.
 */
public class LoggingService
{
    private static LoggingService loggingService = new LoggingService();

    /**
     * Empty private constructor for class.
     */
    private LoggingService() { }

    /**
     * Gets an instance of the logging utility class.
     *
     * @return Instance of logger.
     */
    public static LoggingService getInstance()
    {
        return loggingService;
    }

    /**
     * Logs exceptions to a file.
     *
     * @param className The name of the class where the exception was thrown.
     * @param methodName The name of the method where the exception was thrown.
     * @param exception The exception that was thrown.
     * @throws IOException
     */
    public static void LogException(String className, String methodName, Exception exception) throws IOException {

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);

        String logMessage = "=================================================\n" +
                "An exception was thrown in Class: " + className + " Method: " + methodName + "\n" +
                "Exception: " + exception.toString() + "\n" +
                "Stack Trace: " + stringWriter.toString() + "\n" +
                "=================================================\n";

        File file = new File("./exception_log.txt");
        FileWriter fileWriter;
        fileWriter = file.exists() ? new FileWriter(file, true) : new FileWriter(file);
        fileWriter.write(logMessage);
        fileWriter.close();
    }

    /**
     * Logs login attempts to a file.
     *
     * Used a lambda expression here as a succinct way of indicating if the log was successful without creating another method
     * the function returns a true if the file was successfully saved, otherwise it returns false.
     *
     * @param username The attempted username.
     * @param password The attempted password.
     * @param attemptIsSuccessful A boolean indicating if the attempt was successful.
     * @param localizationService The service used for localization.
     * @param locale The locale of the user.
     * @throws Exception Throws exception if attempt to log login results was unsuccessful.
     */
    public static void LogLoginAttempt(String username, String password, boolean attemptIsSuccessful,
                                       LocalizationService localizationService, Locale locale) throws Exception
    {
        String message = "--login attempt at " + ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")) + "--\n" +
                "Username: " + username + " Password: " + password + " Successful Login: " + attemptIsSuccessful  + "\n";

        File file = new File("./login_activity.txt");
        FileWriter fileWriter;

        Predicate<FileWriter> writeToFile = x -> {
            try {
                x.write(message);
                x.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        };

        fileWriter = file.exists() ? new FileWriter(file, true) : new FileWriter(file);

        if (!writeToFile.test(fileWriter))
            throw new Exception(localizationService.GetLocalizedMessage("logerror", locale));
    }
}
