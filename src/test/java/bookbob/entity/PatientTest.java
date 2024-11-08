package bookbob.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

//@author yentheng0110
public class PatientTest {
    private Patient patient;
    private String name;
    private String nric;
    private String phoneNumber;
    private String dateOfBirth;
    private String homeAddress;
    private ArrayList<String> allergies;
    private String sex;
    private ArrayList<String> medicalHistories;
    private ArrayList<Visit> visits;

    //@author yentheng0110
    @BeforeEach
    public void setUp() {
        // Initialize test data
        name = "John Doe";
        nric = "S9234567A";
        phoneNumber = "98765432";
        dateOfBirth = "08091992";
        homeAddress = "Block 123 Farrer Road";
        allergies = new ArrayList<>();
        allergies.add("Peanuts");
        sex = "Male";
        medicalHistories = new ArrayList<>();
        medicalHistories.add("Diabetes");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime visitDate = LocalDateTime.parse("06-11-2024 18:00", formatter);
        visits = new ArrayList<>();
        visits.add(new Visit(visitDate));
    }

    //@author yentheng0110
    @Test
    public void testPatientConstructor_withAllPatientDetails_instantiatePatientObjectCorrectly() {
        // Create a Patient instance with the test data
        patient = new Patient(name, nric, phoneNumber, dateOfBirth, homeAddress, allergies, sex, medicalHistories,
                visits);
        // Check if all fields are correctly initialised
        assertEquals(name, patient.getName());
        assertEquals(nric, patient.getNric());
        assertEquals(phoneNumber, patient.getPhoneNumber());
        assertEquals(dateOfBirth, patient.getDateOfBirth());
        assertEquals(homeAddress, patient.getHomeAddress());
        assertEquals(allergies, patient.getAllergies());
        assertEquals(sex, patient.getSex());
        assertEquals(medicalHistories, patient.getMedicalHistories());
        assertEquals(visits, patient.getVisits());
    }

    //@author yentheng0110
    @Test
    public void testPatientConstructor_withMandatoryFields_instantiatePatientObjectCorrectly() {
        patient = new Patient(name, nric, visits);
        // Check if all fields are correctly initialised
        assertEquals(name, patient.getName());
        assertEquals(nric, patient.getNric());
        assertEquals(visits, patient.getVisits());
    }

    //@author yentheng0110
    @Test
    public void testSetName() {
        patient = new Patient(name, nric, phoneNumber, dateOfBirth, homeAddress, allergies, sex, medicalHistories,
                visits);
        patient.setName("Jane Doe");
        assertEquals("Jane Doe", patient.getName());
    }

    //@author yentheng0110
    @Test
    public void testSetNric() {
        patient = new Patient(name, nric, phoneNumber, dateOfBirth, homeAddress, allergies, sex, medicalHistories,
                visits);
        patient.setNric("S7654321B");
        assertEquals("S7654321B", patient.getNric());
    }

    //@author yentheng0110
    @Test
    public void testSetDateOfBirth() {
        patient = new Patient(name, nric, phoneNumber, dateOfBirth, homeAddress, allergies, sex, medicalHistories,
                visits);
        patient.setDateOfBirth("02021976");
        assertEquals("02021976", patient.getDateOfBirth());
    }

    //@author yentheng0110
    @Test
    public void testSetPhoneNumber() {
        patient = new Patient(name, nric, phoneNumber, dateOfBirth, homeAddress, allergies, sex, medicalHistories,
                visits);
        patient.setPhoneNumber("87654321");
        assertEquals("87654321", patient.getPhoneNumber());
    }

    //@author yentheng0110
    @Test
    public void testSetHomeAddress() {
        patient = new Patient(name, nric, phoneNumber, dateOfBirth, homeAddress, allergies, sex, medicalHistories,
                visits);
        patient.setHomeAddress("Block 456 Bukit Batok West Avenue 8");
        assertEquals("Block 456 Bukit Batok West Avenue 8", patient.getHomeAddress());
    }

    //@author yentheng0110
    @Test
    public void testSetVisits() {
        patient = new Patient(name, nric, phoneNumber, dateOfBirth, homeAddress, allergies, sex, medicalHistories,
                visits);
        ArrayList<Visit> newVisits = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        newVisits.add(new Visit(LocalDateTime.parse("08-11-2024 08:00", formatter)));
        patient.setVisits(newVisits);
        assertEquals(newVisits, patient.getVisits());
    }

    //@author yentheng0110
    @Test
    public void testSetAllergies() {
        patient = new Patient(name, nric, phoneNumber, dateOfBirth, homeAddress, allergies, sex, medicalHistories,
                visits);
        ArrayList<String> newAllergies = new ArrayList<>();
        newAllergies.add("Shellfish");
        patient.setAllergies(newAllergies);
        assertEquals(newAllergies, patient.getAllergies());
    }

    //@author yentheng0110
    @Test
    public void testSetSex() {
        patient = new Patient(name, nric, phoneNumber, dateOfBirth, homeAddress, allergies, sex, medicalHistories,
                visits);
        patient.setSex("Female");
        assertEquals("Female", patient.getSex());
    }

    //@author yentheng0110
    @Test
    public void testSetMedicalHistories() {
        patient = new Patient(name, nric, phoneNumber, dateOfBirth, homeAddress, allergies, sex, medicalHistories,
                visits);
        ArrayList<String> newMedicalHistories = new ArrayList<>();
        newMedicalHistories.add("Hypertension");
        patient.setMedicalHistories(newMedicalHistories);
        assertEquals(newMedicalHistories, patient.getMedicalHistories());
    }

    //@author yentheng0110
    @Test
    public void testToString() {
        patient = new Patient(name, nric, phoneNumber, dateOfBirth, homeAddress, allergies, sex, medicalHistories,
                visits);
        String expectedString = "Name: John Doe, NRIC: S9234567A, Phone: 98765432, Address: Block 123 Farrer Road, " +
                "DOB: 08091992, Allergy: [Peanuts], Sex: Male, Medical History: [Diabetes]";
        assertEquals(expectedString, patient.toString());
    }
}
