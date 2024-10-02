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
        String[] inputParts = input.split(" ");
        String name = "";
        String NRIC = "";
        String dateOfBirth = "";
        String phoneNumber = "";
        String homeAddress = "";
        String diagnosis = "";
        List<String> medications = new ArrayList<>();

        for (String part : inputParts) {
            if (part.startsWith("n/")) {
                name = part.substring(2);
            } else if (part.startsWith("ic/")) {
                NRIC = part.substring(3);
            } else if (part.startsWith("p/")) {
                phoneNumber = part.substring(2);
            } else if (part.startsWith("d/")) {
                diagnosis = part.substring(2);
            } else if (part.startsWith("m/")) {
                medications.add(part.substring(2));
            } else if (part.startsWith("ha/")) {
                homeAddress = part.substring(3);
            } else if (part.startsWith("dob/")) {
                dateOfBirth = part.substring(4);
            }
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

    public void find(String input, Records records) {
        String[] inputArr = input.split("\\s+", 3);

        CommandAttributeType commandAttributeType = null;

        for (CommandAttributeType t : CommandAttributeType.values()) {
            if (t.getLabel().equals(inputArr[1])) {
                commandAttributeType = t;
                break;
            }
        }

        if (commandAttributeType == null) {
            System.out.println("Invalid find");
            return;
        }

        String[] keywords = inputArr[2].split("\\s+");

        List<Patient> findList = Find.findPatients(commandAttributeType, records, keywords);

        if (findList.isEmpty()) {
            System.out.println("No patients found");
            return;
        }

        for (Patient patient : findList) {
            System.out.println(patient);
        }
    }

    // Takes in an input string and determines whether to exit the program
    public void exit(String input) {
        if(input.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
    }
}

