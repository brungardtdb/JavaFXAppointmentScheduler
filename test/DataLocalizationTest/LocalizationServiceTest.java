package DataLocalizationTest;

import app.DataLocalization.LocalizationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Locale;

public class LocalizationServiceTest
{
    final LocalizationService localizationService = LocalizationService.getInstance();

    @Test
    void DateTimeToUTC()
    {
        ZonedDateTime mountainTime = ZonedDateTime.of(2020, 02, 28,15,0,0,0, ZoneId.of("America/Denver"));
        ZonedDateTime utcTime = ZonedDateTime.of(2020, 02, 28,22,0,0,0, ZoneId.of("UTC"));
        var result = localizationService.ZonedDateTimeToUTC(mountainTime);
        Assertions.assertEquals(result, utcTime);
    }

    @Test
    void DateTimeToEST()
    {
        ZonedDateTime easternTime = ZonedDateTime.of(2020, 02, 28,15,0,0,0, ZoneId.of("US/Eastern"));
        ZonedDateTime utcTime = ZonedDateTime.of(2020, 02, 28,20,0,0,0, ZoneId.of("UTC"));
        var result = localizationService.ZonedDateTimeToUTC(easternTime);
        Assertions.assertEquals(result, utcTime);
    }

    @Test
    void ZonedDateTimeToLocal()
    {
        ZonedDateTime easternTime = ZonedDateTime.of(2020, 02, 28,17,0,0,0, ZoneId.of("US/Eastern"));
        ZonedDateTime utcTime = ZonedDateTime.of(2020, 02, 28,22,0,0,0, ZoneId.of("UTC"));
        ZonedDateTime mountainTime = ZonedDateTime.of(2020, 02, 28,15,0,0,0, ZoneId.of("America/Denver"));
        var utcResult = localizationService.ZonedDateTimeToLocal(utcTime, ZoneId.of("America/Denver"));
        var estResult = localizationService.ZonedDateTimeToLocal(easternTime, ZoneId.of("America/Denver"));
        Assertions.assertEquals(utcResult, mountainTime);
        Assertions.assertEquals(estResult, mountainTime);
    }

    @Test
    void AddressFormatTest()
    {
        String usAddress = "123 ABC Street, White Plains";
        String caAddress = "123 ABC Street, Newmarket";
        String ukAddress = "123 ABC Street, Greenwich, London";
        String usOutput = localizationService.FormatAddress("123 ABC Street", "N/A", "White Plains", ZoneId.of("America/Denver"));
        String caOutput = localizationService.FormatAddress("123 ABC Street", "N/A", "Newmarket", ZoneId.of("Canada/Saskatchewan"));
        String ukOutput = localizationService.FormatAddress("123 ABC Street", "Greenwich", "London", ZoneId.of("Europe/London"));
        Assertions.assertEquals(usOutput, usAddress);
        Assertions.assertEquals(caOutput, caAddress);
        Assertions.assertEquals(ukOutput, ukAddress);
    }

    @Test
    void GetLocalizedMessageTest() throws Exception
    {
        String engMessage = "Hello";
        String frMessage = "Bonjour";
        Locale engLocale = new Locale("en");
        Locale frLocale = new Locale("fr");
        String engResult = localizationService.GetLocalizedMessage("hello", engLocale);
        String frResult = localizationService.GetLocalizedMessage("hello", frLocale);
        Assertions.assertEquals(engMessage, engResult);
        Assertions.assertEquals(frMessage, frResult);
    }


}
