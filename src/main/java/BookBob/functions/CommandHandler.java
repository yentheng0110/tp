package BookBob.functions;

import BookBob.entity.Patient;
import BookBob.entity.Records;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {

    // Prints output for help command
    public void help() {
        System.out.println("+-----------+---------------------------------------+---------------------------------+\n" +
                "| Action    | Format                                | Example                         |\n" +
                "+-----------+---------------------------------------+---------------------------------+\n" +
                "| Help      | help                                  | help                            |\n" +
                "+-----------+---------------------------------------+---------------------------------+\n" +
                "| Add       | add n/NAME ic/NRIC [p/PHONE_NUMBER]   | add n/James Ho ic/S9534567A     |\n" +
                "|           | [d/DIAGNOSIS] [m/MEDICATION]          | p/91234567 d/Asthma m/Albuterol |\n" +
                "|           | [ha/HOME_ADDRESS] [dob/DATE_OF_BIRTH] | ha/NUS-PGPR dob/13121995        |\n" +
                "+-----------+---------------------------------------+---------------------------------+\n" +
                "| List      | list                                  | list                            |\n" +
                "+-----------+---------------------------------------+---------------------------------+\n" +
                "| Find      | find NAME [KEYWORDS] OR               | find NRIC S1234                 |\n" +
                "|           | find NRIC [KEYWORDS] OR               |                                 |\n" +
                "|           | find PHONE_NUMBER [KEYWORDS] OR       |                                 |\n" +
                "|           | find DIAGNOSIS [KEYWORDS] OR          |                                 |\n" +
                "|           | find MEDICATION [KEYWORDS] OR         |                                 |\n" +
                "|           | find HOME_ADDRESS [KEYWORDS] OR       |                                 |\n" +
                "|           | find DATE_OF_BIRTH [KEYWORDS]         |                                 |\n" +
                "+-----------+---------------------------------------+---------------------------------+\n" +
                "| Delete    | delete NRIC                           | delete S9534567A                |\n" +
                "+-----------+---------------------------------------+---------------------------------+\n" +
                "| Save      | save(automatic)                       | save                            |\n" +
                "+-----------+---------------------------------------+---------------------------------+\n" +
                "| Retrieve/ | retrieve or import                    | retrieve                        |\n" +
                "| Import    | (automatic)                           |                                 |\n" +
                "+-----------+---------------------------------------+---------------------------------+\n" +
                "| Exit      | exit                                  | exit                            |\n" +
                "+-----------+---------------------------------------+---------------------------------+\n");
    }

    public void add(String input, Records records) {
        String name = "";
        String NRIC = "";
        String dateOfBirth = "";
        String phoneNumber = "";
        String homeAddress = "";
        String diagnosis = "";
        List<String> medications = new ArrayList<>();

        // Extract name
        int nameStart = input.indexOf("n/");
        int NRICStart = input.indexOf("ic/");
        if (nameStart != -1 && NRICStart != -1) {
            name = input.substring(nameStart + 2, NRICStart).trim();
        }

        // Extract NRIC
        int phoneStart = input.indexOf("p/");
        if (NRICStart != -1 && phoneStart != -1) {
            NRIC = input.substring(NRICStart + 3, phoneStart).trim();
        }

        // Extract phone number
        int diagnosisStart = input.indexOf("d/");
        if (phoneStart != -1 && diagnosisStart != -1) {
            phoneNumber = input.substring(phoneStart + 2, diagnosisStart).trim();
        }

        // Extract diagnosis
        int medicationStart = input.indexOf("m/");
        if (diagnosisStart != -1 && medicationStart != -1) {
            diagnosis = input.substring(diagnosisStart + 2, medicationStart).trim();
        }

        // Extract medications (split by comma)
        int homeAddressStart = input.indexOf("ha/");
        if (medicationStart != -1 && homeAddressStart != -1) {
            String meds = input.substring(medicationStart + 2, homeAddressStart).trim();
            String[] medsArray = meds.split(",\\s*");
            for (String med : medsArray) {
                medications.add(med.trim());
            }
        }

        // Extract home address
        int dobStart = input.indexOf("dob/");
        if (homeAddressStart != -1 && dobStart != -1) {
            homeAddress = input.substring(homeAddressStart + 3, dobStart).trim();
        }

        // Extract date of birth
        if (dobStart != -1) {
            dateOfBirth = input.substring(dobStart + 4).trim();
        }

        Patient patient = new Patient(name, NRIC);
        patient.setPhoneNumber(phoneNumber);
        patient.setDiagnosis(diagnosis);
        patient.setHomeAddress(homeAddress);
        patient.setDateOfBirth(dateOfBirth);
        patient.setMedication(medications);

        records.addPatient(patient);
        System.out.println("Patient " + name + " with NRIC " + NRIC + " added.");
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

