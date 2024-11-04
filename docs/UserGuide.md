# User Guide

## Introduction

{Give a product intro}

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 17 or above installed.
1. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features 

## Viewing Help 
Shows the available list of commands and some guiding information.

Format: `help`

```
+-------------+---------------------------------------+---------------------------------+
| Action      | Format                                | Example                         |
+-------------+---------------------------------------+---------------------------------+
| Help        | help                                  | help                            |
+-------------+---------------------------------------+---------------------------------+
| Add         | add n/NAME ic/NRIC [p/PHONE_NUMBER]   | add n/James Ho ic/S9534567A     |
|             | [d/DIAGNOSIS] [m/MEDICATION]          | p/91234567 d/Asthma m/Albuterol |
|             | [ha/HOME_ADDRESS] [dob/DATE_OF_BIRTH] | ha/NUS-PGPR dob/01011990        |
|             | [v/VISIT_DATE_TIME] [al/ALLERGY]      | v/21-10-2024 15:48 al/Pollen    |
|             | [s/SEX] [mh/MEDICALHISTORY]           | s/Female mh/Diabetes            |
|             | DATE format: dd-mm-yyyy               |                                 |
|             | TIME format: HH:mm                    |                                 |
+-------------+---------------------------------------+---------------------------------+
| Add Visit   | addVisit ic/NRIC v/VISIT_DATE_TIME    | addVisit ic/S9534567A           |
|             | [d/DIAGNOSIS] [m/MEDICATION]          | v/21-10-2024 15:48              |
|             | DATE format: dd-mm-yyyy               | d/Fever,Headache,Flu            |
|             | TIME format: HH:mm                    | m/Paracetamol,Ibuprofen         |
+-------------+---------------------------------------+---------------------------------+
| List        | list                                  | list                            |
+-------------+---------------------------------------+---------------------------------+
| Find        | find n/NAME          OR               | find n/John Doe                 |
|             | find ic/NRIC         OR               | find ic/S1234                   |
|             | find p/PHONE_NUMBER  OR               | find p/91234567                 |
|             | find d/DIAGNOSIS     OR               | find d/Fever                    |
|             | find m/MEDICATION    OR               | find m/Panadol                  |
|             | find ha/HOME_ADDRESS OR               | find ha/NUS PGPR                |
|             | find dob/DATE_OF_BIRTH OR             | find dob/01011990               |
|             | find al/ALLERGY      OR               | find al/Peanuts                 |
|             | find find s/SEX      OR               | find find s/Female              |
|             | find mh/MEDICAL_HISTORY               | find mh/Diabetes                |
+-------------+---------------------------------------+---------------------------------+
| Delete      | delete NRIC                           | delete S9534567A                |
+-------------+---------------------------------------+---------------------------------+
| Add         | appointment n/NAME ic/NRIC            | add n/James Ho ic/S9534567A     |
| Appointment | date/DATE time/TIME                   | date/01-04-2025 time/12:00      |
|             | DATE format: dd-mm-yyyy               |                                 |
|             | TIME format: HH:mm                    |                                 |
+-------------+---------------------------------------+---------------------------------+
| List        | listAppointments                      | list                            |
| Appointment |                                       |                                 |
+-------------+---------------------------------------+---------------------------------+
| Find        | findAppointment n/NAME          OR    | findAppointment n/John Doe      |
| Appointment | findAppointment ic/NRIC         OR    | findAppointment ic/S1234        |
|             | findAppointment date/DATE       OR    | findAppointment date/01-04-2025 |
|             | findAppointment time/TIME       OR    | findAppointment time/12:00      |
|             | DATE format: dd-mm-yyyy               |                                 |
|             | TIME format: HH:mm                    |                                 |
+-------------+---------------------------------------+---------------------------------+
| Delete      | deleteAppointment NRIC                | deleteAppointment S9534567A     |
| Appointment | date/DATE time/TIME                   | date/01-04-2025 time/12:00      |
|             | DATE format: dd-mm-yyyy               |                                 |
|             | TIME format: HH:mm                    |                                 |
+-------------+---------------------------------------+---------------------------------+
| Find        | findVisit NRIC                        | findVisit S9534567A             |
| Visits      |                                       |                                 |
+-------------+---------------------------------------+---------------------------------+
| Find        | findDiagnosis diagnosis               | findDiagnosis fever             |
| Diagnosis   |                                       |                                 |
+-------------+---------------------------------------+---------------------------------+
| Find        | findMedication medication             | findMedication Panadol          |
| Medication  |                                       |                                 |
+-------------+---------------------------------------+---------------------------------+
| Save        | save(automatic)                       |                                 |
+-------------+---------------------------------------+---------------------------------+
| Retrieve/   | retrieve or import                    |                                 |
| Import      | (automatic)                           |                                 |
+-------------+---------------------------------------+---------------------------------+
| Exit        | exit                                  | exit                            |
+-------------+---------------------------------------+---------------------------------+

```

## Deleting a Patient Record
Deletes the patient record based on the given NRIC number\
The delete function will only work on the NRIC number and not the patient's name\
Format: delete NRIC

Example: `delete S1234567A`
```
Example Output:

Patient John Doe, S1234567A, has been deleted."
```
Example: `delete John Doe`

```
Example Output:

Please provide the NRIC of the patient, not the name.
```

## Adding an appointment for a patient
Adds an appointment for a patient on the date and time\
If the selected appointment slot has already been taken, it will prompt the next available time slot\
Format: appointment n/NAME ic/NRIC date/DATE time/TIME\
Date format is in DD-MM-YYYY and Time format is in HH:mm

