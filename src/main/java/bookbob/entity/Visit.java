package bookbob.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Visit {
    private LocalDateTime visitDate;
    private ArrayList<String> diagnoses;
    private ArrayList<String> medications;

    public Visit(LocalDateTime visitDate) {
        this.visitDate = visitDate;
        this.diagnoses = new ArrayList<>();
        this.medications = new ArrayList<>();
    }

    // Constructor for retrieving data
    public Visit(LocalDateTime visitDate, ArrayList<String> diagnoses, ArrayList<String> medications) {
        this.visitDate = visitDate;
        this.diagnoses = diagnoses;
        this.medications= medications;
    }

    public ArrayList<String> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(ArrayList<String> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public ArrayList<String> getMedications() {
        return medications;
    }

    public void setMedications(ArrayList<String> medications) {
        this.medications = medications;
    }

    public LocalDateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDateTime visitDate) {
        this.visitDate = visitDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return this.getVisitDate().format(formatter) + ", Diagnosis: " + getDiagnoses() +
                ", Medications: " + getMedications();
    }
}
