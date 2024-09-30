package seedu.duke;

import java.util.ArrayList;

public class Records {
    public ArrayList<Patient> patients;

    Records() {
        patients = new ArrayList<>();
    }

    public void delete(String nric) {
        int initialPatientSize = patients.size();
        if (patients.isEmpty()) {
            System.out.println("There are no patients in the record currently.");
            return;
        }
        for (int i = 0; i < patients.size(); i++) {
            Patient currentPatient = patients.get(i);
            String patientNRIC = currentPatient.nric;
            if (patientNRIC.equals(nric)) {
                this.patients.remove(i);
                System.out.println("Patient " + currentPatient.name + ", " + patientNRIC + ", has been deleted.");
                break;
            }
        }
        if (patients.size() == initialPatientSize) {
            System.out.println("Patient " + nric + " not found");
        }
    }
}
