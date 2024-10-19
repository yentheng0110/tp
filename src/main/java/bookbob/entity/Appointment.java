package bookbob.entity;

import java.time.LocalDate;
import java.time.LocalTime;

//@@author G13nd0n
public class Appointment implements Comparable<Appointment> {
    private String patientName;
    private String patientNric
    private LocalTime time;
    private LocalDate date;

    //@@author G13nd0n
    public Appointment(String patientName, String patientNric, String date, String time) {
        this.patientName = patientName;
        this.patientNric = patientNric;
        this.date = LocalDate.parse(date);
        this.time = LocalTime.parse(time);

    }

    //@@author G13nd0n
    public String getPatientName() {
        return patientName;
    }

    public String getPatientNric() {
        return patientNric;
    }

    //@@author G13nd0n
    public LocalDate getDate() {
        return date;
    }

    //@@author G13nd0n
    public LocalTime getTime() {
        return time;
    }

    //@@author G13nd0n
    @Override
    public String toString() {
        return "Appointment on " + date + time + " with Patient " + patientName + ", " +
                patientNric + ".";
    }

    //@@author G13nd0n
    @Override
    public int compareTo(Appointment other) {
        LocalDate patient1Date = this.date;
        LocalDate patient2Date = other.date;
        LocalTime patient1Time = this.time;
        LocalTime patient2Time = other.time;
        /*    Patient patient2 = other.patient;
        String patient1Name = this.patient.getName();
        String patient2Name = patient2.getName();
        String patient1Nric = this.patient.getNric();
        String patient2Nric = patient2.getName();
        LocalDateTime patient1Time = this.dateTime;
        LocalDateTime patient2Time = other.dateTime;
        */
        if (patient1Time.isBefore(patient2Time) && patient1Date.isBefore(patient2Date)) {
            return 1;
        } else if (patient1Time.isBefore(patient2Time) && patient1Date.isAfter(patient2Date)) {
            return -1;
        } else if (patient1Time.isAfter(patient2Time) && patient1Date.isAfter(patient2Date)) {
            return -1;
        } else {
            return 0;
        }

    }
}
