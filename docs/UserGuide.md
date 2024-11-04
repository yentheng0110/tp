---
layout: default
---

# BookBob User Guide

---
## Introduction
BookBob is a desktop application tailored for Dr Bob's private General Practitioner clinic. It facilitates the storage 
and retrieval of patient information, including names, NRICs, genders, dates of birth, phone numbers, home addresses, 
allergies, medical histories and visit records with details on diagnoses and prescribed medications. BookBob also helps 
Dr Bob stay organised by tracking daily appointments and providing reminders each morning. Optimised for a Command Line 
Interface (CLI), BookBob allows for efficient management of patient information and appointments.

---
## Table of Contents
- [Quick Start](#quick-start)
- [Features](#features)
    - [Viewing Help](#viewing-help)
    - [Adding a Patient Record](#adding-a-patient-record)
    - [Listing all Patient Records](#listing-a-patient-record)
    - [Finding a Patient Record](#finding-a-patient-record)
    - [Deleting a Patient Record](#deleting-a-patient-record)
    - [Editing a Patient Record](#editing-a-patient-record)
    - [Adding a Visit Record](#adding-a-visit-record)
    - [Editing a Visit Record](#editing-a-visit-record)
    - [Adding a Patient Appointment](#adding-a-patient-appointment)
    - [Deleting a Patient Appointment](#deleting-a-patient-appointment)
    - [List all Patient Appointments](#list-all-patient-appointments)
    - [Finding a Patient Appointment](#finding-a-patient-appointment)
    - [Finding All Patient Visits by NRIC](#finding-all-patient-visits-by-NRIC)
    - [Finding All Patient Visits by Diagnosis](#finding-all-patient-visits-by-diagnosis)
    - [Finding All Patient Visits by Medication](#finding-all-patient-visits-by-medication)
    - [Exiting the Program](#exiting-the-program)
- [FAQ](#faq)
- [Command Summary](#command-summary)

---
## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 17 installed in your computer.
2. Download the <u>latest</u> BookBob.jar file from [here](https://github.com/AY2425S1-CS2113-T10-2/tp/releases).
3. Copy the file to your desired home folder for BookBob where you would like to run it from.
4. Open a command prompt or terminal, navigate to the folder containing BookBob.jar, and execute the command : **`java -jar BookBob.jar`** <br>
The following output would be shown : 
` Welcome to BookBob, Dr. Bob!`

---
# Features
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

---
## Finding a Patient Record
<div style="background-color: #F5F9FE; padding: 12px; border-radius: 4px; border-left: 4px solid #2196F3; color: #1A1A1A;">
🚨 <b>Note:</b> this "find" command is to search for standard patient information and details.<br><br>
BookBob has dedicated commands namely findAppointment, findVisit, findMedication, findDiagnosis, which you can read about in the next sections below.
</div>

"find" Searches for patient records based on search parameters such as name, NRIC, phone number, home address, date of birth, allergies, sex, or medical history.<br>
Format: find Prefix/Value where Prefix can be:
- n/NAME
- ic/NRIC
- p/PHONE_NUMBER
- ha/HOME_ADDRESS
- dob/DATE_OF_BIRTH
- al/ALLERGY
- s/SEX
- mh/MEDICAL_HISTORY <br>
<br>
Note : <br>
- Trailing spaces are fine, but there are no spaces anywhere in between Prefix/Value.  <br>

Examples:
* `find n/John` - Finds all patients whose names contain "John"

Example Output:
```
Matching patients:
Name: John Doe, NRIC: S9534567A, Phone: 91234567, Home Address: Clementi Road, DOB: 01-01-1990, Allergies: [Peanuts], Sex: Male, Medical Histories: [Asthma]
```

* `find ic/S9534567A` - Finds all patients whose NRIC contains "S9534567A"
* `find p/91234567` - Finds all patients whose phone numbers contain "91234567"
* `find ha/Clementi` - Finds all patients whose addresses contain "Clementi"
* `find al/Peanuts` - Finds all patients with peanut allergies
* `find s/Male` - Finds all male patients
* `find mh/Asthma` - Finds all patients with asthma in their medical history

Note : <br> 
* Partial String character search matches are allowed and will work. E.g. "find ic/S953", "find p/9123" is allowed.
* "find" is case-insensitive, searching with either capital or non-capital letters is allowed and will work.
* Multiple search parameters are allowed, and Parameters entered in the input can be of any order.

---
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

---
## Adding a Visit Record
Adds a new visit record for an existing patient.<br>
Format: addVisit ic/NRIC v/VISIT_DATE_TIME [d/DIAGNOSIS] [m/MEDICATION] <br>
Date and Time format must be in : dd-MM-yyyy HH:mm <br>
Note: Single diagnosis and medications can be added; <u>Multiple diagnoses and/or medications are also allowed</u>, by separating them with commas. 

Example: `addVisit ic/S9534567A v/21-10-2024 15:48 d/Fever,Headache,Flu m/Paracetamol,Ibuprofen`

Example Output:
```
Visit added successfully for patient: John Doe
Visit date: 21-10-2024 15:48
Diagnoses: Fever, Headache, Flu
Medications: Paracetamol, Ibuprofen
```

Additional examples:
* `addVisit ic/S9534567A v/22-10-2024 09:30 d/Cough m/Cough Syrup` - Adds a visit with single diagnosis and medication
* `addVisit ic/S9534567A v/23-10-2024 14:00` - Adds a visit without diagnosis or medication

    Note : <br>
• The NRIC must belong to an existing patient in the system <br>
• Date and Time format must be in : dd-MM-yyyy HH:mm <br>
• Parameters entered in the input can be of <u>any order</u> and is <u>allowed</u>, i.e. you may input "ic/", "v/", "d/", "m/" <u>in any order</u>. 
Or you may also choose to stick to convention and input "ic/", "v/", "d/", "m/" in this order.

---
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

---
## Adding a Patient Appointment
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
---
## Deleting a Patient Appointment
Delete an appointment for a patient on the date and time\
Format: appointment ic/NRIC date/DATE time/TIME\
Date format is in DD-MM-YYYY and Time format is in HH:mm

Example: `deleteAppointment ic/S1234567A date/18-11-2024 time/18:00`
```
Example Output:

Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A has been deleted.
```

---
## List all Patient Appointments
List all the appointments on the schedule\
Format: listAppointments

Example: `listAppointments`
```
Example Output:

Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.
Appointment on 18-11-2024 18:00 with Patient Will Smith, S7654321A.
```

---
## Finding a Patient Appointment
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

---
## Exiting the Program
Exits the program.

Format: `exit`

---
## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: Install the app on the other computer and overwrite the empty data file it generates with the data file from your
previous BookBob home folder.


---
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
