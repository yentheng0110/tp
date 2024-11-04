# User Guide

## Introduction

{Give a product intro}

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 17 installed in your computer.
2. Download the <u>latest</u> BookBob.jar file from [here](https://github.com/AY2425S1-CS2113-T10-2/tp/releases).
3. Copy the file to your desired home folder for BookBob where you would like to run it from.
4. Open a command prompt or terminal, navigate to the folder containing BookBob.jar, and execute the command : **`java -jar BookBob.jar`** <br>
The following output would be shown : 
` Welcome to BookBob, Dr. Bob!`


## Features 

## Finding a Patient Record
Searches for patient records based on search parameters such as name, NRIC, phone number, home address, date of birth, allergies, sex, or medical history.<br>
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
* Parameters entered in the input can be of any order

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
## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
