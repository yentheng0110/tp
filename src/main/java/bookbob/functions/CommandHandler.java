package bookbob.functions;

import bookbob.entity.Patient;
import bookbob.entity.Records;

import java.util.*;
import java.util.stream.Collectors;

public class CommandHandler {
    private Scanner scanner;

    public CommandHandler() {
        this.scanner = new Scanner(System.in);
    }

    // Prints output for help command
    public void help() {
        System.out.println("""
                +-----------+---------------------------------------+---------------------------------+
                | Action    | Format                                | Example                         |
                +-----------+---------------------------------------+---------------------------------+
                | Help      | help                                  | help                            |
                +-----------+---------------------------------------+---------------------------------+
                | Add       | add n/NAME ic/NRIC [p/PHONE_NUMBER]   | add n/James Ho ic/S9534567A     |
                |           | [d/DIAGNOSIS] [m/MEDICATION]          | p/91234567 d/Asthma m/Albuterol |
                |           | [ha/HOME_ADDRESS] [dob/DATE_OF_BIRTH] | ha/NUS-PGPR dob/1990-01-01      |
                +-----------+---------------------------------------+---------------------------------+
                | List      | list                                  | list                            |
                +-----------+---------------------------------------+---------------------------------+
                | Find      | find n/NAME          OR               | find n/John Doe                 |
                |           | find ic/NRIC         OR               | find ic/S1234                   |
                |           | find p/PHONE_NUMBER  OR               | find p/91234567                 |
                |           | find d/DIAGNOSIS     OR               | find d/Fever                    |
                |           | find m/MEDICATION    OR               | find m/Panadol                  |
                |           | find ha/HOME_ADDRESS OR               | find ha/NUS PGPR                |
                |           | find dob/DATE_OF_BIRTH                | find dob/1990-01-01             |
                +-----------+---------------------------------------+---------------------------------+
                | Delete    | delete NRIC                           | delete S9534567A                |
                +-----------+---------------------------------------+---------------------------------+
                | Save      | save(automatic)                       | save                            |
                +-----------+---------------------------------------+---------------------------------+
                | Retrieve/ | retrieve or import                    | retrieve                        |
                | Import    | (automatic)                           |                                 |
                +-----------+---------------------------------------+---------------------------------+
                | Exit      | exit                                  | exit                            |
                +-----------+---------------------------------------+---------------------------------+""");
    }

    public void add(String input, Records records) {
        String name = "";
        String nric = "";
        String dateOfBirth = "";
        String phoneNumber = "";
        String homeAddress = "";
        String diagnosis = "";
        List<String> medications = new ArrayList<>();

        // Extract name
        int nameStart = input.indexOf("n/");
        int nricStart = input.indexOf("ic/");
        if (nameStart != -1 && nricStart != -1) {
            name = input.substring(nameStart + 2, nricStart).trim();
        } else if (nameStart != -1) { // Handle case where only name is provided
            name = input.substring(nameStart + 2).trim();
            System.out.println("Please provide the NRIC for the patient named " + name +
                    ", then add the patient record again.");
            return; // Exit the method until user provides NRIC
        }

        // Extract NRIC
        int phoneStart = input.indexOf("p/");
        if (nricStart != -1 && phoneStart != -1) {
            nric = input.substring(nricStart + 3, phoneStart).trim();
        } else if (nricStart != -1) { // Handle case where there is no phone number
            nric = input.substring(nricStart + 3).trim();
        }

        // Extract phone number
        int diagnosisStart = input.indexOf("d/");
        if (phoneStart != -1 && diagnosisStart != -1) {
            phoneNumber = input.substring(phoneStart + 2, diagnosisStart).trim();
        } else if (phoneStart != -1) { // Handle case where there is no diagnosis
            int nextFieldStart = findNextFieldStart(input, phoneStart);
            phoneNumber = input.substring(phoneStart + 2, nextFieldStart).trim();
        }

        // Extract diagnosis
        int medicationStart = input.indexOf("m/");
        if (diagnosisStart != -1 && medicationStart != -1) {
            diagnosis = input.substring(diagnosisStart + 2, medicationStart).trim();
        } else if (diagnosisStart != -1) { // Handle case where there is no medication
            diagnosis = input.substring(diagnosisStart + 2).trim();
        }

        // Extract medications (split by comma)
        int homeAddressStart = input.indexOf("ha/");
        if (medicationStart != -1 && homeAddressStart != -1) {
            String meds = input.substring(medicationStart + 2, homeAddressStart).trim();
            String[] medsArray = meds.split(",\\s*");
            for (String med : medsArray) {
                medications.add(med.trim());
            }
        } else if (medicationStart != -1) { // Handle case where there is no home address
            String meds = input.substring(medicationStart + 2).trim();
            String[] medsArray = meds.split(",\\s*");
            for (String med : medsArray) {
                medications.add(med.trim());
            }
        }

        // Extract home address
        int dobStart = input.indexOf("dob/");
        if (homeAddressStart != -1 && dobStart != -1) {
            homeAddress = input.substring(homeAddressStart + 3, dobStart).trim();
        } else if (homeAddressStart != -1) { // Handle case where there is no DOB
            homeAddress = input.substring(homeAddressStart + 3).trim();
        }

        // Extract date of birth
        if (dobStart != -1) {
            dateOfBirth = input.substring(dobStart + 4).trim();
        }

        Patient patient = new Patient(name, nric);
        patient.setPhoneNumber(phoneNumber);
        patient.setDiagnosis(diagnosis);
        patient.setHomeAddress(homeAddress);
        patient.setDateOfBirth(dateOfBirth);
        patient.setMedication(medications);

        records.addPatient(patient);
        System.out.println("Patient " + name + " with NRIC " + nric + " added.");
    }

