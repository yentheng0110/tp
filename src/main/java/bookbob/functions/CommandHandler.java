package bookbob.functions;

import bookbob.entity.Patient;
import bookbob.entity.Records;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {

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
                |           | [ha/HOME_ADDRESS] [dob/DATE_OF_BIRTH] | ha/NUS-PGPR dob/13121995        |
                +-----------+---------------------------------------+---------------------------------+
                | List      | list                                  | list                            |
                +-----------+---------------------------------------+---------------------------------+
                | Find      | find NAME [KEYWORDS] OR               | find NRIC S1234                 |
                |           | find NRIC [KEYWORDS] OR               |                                 |
                |           | find PHONE_NUMBER [KEYWORDS] OR       |                                 |
                |           | find DIAGNOSIS [KEYWORDS] OR          |                                 |
                |           | find MEDICATION [KEYWORDS] OR         |                                 |
                |           | find HOME_ADDRESS [KEYWORDS] OR       |                                 |
                |           | find DATE_OF_BIRTH [KEYWORDS]         |                                 |
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
            System.out.println("Name: " + patient.getName() + ", NRIC: " + patient.getNric() +
                    ", Phone: " + patient.getPhoneNumber() + ", Diagnosis: " + patient.getDiagnosis() +
                    ", Medication: " + patient.getMedication() + ", Address: " + patient.getHomeAddress() +
                    ", DOB: " + patient.getDateOfBirth());
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
}

