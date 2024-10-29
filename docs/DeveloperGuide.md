# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}


## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

# Implementation

[Proposed] Appointment Feature

The proposed appointment mechanism is facilitated by CommandHandler. It creates an appointment slot via the Appointment 
class and recorded within the AppointmentRecord class. The appointment records will then be saved into the FileHandler

- `CommandHandler.appointment(String input, AppointmentRecord appointmentRecord)` - check the input to check if valid
- `FileHandler.initFile(AppointmentRecord appointmentRecord)` - loads any saved appointment records
- `appointmentRecord.checkAvailability(LocalDate date, LocalTime time)` - check for availability at given time and date
- `AppointmentRecord.addAppointment(Appointment appointment)` - add appointment to the appointment record
- `Appointment(String name, String nric, String date, String time)` - creates appointment with the given name, nric,
- date and time
- `FileHandler.autosave(AppointmentRecord appointmentRecord)` - save the appointment records
  

Doctor starts up the application and executes `appointment n/John Doe ic/S123A date/18-11-2024 time/18:00`

Object Diagram portray before execution of application feature:
![img.png](BeforeExecutionOD.png)

Object Diagram after execution of appointment feature:
![img.png](AfterExecutionOD.png)

The Sequence Diagram for the execution of the appointment feature:
![img.png](NewAppointmentSD.png)

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

- Mainstream OS: Windows, Linux, Unix, MacOS
- NRIC: Identification Number

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
