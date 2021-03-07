package app.Util;

import app.DataLocalization.LocalizationService;
import app.UserData.Enums.AppointmentType;
import app.UserData.Models.AppointmentModel;
import app.UserData.Models.ContactModel;
import net.bytebuddy.asm.Advice;

import java.util.*;
import java.util.stream.Collectors;

public class ReportService
{
    private static ReportService reportService = new ReportService();
    private String startEndReport = "=========================================================================";

    private ReportService() { }

    public static ReportService getInstance()
    {
        return reportService;
    }

    public String AppointmentsByTypeAndMonth(List<AppointmentModel> appointments)
    {
        String report = startEndReport;

        List<Integer> months = new ArrayList<>();
        List<Integer> years = new ArrayList<>();

        for (AppointmentModel appointment :appointments)
        {
            if (!months.contains(appointment.getStartDate().getMonthValue()))
                months.add(appointment.getStartDate().getMonthValue());

            if (!years.contains(appointment.getStartDate().getYear()))
                years.add(appointment.getStartDate().getYear());
        }

        years.sort(Comparator.comparing(y -> y.longValue()));
        months.sort(Comparator.comparing(m -> m.longValue()));

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

        String header =  "\n" + ID + "\t\t" + customerID + "\t" + title + "\t\t" + type + "\t\t"  + startDateTime + "\t\t\t\t" +
                endDateTime + "\t\t\t\t" + description + "\n";

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
                        appointment.getLocalStartDate() + "\t\t" + appointment.getLocalEndDate() + "\t\t" + appointment.getDescription() + "\n";
            }
        }
        report += startEndReport + startEndReport;
        return report;
    }
}
