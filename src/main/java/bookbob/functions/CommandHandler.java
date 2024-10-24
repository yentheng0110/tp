package bookbob.functions;

import bookbob.entity.Appointment;
import bookbob.entity.AppointmentRecord;
import bookbob.entity.Patient;
import bookbob.entity.Records;
import bookbob.entity.Visit;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
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

    public CommandHandler() throws IOException {
    }
  
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
                +-------------+---------------------------------------+---------------------------------+
                | Add Visit   | addVisit ic/NRIC v/VISIT_DATE_TIME    | addVisit ic/S9534567A           |
                |             | [d/DIAGNOSIS] [m/MEDICATION]          | v/21-10-2024 15:48              |
                |             |                                       | d/Fever,Headache,Flu            |
                |             |                                       | m/Paracetamol,Ibuprofen         |
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
                +-------------+---------------------------------------+---------------------------------+""");
    }

    //@@author yentheng0110
    public void add(String input, Records records) throws IOException {
        String name = "";
        String nric = "";
        String sex = "";
        String dateOfBirth = "";
        String phoneNumber = "";
        String homeAddress = "";
        ArrayList<Visit> visits = new ArrayList<>();
        ArrayList<String> diagnoses = new ArrayList<>();
        ArrayList<String> medications = new ArrayList<>();
        ArrayList<String> allergies = new ArrayList<>();
        ArrayList<String> medicalHistories = new ArrayList<>();

        // Extract name (a mandatory field)
        int nameStart = input.indexOf("n/");

        assert nameStart != -1 : "Please provide a valid patient name.";

        if (nameStart == -1) {
            System.out.println("Please provide the patient's name.");
            return;
        }

        int nameEnd = findNextFieldStart(input, nameStart + 2);
        name = input.substring(nameStart + 2, nameEnd).trim();

        // Extract nric (a mandatory field)
        int nricStart = input.indexOf("ic/");

        assert nricStart != -1 : "Please provide a valid patient NRIC.";

        if (nricStart == -1) {
            System.out.println("Please provide the patient's NRIC.");
            return;
        }

        int nricEnd = findNextFieldStart(input, nricStart + 3);
        nric = input.substring(nricStart + 3, nricEnd).trim();

        // Extract sex
        int sexStart = input.indexOf("s/");
        if (sexStart != -1) {
            int sexEnd = findNextFieldStart(input, sexStart + 2);
            sex = input.substring(sexStart + 2, sexEnd).trim();
        }

        // Extract date of birth
        int dobStart = input.indexOf("dob/");
        if (dobStart != -1) {
            int dobEnd = findNextFieldStart(input, dobStart + 4);
            dateOfBirth = input.substring(dobStart + 4, dobEnd).trim();
        }

        // Extract phone number
        int phoneStart = input.indexOf("p/");
        if (phoneStart != -1) {
            int phoneEnd = findNextFieldStart(input, phoneStart + 2);
            phoneNumber = input.substring(phoneStart + 2, phoneEnd).trim();
        }

        // Extract home address
        int homeAddressStart = input.indexOf("ha/");
        if (homeAddressStart != -1) {
            int homeAddressEnd = findNextFieldStart(input, homeAddressStart + 3);
            homeAddress = input.substring(homeAddressStart + 3, homeAddressEnd).trim();
        }

        // Extract the first visit date of the patient
        int visitStart = input.indexOf("v/");
        LocalDateTime visitDate = null;

        assert visitStart != -1 : "Please provide a date for the patient's visit";

        if (visitStart == -1) {
            System.out.println("Please provide a date for the patient's visit.");
            return;
        }

        int visitEnd = findNextFieldStart(input, visitStart + 2);
        String visitDateString = input.substring(visitStart + 2, visitEnd).trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        try {
            visitDate = LocalDateTime.parse(visitDateString, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid visit date format. Please use 'dd-MM-yyyy HH:mm' format.");
        }

        // Add the visit date, diagnoses and medications to a Visit object
        Visit visit = new Visit(visitDate, diagnoses, medications);
        visits.add(visit);

        // Extract diagnoses (split by comma)
        int diagnosisStart = input.indexOf("d/");
        if (diagnosisStart != -1) {
            int diagnosisEnd = findNextFieldStart(input, diagnosisStart + 2);
            String diagnosisInput = input.substring(diagnosisStart + 2, diagnosisEnd).trim();
            String[] diagnosisArray = diagnosisInput.split(",\\s*");
            for (String diagnosis : diagnosisArray) {
                diagnoses.add(diagnosis.trim());
            }
        }

        // Extract medications (split by comma)
        int medicationStart = input.indexOf("m/");
        if (medicationStart != -1) {
            int medicationEnd = findNextFieldStart(input, medicationStart + 2);
            String medicationInput = input.substring(medicationStart + 2, medicationEnd).trim();
            String[] medsArray = medicationInput.split(",\\s*");
            for (String med : medsArray) {
                medications.add(med.trim());
            }
        }

        // Extract allergies (split by comma)
        int allergyStart = input.indexOf("al/");
        if (allergyStart != -1) {
            int allergyEnd = findNextFieldStart(input, allergyStart + 3);
            String allergyInput = input.substring(allergyStart + 3, allergyEnd).trim();
            String[] allergiesArray = allergyInput.split(",\\s*");
            for (String allergy : allergiesArray) {
                allergies.add(allergy.trim());
            }
        }

        // Extract medical histories (split by comma)
        int medicalHistoryStart = input.indexOf("mh/");
        if (medicalHistoryStart != -1) {
            int medicalHistoryEnd = findNextFieldStart(input, medicalHistoryStart + 3);
            String medicalHistoryInput = input.substring(medicalHistoryStart + 3, medicalHistoryEnd).trim();
            String[] medicalHistoriesArray = medicalHistoryInput.split(",\\s*");
            for (String medicalHistory : medicalHistoriesArray) {
                medicalHistories.add(medicalHistory.trim());
            }
        }

        Patient patient = new Patient(name, nric, visits);
        patient.setSex(sex);
        patient.setDateOfBirth(dateOfBirth);
        patient.setPhoneNumber(phoneNumber);
        patient.setHomeAddress(homeAddress);
        patient.setAllergies(allergies);
        patient.setMedicalHistories(medicalHistories);
        records.addPatient(patient);
        System.out.println("Patient " + name + " with NRIC " + nric + " added.");

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

        for (Patient patient : patients) {
            // Print patient information
            System.out.println("Name: " + patient.getName() + ", NRIC: " + patient.getNric() +
                    ", Phone: " + patient.getPhoneNumber() + ", Home Address: " + patient.getHomeAddress() +
                    ", DOB: " + patient.getDateOfBirth() + ", Allergies: " + patient.getAllergies() +
                    ", Sex: " + patient.getSex() + ", Medical Histories: " + patient.getMedicalHistories());

            // Print all visits
            if (!patient.getVisits().isEmpty()) {
                for (Visit visit : patient.getVisits()) {
                    System.out.println("    Visit Date: " + visit.getVisitDate().format(formatter) +
                            ", Diagnosis: " + visit.getDiagnoses() +
                            ", Medications: " + visit.getMedications());
                }
            } else {
                System.out.println("No visits recorded");
            }
            System.out.println(); // Add blank line between patients
        }
    }

    //@@author yentheng0110
    public void edit(String input, Records records) throws IOException {
        // Extract NRIC from the input command
        int nricStart = input.indexOf("ic/");
        assert nricStart != -1 : "Please provide a valid patient NRIC in the records.";
        if (nricStart == -1) {
            System.out.println("Please provide a valid patient NRIC in the records.");
            return;
        }
        int nricEnd = findNextFieldStart(input, nricStart + 3);
        String nric = input.substring(nricStart + 3, nricEnd).trim();

        Patient patientToBeEdited = null;

        // Search for the patient with matching name and NRIC
        for (Patient patient : records.getPatients()) {
            if (patient.getNric().trim().replaceAll("\\s+", "")
                    .equalsIgnoreCase(nric.replaceAll("\\s+", "").trim())) {
                patientToBeEdited = patient;
                break;
            }
        }

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
        int nameStart = updates.indexOf("n/");
        String newName = null;
        if (nameStart != -1) {
            int nameEnd = findNextFieldStart(updates, nameStart + 2);
            newName = updates.substring(nameStart + 2, nameEnd).trim();
        }

        int newNRICStart = updates.indexOf("newic/");
        String newNRIC = null;
        if (newNRICStart != -1) {
            int newNRICEnd = findNextFieldStart(updates, newNRICStart + 6);
            newNRIC = updates.substring(newNRICStart + 6, newNRICEnd).trim();
        }

        int sexStart = updates.indexOf("s/");
        String newSex = null;
        if (sexStart != -1) {
            int sexEnd = findNextFieldStart(updates, sexStart + 2);
            newSex = updates.substring(sexStart + 2, sexEnd).trim();
        }

        int dobStart = updates.indexOf("dob/");
        String newDob = null;
        if (dobStart != -1) {
            int dobEnd = findNextFieldStart(updates, dobStart + 4);
            newDob = updates.substring(dobStart + 4, dobEnd).trim();
        }

        int phoneStart = updates.indexOf("p/");
        String newPhone = null;
        if (phoneStart != -1) {
            int phoneEnd = findNextFieldStart(updates, phoneStart + 2);
            newPhone = updates.substring(phoneStart + 2, phoneEnd).trim();
        }

        int homeAddressStart = updates.indexOf("ha/");
        String newHomeAddress = null;
        if (homeAddressStart != -1) {
            int homeAddressEnd = findNextFieldStart(updates, homeAddressStart + 3);
            newHomeAddress = updates.substring(homeAddressStart + 3, homeAddressEnd).trim();
        }

        int allergyStart = updates.indexOf("al/");
        ArrayList<String> newAllergies = null;
        if (allergyStart != -1) {
            int allergyEnd = findNextFieldStart(updates, allergyStart + 3);
            String allergiesUpdatedInput = updates.substring(allergyStart + 3, allergyEnd).trim();
            String[] updatedAllergies = allergiesUpdatedInput.split(",\\s*");
            for (String allergy : updatedAllergies) {
                newAllergies.add(allergy.trim());
            }
        }

        int medicalHistoryStart = updates.indexOf("mh/");
        ArrayList<String> newMedicalHistories = null;
        if (medicalHistoryStart != -1) {
            int medicalHistoryEnd = findNextFieldStart(updates, medicalHistoryStart + 3);
            String medicalHistoriesUpdatedInput = updates.substring(medicalHistoryStart + 3, medicalHistoryEnd).trim();
            String[] updatedMedicalHistories = medicalHistoriesUpdatedInput.split(",\\s*");
            for (String medicalHistory : updatedMedicalHistories) {
                newMedicalHistories.add(medicalHistory.trim());
            }
        }

        // Update patient details only if new values are provided
        if (newName != null) {
            patientToBeEdited.setName(newName);
        }
        if (newNRIC != null) {
            patientToBeEdited.setNric(newNRIC);
        }
        if (newSex != null) {
            patientToBeEdited.setSex(newSex);
        }
        if (newDob != null) {
            patientToBeEdited.setDateOfBirth(newDob);
        }
        if (newPhone != null) {
            patientToBeEdited.setPhoneNumber(newPhone);
        }
        if (newHomeAddress != null) {
            patientToBeEdited.setHomeAddress(newHomeAddress);
        }
        if (newAllergies != null) {
            patientToBeEdited.setAllergies(newAllergies);
        }
        if (newMedicalHistories != null) {
            patientToBeEdited.setMedicalHistories(newMedicalHistories);
        }

        // Confirm the updated details
        System.out.println("Patient record updated successfully.");
        System.out.println("Updated patient details:");
        System.out.println(patientToBeEdited);

        records.addPatient(patientToBeEdited);
        FileHandler.autosave(records);
    }

    public void editVisit(String input, Records records) throws IOException {
        // Extract NRIC from input command
        int nricStart = input.indexOf("ic/");
        assert nricStart != -1 : "Please provide a valid patient NRIC in the records.";
        if (nricStart == -1) {
            System.out.println("Please provide a valid patient NRIC.");
            return;
        }
        int nricEnd = findNextFieldStart(input, nricStart + 3);
        String nric = input.substring(nricStart + 3, nricEnd).trim();

        Patient patient = null;

        // Search for the patient by NRIC
        for (Patient p : records.getPatients()) {
            if (p.getNric().trim().replaceAll("\\s+", "")
                    .equalsIgnoreCase(nric.replaceAll("\\s+", "").trim())) {
                patient = p;
                break;
            }
        }

        if (patient == null) {
            System.out.println("No patient found with the given NRIC.");
            return;
        }

        // Extract visit date from input command
        int dateStart = input.indexOf("date/");
        assert dateStart != -1 : "Please provide a valid visit date.";
        if (dateStart == -1) {
            System.out.println("Please provide a valid visit date.");
            return;
        }
        int dateEnd = findNextFieldStart(input, dateStart + 5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime visitDate = LocalDateTime.parse(input.substring(dateStart + 5, dateEnd).trim(), formatter);

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

        // Extract optional updates for visit
        int newDateStart = input.indexOf("newDate/");
        LocalDateTime newDate = null;
        if (newDateStart != -1) {
            int newDateEnd = findNextFieldStart(input, newDateStart + 8);
            newDate = LocalDateTime.parse(input.substring(newDateStart + 8, newDateEnd).trim(), formatter);
            visitToBeEdited.setVisitDate(newDate);
        }

        int diagnosesStart = input.indexOf("d/");
        ArrayList<String> newDiagnoses = new ArrayList<>();
        if (diagnosesStart != -1) {
            int diagnosesEnd = findNextFieldStart(input, diagnosesStart + 2);
            String diagnosesInput = input.substring(diagnosesStart + 2, diagnosesEnd).trim();
            String[] diagnosesArray = diagnosesInput.split(",\\s*");
            for (String diagnosis : diagnosesArray) {
                newDiagnoses.add(diagnosis.trim());
            }
            visitToBeEdited.setDiagnoses(newDiagnoses);
        }

        int medicationStart = input.indexOf("m/");
        ArrayList<String> newMedications = new ArrayList<>();
        if (medicationStart != -1) {
            int medicationEnd = findNextFieldStart(input, medicationStart + 2);
            String medicationsInput = input.substring(medicationStart + 2, medicationEnd).trim();
            String[] medicationsArray = medicationsInput.split(",\\s*");
            for (String medication : medicationsArray) {
                newMedications.add(medication.trim());
            }
            visitToBeEdited.setMedications(newMedications);
        }

        // Confirm the updated visit details
        System.out.println("Visit record updated successfully.");
        System.out.println("Updated visit details:");
        System.out.println(visitToBeEdited);

        // Save changes
        FileHandler.autosave(records);
    }

    // @@author coraleaf0602
    public void delete(String nric, Records records) throws IOException {
        assert nric != null : "Please provide a valid NRIC";

        List<Patient> patients = records.getPatients();
        double initialSize = patients.size();
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            if (patient.getNric().equals(nric)) {
                patients.remove(i);
                System.out.println("Patient " + patient.getName() + ", " + nric + ", has been deleted.");
                break;
            }
        }

        if (patients.size() == initialSize) {
            System.out.println("Patient with " + nric + " not found");
        }
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
                    + "find n/NAME ic/NRIC [p/PHONE] [ha/ADDRESS] [dob/DOB] [al/ALLERGY] [s/SEX] [mh/MEDICAL_HISTORY]");
            return;
        }

        List<Patient> matchedPatients = records.getPatients().stream()
                .filter(patient -> matchesSearchCriteria(patient, searchParams))
                .collect(Collectors.toList());

        displayResults(matchedPatients);
    }

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

    private boolean isValidSearchKey(String key) {
        return Arrays.asList("n", "ic", "p", "ha", "dob", "al", "s", "mh").contains(key);
    }

    private boolean matchesSearchCriteria(Patient patient, Map<String, String> searchParams) {
        logger.log(Level.FINE, "Checking if patient matches search criteria: {0}", patient);

        boolean matches = searchParams.entrySet().stream().allMatch(entry -> {
            String key = entry.getKey();
            String value = entry.getValue();
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
                return patient.getDateOfBirth().toLowerCase().contains(value);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String name = "";
        String nric = "";
        String date = "";
        String time = "";

        int nameStart = input.indexOf("n/");
        int nricStart = input.indexOf("ic/");
        int dateStart = input.indexOf("date/");
        int timeStart = input.indexOf("time/");

        int nameEnd = findNextFieldStart(input, nameStart + 2);
        name = input.substring(nameStart + 2, nameEnd).trim();
        int nricEnd = findNextFieldStart(input, nricStart + 2);
        nric = input.substring(nricStart + 3, nricEnd).trim();
        int dateEnd = findNextFieldStart(input, dateStart + 2);
        date = input.substring(dateStart + 5, dateEnd).trim();
        int timeEnd = findNextFieldStart(input, timeStart + 2);
        time = input.substring(timeStart + 5, timeEnd).trim();
        LocalDate availableDate = LocalDate.parse(date, formatter);
        LocalTime availableTime = LocalTime.parse(time);

        LocalTime nextAvailableTime= appointmentRecord.checkAvailability(availableDate, availableTime);

        if (nextAvailableTime == availableTime) {
            Appointment appointment = new Appointment(name, nric, date, time);
            appointmentRecord.addAppointment(appointment);

            System.out.println("Appointment on " + appointment.getDate().format(formatter) + " " +
                    appointment.getTime() + " with Patient " + appointment.getPatientName() + ", " +
                    appointment.getPatientNric() + " has been added.");
        } else {
            System.out.println("There is already an appointment at the given timeslot. " +
                    "The next available timeslot is: " + nextAvailableTime.toString());
        }
        FileHandler.autosave(appointmentRecord);
    }

    //@@author G13nd0n
    public void deleteAppointment(String input, AppointmentRecord appointmentRecord) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String nric = "";
        String date = "";
        String time = "";

        int nricStart = input.indexOf("ic/");
        int dateStart = input.indexOf("date/");
        int timeStart = input.indexOf("time/");

        if (nricStart == -1 || dateStart == -1 || timeStart == -1) {
            throw new IllegalArgumentException();
        }

        int nricEnd = findNextFieldStart(input, nricStart + 2);
        nric = input.substring(nricStart + 3, nricEnd).trim();
        int dateEnd = findNextFieldStart(input, dateStart + 2);
        date = input.substring(dateStart + 5, dateEnd).trim();
        int timeEnd = findNextFieldStart(input, timeStart + 2);
        time = input.substring(timeStart + 5, timeEnd).trim();
        List<Appointment> appointments = appointmentRecord.getAppointments();
        String patientName = "";

        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            patientName = appointment.getPatientName();
            String patientNric = appointment.getPatientNric();
            String patientDate = appointment.getDate().format(formatter);
            String patientTime = appointment.getTime().toString();
            if (!patientNric.equals(nric)) {
                System.out.println("Appointment with Patient of " + nric + " does not exist.");
                continue;
            }
            if (!patientDate.equals(date)) {
                System.out.println("Appointment with Patient of " + nric + " on " + date + "" +
                        "does not exist.");
                continue;
            }
            if (!patientTime.equals(time)) {
                System.out.println("Appointment with Patient of " + nric + " on " + date + " " + time +
                        "does not exist.");
                continue;
            }
            appointments.remove(i);
            break;
        }
        appointmentRecord.setAppointments(appointments);
        System.out.println("Appointment on " + date + " " + time + " with Patient " + patientName + ", " +
                nric + " has been deleted.");

        FileHandler.autosave(appointmentRecord);
    }

    //@@author G13nd0n
    public void listAppointments(AppointmentRecord appointmentRecord) {
        List<Appointment> appointments = appointmentRecord.getAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
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
        LocalDate today = LocalDate.now();
        List<Appointment> appointments = appointmentRecord.getAppointments();
        List<Appointment> updatedAppointments = new ArrayList<Appointment>();
        for (int i = 0; i < appointments.size(); i++) {
            Appointment currentAppointment = appointments.get(i);
            LocalDate appointmentDate = currentAppointment.getDate();
            if (appointmentDate.isAfter(today)) {
                updatedAppointments.add(currentAppointment);
            }
        }
        appointmentRecord.setAppointments(updatedAppointments);
        FileHandler.autosave(appointmentRecord);
    }

    //@@author kaboomzxc
    public void addVisit(String input, Records records) throws IOException {
        try {
            // Extract NRIC first (mandatory field)
            int nricStart = input.indexOf("ic/");
            if (nricStart == -1) {
                System.out.println("Please provide the patient's NRIC.");
                return;
            }
            int nricEnd = findNextFieldStart(input, nricStart + 3);
            String nric = input.substring(nricStart + 3, nricEnd).trim();

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
            int visitStart = input.indexOf("v/");
            if (visitStart == -1) {
                System.out.println("Please provide the visit date and time.");
                return;
            }
            int visitEnd = findNextFieldStart(input, visitStart + 2);
            String visitDateString = input.substring(visitStart + 2, visitEnd).trim();

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
            ArrayList<String> medications = new ArrayList<>();
            int medicationStart = input.indexOf("m/");
            if (medicationStart != -1) {
                int medicationEnd = findNextFieldStart(input, medicationStart + 2);
                String medicationInput = input.substring(medicationStart + 2, medicationEnd).trim();
                if (!medicationInput.isEmpty()) {
                    String[] medsArray = medicationInput.split(",\\s*");
                    medications.addAll(Arrays.asList(medsArray));
                }
            }

            // Extract diagnoses (optional, can be multiple)
            ArrayList<String> diagnoses = new ArrayList<>();
            int diagnosisStart = input.indexOf("d/");
            if (diagnosisStart != -1) {
                int diagnosisEnd = findNextFieldStart(input, diagnosisStart + 2);
                String diagnosisInput = input.substring(diagnosisStart + 2, diagnosisEnd).trim();
                if (!diagnosisInput.isEmpty()) {
                    String[] diagnosisArray = diagnosisInput.split(",\\s*");
                    diagnoses.addAll(Arrays.asList(diagnosisArray));
                }
            }

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



    // @@author coraleaf0602
    // Prints out the number of times a patient visited the clinic - need a command to call this if we want to see
    // the associated appointments for a patient
    public void printVisits(Patient patient) {
        if (patient.getVisits().isEmpty()) {
            System.out.println("No visits found for patient: " + patient.getName());
        } else {
            System.out.println("Visits for patient: " + patient.getName());
            for (Visit visit : patient.getVisits()) {
                System.out.println(visit.toString());
            }
        }
    }
}
