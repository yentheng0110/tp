package bookbob;

import bookbob.entity.Records;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bookbob.functions.CommandHandler;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookBobTest {
    CommandHandler command = new CommandHandler();
    Records records = new Records();
    ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    PrintStream standardOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    // @@ Author G13nd0n
    @Test
    void add_onePatient_onePatientInRecord() {
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000",
                records);
        assertEquals(1, records.getPatients().size());
    }

    // @@Author G13nd0n
    @Test
    void delete_onePatient_twoPatientInRecord() {
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
    void testList() {
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
    void testFind() {
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000",
                records);
        command.add("add n/Will Smith ic/S7654321B p/91234567 d/AIDS m/Paracetamol ha/CAPT dob/18-06-2003",
                records);
        command.find("ic/S7654321B", records);
        String expectedOutput = "Patient John Doe with NRIC S1234567A added.\nPatient Will Smith with NRIC S7654321B " +
                "added.\n" + "Matching patients:\nName: Will Smith, NRIC: S7654321B, DOB: 18-06-2003, " +
                "Phone: 91234567, Address: CAPT, Diagnosis: AIDS, Medication: [Paracetamol]";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }
}
