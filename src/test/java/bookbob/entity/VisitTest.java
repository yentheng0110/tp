package bookbob.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VisitTest {
    @Test
    public void testVisitWithEmptyLists() {
        LocalDateTime testDate = LocalDateTime.now();
        Visit visit = new Visit(testDate);
        assertTrue(visit.getDiagnoses().isEmpty());
        assertTrue(visit.getMedications().isEmpty());
        assertEquals(testDate, visit.getVisitDate());
    }

    @Test
    public void testVisitWithDiagnosesAndMedications() {
        LocalDateTime testDate = LocalDateTime.now();
        ArrayList<String> testDiagnoses = new ArrayList<>(Arrays.asList("Asthma"));
        ArrayList<String> testMedications = new ArrayList<>(Arrays.asList("Albuterol"));
        Visit visit = new Visit(testDate, testDiagnoses, testMedications);
        assertEquals(testDate, visit.getVisitDate());
        assertEquals(testDiagnoses, visit.getDiagnoses());
        assertEquals(testMedications, visit.getMedications());
    }

    @Test
    public void testSettingDiagnosesAndMedications() {
        LocalDateTime testDate = LocalDateTime.now();
        Visit visit = new Visit(testDate);
        ArrayList<String> newDiagnoses = new ArrayList<>(Arrays.asList("Diabetes"));
        ArrayList<String> newMedications = new ArrayList<>(Arrays.asList("Insulin"));
        visit.setDiagnoses(newDiagnoses);
        visit.setMedications(newMedications);
        assertEquals(newDiagnoses, visit.getDiagnoses());
        assertEquals(newMedications, visit.getMedications());
    }

    @Test
    public void testSetDiagnoses() {
        LocalDateTime visitDate = LocalDateTime.now();
        Visit visit = new Visit(visitDate);
        ArrayList<String> newDiagnoses = new ArrayList<>(Arrays.asList("Flu", "Cold"));
        visit.setDiagnoses(newDiagnoses);
        assertEquals(newDiagnoses, visit.getDiagnoses());
    }

    @Test
    public void testSetMedications() {
        LocalDateTime visitDate = LocalDateTime.now();
        Visit visit = new Visit(visitDate);
        ArrayList<String> newMedications = new ArrayList<>(Arrays.asList("Ibuprofen", "Acetaminophen"));
        visit.setMedications(newMedications);
        assertEquals(newMedications, visit.getMedications());
    }

    @Test
    public void testSetVisitDate() {
        LocalDateTime visitDate = LocalDateTime.of(2024, 10, 21, 15, 48);
        Visit visit = new Visit(visitDate);
        LocalDateTime newVisitDate = LocalDateTime.of(2025, 11, 22, 10, 30);
        visit.setVisitDate(newVisitDate);
        assertEquals(newVisitDate, visit.getVisitDate());
    }


    @Test
    public void testToString() {
        String dateTimeString = "21-10-2024 15:48";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime testDate = LocalDateTime.parse(dateTimeString, formatter);
        ArrayList<String> diagnoses = new ArrayList<>(Arrays.asList("Cold"));
        ArrayList<String> medications = new ArrayList<>(Arrays.asList("Paracetamol"));
        Visit visit = new Visit(testDate, diagnoses, medications);
        String expected = "21-10-2024 15:48, Diagnosis: [Cold], Medications: [Paracetamol]";
        assertEquals(expected, visit.toString());
    }

}
