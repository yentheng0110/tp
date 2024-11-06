# Ma Yitao - Project Portfolio Page

## Overview
BookBob is a desktop application tailored for Dr Bob's private General Practitioner clinic. 
It helps the doctor to archive patient information, including names, NRICs, genders, dates of birth,
phone numbers, home addresses, allergies, medical histories and visit records with details of diagnosis
and medication for each visit when applicable. It also provides a system for simple records
of future appointments, giving reminders in the morning for the day's appointments when the app initiated.

### Summary of Contributions
### Summary of Contributions

- Code Contributed: [RepoSense Link](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=princecatt)
- Enhancements implemented:
  New Features
  - Wrote major parts for FileHandler class
      - What it does: allow automatic saving and retrieving of records and AppointmentRecord in the local data file
      - Justifications: This feature improves the product by allowing user to spare effort to save data manually
      - Highlights: 
        - The initialisation of the system will check the designated directory for the data file and create level by
        level if not found. The class will then read in the data file and store respectively into newly created Records
        and AppointmentRecord classes, ready for doctor's command.
        - The saving of the file is done every time a change happens to the Records or the AppointmentRecord classes 
        automatically. Every saving is re-writing the whole file with the two changed classes. The two classes are
        respectively stored in two files `bookbob_data` and `bookbob_appointment` located at `./data/`
  - Wrote 3 commands for finding visits
      - What it does: allow user to find visits by ic, diagnosis and medications
      - Justifications: This feature improve the product by allowing user to search for visits and corresponding patient
    with specific fields so that the doctor could compare and make best decisions
      - Highlights:
        - The find by ic command will return the patient's all visit records, while the find by diagnosis and by
        medications will return the patient's information and the certain visit record where the diagnosis/medication is
        found
  - Wrote additional testing for FileHandler class
- Contributions to User Guide:
  - Added documentation for the features `save/retrieve patient records and appointment records`, `find patient visits`
- Contributions to Developer Guide:
  - Added class diagram for `FileHandler` class
  - Added association arrows for all class diagrams
  - Fixed style problems for most UML diagrams
  - Added object diagram for the adding new patient behavior
  - Added information for the Non-functional requirements 
- Review/mentoring contributions:
  - Help fixed the CI issues that was accumulated over time(checkstyle test and checkstyle main)
  - Fixed some bugs throughout the project: LocalDateTime formatter issue... 
