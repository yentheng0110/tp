package bookbob.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

//@@author G13nd0n
public class Appointment implements Comparable<Appointment>, OutputConversion {
    private String patientName;
    private String patientNric;
    private LocalTime time;
    private LocalDate date;
    private long consultationDuration = 30;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    //@@author G13nd0n
    public Appointment(String patientName, String patientNric, String date, String time) {
        this.patientName = patientName;
        this.patientNric = patientNric;
        this.date = LocalDate.parse(date, formatter);
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
    public long getConsultationDuration() {
        return consultationDuration;
    }

    //@@author G13nd0n
    @Override
    public String toString() {
        return "Appointment on " + date.format(formatter) + " " + time + " with Patient " + patientName + ", " +
                patientNric + ".";
    }

    //@@author G13nd0n
    @Override
    public int compareTo(Appointment other) {
        LocalDate patient1Date = this.date;
        LocalDate patient2Date = other.date;
        LocalTime patient1Time = this.time;
        LocalTime patient2Time = other.time;
        if (patient1Date.isBefore(patient2Date)) {
            return -1;
        } else if (patient1Date.equals(patient2Date) && patient1Time.isBefore(patient2Time)) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String convertPatientToOutputText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String patientName = this.getPatientName();
        String patientNric = this.getPatientNric();
        String date = this.getDate().format(formatter);
        String time = this.getTime().toString();
        String output = "";
        output += "Name: " + patientName + "|" + "NRIC: " + patientNric + "|"
                + "Date: " + date  + "|" + "Time: " + time;

        return output;
    }
}
