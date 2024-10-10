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
        assertEquals("+-----------+---------------------------------------+---------------------------------+\n" +
                        "| Action    | Format                                | Example                         |\n" +
                        "+-----------+---------------------------------------+---------------------------------+\n" +
                        "| Help      | help                                  | help                            |\n" +
                        "+-----------+---------------------------------------+---------------------------------+\n" +
                        "| Add       | add n/NAME ic/NRIC [p/PHONE_NUMBER]   | add n/James Ho ic/S9534567A     |\n" +
                        "|           | [d/DIAGNOSIS] [m/MEDICATION]          | p/91234567 d/Asthma m/Albuterol |\n" +
                        "|           | [ha/HOME_ADDRESS] [dob/DATE_OF_BIRTH] | ha/NUS-PGPR dob/1990-01-01      |\n" +
                        "+-----------+---------------------------------------+---------------------------------+\n" +
                        "| List      | list                                  | list                            |\n" +
                        "+-----------+---------------------------------------+---------------------------------+\n" +
                        "| Find      | find n/NAME          OR               | find n/John Doe                 |\n" +
                        "|           | find ic/NRIC         OR               | find ic/S1234                   |\n" +
                        "|           | find p/PHONE_NUMBER  OR               | find p/91234567                 |\n" +
                        "|           | find d/DIAGNOSIS     OR               | find d/Fever                    |\n" +
                        "|           | find m/MEDICATION    OR               | find m/Panadol                  |\n" +
                        "|           | find ha/HOME_ADDRESS OR               | find ha/NUS PGPR                |\n" +
                        "|           | find dob/DATE_OF_BIRTH                | find dob/1990-01-01             |\n" +
                        "+-----------+---------------------------------------+---------------------------------+\n" +
                        "| Delete    | delete NRIC                           | delete S9534567A                |\n" +
                        "+-----------+---------------------------------------+---------------------------------+\n" +
                        "| Save      | save(automatic)                       | save                            |\n" +
                        "+-----------+---------------------------------------+---------------------------------+\n" +
                        "| Retrieve/ | retrieve or import                    | retrieve                        |\n" +
                        "| Import    | (automatic)                           |                                 |\n" +
                        "+-----------+---------------------------------------+---------------------------------+\n" +
                        "| Exit      | exit                                  | exit                            |\n" +
                        "+-----------+---------------------------------------+---------------------------------+\n"
                                .trim(),
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
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS PGPR dob/13121995", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13121995", records);
        command.find("dob/13121995", records);
        assertEquals("Patient James Ho with NRIC S9534567A added." + System.lineSeparator() +
                        "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                        "Matching patients:" + System.lineSeparator() +
                        "Name: James Ho, NRIC: S9534567A, Phone: 91234567, Diagnosis: Asthma," +
                        " Medication: [Albuterol], " +
                        "Address: NUS PGPR, DOB: 13121995" + System.lineSeparator() +
                        "Name: John Doe, NRIC: S1234567Z, Phone: 97654321, Diagnosis: Fever, Medication: [Panadol], " +
                        "Address: Hougang Green, DOB: 13121995",
                outputStreamCaptor.toString().trim());
    }

    // @@ Author G13nd0n
    @Test
    void add_onePatient_onePatientInRecord() throws IOException{
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000",
                records);
        assertEquals(1, records.getPatients().size());
    }

    // @@Author G13nd0n
    @Test
    void delete_onePatient_twoPatientInRecord() throws IOException{
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000",
                records);
        command.add("add n/Will Smith ic/S7654321B p/91234567 d/AIDS m/Paracetamol ha/CAPT dob/18-06-2003",
                records);
        command.add("add n/Shawn Knowles ic/S2468013C p/87654321 d/Fever m/Aspirin ha/Tembusu dob/23-11-1998",
                records);
        command.delete("S2468013C", records);
        assertEquals(2, records.getPatients().size());
    }

    // @@Author G13nd0n
    @Test
    void testList_twoInputs_twoPatientsInRecord() throws IOException{
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000",
                records);
        command.add("add n/Will Smith ic/S7654321B p/91234567 d/AIDS m/Paracetamol ha/CAPT dob/18-06-2003",
                records);
        command.list(records);
        String expectedOutput = "Patient John Doe with NRIC S1234567A added.\nPatient Will Smith with NRIC S7654321B " +
                "added.\nName: John Doe, NRIC: S1234567A, Phone: 98765432, Diagnosis: COVID-19, " +
                "Medication: [Paracetamol], Address: RC4, DOB: 13-04-2000\n" +
                "Name: Will Smith, NRIC: S7654321B, Phone: 91234567, Diagnosis: AIDS, Medication: [Paracetamol]," +
                " Address: CAPT, DOB: 18-06-2003";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    // @@Author G13nd0n
    @Test
    void testFind() throws IOException{
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000",
                records);
        command.add("add n/Will Smith ic/S7654321B p/91234567 d/AIDS m/Paracetamol ha/CAPT dob/18-06-2003",
                records);
        command.find("ic/S7654321B", records);
        String expectedOutput = "Patient John Doe with NRIC S1234567A added.\nPatient Will Smith with NRIC S7654321B " +
                "added.\n" + "Matching patients:\nName: Will Smith, NRIC: S7654321B, " +
                "Phone: 91234567, Diagnosis: AIDS, Medication: [Paracetamol], Address: CAPT, DOB: 18-06-2003";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }
  
    //@@author yentheng0110
    @Test
    void testList_emptyList_noPatientFoundMessage() {
        command.list(records);
        String expectedOutput = "No patients found.";
    }
  
    //@@author yentheng0110
    @Test
    void testDelete_deleteNonExistingPatient_recordsSizeRemainsTheSame() throws IOException {
        command.add("add n/Jack Wong ic/S9765432T p/87658976 d/Gastric m/Gaviscon ha/Bukit Gombak dob/06071997",
                records);
        command.delete("S9587690H", records);
        assertEquals(1, records.getPatients().size());
    }
    
    //@@author yentheng0110
    @Test
    void testFind_invalidSearchFormat_expectAnErrorMessageWithGuidance() throws IOException {
        command.add("add n/Jack Wong ic/S9765432T p/87658976 d/Gastric m/Gaviscon ha/Bukit Gombak dob/06071997",
                records);
        command.find("Jack", records);
        String expectedOutput = "Patient Jack Wong with NRIC S9765432T added.\nInvalid search parameters. " +
                "Please use the format: find n/NAME ic/NRIC [p/PHONE] [d/DIAGNOSIS] " +
                "[m/MEDICATION] [ha/ADDRESS] [dob/DOB]";
        assertEquals(expectedOutput,
                  outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }
    
    //@@author yentheng0110
    @Test
    void testAdd_addPatientWithoutNRIC_patientNotAdded() throws IOException {
        command.add("add n/Jane Tan", records);
        String expectedOutput =
                "Please provide the NRIC for the patient named Jane Tan, then add the patient record again.";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }
}
