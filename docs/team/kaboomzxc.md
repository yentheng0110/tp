# Ong JunZheng - Project Portfolio Page

## Overview
BookBob is a Command Line Interface (CLI) desktop application to assist in the efficient management of patient information and appointment scheduling.
Clinicians, Doctors, and Medical Professionals are welcomed to use BookBob. <br>

Below are my contributions to the Team Project.

---

## Summary of Contributions
- <b>Code contributed:</b> [RepoSense link](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=kaboomzxc&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20)
  - I have contributed over 3.5k LoC spread among functional code, test code and documentation writings.

- <b>Core Features implemented:</b>
    - <b>`CommandHandler` class, `Main` class </b><br>
        - >`find` command
        - "find" Finds patient details such as Name, NRIC, Phone number, Home Address, DOB, Allergy, Sex, Medical history, Diagnoses, Medications.
        - "find" command is Case-insensitive and Trims trailing spaces.
        - Partial matching is supported and allowed. Multiple search parameters are allowed. 
        - Parameters entered in the input can be in any order. Very flexible search criteria.
        - Complete with extensive user-input validation, robust comprehensive error handling, logging.
        - High Complexity from search logic handling multiple criteria simultaneously, and clean parameter extraction using regex and string manipulation, and Stream usage to filter results. <br>
          <br>
        - >`addVisit` command
        - "addVisit" Adds a new visit record for an existing patient. Checks for and validates existing patient.
        - Parameters entered in the input can be in any order.
        - Supports inputs of multiple Diagnoses and/or Medications(seperating by commas).
        - Mandatory field checks and validation of "NRIC" and "Visit Date&Time".
        - Date and Time format validation in : dd-MM-yyyy HH:mm .
        - Validates and compares local system Date&Time and prevents addition of Visits in a Future Date&Time. 
        - Guards against duplicate visits of the same patient at the same exact Date&time.
        - Complete with extensive validation layers, robust comprehensive error handling, logging. 
        - Integrated with data file persistence(saving and loading). <br>
          <br>
- <b>Enhancements implemented:</b>
   - Implemented JUnit Tests for my "find" command and "addVisit" command. Implement Tests in the aspiration for higher quality and reliability of our product.
   - Contributed testing of Parser class, ParserTest.java and achieved 51 tests case passed, 100% class, 100% method, 70% line, 92% branch coverage. [PR#375](https://github.com/AY2425S1-CS2113-T10-2/tp/pull/375)
   - I enhanced the appearance of all our team organisation's Github Pages by using Jekyll and HTML+CSS.  <br>
     <br>
- <b>Contributions to the User Guide(UG):</b><br>
    - I made extensive contributions to the UG.
    - For UG v1.0, I contributed a near-complete full draft.
    - For UG v2.0 and v2.1, added the following sections : "Quick Start", "Finding a Patient Record", "Adding a Visit Record", "FAQ", "Appendix A: Miscellaneous". 
    I also contributed to many other portions throughout the UG, e.g. all the colored info boxes with Notes. 
    - I also contributed the implemenation of the fine line dividers between sections, enhancing the appearance of the whole UG. 
      And Proofreading, Formatting, cleaning up & Typo fixing.
    - I also contributed to the "ReadME" file in ./tp/docs, which fronts the main landing page of our tP Github pages.<br>
     <br>
- <b>Contributions to the Developer Guide(DG):</b><br>   
    - I made extensive contributions to the DG.
    - I added the following sections : "Table of Contents", "Appendix E: Instructions for Manual Testing". 
    - I also contributed to many other portions throughout the DG e.g. adding the fine line dividers between sections, formatting, proofreading, etc.
    - I did almost all the (revision v2) Diagrams seen in the DG. (Note, our v1 Diagrams were shared equally, 
      for v2 the team worked on other tasks e.g. refactoring the codebase while I worked on the v2 Diagrams) <br>
    <br>
- <b>Contributions to Team-Based Tasks</b>
    - Setting up of GitHub team Org and Repo.
    - Fix Continuous Integration(CI) and Checkstyle issues.
    - Generating UG and DG PDF files and sending to group.
    - JUnit Testing. Tools : Integrated Mockito into our project.
    - Fixing Bugs, e.g. fixing compile error [Issue#140](https://github.com/AY2425S1-CS2113-T10-2/tp/issues/140) <br>
    <br>
- <b>Review/Mentoring Contributions</b>
    - I always try my best to be a meaningful and helpful teammate, being very active and responsive in tP throughout, engaging in discussions, suggesting solutions and improvements,
      responding to questions, giving reminders, asking TA and Prof and conveying to our Team, finding and screenshotting info from CS2113 website to provide easy reference and readability to team, etc. <br>
    <br>
- <b>Contributions Beyond the Project Team</b>
    - Evidence of helping others : 
      - Link to the repository's GitHub Issues where I reported bugs during PE-D [(I am Tester E)](https://github.com/AY2425S1-CS2113-T11-3/tp/issues?q=tester+E)
      - PRs reviewed(with non-trivial review comments) : [W12-1](https://github.com/nus-cs2113-AY2425S1/tp/pull/1)