    // Utility method to find the start of the next field or the end of the input string
    private int findNextFieldStart(String input, int currentFieldEnd) {
        int nextFieldStart = input.length(); // Default to end of string
        String[] fieldPrefixes = {"d/", "m/", "ha/", "dob/"};
        for (String prefix : fieldPrefixes) {
            int prefixStart = input.indexOf(prefix, currentFieldEnd);
            if (prefixStart != -1 && prefixStart < nextFieldStart) {
                nextFieldStart = prefixStart;
            }
        }
        return nextFieldStart;
    }


    public void list(Records records) {
        List<Patient> patients = records.getPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }


    public void delete(String nric, Records records) {
        List<Patient> patients = records.getPatients();
        int initialPatientSize = patients.size();
        if (initialPatientSize == 0) {
            System.out.println("There are no patients in the record currently.");
            return;
        }
        for (int i = 0; i < patients.size(); i++) {
            Patient currentPatient = patients.get(i);
            String patientNRIC = currentPatient.getNric();
            if (patientNRIC.equals(nric)) {
                patients.remove(i);
                System.out.println("Patient " + currentPatient.getName() + ", " + patientNRIC + ", has been deleted.");
                break;
            }
        }
        if (patients.size() == initialPatientSize) {
            System.out.println("Patient " + nric + " not found");
        }
    }

    // Takes in an input string and determines whether to exit the program
    public void exit(String input) {
        if(input.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
    }
    public void find(String input, Records records) {
        Map<String, String> searchParams = extractSearchParams(input);

        if (searchParams.isEmpty()) {
            System.out.println("Invalid search parameters. Please use the format: find n/NAME ic/NRIC [p/PHONE] [d/DIAGNOSIS] [m/MEDICATION] [ha/ADDRESS] [dob/DOB]");
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
                    if (isValidSearchKey(key)) {
                        params.put(key, keyValue[1].toLowerCase().trim());
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
        return searchParams.entrySet().stream().allMatch(entry -> {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "n":
                    return patient.getName().toLowerCase().contains(value);
                case "ic":
                    return patient.getNric().toLowerCase().contains(value);
                case "p":
                    return patient.getPhoneNumber().toLowerCase().contains(value);
                case "d":
                    return patient.getDiagnosis().toLowerCase().contains(value);
                case "m":
                    return patient.getMedication().stream()
                            .anyMatch(med -> med.toLowerCase().contains(value));
                case "ha":
                    return patient.getHomeAddress().toLowerCase().contains(value);
                case "dob":
                    return patient.getDateOfBirth().toLowerCase().contains(value);
                default:
                    return false;
            }
        });
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
}

