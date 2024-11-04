# User Guide

## Introduction

{Give a product intro}

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 17 or above installed.
1. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features 

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
