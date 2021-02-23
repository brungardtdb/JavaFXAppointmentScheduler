package app.DataLocalization;

import app.Util.PropertiesService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Properties;

/**
 * Class for handling localization/internationalization conversions.
 */
public class LocalizationService
{
    // Use Singleton design pattern, we should only need one class for localization.
    static LocalizationService localizationService = new LocalizationService();

    /**
     * Private empty constructor for class.
     */
    private LocalizationService() { }

    /**
     * Gets instance of LocalizationService.
     *
     * @return LocalizationService.
     */
    public static LocalizationService getInstance()
    {
        return  localizationService;
    }

    /**
     * Converts ZonedDateTime to UTC.
     *
     * @param zonedDateTime The time to convert.
     * @return Time converted to UTC.
     */
    public ZonedDateTime ZonedDateTimeToUTC(ZonedDateTime zonedDateTime)
    {
        ZonedDateTime utcDateTime = ZonedDateTime.ofInstant(zonedDateTime.toInstant(), ZoneId.of("UTC"));
        return  utcDateTime;
    }

    /**
     * Converts ZonedDateTime to UTC.
     *
     * @param zonedDateTime The time to convert.
     * @return Time converted to UTC.
     */
    public ZonedDateTime ZonedDateTimeToEST(ZonedDateTime zonedDateTime)
    {
        ZonedDateTime estDateTime = ZonedDateTime.ofInstant(zonedDateTime.toInstant(), ZoneId.of("US/Eastern"));
        return  estDateTime;
    }

    /**
     * Converts ZonedDateTime to local time.
     *
     * @param zonedDateTime The time to convert.
     * @param zoneId The zone ID for local time.
     * @return Time converted to local time.
     */
    public ZonedDateTime ZonedDateTimeToLocal(ZonedDateTime zonedDateTime, ZoneId zoneId)
    {
        ZonedDateTime utcDateTime = ZonedDateTime.ofInstant(zonedDateTime.toInstant(), zoneId);
        return  utcDateTime;
    }

    /**
     * Formats address based on zone ID.
     *
     * @param street Street for address.
     * @param city City for address.
     * @param country Country for address.
     * @param zoneId Zone ID for address.
     * @return The formatted address.
     */
    public String FormatAddress(String street, String city, String country, ZoneId zoneId)
    {
        return zoneId.equals(ZoneId.of("Europe/London")) ?
                FormatUKAddress(street, city, country) : FormatUSandCAAddress(street, city);
    }

    /**
     * Formats addresses for US and Canada.
     *
     * @param street Street for address.
     * @param city City for address.
     * @return The formatted address.
     */
    private String FormatUSandCAAddress(String street, String city) { return street + ", " + city; }

    /**
     * Formats addresses for UK.
     *
     * @param street Street for address.
     * @param city City for address.
     * @param country Country for address.
     * @return The formatted address.
     */
    private String FormatUKAddress(String street, String city, String country) { return street + ", " + city + ", " + country; }

    /**
     * Returns string message in proper language for given locale.
     *
     * @param message Key for message to be returned.
     * @param locale Locale for user.
     * @return The message in the appropriate language.
     * @throws Exception Java.io.FileNotFoundException.
     */
    public String GetLocalizedMessage(String message, Locale locale) throws Exception
    {
        String language = locale.getLanguage();
        String localizationFile = language.equals("en") ? "en.properties" : "fr.properties";
        PropertiesService propertiesService = new PropertiesService();
        Properties properties = propertiesService.GetProperties(localizationFile);
        return properties.getProperty(message);
    }
}
