# Wong Yen Theng's Project Portfolio Page

## Overview
BookBob is a desktop application tailored for Dr Bob's private General Practitioner clinic. BookBob helps
streamline clinic management by storing and retrieving patient information, including names, NRICs, genders, dates of
birth, phone numbers, home addresses, allergies, medical histories and detailed visit records (visit dates and times,
diagnoses and prescribed medications). BookBob also assists Dr Bob in staying organised by tracking daily appointments 
and sending reminders each morning. Optimised for a Command Line Interface (CLI), BookBob enables efficient management 
of patient information and appointment scheduling.

Given below are my contributions to the project.

---
## Summary of Contributions
- <b>Code contributed:</b> [RepoSense link](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=yentheng0110&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-09-20&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)
- <b>Enhancements implemented:</b>
  - <b>`CommandHandler` class, `Main` class and `Records` class</b><br>
    - >`add` command
    - Adds a new patient record based on the details given by the user. 
    - Mandatory fields (name, NRIC, visit date and time) require user input to add a patient successfully. If any of 
    these fields are missing, an error message will prompt the user to provide the missing inputs. 
    - If an input is incorrectly formatted but mandatory fields are correctly filled, the patient will still be added, 
    with incorrectly formatted fields left empty and other inputs unaffected.
    - Accepts multiple entries for attributes like allergies, medical histories, diagnoses and medications, separated by
    commas.<br>
    <br>
    - >`list` command
    - Iterates through all patient records and displays their details, including name, NRIC, date of birth, sex, phone 
    number, home address, allergies and medical histories. For each patient, the command also iterates through their 
    visit records, displaying visit dates and times, diagnoses and medications prescribed during the visits.<br>
    <br>
    - <b>Completeness of this Feature:</b><br>
    The data is formatted neatly for printing. To enhance readability and help users easily differentiate between 
    records, I also included a blank line between each patient's details.<br>
    <br>
    - > `edit` command: 
    - Allows users to modify specific field(s) in a patient's record. The user provides the patient's NRIC and 
    specifies the field(s) they wish to edit, such as name, NRIC, date of birth, sex, phone number, home address, 
    allergies or medical histories, all of which are optional fields.<br>
     <br>
    - <b>Completeness of this Feature:</b><br>
    The system updates the specified field(s) while retaining the existing data for other attributes, allowing for 
    seamless updates in patient records.<br>
     <br>
    - >`editVisit` command: 
    - Allows users to modify a patient's visit record. The user provides the patient's NRIC, the visit date and time
    to be edited (mandatory fields), and specifies the new visit detail(s) to be updated such as the updated date, 
    diagnoses and medications, all of which are optional fields.<br>
    <br>
    - <b>Completeness of this Feature:</b><br>
    The system updates the visit record with the new information while preserving other visit details, enabling 
    seamless updates to a patient's visit record.

    <b>Implementation Complexity for `add`, `list`, `edit` and `editVisit` commands:</b><br>
    Each field requires custom logic to validate inputs and printing meaningful error messages for the users. Examples:<br>
    1. Names can only contain alphabets (both uppercase and lowercase), hyphens, slashes, commas and spaces.
    2. a Singapore NRIC must be 9 characters long, starting with "S" or "T", containing numbers in the middle and ending with an alphabet) (case-insensitive).<br>
       I also created a method named `nricIsPresentInExistingRecords(String name, String nric)` in the `Records` class to 
       prevent duplicate NRIC entries. 
    3. Date of birth must be in the format: dd-MM-yyyy 
    4. Sex can only be "F", "Female", "M" or "Male" (case-insensitive).
    5. a Singapore phone number must be 8 digits long and start with "8" or "9")
    6. Home addresses can only contain alphabets (both uppercase and lowercase), numbers, spaces and hyphens. 
    7. Visit date and time must be in the format: dd-MM-yyyy HH:mm
  
  - <b>`Parser` class</b>
I refactored the code by moving some logic from the Main class to a newly created Parser class to adhere to the Single
Responsibility Principle. The Parser class handles the commands inputted by the user.

- <b>Contributions to the UG</b><br>
I added the following sections: Introduction, Table of Contents, Notes under the Features section and sections for
Adding a Patient Record, Listing All Patient Records, Editing a Patient Record and Editing a Visit Record.

- <b>Contributions to the DG</b><br>
I added the following sections: Design & Implementation (Adding New Visits for Existing Patients) and Product Scope 
(Target User Profile and Value Proposition). Additionally, I updated the Command Summary Reference to account for code 
modifications over time, ensuring users receive accurate guidance. I also created the following UML diagrams: class 
diagrams for the Patient and Records classes, object diagrams before and after adding a new visit for an existing patient,
and a sequence diagram for the execution of the addVisit command.

- <b>Contributions to Team-Based Tasks</b>
   - Made necessary general code enhancements, some examples are given below:<br>
a. Extracted duplicate codes into a method such as printUnknownCommand(String command)<br>
b. Modified arrowhead code for nricIsPresentInExistingRecords(String name, String nric) method by using guard clauses.
   - Enabled assertions in Gradle. 
   - Maintained the issue tracker. 
   - Released the JAR file for v1.0, v2.0 and v2.1 milestones, released UG and DG for v2.0 and v2.1 milestones on GitHub Release.
   - Created JUnit tests to thoroughly test BookBob, specifically PatientTest, RecordsTest and CommandHandlerTest, with a 
focus on the `add`, `list`, `edit` and `editVisit` commands, to achieve maximum test coverage.<br>

- <b>Review/mentoring Contributions</b>
  - Links to some PRs I reviewed: [PR1](https://github.com/AY2425S1-CS2113-T10-2/tp/pull/138), 
  [PR2](https://github.com/AY2425S1-CS2113-T10-2/tp/pull/153),
  [PR3](https://github.com/AY2425S1-CS2113-T10-2/tp/pull/186)

  - I always try my best to help my team members and answer their queries in our Telegram group chat, suggesting possible 
  solutions. 

- <b>Contributions Beyond the Project Team</b>
  - Evidence of helping others: [Link to the repository's GitHub Issues where I reported bugs during PE-D (I am Tester D)](https://github.com/AY2425S1-CS2113-T11-4/tp/issues?q=is%3Aopen+is%3Aissue)
