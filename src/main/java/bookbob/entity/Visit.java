package bookbob.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Visit {
    private final LocalDateTime visitDate;
    private final ArrayList<String> diagnoses;
    private final ArrayList<String> medications;

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

    public ArrayList<String> getMedications() {
        return medications;
    }

    public LocalDateTime getVisitDate() {
        return visitDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return this.getVisitDate().format(formatter) + ", Diagnosis: " + getDiagnoses() +
                ", Medications: " + getMedications();
    }
}
