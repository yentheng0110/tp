package bookbob.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@author yentheng0110
public class RecordsTest {
    private Records records;
    private Patient patientOne;
    private Patient patientTwo;

    //@author yentheng0110
    @BeforeEach
    public void setUp() {
        records = new Records();

        // Initialise sample patient data
        String name = "John Doe";
        String nric = "S9234567A";
        String phoneNumber = "98765432";
        String dateOfBirth = "09081992";
        String homeAddress = "Block 123 Farrer Road";
        ArrayList<String> allergies = new ArrayList<>();
        allergies.add("Peanuts");
        String sex = "Male";
        ArrayList<String> medicalHistories = new ArrayList<>();
        medicalHistories.add("Diabetes");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime visitDate = LocalDateTime.parse("06-11-2024 18:00", formatter);
        ArrayList<Visit> visits = new ArrayList<>();
        visits.add(new Visit(visitDate));

        patientOne = new Patient(name, nric, phoneNumber, dateOfBirth, homeAddress, allergies, sex, medicalHistories,
                visits);

        name = "Jane Smith";
        nric = "S7654321B";
        phoneNumber = "87654321";
        dateOfBirth = "02021976";
        homeAddress = "Block 456 Elmo Street";
        allergies = new ArrayList<>();
        allergies.add("Pollen");
        sex = "Female";
        medicalHistories = new ArrayList<>();
        medicalHistories.add("Hypertension");
        visitDate = LocalDateTime.parse("07-11-2024 18:00", formatter);
        visits = new ArrayList<>();
        visits.add(new Visit(visitDate));

        patientTwo = new Patient(name, nric, phoneNumber, dateOfBirth, homeAddress, allergies, sex, medicalHistories,
                visits);
    }

    //@author yentheng0110
    @Test
    public void testConstructor_createsEmptyPatientList() {
        assertTrue(records.getPatients().isEmpty());
    }

    //@author yentheng0110
    @Test
    public void addPatient_addOnePatient_patientListContainsThePatientAdded() {
        records.addPatient(patientOne);
        assertTrue(records.getPatients().contains(patientOne));
    }

    //@author yentheng0110
    @Test
    public void addPatient_addTwoPatients_patientListContainsTheTwoPatientsAdded() {
        records.addPatient(patientOne);
        records.addPatient(patientTwo);
        ArrayList<Patient> patients = records.getPatients();
        assertTrue(records.getPatients().contains(patientOne));
        assertTrue(records.getPatients().contains(patientTwo));
    }

    //@author yentheng0110
    @Test
    public void getPatients_getPatientListAfterAddingTwoPatients_patientListSizeIsTwo() {
        records.addPatient(patientOne);
        records.addPatient(patientTwo);
        ArrayList<Patient> patients = records.getPatients();
        assertEquals(2, patients.size());
    }
}
