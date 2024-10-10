package bookbob;

import bookbob.entity.Records;
import org.junit.jupiter.api.Test;

import bookbob.functions.CommandHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookBobTest {
    CommandHandler command = new CommandHandler();
    Records records = new Records();

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    public BookBobTest() throws IOException {
    }

    @Test
    public void sampleTest() {
        assertTrue(true);
    }

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testHelp() {
        command.help();
        assertEquals("""
                +-----------+---------------------------------------+---------------------------------+
                | Action    | Format                                | Example                         |
                +-----------+---------------------------------------+---------------------------------+
                | Help      | help                                  | help                            |
                +-----------+---------------------------------------+---------------------------------+
                | Add       | add n/NAME ic/NRIC [p/PHONE_NUMBER]   | add n/James Ho ic/S9534567A     |
                |           | [d/DIAGNOSIS] [m/MEDICATION]          | p/91234567 d/Asthma m/Albuterol |
                |           | [ha/HOME_ADDRESS] [dob/DATE_OF_BIRTH] | ha/NUS-PGPR dob/1990-01-01      |
                +-----------+---------------------------------------+---------------------------------+
                | List      | list                                  | list                            |
                +-----------+---------------------------------------+---------------------------------+
                | Find      | find n/NAME          OR               | find n/John Doe                 |
                |           | find ic/NRIC         OR               | find ic/S1234                   |
                |           | find p/PHONE_NUMBER  OR               | find p/91234567                 |
                |           | find d/DIAGNOSIS     OR               | find d/Fever                    |
                |           | find m/MEDICATION    OR               | find m/Panadol                  |
                |           | find ha/HOME_ADDRESS OR               | find ha/NUS PGPR                |
                |           | find dob/DATE_OF_BIRTH                | find dob/1990-01-01             |
                +-----------+---------------------------------------+---------------------------------+
                | Delete    | delete NRIC                           | delete S9534567A                |
                +-----------+---------------------------------------+---------------------------------+
                | Save      | save(automatic)                       | save                            |
                +-----------+---------------------------------------+---------------------------------+
                | Retrieve/ | retrieve or import                    | retrieve                        |
                | Import    | (automatic)                           |                                 |
                +-----------+---------------------------------------+---------------------------------+
                | Exit      | exit                                  | exit                            |
                +-----------+---------------------------------------+---------------------------------+""",
                outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testAdd() throws IOException {
        command.add("add n/James-Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13121995", records);
        assertEquals("Patient James-Ho with NRIC S9534567A added.".trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    void testDelete() throws IOException {
        command.add("add n/James-Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13121995"
                , records);
        command.delete("S9534567A", records);
        String expectedOutput = "Patient James-Ho with NRIC S9534567A added.\n" +
                "Patient James-Ho, S9534567A, has been deleted.";
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim().replace(System.lineSeparator(),
                "\n"));
    }

    @Test
    void testList() throws IOException {
        command.add("add n/James-Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13121995", records);
        command.list(records);
        assertEquals("Patient James-Ho with NRIC S9534567A added.\n" +
                "Name: James-Ho, NRIC: S9534567A, Phone: 91234567, Diagnosis: Asthma, Medication: [Albuterol], " +
                "Address: NUS-PGPR, DOB: 13121995", outputStreamCaptor.toString().trim().replace(System.lineSeparator(),
                "\n"));
    }

    @Test
    void testFindName() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS PGPR dob/13121995", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13121995", records);
        command.find("n/james", records);
        assertEquals("Patient James Ho with NRIC S9534567A added." + System.lineSeparator() +
                "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                "Matching patients:" + System.lineSeparator() +
                "Name: James Ho, NRIC: S9534567A, Phone: 91234567, Diagnosis: Asthma, Medication: [Albuterol], " +
                "Address: NUS PGPR, DOB: 13121995", outputStreamCaptor.toString().trim());
    }

    @Test
    void testFindIc() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS PGPR dob/13121995", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13121995", records);
        command.find("ic/S1234567Z", records);
        assertEquals("Patient James Ho with NRIC S9534567A added." + System.lineSeparator() +
                "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                "Matching patients:" + System.lineSeparator() +
                "Name: John Doe, NRIC: S1234567Z, Phone: 97654321, Diagnosis: Fever, Medication: [Panadol], " +
                "Address: Hougang Green, DOB: 13121995", outputStreamCaptor.toString().trim());
    }

    @Test
    void testFindPhoneNumber() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS PGPR dob/13121995", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13121995", records);
        command.find("p/91234567", records);
        assertEquals("Patient James Ho with NRIC S9534567A added." + System.lineSeparator() +
                "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                "Matching patients:" + System.lineSeparator() +
                "Name: James Ho, NRIC: S9534567A, Phone: 91234567, Diagnosis: Asthma, Medication: [Albuterol], " +
                "Address: NUS PGPR, DOB: 13121995", outputStreamCaptor.toString().trim());
    }
    @Test
    void testFindDiagnosis() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS PGPR dob/13121995", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13121995", records);
        command.find("d/Asthma", records);
        assertEquals("Patient James Ho with NRIC S9534567A added." + System.lineSeparator() +
                "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                "Matching patients:" + System.lineSeparator() +
                "Name: James Ho, NRIC: S9534567A, Phone: 91234567, Diagnosis: Asthma, Medication: [Albuterol], " +
                "Address: NUS PGPR, DOB: 13121995", outputStreamCaptor.toString().trim());
    }
    @Test
    void testFindMedication() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS PGPR dob/13121995", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13121995", records);
        command.find("m/Albuterol", records);
        assertEquals("Patient James Ho with NRIC S9534567A added." + System.lineSeparator() +
                "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                "Matching patients:" + System.lineSeparator() +
                "Name: James Ho, NRIC: S9534567A, Phone: 91234567, Diagnosis: Asthma, Medication: [Albuterol], " +
                "Address: NUS PGPR, DOB: 13121995", outputStreamCaptor.toString().trim());
    }
    @Test
    void testFindHomeAddress() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS PGPR dob/13121995", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13121995", records);
        command.find("ha/NUS PGPR", records);
        assertEquals("Patient James Ho with NRIC S9534567A added." + System.lineSeparator() +
                "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                "Matching patients:" + System.lineSeparator() +
                "Name: James Ho, NRIC: S9534567A, Phone: 91234567, Diagnosis: Asthma, Medication: [Albuterol], " +
                "Address: NUS PGPR, DOB: 13121995", outputStreamCaptor.toString().trim());
    }
    @Test
    void testFindDateOfBirth() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS PGPR dob/13121995",
                records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13121995",
                records);
        command.find("dob/13121995", records);
        assertEquals("Patient James Ho with NRIC S9534567A added." + System.lineSeparator() +
                        "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                        "Matching patients:" + System.lineSeparator() +
                        "Name: James Ho, NRIC: S9534567A, Phone: 91234567, Diagnosis: Asthma, " +
                        "Medication: [Albuterol], " +
                        "Address: NUS PGPR, DOB: 13121995" + System.lineSeparator() +
                        "Name: John Doe, NRIC: S1234567Z, Phone: 97654321, Diagnosis: Fever, Medication: [Panadol], " +
                        "Address: Hougang Green, DOB: 13121995",
                outputStreamCaptor.toString().trim());
    }
}
