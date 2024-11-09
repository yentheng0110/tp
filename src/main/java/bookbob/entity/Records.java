package bookbob.entity;

import java.util.ArrayList;

public class Records {
    private final ArrayList<Patient> patients;

    // default constructor: empty
    //@@author G13nd0n
    public Records() {
        this.patients = new ArrayList<>();
    }

    // add a patient to records
    //@@author G13nd0n
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    //@@author G13nd0n
    public void addPatient(String name, String nric, ArrayList<Visit> visits, String sex, String dateOfBirth,
                           String phoneNumber, String homeAddress, ArrayList<String> allergies,
                           ArrayList<String> medicalHistories) {
        Patient patient = new Patient(name, nric, visits);
        patient.setSex(sex);
        patient.setDateOfBirth(dateOfBirth);
        patient.setPhoneNumber(phoneNumber);
        patient.setHomeAddress(homeAddress);
        patient.setAllergies(allergies);
        patient.setMedicalHistories(medicalHistories);
        patients.add(patient);
        System.out.println("Patient " + name + " with NRIC " + nric + " added.");
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void delete(String nric) {
        assert nric != null : "Please provide a valid NRIC";

        double initialSize = patients.size();
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        try {
            Integer.parseInt(nric.substring(1, 2));
        } catch (NumberFormatException e){
            System.out.println("Please provide the NRIC of the patient, not the name.");
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
    }
}
