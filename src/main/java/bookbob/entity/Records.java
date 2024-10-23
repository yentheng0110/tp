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
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    // getter
    public ArrayList<Patient> getPatients() {
        return patients;
    }
}
