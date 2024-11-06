package functions;


import bookbob.entity.Appointment;
import bookbob.entity.Patient;
import bookbob.entity.Records;
import bookbob.entity.Visit;
import bookbob.functions.CommandHandler;
import bookbob.functions.FileHandler;
import org.junit.jupiter.api.Test;
import bookbob.entity.AppointmentRecord;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileHandlerTest {
    FileHandler fileHandler = new FileHandler();
    CommandHandler command = new CommandHandler();

    public FileHandlerTest() throws IOException {
    }

    //@@author PrinceCatt
    @Test
    void testTextConverterFullInformationRecords() throws IOException {
        ArrayList<String> diagnosis = new ArrayList<>();
        diagnosis.add("Tummy bug");
        ArrayList<String> medications = new ArrayList<>();
        medications.add("Gaviscon");
        String dateTimeString = "21-10-2024 15:48";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime visitDateTime = LocalDateTime.parse(dateTimeString, formatter);
        ArrayList<Visit> visits = new ArrayList<>();
        visits.add(new Visit(visitDateTime, diagnosis, medications));
        ArrayList<String> allergies = new ArrayList<>();
        allergies.add("Peanuts");
        ArrayList<String> medicalHistory = new ArrayList<>();
        medicalHistory.add("History of gastritis");

        Patient patient = new Patient("John", "S9765432T", "87658976", "06071997",
                "Bukit Gombak", allergies, "Male", medicalHistory, visits);
        String output = fileHandler.convertPatientToOutputText(patient);
        assertEquals(output, "Name: John | NRIC: S9765432T | Phone Number: 87658976 | " +
                "Date_Of_Birth: 06071997 | Home Address: Bukit Gombak | Allergy: [Peanuts] " +
                "| Sex: Male | Medical History: [History of gastritis] | Visit: " +
                "[21-10-2024 15:48, Diagnosis: [Tummy bug], " +
                "Medications: [Gaviscon]];", output);
    }

    //@@author PrinceCatt and coraleaf0602
    @Test
    void testTextConverterPartialInformationRecords() {
        String dateTimeString = "21-10-2024 15:48";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime visitDateTime = LocalDateTime.parse(dateTimeString, formatter);
        ArrayList<Visit> visits = new ArrayList<>();
        visits.add(new Visit(visitDateTime));
        Patient patient = new Patient("John", "S9765432T", visits);
        String output = fileHandler.convertPatientToOutputText(patient);
        assertEquals(output, "Name: John | NRIC: S9765432T | Phone Number:  | " +
                "Date_Of_Birth:  | Home Address:  | Allergy: [] | Sex:  | Medical History: [] | " +
                "Visit: [21-10-2024 15:48, Diagnosis: [], Medications: []];");
    }

    //@@author PrinceCatt
    @Test
    void testTextConverterAppointmentRecords() {
        String name = "John";
        String nric = "S9765432T";
        String date = "21-10-2024";
        String time = "15:48";
        Appointment appointment = new Appointment(name, nric, date, time);
        String output = fileHandler.convertPatientToOutputText(appointment);
        assertEquals(output, "Name: John|NRIC: S9765432T|Date: 21-10-2024|Time: 15:48");
    }

    @Test
    void testFileInitializationRecords() throws IOException {
        Records records = new Records();            //initialize a new record to clear file content
        fileHandler.autosave(records);
        command.add("add n/Jack Wong ic/S9765432T p/87658976 mh/diabetes d/Gastric m/Gaviscon Panadol " +
                "v/01-10-2024 17:30 ha/Bukit Gombak dob/06071997", records);
        Patient patient = records.getPatients().get(0);
        fileHandler.autosave(records);
        fileHandler.initFile(records);
        Patient newPatient = records.getPatients().get(0);
        assertEquals(patient,newPatient);
    }

    @Test
    void testFileInitializationAppointmentRecords() throws IOException {
        AppointmentRecord appointmentRecord = new AppointmentRecord();
        fileHandler.autosave(appointmentRecord);
        command.appointment("appointment n/Jack Wong ic/S9765432T date/04-11-2024 " +
                "time/21:19", appointmentRecord);
        Appointment appointment = appointmentRecord.getAppointments().get(0);
        fileHandler.autosave(appointmentRecord);
        fileHandler.initFile(appointmentRecord);
        Appointment newAppointment = appointmentRecord.getAppointments().get(0);
        assertEquals(appointment,newAppointment);
    }

    @Test
    void testParseVisitInputString() throws IOException {
        String visitString = "[01-10-2024 17:30, Diagnosis: [Gastric], Medications: [Gaviscon]]";
        Visit convertedVisit = fileHandler.parseVisitInputString(visitString);

        String dateTimeString = "01-10-2024 17:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime visitDateTime = LocalDateTime.parse(dateTimeString, formatter);
        ArrayList<String> diagnosis = new ArrayList<>();
        ArrayList<String> medications = new ArrayList<>();
        diagnosis.add("Gastric");
        medications.add("Gaviscon");
        Visit visit = new Visit(visitDateTime, diagnosis, medications);
        assertEquals(visit.toString(), convertedVisit.toString());
    }

    @Test
    void testParseVisitInputStringNull() throws IOException {
        String visitString = "";
        Visit convertedVisit = fileHandler.parseVisitInputString(visitString);
        assertEquals(null, convertedVisit);
    }

    @Test
    void testParseVisitInputStringNull2() throws IOException {
        String visitString = "[01-10-2024 17:30, : [Gastric], Medications: [Gaviscon]]";
        Visit convertedVisit = fileHandler.parseVisitInputString(visitString);
        assertEquals(null, convertedVisit);
    }
}
