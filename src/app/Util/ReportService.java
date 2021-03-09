package app.Util;

import app.DataLocalization.LocalizationService;
import app.UserData.Enums.AppointmentType;
import app.UserData.Models.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that handles report generation.
 */
public class ReportService
{
    private static ReportService reportService = new ReportService();
    private String startEndReport = "=========================================================================";

    /**
     * Empty private constructor for class.
     */
    private ReportService() { }

    /**
     * Method for getting instance of report service.
     *
     * @return Returns a single instance of the report service.
     */
    public static ReportService getInstance()
    {
        return reportService;
    }

    /**
     * Generates a report of all appointments by appointment type and month.
     *
     * I used streams and lambda expressions in this method to sort the lists of appointments according to
     * type, month, and year in order to show reports for all appointments sorted by type and month.
     *
     * Shout out to this post on StackOverflow for showing me how to sort the list of years and months in a single line:
     * https://stackoverflow.com/questions/40517977/sorting-a-list-with-stream-sorted-in-java
     *
     * @param appointments A list of all appointments.
     * @return A string containing a report of all appointments by type and month.
     */
    public String AppointmentsByTypeAndMonth(List<AppointmentModel> appointments)
    {
        String report = startEndReport;

        List<Integer> months = new ArrayList<>();
        List<Integer> years = new ArrayList<>();

        // Get unique values for years and months
        for (AppointmentModel appointment :appointments)
        {
            if (!months.contains(appointment.getStartDate().getMonthValue()))
                months.add(appointment.getStartDate().getMonthValue());

            if (!years.contains(appointment.getStartDate().getYear()))
                years.add(appointment.getStartDate().getYear());
        }

        // Sort years and months
        years.sort(Comparator.comparing(y -> y.longValue()));
        months.sort(Comparator.comparing(m -> m.longValue()));

        // Add QTY of each type per month to report
        for (Integer year: years)
        {
            for (Integer month: months)
            {
                List<AppointmentModel> planningAppointments = appointments.stream().filter(x ->
                        x.getAppointmentType() == AppointmentType.PLANNING &&
                        x.getStartDate().getYear() == year &&
                        x.getStartDate().getMonthValue() == month)
                        .collect(Collectors.toList());

                List<AppointmentModel> debriefingAppointments = appointments.stream().filter(x ->
                        x.getAppointmentType() == AppointmentType.DEBRIEFING &&
                                x.getStartDate().getYear() == year &&
                                x.getStartDate().getMonthValue() == month)
                        .collect(Collectors.toList());

                boolean hasPlanning = planningAppointments.size() > 0;
                boolean hasDebriefing = debriefingAppointments.size() > 0;

                if (hasPlanning || hasDebriefing)
                    report += "\n --- " + month + " / " + year + "\n";

                if (hasPlanning)
                    report += AppointmentType.PLANNING.toString() + ": " + planningAppointments.size() + "\n";

                if (hasDebriefing)
                    report += AppointmentType.DEBRIEFING.toString()  + ": " + debriefingAppointments.size() + "\n";
            }
        }

        report += startEndReport;
        return report;
    }

    /**
     * Generates a report showing the appointment schedule for each contact.
     *
     * @param appointments A list of all appointments.
     * @param contacts A list of all contacts.
     * @param localizationService The service used to localize text and dates.
     * @param locale The locale of the user.
     * @return A string containing a report showing each contact schedule.
     * @throws Exception Java.io.FileNotFoundException.
     */
    public String GetContactSchedules(List<AppointmentModel> appointments, List<ContactModel> contacts,
                                      LocalizationService localizationService, Locale locale) throws Exception
    {
        String report = startEndReport + startEndReport;
        String ID = localizationService.GetLocalizedMessage("ID", locale);
        String title = localizationService.GetLocalizedMessage("title", locale);
        String type = localizationService.GetLocalizedMessage("type", locale);
        String description = localizationService.GetLocalizedMessage("description", locale);
        String startDateTime = localizationService.GetLocalizedMessage("startdate", locale);
        String endDateTime = localizationService.GetLocalizedMessage("enddate", locale);
        String customerID = localizationService.GetLocalizedMessage("customerID", locale);

        String header =  "\n" + ID + "\t\t" + customerID + "\t" + title + "\t\t\t" + type + "\t\t"  + startDateTime + "\t\t\t\t" +
                endDateTime + "\t\t\t" + description + "\n";

        for (ContactModel contact : contacts)
        {
            report += "\n" + contact.getContactName() + "\n";
            report += header;

            List<AppointmentModel> contactAppointments = appointments.stream().filter(x ->
                    x.getContactID() == contact.getContactID()).
                    collect(Collectors.toList());

            for (AppointmentModel appointment : contactAppointments)
            {
                report += "\n" + appointment.getAppointmentID() + "\t\t" + appointment.getCustomerID() + "\t\t\t" + appointment.getTitle() + "\t\t" +
                        appointment.getAppointmentType().toString() + "\t" +
                        appointment.getFormattedLocalStartDate() + "\t\t" + appointment.getFormattedLocalEndDate() + "\t\t" + appointment.getDescription() + "\n";
            }
        }
        report += startEndReport + startEndReport;
        return report;
    }


    /**
     * Generates a report of all customers in each country.
     *
     * I used streams and lambdas in this class to filter out divisions by country, and then to match customers to those
     * divisions. This was necessary because country information is not stored on the customer model, so it was necessary
     * to do a sort of post-database inner join.
     *
     * @param allCustomers A list of all customers.
     * @param allDivisions A list of all divisions.
     * @param allCountries A list of all countries.
     * @return A string containing a report of each country and the customers within that country.
     */
    public String GetCustomerByCountry(List<CustomerModel> allCustomers, List<DivisionModel> allDivisions, List<CountryModel> allCountries)
    {
        String report = startEndReport;

        for (CountryModel country: allCountries)
        {
            report += "\n" + "---" + country.getCountryName() + "---" + "\n";

            List<Integer> countryDivisionIDs = allDivisions.stream().filter(x ->
                    x.getCountryID() == country.getCountryID())
                    .map(DivisionModel::getCountryID)
                    .collect(Collectors.toList());

            List<CustomerModel> countryCustomers = allCustomers.stream().filter(x ->
                    countryDivisionIDs.stream()
                            .anyMatch(d -> d == x.getCustomerID()))
                            .collect(Collectors.toList());

            for (CustomerModel customer : countryCustomers)
            {
                report += customer.getCustomerID() + " " + customer.getCustomerName() + "\n";
            }
        }
        report += startEndReport;
        return report;
    }
}
