# Glendon Tan - Project Portfolio Page

## Overview
BookBob is a desktop application tailored for Dr Bob’s private General Practitioner clinic. It facilitates the storage 
and retrieval of patient information, including names, NRICs, genders, dates of birth, phone numbers, home addresses, 
allergies, medical histories and visit records with details on diagnoses and prescribed medications. BookBob also helps 
Dr Bob stay organised by tracking daily appointments and providing reminders each morning. Optimised for a Command Line 
Interface (CLI), BookBob allows for efficient management of patient information and appointments.

### Summary of Contributions
- Code Contributed: [RepoSense Link](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=g13nd0n)
- Enhancements implemented:
  New Features
  - Add appointment for future schedule
      - What it does: allow user to add appointments made with patients
      - Justifications: This features improve the product by allowing user to track their time to avoid possible 
        overbooking of time for consultation with patients
      - Highlights: 
        - The adding of appointment will also check if the time is available based on the current list of 
          appointments that have already been made. Additionally, it is taken into account that the consultation duration is 
          30 minutes. It will suggest the next available timing, ensuring that the time and consultation duration will not 
          clash with other appointments. The implementation is challenging as it requires a few conditional statement to
          take note of timings of the different appointments.
        - Furthermore, once added the appointment, the list of appointments will be sorted according to date and time. 
          Implementation required ensure the appointment class implements the Comparable interface and have the compareTo
          methods and sorting them out. Additionally, implementation will need to ensure that regardless the order of the 
          appointment is added, it will be sorted appropriately
          while comparison are correct
    - Delete appointments
      - What it does: delete appointments based on the specific nric, date and time given
      - Justification: This feature improves product by allowing user to delete the appointments that might have been 
        made on accident. It requires date and time to ensure that the appointment deleted is the correct appointment.
    - List all appointments
      - What it does: List all appointments in chronological order
      - Justification: This improves the product by allowing user to look at all the appointments that has been made
      - Highlights: The list of appointments will always be sorted as user should know the appointments in date order.
    - Find appointments based on specific keywords such as date, time, name or nric
      - What it does: Display all appointments based on the search keyword
      - Justifications: This feature improves the product by allowing users to find the appointments for the specific
        name, nric, date or time instead of listing all appointments and look for the appointments wanted
    - Appointments Notice upon logging in to the system
      - What it does: Display all the appointments for the day
      - Justification: This feature improves the product by removing the need for the user to list all appointments and 
        look for the appointments for the day.
    - Removal of appointments for the day
      - What it does: Automatically remove all the past appointments upon logging off
      - Justification: This feature improves the product by removing the need for user to personally delete the 
        appointments that have already passed.
      - Highlights: The appointments that are removed would be those that are before the time that the user logged off.
        Implementations would required to check both date and time before removal and not simply removing all the 
        appointments for the day.
  - Wrote additional testing for the new features added. Included testing of methods defined in the `Appointment` and 
  `AppointmentRecord` classes. #248
- Contributions to User Guide:
  - Added documentation for the features `delete`, `appointment`, `deleteAppointment`, `listAppointments` and 
  `findAppointment` #205
- Contributions to Developer Guide:
  - Added class diagram for `Appointment` and `AppointmentRecord` class
  - Added details, sequence diagram and object diagram for the Appointment Feature
  - Added information for the Glossary 
- Review/mentoring contributions:
  - Help teammates in solving JUnit test issues e.g. System.lineSeparator() was not replaced with \n
  - Help fixed the CI issues that was accumulated over time #75

## Contributions to the Developer Guide
- Appointment Class Diagram\
![Appointment.png](..%2FAppointment.png)
- AppointmentRecord Class Diagram\
![AppointmentRecord.png](..%2FAppointmentRecord.png)
- Object Diagram for Appointment Feature\
![BeforeExecutionOD.png](..%2FBeforeExecutionOD.png)
![AfterExecutionOD.png](..%2FAfterExecutionOD.png)
- Sequence Diagram for Appointment Feature\
![NewAppointmentSD.png](..%2FNewAppointmentSD.png)

```
Appendix D : Glossary
- Mainstream OS: Windows, Linux, Unix, MacOS
- NRIC: National Registration Identity Card (Identification Number)
```

## Contributions to the User Guide
```
Deleting a Patient Record

Deletes the patient record based on the given NRIC number\
The delete function will only work on the NRIC number and not the patient's name\
Format: delete NRIC

Example: `delete S1234567A`
    Example Output:
    Patient John Doe, S1234567A, has been deleted."

Example: `delete John Doe`
    Example Output:
    Please provide the NRIC of the patient, not the name.

```
```
Adding a Patient Appointment

Adds an appointment for a patient on the date and time\
If the selected appointment slot has already been taken, it will prompt the next available time slot\
Format: appointment n/NAME ic/NRIC date/DATE time/TIME\
Date format is in DD-MM-YYYY and Time format is in HH:mm

Example: `appointment n/John Doe ic/S1234567A date/18-11-2024 time/18:00`
    Example Output:

    Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A has been added.
Example: `appointment n/Will Smith ic/S7654321A date/18-11-2024 time/18:00`
    Example Output:
    There is already an appointment at the given timeslot. The next available timeslot is: 18:30
```
```
Deleting a Patient Appointment

Delete an appointment for a patient on the date and time\
Format: appointment ic/NRIC date/DATE time/TIME\
Date format is in DD-MM-YYYY and Time format is in HH:mm

Example: `deleteAppointment ic/S1234567A date/18-11-2024 time/18:00`
    Example Output:
    Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A has been deleted.
```

```
List all Patient Appointments

List all the appointments on the schedule\
Format: listAppointments

Example: `listAppointments`
    Example Output:
    Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.
    Appointment on 18-11-2024 18:00 with Patient Will Smith, S7654321A.
```
```
Finding a Patient Appointment

Find an appointment with a patient based on the given name, nric, date or time\
Format:
    findAppointment n/NAME OR\
    findAppointment ic/NRIC OR\
    findAppointment date/DATE OR\
    findAppointment time/TIME\
Date format is in DD-MM-YYYY and Time format is in HH:mm

Example: `findAppointment n/John`
    Example Output:
    Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.
    Appointment on 30-11-2024 9:00 with Patient John Tan, S2468123A.

Example: `findAppointment ic/S1234567A`
    Example Output:
    Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.

Example: `findAppointment date/18-11-2024`
    Example Output:
    Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.
    Appointment on 18-11-2024 18:00 with Patient Will Smith, S7654321A.

Example: `findAppointment time/18:00`
    Example Output:

    Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.
    Appointment on 20-11-2024 18:00 with Patient Hela, S9876543A.
```
