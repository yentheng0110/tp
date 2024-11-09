package bookbob.functions;

import bookbob.entity.Appointment;
import bookbob.entity.AppointmentRecord;
import bookbob.entity.Patient;
import bookbob.entity.Records;
import bookbob.entity.Visit;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CommandHandler {
    private static final Logger logger = Logger.getLogger(CommandHandler.class.getName());
  
    // Prints output for help command
    //@@author coraleaf0602 and yentheng0110 and G13nd0n and PrinceCatt and kaboomzxc
    public void help() {
        System.out.println("""
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
                |             | date/VISIT_DATE_TIME                  | date/06-11-2024 14:00           |
                |             | [newDate/NEW_DATE_TIME]  [d/DIAGNOSIS]| newDate/08-11-2024 14:00        |
                |             | [m/MEDICATION]                        | d/Asthma m/Panadol, Antibiotics |
                |             | DATE format: dd-mm-yyyy               |                                 |
                |             | TIME format: HH:mm                    |                                 |
                +-------------+---------------------------------------+---------------------------------+
                | List        | list                                  | list                            |
                +-------------+---------------------------------------+---------------------------------+
                | Find        | find n/NAME            OR             | find n/John Doe                 |
                |             | find ic/NRIC           OR             | find ic/S1234                   |
                |             | find p/PHONE_NUMBER    OR             | find p/91234567                 |
                |             | find ha/HOME_ADDRESS   OR             | find ha/NUS PGPR                |
                |             | find dob/DATE_OF_BIRTH OR             | find dob/01011990               |
                |             | find al/ALLERGY        OR             | find al/Peanuts                 |
                |             | find s/SEX             OR             | find s/Female                   |
                |             | find mh/MEDICAL_HISTORY               | find mh/Diabetes                |
                +-------------+---------------------------------------+---------------------------------+
                | Delete      | delete NRIC                           | delete S9534567A                |
                +-------------+---------------------------------------+---------------------------------+
                | Add         | appointment n/NAME ic/NRIC            | appointment n/James Ho          |
                | Appointment | date/DATE time/TIME                   | ic/S9534567A date/01-04-2025    |
                |             | DATE format: dd-mm-yyyy               | time/12:00                      |
                |             | TIME format: HH:mm                    |                                 |
                +-------------+---------------------------------------+---------------------------------+
                | List        | listAppointments                      | listAppointments                |
                | Appointment |                                       |                                 |
                +-------------+---------------------------------------+---------------------------------+
                | Find        | findAppointment n/NAME          OR    | findAppointment n/John Doe      |
                | Appointment | findAppointment ic/NRIC         OR    | findAppointment ic/S1234567A    |
                |             | findAppointment date/DATE       OR    | findAppointment date/01-04-2025 |
                |             | findAppointment time/TIME       OR    | findAppointment time/12:00      |
                |             | DATE format: dd-mm-yyyy               |                                 |
                |             | TIME format: HH:mm                    |                                 |
                +-------------+---------------------------------------+---------------------------------+
                | Delete      | deleteAppointment ic/NRIC             | deleteAppointment ic/S9534567A  |
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
                | Save        | save (automatic)                      |                                 |
                +-------------+---------------------------------------+---------------------------------+
                | Retrieve/   | retrieve or import                    |                                 |
                | Import      | (automatic)                           |                                 |
                +-------------+---------------------------------------+---------------------------------+
                | Exit        | exit                                  | exit                            |
                +-------------+---------------------------------------+---------------------------------+""");
    }

    //@@author yentheng0110
    public void add(String input, Records records) throws IOException {
        String name = extractName(input);
        if (name.isEmpty()) {
            return;
        }
        String nric = extractNric(input);
        if (nric.isEmpty()) {
            return;
        }
        String sex = extractGender(input);
        LocalDate dateOfBirth = extractDateOfBirth(input);
        String phoneNumber = extractPhoneNumber(input);
        String homeAddress = extractHomeAddress(input);
        ArrayList<String> diagnoses = extractDiagnoses(input);
        ArrayList<String> medications = extractMedications(input);
        ArrayList<String> allergies = extractAllergies(input);
        ArrayList<String> medicalHistories = extractMedicalHistories(input);
        ArrayList<Visit> visits = new ArrayList<>();
        LocalDateTime visitDate = extractVisitDateTime(input);

        Visit visit = new Visit(visitDate, diagnoses, medications);
        visits.add(visit);

        records.addPatient(name, nric, visits, sex, dateOfBirth, phoneNumber, homeAddress, allergies, medicalHistories);

        FileHandler.autosave(records);
    }

    //@@author yentheng0110
    // Utility method to find the start of the next field or the end of the input string
    private int findNextFieldStart(String input, int currentIndex) {
        int nextIndex = input.length(); // Default to end of input
        String[] prefixes = {"ic/", "p/", "d/", "m/", "ha/", "dob/", "v/",
                             "date/", "time/", "al/", "s/", "mh/", "/to", "newDate/"};
        for (String prefix : prefixes) {
            int index = input.indexOf(prefix, currentIndex);
            if (index != -1 && index < nextIndex) {
                nextIndex = index;
            }
        }
        return nextIndex;
    }

    //@@author yentheng0110
    public void list(Records records) {
        List<Patient> patients = records.getPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        DateTimeFormatter dobFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Patient patient : patients) {
            String dateOfBirthString = "";
            if (patient.getDateOfBirth() != null) {
                dateOfBirthString = patient.getDateOfBirth().format(dobFormatter);
            }

            // Print patient information
            System.out.println("Name: " + patient.getName() + ", NRIC: " + patient.getNric() +
                    ", Phone: " + patient.getPhoneNumber() + ", Home Address: " + patient.getHomeAddress() +
                    ", DOB: " + dateOfBirthString + ", Allergies: " + patient.getAllergies() +
                    ", Sex: " + patient.getSex() + ", Medical Histories: " + patient.getMedicalHistories());

            //@@author kaboomzxc
            // Print all visits
            for (Visit visit : patient.getVisits()) {
                System.out.println("    Visit Date: " + visit.getVisitDate().format(formatter) +
                        ", Diagnosis: " + visit.getDiagnoses() +
                        ", Medications: " + visit.getMedications());
            }

            System.out.println(); // Add blank line between patients
        }
    }

    //@@author yentheng0110
    public void edit(String input, Records records) throws IOException {
        // Extract NRIC from the input command
        String nric = extractNric(input);
        Patient patientToBeEdited = null;

        // Search for the patient with matching name and NRIC
        patientToBeEdited = extractPatient(records, nric, patientToBeEdited);

        if (patientToBeEdited == null) {
            System.out.println("No patient found.");
            return;
        }
        records.getPatients().remove(patientToBeEdited);
        String[] parts = input.split("/to", 2);
        if (parts.length < 2) {
            System.out.println("No fields provided to update.");
            return;
        }
        String updates = parts[1].trim();  // Get everything after "/to"
        // Extract optional fields for updating if provided by the user
        String newName = extractNewName(updates);
        String newNRIC = extractNewNric(updates);
        String newSex = extractGender(updates);
        LocalDate newDob = extractDateOfBirth(updates);
        String newPhone = extractPhoneNumber(updates);
        String newHomeAddress = extractHomeAddress(updates);
        ArrayList<String> newAllergies = extractAllergies(updates);
        ArrayList<String> newMedicalHistories = extractMedicalHistories(updates);

        // Update patient details only if new values are provided
        if (!newName.isEmpty()) {
            patientToBeEdited.setName(newName);
        }
        if (!newNRIC.isEmpty()) {
            patientToBeEdited.setNric(newNRIC);
        }
        if (!newSex.isEmpty()) {
            patientToBeEdited.setSex(newSex);
        }
        if (newDob != null) {
            patientToBeEdited.setDateOfBirth(newDob);
        }
        if (!newPhone.isEmpty()) {
            patientToBeEdited.setPhoneNumber(newPhone);
        }
        if (!newHomeAddress.isEmpty()) {
            patientToBeEdited.setHomeAddress(newHomeAddress);
        }
        if (!newAllergies.isEmpty()) {
            patientToBeEdited.setAllergies(newAllergies);
        }
        if (!newMedicalHistories.isEmpty()) {
            patientToBeEdited.setMedicalHistories(newMedicalHistories);
        }
        // Confirm the updated details
        System.out.println("Patient record updated successfully.");
        System.out.println("Updated patient details:");
        System.out.println(patientToBeEdited);

        records.addPatient(patientToBeEdited);
        FileHandler.autosave(records);
    }

    //@@author yentheng0110
    public void editVisit(String input, Records records) throws IOException {
        // Extract NRIC from input command
        String nric = extractNric(input);

        Patient patient = null;
        patient = extractPatient(records, nric, patient);

        if (patient == null) {
            System.out.println("No patient found with the given NRIC.");
            return;
        }

        // Extract visit date from input command
        String date = extractDate(input);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime visitDate = LocalDateTime.parse(date, formatter);

        Visit visitToBeEdited = null;

        // Search for the visit with the matching date
        for (Visit visit : patient.getVisits()) {
            if (visit.getVisitDate().equals(visitDate)) {
                visitToBeEdited = visit;
                break;
            }
        }

        if (visitToBeEdited == null) {
            System.out.println("No visit found on the given date.");
            return;
        }

        // Extract optional updates for visit\
        LocalDateTime newDate = null;
        int newDateStart = input.indexOf("newDate/");
        if (newDateStart != -1) {
            int newDateEnd = findNextFieldStart(input, newDateStart + 8);
            newDate = LocalDateTime.parse(input.substring(newDateStart + 8, newDateEnd).trim(), formatter);
            visitToBeEdited.setVisitDate(newDate);
        }

        ArrayList<String> newDiagnoses = extractDiagnoses(input);
        visitToBeEdited.setDiagnoses(newDiagnoses);


        int medicationStart = input.indexOf("m/");
        ArrayList<String> newMedications = extractMedications(input);
        visitToBeEdited.setMedications(newMedications);

        // Confirm the updated visit details
        System.out.println("Visit record updated successfully.");
        System.out.println("Updated visit details:");
        System.out.println(visitToBeEdited);

        // Save changes
        FileHandler.autosave(records);
    }

    // @@author G13nd0n
    public void delete(String nric, Records records) throws IOException {
        records.delete(nric);
        FileHandler.autosave(records);
    }

    // @@author coraleaf0602
    // Takes in an input string and determines whether to exit the program
    public void exit(String input) {
        if(input.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
    }

    //@@author kaboomzxc
    public void find(String input, Records records) {

        // Assertion to ensure input is not null
        assert input != null : "Input cannot be null";

        // Input validation
        if (input == null || input.trim().isEmpty()) {
            logger.log(Level.WARNING, "Input cannot be null or empty");
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        Map<String, String> searchParams = extractSearchParams(input);

        if (searchParams.isEmpty()) {
            logger.log(Level.WARNING, "No valid search parameters provided.");
            System.out.println("Invalid search parameters. Please use the format: "
                    + "find [n/NAME] [ic/NRIC] [p/PHONE] [ha/ADDRESS] [dob/DOB] [al/ALLERGY] [s/SEX] " +
                    "[mh/MEDICAL_HISTORY]");
            return;
        }

        List<Patient> matchedPatients = records.getPatients().stream()
                .filter(patient -> matchesSearchCriteria(patient, searchParams))
                .collect(Collectors.toList());

        displayResults(matchedPatients);
    }

    //@@author kaboomzxc
    private Map<String, String> extractSearchParams(String input) {
        Map<String, String> params = new HashMap<>();
        String[] parts = input.split("\\s+");
        for (String part : parts) {
            if (part.contains("/")) {
                String[] keyValue = part.split("/", 2);
                if (keyValue.length == 2 && !keyValue[1].trim().isEmpty()) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    if (isValidSearchKey(key)) {
                        params.put(key, value.toLowerCase().trim());
                    } else {
                        logger.log(Level.WARNING, "Invalid search key encountered: {0}", key);
                        throw new IllegalArgumentException("Invalid search key: " + key);
                    }
                }
            }
        }
        return params;
    }

    //@@author kaboomzxc
    private boolean isValidSearchKey(String key) {
        return Arrays.asList("n", "ic", "p", "ha", "dob", "al", "s", "mh").contains(key);
    }

    //@@author kaboomzxc
    private boolean matchesSearchCriteria(Patient patient, Map<String, String> searchParams) {
        logger.log(Level.FINE, "Checking if patient matches search criteria: {0}", patient);

        boolean matches = searchParams.entrySet().stream().allMatch(entry -> {
            String key = entry.getKey();
            String value = entry.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            switch (key) {
            case "n":
                return patient.getName().toLowerCase().contains(value);
            case "ic":
                return patient.getNric().toLowerCase().contains(value);
            case "p":
                return patient.getPhoneNumber().toLowerCase().contains(value);
            case "ha":
                return patient.getHomeAddress().toLowerCase().contains(value);
            case "dob":
                return patient.getDateOfBirth().format(formatter).contains(value);
            case "al":
                // Check if any allergy matches the search value
                return patient.getAllergies().stream()
                        .anyMatch(allergy -> allergy.toLowerCase().contains(value));
            case "s":
                return patient.getSex().toLowerCase().contains(value);
            case "mh":
                // Check if any medical history matches the search value
                return patient.getMedicalHistories().stream()
                        .anyMatch(history -> history.toLowerCase().contains(value));
            default:
                return false;
            }
        });

        logger.log(Level.FINE, "Patient {0} matches criteria: {1}", new Object[]{patient.getNric(), matches});
        return matches;
    }
    
    //@@author kaboomzxc
    private void displayResults(List<Patient> patients) {
        if (patients.isEmpty()) {
            System.out.println("No matching patients found.");
        } else {
            System.out.println("Matching patients:");
            for (Patient patient : patients) {
                System.out.println(patient);
            }
        }
    }

    //@@author G13nd0n
    public void appointment(String input, AppointmentRecord appointmentRecord) throws IOException {
        String name = extractName(input);
        String nric = extractNric(input);;
        String date = extractDate(input);
        String time = extractTime(input);
        appointmentRecord.addAppointment(name, nric, date, time);
        FileHandler.autosave(appointmentRecord);
    }

    //@@author G13nd0n
    public void deleteAppointment(String input, AppointmentRecord appointmentRecord) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String nric = extractNric(input);
        String date = extractDate(input);
        String time = extractTime(input);
        appointmentRecord.deleteAppointment(nric, date, time);
        FileHandler.autosave(appointmentRecord);
    }

    //@@author G13nd0n
    public void listAppointments(AppointmentRecord appointmentRecord) {
        appointmentRecord.listAppointments();
    }

    //@@author G13nd0n
    public void findAppointment(String input, AppointmentRecord appointmentRecord) {
        List<Appointment> appointments = appointmentRecord.findAppointments(input);
        if (appointments.isEmpty()) {
            System.out.println("No matching appointments found.");
            return;
        }
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    //@@author G13nd0n
    public void removePastAppointments(AppointmentRecord appointmentRecord) throws IOException {
        appointmentRecord.removePastAppointments();
        FileHandler.autosave(appointmentRecord);
    }

    //@@author kaboomzxc
    public void addVisit(String input, Records records) throws IOException {
        try {
            // Extract NRIC first (mandatory field)
            String nric = extractNric(input);
            if (nric.isEmpty()) {
                return;
            }

            // Find the patient with matching NRIC
            Patient targetPatient = null;
            for (Patient patient : records.getPatients()) {
                if (patient.getNric().equals(nric)) {
                    targetPatient = patient;
                    break;
                }
            }

            if (targetPatient == null) {
                System.out.println("No patient found with NRIC: " + nric);
                return;
            }

            // Extract visit date (mandatory field)
            String visitDateString = extractVisitDate(input);
            if (visitDateString == null) {
                return;
            }

            // Parse visit date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime visitDate;
            try {
                visitDate = LocalDateTime.parse(visitDateString, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use dd-MM-yyyy HH:mm format (e.g., 21-10-2024 15:48)");
                return;
            }

            // Extract medications (optional, can be multiple)
            ArrayList<String> medications = extractMedications(input);

            // Extract diagnoses (optional, can be multiple)
            ArrayList<String> diagnoses = extractDiagnoses(input);

            // Create new visit with the collected data
            Visit newVisit = new Visit(visitDate, diagnoses, medications);
            targetPatient.getVisits().add(newVisit);

            // Save the updated records
            FileHandler.autosave(records);

            // Print confirmation message with visit details
            System.out.println("Visit added successfully for patient: " + targetPatient.getName());
            System.out.println("Visit date: " + visitDate.format(formatter));
            if (!diagnoses.isEmpty()) {
                System.out.println("Diagnoses: " + String.join(", ", diagnoses));
            }
            if (!medications.isEmpty()) {
                System.out.println("Medications: " + String.join(", ", medications));
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding visit", e);
            System.out.println("Error adding visit: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //find visit by nric and print all visits to terminal
    //@@author PrinceCatt
    public void findVisitByIc(String nric, Records records) {
        ArrayList<Patient> patientList = records.getPatients();
        boolean isFound = false;
        for (Patient patient : patientList) {
            if (patient.getNric().equals(nric)) {
                ArrayList<Visit> visits = patient.getVisits();
                isFound = true;

                for (Visit visit : visits) {
                    System.out.println(visit.toString());
                }
            }
        }
        if (!isFound) {
            System.out.println("No patient visit record found with NRIC: " + nric);
        }
    }

    //find patient by diagnosis and print the specific patient and visit to terminal
    //@@author PrinceCatt
    public void findVisitByDiagnosis(String symptom, Records records) {
        ArrayList<Patient> patientList = records.getPatients();
        boolean found = false;
        for (Patient patient : patientList) {
            ArrayList<Visit> visits = patient.getVisits();
            for (Visit visit : visits) {
                if (visit.getDiagnoses().contains(symptom) || patient.getMedicalHistories().contains(symptom)) {
                    System.out.println("---------------------------------");
                    System.out.println(patient.toString());
                    System.out.println(visit.toString());
                    System.out.println("---------------------------------");
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No patient found with symptom: " + symptom);
        }
    }

    //find visit by medication and print all visits to terminal
    //@@author PrinceCatt
    public void findVisitByMedication(String medication, Records records) {
        ArrayList<Patient> patientList = records.getPatients();
        boolean isFound = false;
        for (Patient patient : patientList) {
            ArrayList<Visit> visits = patient.getVisits();
            for (Visit visit : visits) {
                if (visit.getMedications().contains(medication)) {
                    System.out.println("---------------------------------");
                    System.out.println(patient.toString());
                    System.out.println(visit.toString());
                    System.out.println("---------------------------------");
                    isFound = true;
                }
            }
        }
        if (!isFound) {
            System.out.println("No patient found with medication: " + medication);
        }
    }

    //@@author G13nd0n
    private LocalDateTime extractVisitDateTime(String input) {
        LocalDateTime visitDate = null;
        String visitDateString = extractVisitDate(input);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        try {
            visitDate = LocalDateTime.parse(visitDateString, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid visit date format. Please use 'dd-MM-yyyy HH:mm' format.");
        }
        return visitDate;
    }

    //@@author G13nd0n
    private ArrayList<String> extractMedicalHistories(String input) {
        int lengthOfMedicalHistoriesIndicator = 3;
        ArrayList<String> medicalHistories = new ArrayList<>();
        int medicalHistoryStart = input.indexOf("mh/");
        if (medicalHistoryStart != -1) {
            int medicalHistoryEnd = findNextFieldStart(input, medicalHistoryStart +
                    lengthOfMedicalHistoriesIndicator);
            String medicalHistoryInput = input.substring(medicalHistoryStart + lengthOfMedicalHistoriesIndicator,
                    medicalHistoryEnd).trim();
            String[] medicalHistoriesArray = medicalHistoryInput.split(",\\s*");
            for (String medicalHistory : medicalHistoriesArray) {
                medicalHistories.add(medicalHistory.trim());
            }
        }
        return medicalHistories;
    }

    //@@author G13nd0n
    private ArrayList<String> extractAllergies(String input) {
        int lengthOfAllergiesIndicator = 3;
        ArrayList<String> allergies = new ArrayList<>();
        int allergyStart = input.indexOf("al/");
        if (allergyStart != -1) {
            int allergyEnd = findNextFieldStart(input, allergyStart + lengthOfAllergiesIndicator);
            String allergyInput = input.substring(allergyStart + lengthOfAllergiesIndicator, allergyEnd).trim();
            String[] allergiesArray = allergyInput.split(",\\s*");
            for (String allergy : allergiesArray) {
                allergies.add(allergy.trim());
            }
        }
        return allergies;
    }

    //@@author G13nd0n
    private ArrayList<String> extractMedications(String input) {
        int lengthOfMedicationIndicator = 2;
        ArrayList<String> medications = new ArrayList<>();
        int medicationStart = input.indexOf("m/");
        if (medicationStart != -1) {
            int medicationEnd = findNextFieldStart(input, medicationStart + lengthOfMedicationIndicator);
            String medicationInput = input.substring(medicationStart + lengthOfMedicationIndicator,
                    medicationEnd).trim();
            String[] medsArray = medicationInput.split(",\\s*");
            for (String med : medsArray) {
                medications.add(med.trim());
            }
        }
        return medications;
    }

    //@@author G13nd0n
    private ArrayList<String> extractDiagnoses(String input) {
        int lengthOfDiagnosesIndicator = 2;
        ArrayList<String> diagnoses = new ArrayList<>();
        int diagnosisStart = input.indexOf("d/");
        if (diagnosisStart != -1) {
            int diagnosisEnd = findNextFieldStart(input, diagnosisStart + lengthOfDiagnosesIndicator);
            String diagnosisInput = input.substring(diagnosisStart + lengthOfDiagnosesIndicator, diagnosisEnd).trim();
            String[] diagnosisArray = diagnosisInput.split(",\\s*");
            for (String diagnosis : diagnosisArray) {
                diagnoses.add(diagnosis.trim());
            }
        }
        return diagnoses;
    }

    //@@author G13nd0n
    private String extractHomeAddress(String input) {
        int lengthOfHomeAdressIndicator = 3;
        String homeAddress = "";
        int homeAddressStart = input.indexOf("ha/");
        if (homeAddressStart != -1) {
            int homeAddressEnd = findNextFieldStart(input, homeAddressStart + lengthOfHomeAdressIndicator);
            homeAddress = input.substring(homeAddressStart + lengthOfHomeAdressIndicator, homeAddressEnd).trim();
        }
        return homeAddress;
    }

    //@@author G13nd0n
    private String extractPhoneNumber(String input) {
        int lengthOfPhoneNumberIndicator = 2;
        String phoneNumber = "";
        int phoneStart = input.indexOf("p/");
        if (phoneStart != -1) {
            int phoneEnd = findNextFieldStart(input, phoneStart + lengthOfPhoneNumberIndicator);
            phoneNumber = input.substring(phoneStart + lengthOfPhoneNumberIndicator, phoneEnd).trim();
        }
        return phoneNumber;
    }

    //@@author G13nd0n @@PrinceCatt @@yentheng0110
    private LocalDate extractDateOfBirth(String input) throws DateTimeParseException {
        LocalDate dateOfBirth = null;
        int dobStart = input.indexOf("dob/");
        if (dobStart != -1) {
            try {
                int lengthOfDateOfBirthIndicator = 4;
                String dateOfBirthString = "";
                int dobEnd = findNextFieldStart(input, dobStart + lengthOfDateOfBirthIndicator);
                dateOfBirthString = input.substring(dobStart + lengthOfDateOfBirthIndicator, dobEnd).trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                dateOfBirth = LocalDate.parse(dateOfBirthString, formatter);
            } catch (DateTimeParseException e) {
                logger.warning("Invalid date of birth entered. Please follow the format: dd-MM-yyyy");
                throw new IllegalArgumentException("Invalid date of birth entered. " +
                        "Please follow the format: dd-MM-yyyy");

            }
        }
        return dateOfBirth;
    }

    //@@author G13nd0n
    private String extractGender(String input) {
        int lengthOfGenderIndicator = 2;
        String sex = "";
        int sexStart = input.indexOf("s/");
        if (sexStart != -1) {
            int sexEnd = findNextFieldStart(input, sexStart + lengthOfGenderIndicator);
            sex = input.substring(sexStart + lengthOfGenderIndicator, sexEnd).trim();
        }
        return sex;
    }

    //@@author G13nd0n
    private String extractTime(String input) {
        int lengthOfTimeIndicator = 5;
        int timeStart = input.indexOf("time/");
        if (timeStart == -1) {
            System.out.println("Please provide the time");
        }
        int timeEnd = findNextFieldStart(input, timeStart + lengthOfTimeIndicator);
        String time = input.substring(timeStart + lengthOfTimeIndicator, timeEnd).trim();
        return time;
    }

    //@@author G13nd0n
    private String extractDate(String input) {
        int lengthOfDateIndicator = 5;
        int dateStart = input.indexOf("date/");
        if (dateStart == -1) {
            System.out.println("Please provide the date");
        }
        int dateEnd = findNextFieldStart(input, dateStart + lengthOfDateIndicator);
        String date = input.substring(dateStart + lengthOfDateIndicator, dateEnd).trim();
        return date;
    }

    //@@author G13nd0n
    private String extractNric(String input) {
        int lengthOfNricIndicator = 3;
        int nricStart = input.indexOf("ic/");
        if (nricStart == -1) {
            System.out.println("Please provide the patient's NRIC.");
            return "";
        }
        int nricEnd = findNextFieldStart(input, nricStart + lengthOfNricIndicator);
        String nric = input.substring(nricStart + lengthOfNricIndicator, nricEnd).trim();
        return nric;
    }

    //@@author G13nd0n
    private String extractNewNric(String updates) {
        int lenghtOfNewNricIndicator = 6;
        String newNRIC = "";
        int newNRICStart = updates.indexOf("newic/");
        if (newNRICStart != -1) {
            int newNRICEnd = findNextFieldStart(updates, newNRICStart + lenghtOfNewNricIndicator);
            newNRIC = updates.substring(newNRICStart + lenghtOfNewNricIndicator, newNRICEnd).trim();
        }
        return newNRIC;
    }

    //@@author G13nd0n
    private String extractName(String input) {
        int lengthOfNameIndicator = 2;
        int nameStart = input.indexOf("n/");
        if (nameStart == -1) {
            System.out.println("Please provide the patient's name");
        }
        int nameEnd = findNextFieldStart(input, nameStart + lengthOfNameIndicator);
        String name = input.substring(nameStart + lengthOfNameIndicator, nameEnd).trim();
        return name;
    }

    private String extractNewName(String input) {
        int lengthOfNameIndicator = 2;
        int nameStart = input.indexOf("n/");
        if (nameStart == -1) {
            return "";
        }
        int nameEnd = findNextFieldStart(input, nameStart + lengthOfNameIndicator);
        String name = input.substring(nameStart + lengthOfNameIndicator, nameEnd).trim();
        return name;
    }

    //@@author G13nd0n
    private static Patient extractPatient(Records records, String nric, Patient patientToBeEdited) {
        for (Patient patient : records.getPatients()) {
            if (patient.getNric().trim().replaceAll("\\s+", "")
                    .equalsIgnoreCase(nric.replaceAll("\\s+", "").trim())) {
                patientToBeEdited = patient;
                break;
            }
        }
        return patientToBeEdited;
    }

    private String extractVisitDate(String input) {
        int lengthOfVisitIndicator = 2;
        int visitStart = input.indexOf("v/");
        if (visitStart == -1) {
            System.out.println("Please provide the visit date and time.");
            return null;
        }
        int visitEnd = findNextFieldStart(input, visitStart + lengthOfVisitIndicator);
        String visitDateString = input.substring(visitStart + lengthOfVisitIndicator, visitEnd).trim();
        return visitDateString;
    }

}
