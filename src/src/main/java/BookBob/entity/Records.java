package BookBob.entity;

import java.util.ArrayList;
import java.util.List;

public class Records {
    private List<Patient> patients;

    // default constructor: empty
    public Records() {
        this.patients = new ArrayList<>();
    }

    // add a patient to records
    public void addPatient(Patient patient) {
        List<Patient> patients = this.getPatients();
        patients.add(patient);
        this.setPatients(patients);
    }

    // setter and getters
    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
