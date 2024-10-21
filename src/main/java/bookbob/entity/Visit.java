package bookbob.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Visit {
    private LocalDateTime visitDate;
    private String diagnosis;
    private List<String> medication;

    public Visit(LocalDateTime visitDate, String diagnosis, List<String> medication) {
        this.visitDate = visitDate;
        this.diagnosis = "";
        this.medication = new ArrayList<>();
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<String> getMedication() {
        return medication;
    }

    public void setMedication(List<String> medication) {
        this.medication = medication;
    }

    public LocalDateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDateTime visitDate) {
        this.visitDate = visitDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Visited the clinic on: " + this.getVisitDate().format(formatter) + ", Diagnosis: " + getDiagnosis() +
                ", Medication: " + getMedication();
    }
}
