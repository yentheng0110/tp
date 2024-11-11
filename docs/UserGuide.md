---
layout: default
---

# BookBob User Guide

---
## Introduction
BookBob is a desktop application tailored for Dr Bob's private General Practitioner clinic. BookBob helps 
streamline clinic management by storing and retrieving patient information, including names, NRICs, genders, dates of 
birth, phone numbers, home addresses, allergies, medical histories and detailed visit records with diagnoses and 
prescribed medications. BookBob also assists Dr Bob in staying organised by tracking daily appointments and sending 
reminders each morning. Optimised for a Command Line Interface (CLI), BookBob enables efficient management of patient 
information and appointment scheduling.

---
## Table of Contents
- [Quick Start](#quick-start)
- [Features](#features)
    - [Viewing Help](#viewing-help)
    - [Adding a Patient Record](#adding-a-patient-record)
    - [Listing all Patient Records](#listing-all-patient-records)
    - [Finding a Patient Record](#finding-a-patient-record)
    - [Deleting a Patient Record](#deleting-a-patient-record)
    - [Editing a Patient Record](#editing-a-patient-record)
    - [Adding a Visit Record](#adding-a-visit-record)
    - [Editing a Visit Record](#editing-a-visit-record)
    - [Adding a Patient Appointment](#adding-a-patient-appointment)
    - [Deleting a Patient Appointment](#deleting-a-patient-appointment)
    - [List all Patient Appointments](#list-all-patient-appointments)
    - [Finding a Patient Appointment](#finding-a-patient-appointment)
    - [Finding Patient Visits](#finding-patient-visits)
    - [Saving/Retrieving Patient Records and Appointment Records Data](#savingretrieving-patient-records-and-appointment-records-data)
    - [Exiting the Program](#exiting-the-program)
- [FAQ](#faq)
- [Command Summary](#command-summary)

---
## Quick Start

1. Ensure that you have Java 17 installed in your computer.
2. Download the <u>latest</u> BookBob.jar file from [here](https://github.com/AY2425S1-CS2113-T10-2/tp/releases).
3. Copy the file to your desired home folder for BookBob where you would like to run it from.
4. Open a command prompt or terminal, navigate to the folder containing BookBob.jar, and execute the command : **`java -jar BookBob.jar`** <br>
The following output would be shown :`Welcome to BookBob, Dr. Bob!`

---
# Features
<hr style="width: 15%; height: 2px; background-color: black; border: none; margin-top: 10px; margin-bottom: 20px;">

**Note:**
1. Extra Input After Commands: Additional input provided after expected inputs for commands such as `list`, `listAppointments`, `help` and `exit` will be treated as unknown commands.
2. Extra Spaces After Commands: Additional spaces after expected inputs for commands such as `list`, `listAppointments`, `help` and `exit` will be ignored.
3. Case Sensitivity for Commands: Commands are case-sensitive. Ensure correct <u>lowercase</u> for <u>commands</u> e.g. `list` instead of `LIST`,
and <u>mixed-case</u> for e.g. `addVisit` instead of `addvisit`
4. Case Sensitivity for Commands Prefixes: Command Prefixes are case-sensitive. Ensure correct <u>lowercase</u> for <u>commands prefixes</u> E.g. "n/", "ic/", instead of
  "N/", "IC/"

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
| Edit        | edit ic/NRIC /to [n/NAME]             | edit ic/S9534567A /to p/80976890|
|             | [newic/NEW_NRIC]  [p/PHONE_NUMBER]    | mh/Diabetes, Hypertension       |
|             | [ha/HOME_ADDRESS] [dob/DATE_OF_BIRTH] |                                 |
|             | [al/ALLERGY] [s/SEX]                  |                                 |
|             | [mh/MEDICALHISTORY]                   |                                 |
+-------------+---------------------------------------+---------------------------------+
| Add Visit   | addVisit ic/NRIC v/VISIT_DATE_TIME    | addVisit ic/S9534567A           |
|             | [d/DIAGNOSIS] [m/MEDICATION]          | v/21-10-2024 15:48              |
|             | DATE format: dd-mm-yyyy               | d/Fever,Headache,Flu            |
|             | TIME format: HH:mm                    | m/Paracetamol,Ibuprofen         |
+-------------+---------------------------------------+---------------------------------+
| Edit Visit  | editVisit ic/NRIC                     | editVisit ic/S7209876Y          |
|             | v/VISIT_DATE_AND_TIME                 | v/06-11-2024 14:00           |
|             | [newDate/NEW_DATE]  [d/DIAGNOSIS]     | newDate/08-11-2024 14:00        |
|             | [m/MEDICATION]                        | d/Asthma m/Panadol, Antibiotics |
|             | DATE format: dd-mm-yyyy               |                                 |
|             | TIME format: HH:mm                    |                                 |
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

## Adding a Patient Record
Adds a new patient record to BookBob. <br>
Format: add n/NAME ic/NRIC [p/PHONE_NUMBER] [d/DIAGNOSIS] [m/MEDICATION] [ha/HOME_ADDRESS] [dob/DATE_OF_BIRTH] v/VISIT_DATE_TIME [al/ALLERGY] [s/SEX] [mh/MEDICALHISTORY] <br>

Note : <br>
• The mandatory fields are name, NRIC and visit date. Optional fields (denoted by square brackets above) include phone
number, diagnoses, medications, home address, date of birth, allergies, sex and medical histories. <br>
• Single diagnosis, medication, allergy and medical history can be added; <u>Multiple diagnoses, medications, allergies and/or medical histories are also allowed</u>, by separating them with commas. <br>
• Date and Time format must be in : dd-MM-yyyy HH:mm <br> 
• Parameters entered in the input can be of <u>any order</u> or you may also choose to stick to the format above. <br>
Example: `add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/01-01-1995 v/21-10-2024 15:48 al/Pollen s/Female mh/Diabetes`

Example Output: 
```
Patient James Ho with NRIC S9534567A added.
```

Additional examples:
* `add n/Patricia Chan ic/S9890897U v/04-11-2024 09:00` - Adds a patient with only mandatory fields inputted.
* `add n/Jacky Cheung ic/S7209876Y ha/Farrer Road p/98765789 v/06-11-2024 14:00` - Adds a patient with mandatory fields
and optional fields with a different order as the format above.

The examples above result in successful patient record additions, which are then saved automatically.

<div style="background-color: #F5F9FE; padding: 12px; border-radius: 4px; border-left: 4px solid #2196F3; color: #1A1A1A;">
💡 <b>Best Practices of using</b> <em><strong>"add" command together with "addVisit" command</strong></em> <b>:</b> <br>
1.) When a new patient visits the clinic, we use <b>"add" command</b>. <br>
<ins> The "add" command is primarily used for adding a patient record with the patient's basic key details information </ins> (such as Name , NRIC , Phone Number), and that Name, NRIC, Patient's First Visit Date, are <ins>Compulsory fields</ins>. <br>
Other <ins>Optional fields</ins> includes e.g. Phone Number, Home Address, DOB, <i>and</i> Diagnoses and Medications from patient's first visit. <br>
<br>
2.) If the <ins>same patient(with same NRIC)</ins> comes back to the clinic for a new visit, we use <b>"addVisit" command</b>,  
to key in the visit Date&Time of this new visit event(which is a compulsory field along with NRIC), AND the Diagnoses of illnesses and Medications prescribed for this new visit event (which are optional fields).<br>
<br>
3.) We then use "list" command to see all patient information.
</div>

---

## Listing All Patient Records
Lists all patient records saved. <br>
Format: `list`

Example Output:
```
Name: James Ho, NRIC: S9534567A, Phone: 91234567, Home Address: NUS-PGPR, DOB: 01011995, Allergies: [Pollen], Sex: Female, Medical Histories: [Diabetes]
Visit Date: 21-10-2024 15:48, Diagnosis: [Asthma], Medications: [Albuterol]

Name: Patricia Chan, NRIC: S90890897U, Phone: , Home Address: , DOB: , Allergies: [], Sex: , Medical Histories: []
Visit Date: 04-11-2024 09:00, Diagnosis: [], Medications: []

Name: Jacky Cheung, NRIC: S7209876Y, Phone: 98765789, Home Address: Farrer Road, DOB: , Allergies: [], Sex: , Medical Histories: []
Visit Date: 06-11-2024 14:00, Diagnosis: [], Medications: []
```
<br>

<div style="background-color: #F5F9FE; padding: 12px; border-radius: 4px; border-left: 4px solid #2196F3; color: #1A1A1A;">
🚨 <b>NOTE:</b> The example below is achieved by executing the commands in this exact order; <b>"add" followed by "addVisit" followed by "list"</b>.
Not by using the "add" command multiple times followed by "list".
</div>

Additional Example Output of the <u>same Patient(with same NRIC)</u> with multiple <u>new visits</u> during <u>different dates</u>, 
with different illness diagnosed and medications prescribed during the different visit event : 

```
Name: Wang Ritz, NRIC: S8634567A, Phone: 91234567, Home Address: PGPR, DOB: 01-02-1990, Allergies: [grass], Sex: female, Medical Histories: [diabetes]
    Visit Date: 20-10-1995 12:35, Diagnosis: [], Medications: []
    Visit Date: 21-10-2024 15:48, Diagnosis: [Fever, Headache, Flu], Medications: [Paracetamol, Ibuprofen, Aspirin]
    Visit Date: 29-12-2026 23:59, Diagnosis: [Fatigue, Dizziness, Cough], Medications: [Mint, Mentos, Coke]
    Visit Date: 11-10-2030 16:45, Diagnosis: [Myopia, Acid Reflux, Rhinitis], Medications: [Naproxen, Omeprazole, Zyrtec]
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
**Note** : <br>
- Trailing spaces are fine, but there are <u>no spaces</u> anywhere in <u>between Prefix/Value</u>.  <br>

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

**Note** : <br> 
* Partial String character search matches are <u>allowed</u> and will work. E.g. "find ic/S953", "find p/9123" is <u>allowed</u>.
* "find" is case-insensitive, searching with either capital or non-capital letters is <u>allowed</u> and will work.
* Multiple search parameters are <u>allowed</u>, and parameters entered in the input can be of any order.

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

## Editing a Patient Record
Edits a current patient record saved in BookBob. <br>
Format: edit ic/NRIC /to [n/NAME] [newic/NEW_NRIC] [p/PHONE_NUMBER] [ha/HOME_ADDRESS] [dob/DATE_OF_BIRTH] [al/ALLERGY] [s/SEX] [mh/MEDICALHISTORY] <br>

Note : <br>
• The mandatory field is patient's NRIC to search for the specific patient record to be edited. 
Then, anything after the `/to` are optional fields (denoted by square brackets above) include name, new NRIC, 
phone number, home address, date of birth, allergies, sex and medical histories. These are the new information to be 
updated for the patient. <br>
• Single allergy and medical history can be added; <u>Multiple allergies and/or medical histories are also allowed</u>, by separating them with commas. <br>
• Parameters entered in the input can be of <u>any order</u> or you may also choose to stick to the format above. <br>
Example: `edit ic/S9534567A /to p/80976890 mh/Diabetes, Hypertension`

Example Output:
```
Patient record updated successfully.
Updated patient details:
Name: James Ho, NRIC: S9534567A, Phone: 80976890, Address: NUS-PGPR, DOB: 01011990, Allergy: [], Sex: Female, Medical History: [Diabetes, Hypertension]
```

Additional examples:
* `edit ic/S9890897U dob/01011998 ha/Orchard Road` - Edit a patient record with optional fields in a different order than the format shown above.

The examples above result in successful patient record updates, which are automatically saved.

---

## Adding a Visit Record
Adds a <u>new visit</u> record for an **<u>existing patient</u>**.<br>
Format: addVisit ic/NRIC v/VISIT_DATE_TIME [d/DIAGNOSIS] [m/MEDICATION] <br>
Date and Time format must be in : dd-MM-yyyy HH:mm <br>
    Note: <br>
• Single diagnosis and medications can be added; <u>Multiple diagnoses and/or medications are also allowed</u>, by separating them with commas. <br>
• Compulsory fields are NRIC and Date&Time of visit, other fields are optional. <br>
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
• The NRIC must belong to an **<u>existing patient</u>** in the system <br>
• Date and Time format must be in : dd-MM-yyyy HH:mm <br>
• Parameters entered in the input can be of <u>any order</u> and is <u>allowed</u>, i.e. you may input "ic/", "v/", "d/", "m/" <u>in any order</u>. 
Or you may also choose to stick to convention and input "ic/", "v/", "d/", "m/" in this order.

---

## Editing a Visit Record
Edits a current visit record of a patient saved in BookBob. <br>
Format: editVisit ic/NRIC v/VISIT_DATE_AND_TIME [newDate/NEW_DATE] [d/DIAGNOSIS] [m/MEDICATION] <br>

Note : <br>
• The mandatory fields for searching and editing a specific visit in a patient's record are the patient's NRIC and visit
date. The optional fields (denoted by square brackets above) include new date, diagnosis and medication. These 
are the new information to be updated for the patient. <br>
• Single diagnosis and medication can be added; <u>Multiple diagnoses and/or medications are also allowed</u>, by 
separating them with commas. <br>
• Parameters entered in the input can be of <u>any order</u> or you may also choose to stick to the format above. <br>
Example: `editVisit ic/S7209876Y v/06-11-2024 14:00 d/Asthma m/Panadol, Antibiotics`

Example Output:
```
Visit record updated successfully.
Updated visit details:
06-11-2024 14:00, Diagnosis: [Asthma], Medications: [Panadol, Antibiotics]
```

Additional examples:
* `editVisit ic/S9089087U v/19-11-2024 18:00 d/Runny Nose newDate/20-11-2024 18:00` - Edit a visit record of a 
patient with optional fields in a different order than the format shown above.

The examples above result in successful visit record updates, which are automatically saved.

---

## Finding Patient Visits
Finds a current visit record of a patient saved in BookBob in three ways, by NRIC, by diagnosis and by medication. <br>

By NRIC: <br> Format: findVisit NRIC<br>
Note : <br>
• Single NRIC to be entered. All corresponding visit records will be printed to terminal,
with exactly matched NRIC. <br>
Example: `findVisit S7209876Y`

Example Output:
```
Processing find visit command
Successfully processed find visit command
20-11-2024 18:00, Diagnosis: [Runny Nose], Medications: [Panadol, Antibiotics]
```
If nothing found:
```
No patient visit record found with NRIC: S7209876Y
```

By diagnosis: <br> Format: findDiagnosis diagnosis<br>
Note : <br>
• Single diagnosis to be entered (case-insensitive). All corresponding patients' information and visit records will be printed to terminal,
with exactly matched diagnosis. <br>
Example: `findDiagnosis Runny Nose`

Example Output:
```
---------------------------------
Name: Patricia Chan, NRIC: S9089087U, Phone: , Address: , DOB: , Allergy: [], Sex: , Medical History: []
20-11-2024 18:00, Diagnosis: [Runny Nose], Medications: [Panadol, Antibiotics]
---------------------------------
---------------------------------
Name: Jacky Cheung, NRIC: S7209876Y, Phone: 91234567 | DOB: , Address: Farrer Road, Allergy: [], Sex: , 
Medical History: []
06-11-2024 14:00, Diagnosis: [Runny Nose], Medications: [Panadol, Antibiotics]
---------------------------------
```
If nothing found:
```
No patient found with symptom: Runny Nose
```

By Medication: <br> Format: findMedication medication<br>
Note : <br>
• Single medication to be entered (case-insensitive). All corresponding patients' information and visit records will be printed to terminal,
with exactly matched medication. <br>
Example: `findMedication Panadol`

Example Output:
```
---------------------------------
Name: Patricia Chan, NRIC: S9089087U, Phone: , Address: , DOB: , Allergy: [], Sex: , Medical History: []
20-11-2024 18:00, Diagnosis: [Runny Nose], Medications: [Panadol, Antibiotics]
---------------------------------
---------------------------------
Name: Jacky Cheung, NRIC: S7209876Y, Phone: 91234567, Address: Farrer Road, DOB: , Allergy: [], Sex: , Medical History: []
06-11-2024 14:00, Diagnosis: [Asthma], Medications: [Panadol, Antibiotics]
---------------------------------
```
If nothing found:
```
No patient found with medication: Panadol
```
---


## Adding a Patient Appointment
Adds an appointment for a patient on the date and time\
If the selected appointment slot has already been taken, it will prompt the next available time slot\
Format: appointment n/NAME ic/NRIC date/DATE time/TIME
#### Extra Information:
Date format is in DD-MM-YYYY and Time format is in HH:mm\
All past appointments will be removed from the system upon exiting.\
Appointments before today's date and time will not be added.

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
Format: appointment ic/NRIC date/DATE time/TIME \
Note: Deleting a Patient Appointment is case-insensitive.

#### Extra Information
Date format is in DD-MM-YYYY and time format is in HH:mm\
The NRIC is case-insensitive

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
Find an appointment with a patient based on the given name, NRIC, date or time\
Format:
findAppointment n/NAME OR\
findAppointment ic/NRIC OR\
findAppointment date/DATE OR\
findAppointment time/TIME

#### Extra Information:
Date format is in DD-MM-YYYY and Time format is in HH:mm
The name and NRIC are case-insensitive

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
## Saving/Retrieving Patient Records and Appointment Records Data
<br>
Saving and retrieving are performed automatically whenever changes are made, with no additional commands required.

---

## Exiting the Program
To exit the program, type `exit` exactly, with no extra spaces or characters following it.
Format: `exit`

---

## FAQ

### Data Management, Updates, & Migration

**Q1** : How do I transfer my data to another computer?

**A** : Install the app on the other computer and overwrite the empty data file it generates with the data file from your previous BookBob home folder.

**Q2**: Can I use BookBob on multiple computers?

**A**: Yes, you can use BookBob on multiple computers by copying your data file between them. However, ensure you're always working with the most recent version of your data.

**Q3** : How do I export my data ?

**A** : Navigate to the "data" folder, copy your "bookbob_data" text file into your external storage device such as a thumbdrive, or external hard disk. The data file can then be used for your own storage and reference, can be a good backup, can be emailed to your colleagues, it can be used for your further analysis such as using Excel for your trend analysis.

**Q4** : How often does BookBob back up my data?

**A**: BookBob automatically saves all changes immediately after they are made. However, we recommend creating manual backups of your data file periodically by copying it to a secure external storage device.

### Patient Records

**Q1**: Is there a limit to how many patient records I can store?

**A**: No, BookBob is designed to handle a large number of patient records efficiently. However, performance may vary depending on your computer's specifications.

### Appointments

**Q1**: Can I schedule recurring appointments?

**A**: Currently, each appointment needs to be scheduled individually. You can use the same time slot on different dates for regular check-ups.

**Q2**: How does BookBob handle appointment conflicts?

**A**: When you attempt to schedule an appointment in an occupied time slot, BookBob will automatically suggest the next available time slot.

### Visit Records

**Q1**: Can I add multiple diagnoses and medications for a single visit?

**A**: Yes, you can add multiple diagnoses and medications by separating them with commas. For example: d/Fever,Headache,Flu m/Paracetamol,Ibuprofen

**Q2**: How far back can I view a patient's visit history?

**A**: BookBob maintains a complete history of all patient visits since their first record. There is no time limit on historical data.



### System Requirements & Technical Support

**Q1**: What operating systems is BookBob compatible with?

**A**: BookBob is compatible with any operating system that supports Java 17, including Windows, macOS, and Linux distributions.

### Miscellaneous

**Q1**: What is the setting and scope of BookBob? 

**A**: BookBob is specialised for the Singapore setting, with NRICs starting with "S" or "T", 
and with Phone Numbers having exactly eight digits and starting with "9" or "8". 

**Q2**: Can 24/7 clinics use BookBob?

**A**: Yes, definitely. BookBob is intended to support 24/7 clinics. Day Clinics are also welcomed to use BookBob.

---
## Command Summary

| Action              | Format                                                                                                                                                                                                                                                   | Example                                                                                                                                                                                                    |
|---------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Help                | `help`                                                                                                                                                                                                                                                   | `help`                                                                                                                                                                                                     |
| Add patient record  | `add n/NAME ic/NRIC [p/PHONE_NUMBER] [d/DIAGNOSIS] [m/MEDICATION] [ha/HOME_ADDRESS] [dob/DATE_OF_BIRTH] v/VISIT_DATE_TIME [al/ALLERGY] [s/SEX] [mh/MEDICALHISTORY]`                                                                                      | `add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/01011990 v/21-10-2024 15:48 al/Pollen s/Female mh/Diabetes`                                                                   |
| Edit patient record | `edit ic/NRIC /to [n/NAME] [newic/NEW_NRIC] [p/PHONE_NUMBER] [ha/HOME_ADDRESS] [dob/DATE_OF_BIRTH] [al/ALLERGY] [s/SEX] [mh/MEDICAL_HISTORY]`                                                                                                            | `edit ic/S9534567A /to p/80976890 mh/Diabetes, Hypertension `                                                                                                                                              |
| Add Visit           | `addVisit ic/NRIC v/VISIT_DATE_TIME [d/DIAGNOSIS] [m/MEDICATION]`<br>DATE format: `dd-mm-yyyy`<br>TIME format: `HH:mm`                                                                                                                                   | `addVisit ic/S9534567A v/21-10-2024 15:48 d/Fever,Headache,Flu m/Paracetamol,Ibuprofen`                                                                                                                    |
| Edit Visit          | `editVisit ic/NRIC v/VISIT_DATE_AND_TIME [newDate/NEW_DATE] [d/DIAGNOSIS] [m/MEDICATION]`<br>DATE format: `dd-mm-yyyy`<br>TIME format: `HH:mm`                                                                                                           | `editVisit ic/S7209876Y v/06-11-2024 14:00 d/Asthma m/Panadol, Antibiotics`                                                                                                                                |
| List                | `list`                                                                                                                                                                                                                                                   | `list`                                                                                                                                                                                                     |
| Find                | `find n/NAME` OR<br>`find ic/NRIC` OR<br>`find p/PHONE_NUMBER` OR<br>`find d/DIAGNOSIS` OR<br>`find m/MEDICATION` OR<br>`find ha/HOME_ADDRESS` OR<br>`find dob/DATE_OF_BIRTH` OR<br>`find al/ALLERGY` OR<br>`find s/SEX` OR<br>`find mh/MEDICAL_HISTORY` | `find n/John Doe`<br>`find ic/S1234`<br>`find p/91234567`<br>`find d/Fever`<br>`find m/Panadol`<br>`find ha/NUS PGPR`<br>`find dob/01011990`<br>`find al/Peanuts`<br>`find s/Female`<br>`find mh/Diabetes` |
| Delete              | `delete NRIC`                                                                                                                                                                                                                                            | `delete S9534567A`                                                                                                                                                                                         |
| Add Appointment     | `appointment n/NAME ic/NRIC date/DATE time/TIME`<br>DATE format: `dd-mm-yyyy`<br>TIME format: `HH:mm`                                                                                                                                                    | `appointment n/James Ho ic/S9534567A date/01-04-2025 time/12:00`                                                                                                                                           |
| List Appointment    | `listAppointments`                                                                                                                                                                                                                                       | `listAppointments`                                                                                                                                                                                         |
| Find Appointment    | `findAppointment n/NAME` OR<br>`findAppointment ic/NRIC` OR<br>`findAppointment date/DATE` OR<br>`findAppointment time/TIME`<br>DATE format: `dd-mm-yyyy`<br>TIME format: `HH:mm`                                                                        | `findAppointment n/John Doe`<br>`findAppointment ic/S1234`<br>`findAppointment date/01-04-2025`<br>`findAppointment time/12:00`                                                                            |
| Delete Appointment  | `deleteAppointment NRIC date/DATE time/TIME`<br>DATE format: `dd-mm-yyyy`<br>TIME format: `HH:mm`                                                                                                                                                        | `deleteAppointment S9534567A date/01-04-2025 time/12:00`                                                                                                                                                   |
| Find Visits         | `findVisit NRIC`                                                                                                                                                                                                                                         | `findVisit S9534567A`                                                                                                                                                                                      |
| Find Diagnosis      | `findDiagnosis diagnosis`                                                                                                                                                                                                                                | `findDiagnosis fever`                                                                                                                                                                                      |
| Find Medication     | `findMedication medication`                                                                                                                                                                                                                              | `findMedication Panadol`                                                                                                                                                                                   | 
| Exit                | `exit`                                                                                                                                                                                                                                                   | `exit`                                                                                                                                                                                                     |
