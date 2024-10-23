package bookbob.functions;

import bookbob.entity.Patient;
import bookbob.entity.Records;
import bookbob.entity.Visit;

import java.util.List;

public class FindVisit {

    //find visit by nric and print all visits to terminal
    public static void findVisitByIc(String nric, Records records) {
        List<Patient> patientList = records.getPatients();
        boolean found = false;
        for (Patient patient : patientList) {
            if (patient.getNric().equals(nric)) {
                List<Visit> visits = patient.getVisit();
                found = true;

                if (visits == null) {
                    System.out.println("No visits found for " + nric + ". Please update accordingly");
                }

                for (Visit visit : visits) {
                    System.out.println(visit.toString());
                }
            }
        }
        if (!found) {
            System.out.println("No patient visit record found with NRIC: " + nric);
        }
    }

    //find patient by diagnosis and print the specific patient and visit to terminal
    public static void findVisitByDiagnosis(String symptom, Records records) {
        List<Patient> patientList = records.getPatients();
        boolean found = false;
        for (Patient patient : patientList) {
            List<Visit> visits = patient.getVisit();
            for (Visit visit : visits) {
                if (visit.getDiagnosis().contains(symptom) || patient.getMedicalHistory().contains(symptom)) {
                    System.out.println("---------------------------------");
                    System.out.println(patient.toString());
                    System.out.println(visit.toString());
                    System.out.println("---------------------------------");
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No patient found with symptom: " + symptom);
        }
    }

    //find visit by medication and print all visits to terminal
    public static void findVisitByMedication(String medication, Records records) {
        List<Patient> patientList = records.getPatients();
        boolean found = false;
        for (Patient patient : patientList) {
            List<Visit> visits = patient.getVisit();
            for (Visit visit : visits) {
                if (visit.getMedications().contains(medication)) {
                    System.out.println("---------------------------------");
                    System.out.println(patient.toString());
                    System.out.println(visit.toString());
                    System.out.println("---------------------------------");
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No patient found with symptom: " + medication);
        }
    }
}
