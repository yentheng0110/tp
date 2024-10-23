package bookbob.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Visit {
    private LocalDateTime visitDate;
    private List<String> diagnosis;
    private List<String> medications;
    public Visit(LocalDateTime visitDate) {
        this.visitDate = visitDate;
        this.diagnosis = new ArrayList<>();
        this.medications= new ArrayList<>();
    }

    // Constructor for retrieving data
    public Visit(LocalDateTime visitDate, List<String> diagnosis, List<String> medication) {
        this.visitDate = visitDate;
        this.diagnosis = diagnosis;
        this.medications= medication;
    }

    public List<String> getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(List<String> diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedication(List<String> medications) {
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
        return "this.getVisitDate().format(formatter) + ", Diagnosis: " + getDiagnosis() +
                ", Medications: " + getMedications();
    }
}
