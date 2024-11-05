package bookbob.functions;

import bookbob.entity.Patient;
import bookbob.entity.Records;
import bookbob.entity.Visit;

import java.util.ArrayList;

public class FindVisit {

    //find visit by nric and print all visits to terminal
    //@@author PrinceCatt
    public static void findVisitByIc(String nric, Records records) {
        ArrayList<Patient> patientList = records.getPatients();
        boolean isFound = false;
        for (Patient patient : patientList) {
            if (patient.getNric().equals(nric)) {
                ArrayList<Visit> visits = patient.getVisits();
                isFound = true;

                for (Visit visit : visits) {
                    System.out.println(visit.toString());
                }
            }
        }
        if (!isFound) {
            System.out.println("No patient visit record found with NRIC: " + nric);
        }
    }

    //find patient by diagnosis and print the specific patient and visit to terminal
    //@@author PrinceCatt
    public static void findVisitByDiagnosis(String symptom, Records records) {
        ArrayList<Patient> patientList = records.getPatients();
        boolean found = false;
        for (Patient patient : patientList) {
            ArrayList<Visit> visits = patient.getVisits();
            for (Visit visit : visits) {
                if (visit.getDiagnoses().contains(symptom) || patient.getMedicalHistories().contains(symptom)) {
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
    //@@author PrinceCatt
    public static void findVisitByMedication(String medication, Records records) {
        ArrayList<Patient> patientList = records.getPatients();
        boolean isFound = false;
        for (Patient patient : patientList) {
            ArrayList<Visit> visits = patient.getVisits();
            for (Visit visit : visits) {
                if (visit.getMedications().contains(medication)) {
                    System.out.println("---------------------------------");
                    System.out.println(patient.toString());
                    System.out.println(visit.toString());
                    System.out.println("---------------------------------");
                    isFound = true;
                }
            }
        }
        if (!isFound) {
            System.out.println("No patient found with symptom: " + medication);
        }
    }
}
