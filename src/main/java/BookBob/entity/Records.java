package BookBob.entity;

import java.util.ArrayList;

public class Records {

    public ArrayList<Patient> patients;

    //default constructor: empty
    public Records() {
        ArrayList<Patient> patients = new ArrayList<>();
        this.patients = patients;
    }

    //add a patient to records
    public void addPatient(Patient patient) {
        ArrayList<Patient> patients = this.getPatients();
        patients.add(patient);
        this.setPatients(patients);
    }

    //setter and getters
    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }
}
