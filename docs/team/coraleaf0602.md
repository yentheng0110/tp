# Cora Zhang - Project Portfolio Page

## Overview
BookBob is a desktop application tailored for Dr Bob’s private General Practitioner clinic. It facilitates the storage
and retrieval of patient information, including names, NRICs, genders, dates of birth, phone numbers, home addresses,
allergies, medical histories and visit records with details on diagnoses and prescribed medications. BookBob also 
tracks daily appointments by providing reminders every time the application is opened. Designed for use with a 
Command Line Interface (CLI), BookBob streamlines the management of patient records and appointment.
### Summary of Contributions
- Code Contributed: 
  - [RepoSense Link](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=g13nd0n)
- Enhancements implemented
  - Help Command 
    - What does it do?
      - Provides users with guidance on how to use the various functions of BookBob, it displays a list of available 
      commands, along with descriptions and examples for using them.
    - Why does BookBob need it?
      - Enhances the product by offering aid to using the application. This feature allows users to directly see all 
      available functionalities of BookBob without referencing any other external material.
  - Exit Command 
    - What does it do?
      - Closes the BookBob application. It ensures that all data is saved properly before terminating the program.
    - Why does BookBob need it?
      - Ensures that no data is lost before closure of application, this is critical in fact-paced 
      environment of a clinic. 
  - Visit Class 
    - What does it do?
      - Represents a record of each patient visit. It includes details such as the date and time of patient visit, 
      diagnoses made and medications prescribed in that specific visit.
    - Why does BookBob need it?
      - Allows the user to track each patient's visit history, which is crucial in effective management of patient 
      data. The user would be able to see a comprehensive view of patient visit details, which aids in better medication
      decisions and more personalised patient care. 
  - Refactoring and Enhancing FileHandler Class
    - What does it do?
      - Automatically saves and retrieves patient records and appointment records to and from a data file saved locally.
    - Why does BookBob need it?
      - Reduces the amount of duplicated code which improves the maintainability and readability of the software.
    - Highlights 
      - Adds visit details to text file 
      - Parses text file with Patient detail which contains visit details
      - Refactor FileHandler class to use interfaces and polymorphism to reduce code duplication
- Contributions to the UG
  - Added documentation for the features `help` and `exit`
  - Added a summary of commands for BookBob
- Contributions to the DG 
  - Added class diagram for `Main` and `Visit` class
  - Added sequence diagram and object diagram for `Main` class
  - Added information for Appendix B: User Stories
  - Added a summary of commands for BookBob under Command Summary Reference.
  - Added information about the overall Architecture of BookBob
- Contributions to team-based tasks
  - Add testing methods for `Visit` class, `add`, `delete`, `list`, and `find` command
  - Fixed PE-D bugs: Added formatting to phone number
- Review/mentoring contributions: 
  - Aided in the process of fixing CI issues - however was not merged in the end
  - Sent reminders to teammates on project expectations and adhering to project guidelines
  - Actively contributed input in project meetings

## Contributions to the Developer Guide
- Main Class Diagram\
  ![Main.png](..%2FMain.png)
- Visit Class Diagram\
  ![Visit.png](..%2FVisit.png)
- Main Sequence Diagram\
  ![MainSequenceDiagram.png](..%2FMainSequenceDiagram.png)
- Main Object Diagram\
  ![MainObjectDiagram.png](..%2FMainObjectDiagram.png)

```
## Appendix B : User Stories

| Version | As a...  | I want to...                                                          | So that I can...                                                    |
|---------|----------|-----------------------------------------------------------------------|---------------------------------------------------------------------|
| v1.0    | new user | see usage instructions for BookBob                                    | quickly understand how to use the app                               |
| v1.0    | new user | input a complex patient case                                          | test BookBob's capabilities thoroughly                              |
| v1.0    | user     | quickly search for a patient record                                   | retrieve information efficiently during consultations               |
| v1.0    | user     | delete case patient information                                       | retain patient information which I am still actively taking care of |
| v1.0    | user     | set up automatic backups of my patient data                           | never lose important information due to technical issues            |
| v2.0    | user     | view my daily appointments at a glance                                | prepare for my day efficiently                                      |
| v2.0    | user     | easily refer to and update a patient's care plan over multiple visits | ensure consistent, long-term care                                   |                                       
```

## Contributions to the User Guide
- Included headings of the UG contributions as the features I've added are quite long
1. Viewing Help
2. Exiting the Program
3. Command Summary