package bookbob.functions;

import bookbob.entity.Records;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandHandlerTest {
    CommandHandler command = new CommandHandler();
    Records records = new Records();
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    public CommandHandlerTest() throws IOException {
    }

    @Test
    public void sampleTest() {
        assertTrue(true);
    }

    //@@author coraleaf0602
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    //@@author coraleaf0602
    @Test
    void test_help_output() {
        command.help();
        assertEquals("+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Action      | Format                                | Example                         |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Help        | help                                  | help                            |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Add         | add n/NAME ic/NRIC [p/PHONE_NUMBER]   | add n/James Ho ic/S9534567A     |\n" +
                        "|             | [d/DIAGNOSIS] [m/MEDICATION]          | p/91234567 d/Asthma m/Albuterol |\n" +
                        "|             | [ha/HOME_ADDRESS] [dob/DATE_OF_BIRTH] | ha/NUS-PGPR dob/01011990        |\n" +
                        "|             | [v/VISIT_DATE_TIME] [al/ALLERGY]      | v/21-10-2024 15:48 al/Pollen    |\n" +
                        "|             | [s/SEX] [mh/MEDICALHISTORY]           | s/Female mh/Diabetes            |\n" +
                        "|             | DATE format: dd-mm-yyyy               |                                 |\n" +
                        "|             | TIME format: HH:mm                    |                                 |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Edit        | edit ic/NRIC /to [n/NAME]             | edit ic/S9534567A /to p/80976890|\n" +
                        "|             | [newic/NEW_NRIC]  [p/PHONE_NUMBER]    | mh/Diabetes, Hypertension       |\n" +
                        "|             | [ha/HOME_ADDRESS] [dob/DATE_OF_BIRTH] |                                 |\n" +
                        "|             | [al/ALLERGY] [s/SEX]                  |                                 |\n" +
                        "|             | [mh/MEDICALHISTORY]                   |                                 |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Add Visit   | addVisit ic/NRIC v/VISIT_DATE_TIME    | addVisit ic/S9534567A           |\n" +
                        "|             | [d/DIAGNOSIS] [m/MEDICATION]          | v/21-10-2024 15:48              |\n" +
                        "|             | DATE format: dd-mm-yyyy               | d/Fever,Headache,Flu            |\n" +
                        "|             | TIME format: HH:mm                    | m/Paracetamol,Ibuprofen         |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Edit Visit  | editVisit ic/NRIC                     | editVisit ic/S7209876Y          |\n" +
                        "|             | date/VISIT_DATE_AND_TIME              | date/06-11-2024 14:00           |\n" +
                        "|             | [newDate/NEW_DATE]  [d/DIAGNOSIS]     | newDate/08-11-2024 14:00        |\n" +
                        "|             | [m/MEDICATION]                        | d/Asthma m/Panadol, Antibiotics |\n" +
                        "|             | DATE format: dd-mm-yyyy               |                                 |\n" +
                        "|             | TIME format: HH:mm                    |                                 |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| List        | list                                  | list                            |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Find        | find n/NAME          OR               | find n/John Doe                 |\n" +
                        "|             | find ic/NRIC         OR               | find ic/S1234                   |\n" +
                        "|             | find p/PHONE_NUMBER  OR               | find p/91234567                 |\n" +
                        "|             | find d/DIAGNOSIS     OR               | find d/Fever                    |\n" +
                        "|             | find m/MEDICATION    OR               | find m/Panadol                  |\n" +
                        "|             | find ha/HOME_ADDRESS OR               | find ha/NUS PGPR                |\n" +
                        "|             | find dob/DATE_OF_BIRTH OR             | find dob/01011990               |\n" +
                        "|             | find al/ALLERGY      OR               | find al/Peanuts                 |\n" +
                        "|             | find find s/SEX      OR               | find find s/Female              |\n" +
                        "|             | find mh/MEDICAL_HISTORY               | find mh/Diabetes                |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Delete      | delete NRIC                           | delete S9534567A                |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Add         | appointment n/NAME ic/NRIC            | add n/James Ho ic/S9534567A     |\n" +
                        "| Appointment | date/DATE time/TIME                   | date/01-04-2025 time/12:00      |\n" +
                        "|             | DATE format: dd-mm-yyyy               |                                 |\n" +
                        "|             | TIME format: HH:mm                    |                                 |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| List        | listAppointments                      | list                            |\n" +
                        "| Appointment |                                       |                                 |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Find        | findAppointment n/NAME          OR    | findAppointment n/John Doe      |\n" +
                        "| Appointment | findAppointment ic/NRIC         OR    | findAppointment ic/S1234        |\n" +
                        "|             | findAppointment date/DATE       OR    | findAppointment date/01-04-2025 |\n" +
                        "|             | findAppointment time/TIME       OR    | findAppointment time/12:00      |\n" +
                        "|             | DATE format: dd-mm-yyyy               |                                 |\n" +
                        "|             | TIME format: HH:mm                    |                                 |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Delete      | deleteAppointment NRIC                | deleteAppointment S9534567A     |\n" +
                        "| Appointment | date/DATE time/TIME                   | date/01-04-2025 time/12:00      |\n" +
                        "|             | DATE format: dd-mm-yyyy               |                                 |\n" +
                        "|             | TIME format: HH:mm                    |                                 |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Find        | findVisit NRIC                        | findVisit S9534567A             |\n" +
                        "| Visits      |                                       |                                 |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Find        | findDiagnosis diagnosis               | findDiagnosis fever             |\n" +
                        "| Diagnosis   |                                       |                                 |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Find        | findMedication medication             | findMedication Panadol          |\n" +
                        "| Medication  |                                       |                                 |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Save        | save(automatic)                       |                                 |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Retrieve/   | retrieve or import                    |                                 |\n" +
                        "| Import      | (automatic)                           |                                 |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Exit        | exit                                  | exit                            |\n" +
                        "+-------------+---------------------------------------+---------------------------------+"
                                .trim(),
                outputStreamCaptor.toString().trim());
    }

    //@@author coraleaf0602
    @AfterEach
    public void tearDown_for_helpCommand() {
        System.setOut(standardOut);
    }

    //@@author coraleaf0602
    @Test
    void test_addOnePatient_toEmptyRecord() throws IOException {
        command.add("add n/James-Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13121995 " +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Diabetes", records);
        assertEquals("Patient James-Ho with NRIC S9534567A added.".trim(), outputStreamCaptor.toString().trim());
    }

    //@@author coraleaf0602
    @Test
    void test_deletePatient_inRecord() throws IOException {
        command.add("add n/James-Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13121995 " +
                "v/21-10-2024 15:48 al/Pollen s/Female mh/Diabetes", records);
        command.delete("S9534567A", records);
        String expectedOutput = "Patient James-Ho with NRIC S9534567A added.\n" +
                "Patient James-Ho, S9534567A, has been deleted.";
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim().replace(System.lineSeparator(),
                "\n"));
    }

    //@@author coraleaf0602
    @Test
    void test_listOnePatient_inRecord() throws IOException {
        command.add("add n/James-Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13121995 " +
                "v/21-10-2024 15:48 al/Pollen s/Female mh/Diabetes", records);
        command.list(records);
        assertEquals("Patient James-Ho with NRIC S9534567A added.\n" +
                        "Name: James-Ho, NRIC: S9534567A, Phone: 91234567, Home Address: NUS-PGPR, DOB: 13121995, " +
                        "Allergies: [Pollen], Sex: Female, Medical Histories: [Diabetes]\n" +
                        "    Visit Date: 21-10-2024 15:48, Diagnosis: [Asthma], Medications: [Albuterol]",
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author coraleaf0602
    @Test
    void test_findName_doesNotExist() {
        command.find("n/james", records);
        assertEquals("No matching patients found.", outputStreamCaptor.toString().trim());
    }

    //@@author coraleaf0602
    @Test
    void test_findIC_doesNotExist() {
        command.find("ic/S1234567Z", records);
        assertEquals("No matching patients found.", outputStreamCaptor.toString().trim());
    }

    //@@author coraleaf0602
    @Test
    void test_findPhoneNumber_doesNotExist() {
        command.find("p/82529790", records);
        assertEquals("No matching patients found.", outputStreamCaptor.toString().trim());
    }

    //@@author coraleaf0602
    @Test
    void test_findHomeAddress_doesNotExist() {
        command.find("ha/NUS Utown", records);
        assertEquals("No matching patients found.", outputStreamCaptor.toString().trim());
    }

    //@@author coraleaf0602
    @Test
    void test_findDateOfBirth_doesNotExist() {
        command.find("dob/13122005", records);
        assertEquals("No matching patients found.", outputStreamCaptor.toString().trim());
    }
}
