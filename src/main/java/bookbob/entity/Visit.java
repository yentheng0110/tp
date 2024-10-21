package bookbob.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Visit {
    private LocalDateTime visitDate;

    public Visit(LocalDateTime visitDate) {
        this.visitDate = visitDate;
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
        return "Visited the clinic on: " + this.getVisitDate().format(formatter);
    }
}
