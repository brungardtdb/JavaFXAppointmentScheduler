Application Title: Appointment Manager
Purpose: The purpose of this application is to serve as an appointment scheduling utility for
a consulting company with customers across varying timezones.

Application Version: V1.0.0

IDE and Version: IntelliJ IDEA Community Edition 2020.2.3
Java JDK Version: Java SE 11.0.9
JavaFX Version: JavaFX-SDK-11.0.2

To start the Appointment Manager, open the application in Intellij IDEA and select "Run 'Main'" from the "Run" drop down
at the top of the screen.

---The login form---
Once the application has started, you will see a login form that will prompt you for a username and a password.
If an incorrect username and password is entered you will see a general error stating that the login information is
incorrect (don't want to give away if it was username or password).

Upon logging in successfully, the user will be notified if they have an upcoming appointment in the next fifteen minutes.

If there is no upcoming appointment, the application will notify the user that there are no upcoming appointments.

Once this notification is closed, the main form will load.

The application will detect the system default language and zone ID and display all application messages and dates
according to local language and time.

---The main form---
On the main form there will be a table showing all customers, and a table showing all appointments. You can filter
appointments by three options: "All", "Weekly", and "Monthly" to view appointments within each time frame.

For both customers and appointments, there is an option to add, modify, and delete records for each.
If a customer or appointment is being added or modified, a separate window will open. If a customer or
appointment is being deleted, you will be asked to confirm that you want to delete the appointment or customer.

If "modify" is selected and no customer or appointment is selected, a warning message will show.

If the user wishes to remove the selection from the customer or appointment table, this can be accomplished by selecting
the background of the main form.

At the bottom right of the screen there are buttons to run three different reports, as well as a button to exit the application.

--The customer form--
The ID field is disabled, you will be prompted to enter the customer information using various text fields and combo boxes.
If a customer is being modified, the form will pre-populate all of the existing customer data.

Once the user is done entering the customer data, they will have the option to save or to cancel.

If the "save" button is selected and the form is missing input, a warning message will display.

The "cancel" button will close the form.

--The appointment form--
The ID field is disabled, you will be prompted to enter the appointment information using text fields, combo boxes,
and date pickers.

If an appointment is being modified, the form will pre-populate with all of the existing customer data.

If the "save" button is selected and the form is missing input, a warning message will display.

If the appointment overlaps with an existing appointment for that customer, a warning message will display.

The "cancel" button will close the form.

---Additional Report---
An additional report that was provided was a report showing a breakdown of the client's customers by the
country they reside in.
