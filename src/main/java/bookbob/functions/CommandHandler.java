package bookbob.functions;

import bookbob.entity.Patient;
import bookbob.entity.Records;
import bookbob.entity.Visit;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final FileHandler fileHandler = new FileHandler();

    public CommandHandler() throws IOException {
    }
  
    // Prints output for help command
    //@@author coraleaf0602
    public void help() {
        System.out.println("""
                +-----------+---------------------------------------+---------------------------------+
                | Action    | Format                                | Example                         |
                +-----------+---------------------------------------+---------------------------------+
                | Help      | help                                  | help                            |
                +-----------+---------------------------------------+---------------------------------+
                | Add       | add n/NAME ic/NRIC [p/PHONE_NUMBER]   | add n/James Ho ic/S9534567A     |
                |           | [d/DIAGNOSIS] [m/MEDICATION]          | p/91234567 d/Asthma m/Albuterol |
                |           | [ha/HOME_ADDRESS] [dob/DATE_OF_BIRTH] | ha/NUS-PGPR dob/01011990        |
                |           | [v/VISIT_DATE_TIME]                   | v/21-10-2024 15:48              |
                +-----------+---------------------------------------+---------------------------------+
                | List      | list                                  | list                            |
                +-----------+---------------------------------------+---------------------------------+
                | Find      | find n/NAME          OR               | find n/John Doe                 |
                |           | find ic/NRIC         OR               | find ic/S1234                   |
                |           | find p/PHONE_NUMBER  OR               | find p/91234567                 |
                |           | find d/DIAGNOSIS     OR               | find d/Fever                    |
                |           | find m/MEDICATION    OR               | find m/Panadol                  |
                |           | find ha/HOME_ADDRESS OR               | find ha/NUS PGPR                |
                |           | find dob/DATE_OF_BIRTH                | find dob/01011990               |
                +-----------+---------------------------------------+---------------------------------+
                | Delete    | delete NRIC                           | delete S9534567A                |
                +-----------+---------------------------------------+---------------------------------+
                | Save      | save(automatic)                       |                                 |
                +-----------+---------------------------------------+---------------------------------+
                | Retrieve/ | retrieve or import                    |                                 |
                | Import    | (automatic)                           |                                 |
                +-----------+---------------------------------------+---------------------------------+
                | Exit      | exit                                  | exit                            |
                +-----------+---------------------------------------+---------------------------------+""");
    }

    //@@author yentheng0110
    public void add(String input, Records records) throws IOException {
        String name = "";
        String nric = "";
        String dateOfBirth = "";
        String phoneNumber = "";
        String homeAddress = "";
        String diagnosis = "";
        List<String> medications = new ArrayList<>();
        List<Visit> visits = new ArrayList<>();

        // Extract name
        int nameStart = input.indexOf("n/");
        int nricStart = input.indexOf("ic/");

        assert nameStart != -1 : "Please provide a valid patient name.";

        if (nameStart == -1) {
            System.out.println("Please provide the patient's name.");
            return;
        }

        int nameEnd = findNextFieldStart(input, nameStart + 2);
        name = input.substring(nameStart + 2, nameEnd).trim();

        assert nricStart != -1 : "Please provide a valid patient NRIC.";

        if (nricStart == -1) {
            System.out.println("Please provide the patient's NRIC.");
            return;
        }

        int nricEnd = findNextFieldStart(input, nricStart + 3);
        nric = input.substring(nricStart + 3, nricEnd).trim();

        // Extract phone number
        int phoneStart = input.indexOf("p/");
        if (phoneStart != -1) {
            int phoneEnd = findNextFieldStart(input, phoneStart + 2);
            phoneNumber = input.substring(phoneStart + 2, phoneEnd).trim();
        }

        // Extract diagnosis
        int diagnosisStart = input.indexOf("d/");
        if (diagnosisStart != -1) {
            int diagnosisEnd = findNextFieldStart(input, diagnosisStart + 2);
            diagnosis = input.substring(diagnosisStart + 2, diagnosisEnd).trim();
        }

        // Extract medications (split by comma)
        int medicationStart = input.indexOf("m/");
        if (medicationStart != -1) {
            int medicationEnd = findNextFieldStart(input, medicationStart + 2);
            String meds = input.substring(medicationStart + 2, medicationEnd).trim();
            String[] medsArray = meds.split(",\\s*");
            for (String med : medsArray) {
                medications.add(med.trim());
            }
        }

        // Extract home address
        int homeAddressStart = input.indexOf("ha/");
        if (homeAddressStart != -1) {
            int homeAddressEnd = findNextFieldStart(input, homeAddressStart + 3);
            homeAddress = input.substring(homeAddressStart + 3, homeAddressEnd).trim();
        }

        // Extract date of birth
        int dobStart = input.indexOf("dob/");
        if (dobStart != -1) {
            int dobEnd = findNextFieldStart(input, dobStart + 4);
            dateOfBirth = input.substring(dobStart + 4, dobEnd).trim();
        }

        // Extract visit date
        int visitStart = input.indexOf("v/");
        LocalDateTime visitTime = null;
        Visit visit = null;
        if (visitStart != -1) {
            String visitDateString = input.substring(visitStart + 2).trim();
            // Attempt to parse using a standard formatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            visitTime = LocalDateTime.parse(visitDateString, formatter);
            visit = new Visit(visitTime, diagnosis, medications);
            visits.add(visit);
        }

        Patient patient = new Patient(name, nric);
        patient.setPhoneNumber(phoneNumber);
        patient.setHomeAddress(homeAddress);
        patient.setDateOfBirth(dateOfBirth);
        patient.setVisit(visits);

        records.addPatient(patient);
        System.out.println("Patient " + name + " with NRIC " + nric + " added.");

        FileHandler.autosave(records);
    }

    //@@author yentheng0110
    // Utility method to find the start of the next field or the end of the input string
    private int findNextFieldStart(String input, int currentIndex) {
        int nextIndex = input.length(); // Default to end of input
        String[] prefixes = {"ic/", "p/", "d/", "m/", "ha/", "dob/"};
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
        for (Patient patient : patients) {
            System.out.println("Name: " + patient.getName() + ", NRIC: " + patient.getNric() +
                    ", Phone: " + patient.getPhoneNumber() + ", Address: " + patient.getHomeAddress() +
                    ", DOB: " + patient.getDateOfBirth());
        }
    }

    //@@author yentheng0110
    public void edit(String input, Records records) throws IOException {
        // Extract name and NRIC from the input command
        String nric = extractValue(input, "ic/");

        Patient patientToBeEdited = null;

        // Search for the patient with matching name and NRIC
        for (Patient patient : records.getPatients()) {
            if (patient.getNric().equalsIgnoreCase(nric)) {
                patientToBeEdited = patient;
                break;  // Stop searching once the patient is found
            }
        }

        if (patientToBeEdited == null) {
            System.out.println("No patient found.");
            return;
        }
        records.getPatients().remove(patientToBeEdited);

        // Extract optional fields for updating
        String newPhoneNumber = extractValue(input, "p/");
        String newHomeAddress = extractValue(input, "ha/");
        String newDob = extractValue(input, "dob/");

        // Update patient details only if new values are provided
        if (!newPhoneNumber.isBlank()) {
            patientToBeEdited.setPhoneNumber(newPhoneNumber);
        }
        if (!newHomeAddress.isBlank()) {
            patientToBeEdited.setHomeAddress(newHomeAddress);
        }
        if (!newDob.isBlank()) {
            patientToBeEdited.setDateOfBirth(newDob);
        }

        // Confirm the updated details
        System.out.println("Patient record updated successfully.");
        System.out.println("Updated patient details:");
        System.out.println(patientToBeEdited);

        records.addPatient(patientToBeEdited);
        FileHandler.autosave(records);
    }

    //@@author yentheng0110
    // Helper method to extract values between prefixes
    private String extractValue(String input, String prefix) {
        int startIndex = input.indexOf(prefix);
        if (startIndex == -1) return "";

        int nextPrefixIndex = findNextPrefixIndex(input, startIndex + prefix.length());
        return input.substring(startIndex + prefix.length(), nextPrefixIndex).trim();
    }

    //@@author yentheng0110
    // Helper method to find the index of the next prefix (or end of string)
    private int findNextPrefixIndex(String input, int currentIndex) {
        String[] prefixes = { "n/", "ic/", "p/", "ha/", "dob/", "d/", "m/" };
        int minIndex = input.length();  // Default to the end of the input

        for (String prefix : prefixes) {
            int prefixIndex = input.indexOf(prefix, currentIndex);
            if (prefixIndex != -1) {
                minIndex = Math.min(minIndex, prefixIndex);
            }
        }
        return minIndex;
    }

    // @@author G13nd0n
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

    // @@author kaboomzxc
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
                    + "find n/NAME ic/NRIC [p/PHONE] [d/DIAGNOSIS] [m/MEDICATION] [ha/ADDRESS] [dob/DOB]");
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
        return Arrays.asList("n", "ic", "p", "d", "m", "ha", "dob").contains(key);
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

    // @@author coraleaf
    // Prints out the number of times a patient visited the clinic - need a command to call this if we want to see
    // the associated appointments for a patient
    public void printVisits(Patient patient) {
        if (patient.getVisit().isEmpty()) {
            System.out.println("No visits found for patient: " + patient.getName());
        } else {
            System.out.println("Visits for patient: " + patient.getName());
            for (Visit visit : patient.getVisit()) {
                System.out.println(visit.toString());
            }
        }
    }
}