Example: `appointment n/John Doe ic/S1234567A date/18-11-2024 time/18:00`
```
Example Output:

Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A has been added.
```

Example: `appointment n/Will Smith ic/S7654321A date/18-11-2024 time/18:00`
```
Example Output:

There is already an appointment at the given timeslot. The next available timeslot is: 18:30
```
## Deleting an appointment with a patient
Delete an appointment for a patient on the date and time\
Format: appointment ic/NRIC date/DATE time/TIME\
Date format is in DD-MM-YYYY and Time format is in HH:mm

Example: `deleteAppointment ic/S1234567A date/18-11-2024 time/18:00`
```
Example Output:

Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A has been deleted.
```
## List all the appointments
List all the appointments on the schedule\
Format: listAppointments

Example: `listAppointments`
```
Example Output:

Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.
Appointment on 18-11-2024 18:00 with Patient Will Smith, S7654321A.
```

## Finding an appointment with a patient
Find an appointment with a patient based on the given name, nric, date or time\
Format:
findAppointment n/NAME OR\
findAppointment ic/NRIC OR\
findAppointment date/DATE OR\
findAppointment time/TIME\
Date format is in DD-MM-YYYY and Time format is in HH:mm

Example: `findAppointment n/John`
```
Example Output:

Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.
Appointment on 30-11-2024 9:00 with Patient John Tan, S2468123A.

```

Example: `findAppointment ic/S1234567A`
```
Example Output:

Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.
```
Example: `findAppointment date/18-11-2024`
```
Example Output:

Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.
Appointment on 18-11-2024 18:00 with Patient Will Smith, S7654321A.

```
Example: `findAppointment time/18:00`
```
Example Output:

Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.
Appointment on 20-11-2024 18:00 with Patient Hela, S9876543A.

```

## Exiting the program
Exits the program.

Format: `exit`
## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

| Action             | Format                                                                                                                                                                                                                                                   | Example                                                                                                                                                                                                    |
|--------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Add patient        | `add n/NAME ic/NRIC [p/PHONE_NUMBER] [d/DIAGNOSIS] [m/MEDICATION] [ha/HOME_ADDRESS] [dob/DATE_OF_BIRTH] [v/VISIT_DATE_TIME] [al/ALLERGY] [s/SEX] [mh/MEDICAL_HISTORY]`<br>DATE format: `dd-mm-yyyy`<br>TIME format: `HH:mm`                              | `add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/01011990 v/21-10-2024 15:48 al/Pollen s/Female mh/Diabetes`                                                                   |
| Add Visit          | `addVisit ic/NRIC v/VISIT_DATE_TIME [d/DIAGNOSIS] [m/MEDICATION]`<br>DATE format: `dd-mm-yyyy`<br>TIME format: `HH:mm`                                                                                                                                   | `addVisit ic/S9534567A v/21-10-2024 15:48 d/Fever,Headache,Flu m/Paracetamol,Ibuprofen`                                                                                                                    |
| List               | `list`                                                                                                                                                                                                                                                   | `list`                                                                                                                                                                                                     |
| Find               | `find n/NAME` OR<br>`find ic/NRIC` OR<br>`find p/PHONE_NUMBER` OR<br>`find d/DIAGNOSIS` OR<br>`find m/MEDICATION` OR<br>`find ha/HOME_ADDRESS` OR<br>`find dob/DATE_OF_BIRTH` OR<br>`find al/ALLERGY` OR<br>`find s/SEX` OR<br>`find mh/MEDICAL_HISTORY` | `find n/John Doe`<br>`find ic/S1234`<br>`find p/91234567`<br>`find d/Fever`<br>`find m/Panadol`<br>`find ha/NUS PGPR`<br>`find dob/01011990`<br>`find al/Peanuts`<br>`find s/Female`<br>`find mh/Diabetes` |
| Delete             | `delete NRIC`                                                                                                                                                                                                                                            | `delete S9534567A`                                                                                                                                                                                         |
| Add Appointment    | `appointment n/NAME ic/NRIC date/DATE time/TIME`<br>DATE format: `dd-mm-yyyy`<br>TIME format: `HH:mm`                                                                                                                                                    | `appointment n/James Ho ic/S9534567A date/01-04-2025 time/12:00`                                                                                                                                           |
| List Appointment   | `listAppointments`                                                                                                                                                                                                                                       | `listAppointments`                                                                                                                                                                                         |
| Find Appointment   | `findAppointment n/NAME` OR<br>`findAppointment ic/NRIC` OR<br>`findAppointment date/DATE` OR<br>`findAppointment time/TIME`<br>DATE format: `dd-mm-yyyy`<br>TIME format: `HH:mm`                                                                        | `findAppointment n/John Doe`<br>`findAppointment ic/S1234`<br>`findAppointment date/01-04-2025`<br>`findAppointment time/12:00`                                                                            |
| Delete Appointment | `deleteAppointment NRIC date/DATE time/TIME`<br>DATE format: `dd-mm-yyyy`<br>TIME format: `HH:mm`                                                                                                                                                        | `deleteAppointment S9534567A date/01-04-2025 time/12:00`                                                                                                                                                   |
| Find Visits        | `findVisit NRIC`                                                                                                                                                                                                                                         | `findVisit S9534567A`                                                                                                                                                                                      |
| Find Diagnosis     | `findDiagnosis diagnosis`                                                                                                                                                                                                                                | `findDiagnosis fever`                                                                                                                                                                                      |
| Find Medication    | `findMedication medication`                                                                                                                                                                                                                              | `findMedication Panadol`                                                                                                                                                                                   | 
| Exit               | `exit`                                                                                                                                                                                                                                                   | `exit`                                                                                                                                                                                                     |
