package BookBob.functions;

import BookBob.entity.Patient;
import BookBob.entity.Records;
import java.util.List;

public class CRUD {
    public void delete(String NRIC, Records records) {
        List<Patient> patients = records.getPatients();
        int initialPatientSize = patients.size();

        if (initialPatientSize == 0) {
            System.out.println("There are no patients in the record currently.");
            return;
        }

        for (int i = 0; i < patients.size(); i++) {
            Patient currentPatient = patients.get(i);
            String patientNRIC = currentPatient.getNric();
            if (patientNRIC.equals(NRIC)) {
                patients.remove(i);
                System.out.println("Patient " + currentPatient.getName() + ", " + patientNRIC + ", has been deleted.");
                break;
            }
        }

        if (patients.size() == initialPatientSize) {
            System.out.println("Patient " + NRIC + " not found");
        }
    }
}
