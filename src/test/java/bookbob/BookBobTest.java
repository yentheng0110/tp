package bookbob;

import bookbob.entity.Patient;
import bookbob.entity.Records;
import bookbob.entity.Visit;
import bookbob.functions.FileHandler;
import org.junit.jupiter.api.Test;

import bookbob.functions.CommandHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookBobTest {
    CommandHandler command = new CommandHandler();
    FileHandler fileHandler;
    Records records = new Records();
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    public BookBobTest() throws IOException {
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

    //@@author kaboomzxc
    @Test
    void testFindName() throws IOException {
        command.add("add n/James-Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13121995 " +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Diabetes", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13121995" +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Chronic Migraine", records);
        command.find("n/james", records);
        assertEquals("Patient James-Ho with NRIC S9534567A added." + System.lineSeparator() +
                "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                "Matching patients:" + System.lineSeparator() +
                "Name: James-Ho, NRIC: S9534567A, Phone: 91234567, Address: NUS-PGPR, DOB: 13121995, " +
                "Allergy: [Pollen], Sex: Male, Medical History: [Diabetes]", outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testFindIc() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13121995 " +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Diabetes", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13121995" +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Chronic Migraine", records);
        command.find("ic/S1234567Z", records);
        assertEquals("Patient James Ho with NRIC S9534567A added." + System.lineSeparator() +
                "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                "Matching patients:" + System.lineSeparator() +
                "Name: John Doe, NRIC: S1234567Z, Phone: 97654321, " +
                "Address: Hougang Green, DOB: 13121995, Allergy: [Pollen], " +
                "Sex: Male, Medical History: [Chronic Migraine]", outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testFindPhoneNumber() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13121995 " +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Diabetes", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13121995" +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Chronic Migraine", records);
        command.find("p/91234567", records);
        assertEquals("Patient James Ho with NRIC S9534567A added." + System.lineSeparator() +
                "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                "Matching patients:" + System.lineSeparator() +
                "Name: James Ho, NRIC: S9534567A, Phone: 91234567, Address: NUS-PGPR, " +
                "DOB: 13121995, Allergy: [Pollen], Sex: Male, Medical History: [Diabetes]",
                outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testFindHomeAddress() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13121995 " +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Diabetes", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13121995" +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Chronic Migraine", records);
        command.find("ha/NUS PGPR", records);
        assertEquals("Patient James Ho with NRIC S9534567A added." + System.lineSeparator() +
                        "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                        "Matching patients:" + System.lineSeparator() +
                        "Name: James Ho, NRIC: S9534567A, Phone: 91234567, Address: NUS-PGPR, " +
                        "DOB: 13121995, Allergy: [Pollen], Sex: Male, Medical History: [Diabetes]",
                outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testFindDateOfBirth() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13121995 " +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Diabetes", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13121995" +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Chronic Migraine", records);
        command.find("dob/13121995", records);
        assertEquals("Patient James Ho with NRIC S9534567A added." + System.lineSeparator() +
                        "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                        "Matching patients:" + System.lineSeparator() +
                        "Name: James Ho, NRIC: S9534567A, Phone: 91234567, Address: NUS-PGPR, " +
                        "DOB: 13121995, Allergy: [Pollen], Sex: Male, " +
                        "Medical History: [Diabetes]" + System.lineSeparator() +
                        "Name: John Doe, NRIC: S1234567Z, Phone: 97654321, " +
                        "Address: Hougang Green, DOB: 13121995, Allergy: [Pollen], " +
                        "Sex: Male, Medical History: [Chronic Migraine]",
                outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testAdd_singlePatient() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 d/Flu m/Paracetamol ha/123 Orch Rd dob/01-01-1990 " +
                "v/21-10-2024 15:27", records);

        assertEquals(1, records.getPatients().size());
        assertEquals("Patient John Doe with NRIC S1234567A added.", outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testDelete_existingPatient() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 d/Flu m/Paracetamol ha/123 Orch Rd dob/01-01-1990 " +
                "v/21-10-2024 15:27", records);
        outputStreamCaptor.reset();

        command.delete("S1234567A", records);

        assertEquals(0, records.getPatients().size());
        assertEquals("Patient John Doe, S1234567A, has been deleted.", outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testList_emptyRecords() {
        command.list(records);

        assertEquals("No patients found.", outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testList_multiplePatients() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 d/Flu m/Paracetamol ha/123 Main St dob/01-01-1990" +
                "v/21-10-2024 15:27", records);
        command.add("add n/Jane Smith ic/S7654321B p/87654321 d/Cough m/Cough Syrup ha/Haji Lane dob/02-02-1995" +
                "v/23-10-2024 14:31", records);
        outputStreamCaptor.reset();

        command.list(records);

        String expectedOutput = "Name: John Doe, NRIC: S1234567A, Phone: 98765432, Home Address: 123 Main St, " +
                "DOB: 01-01-1990, Allergies: [], Sex: , Medical Histories: []\n" +
                "    Visit Date: 21-10-2024 15:27, Diagnosis: [Flu], Medications: [Paracetamol]\n" +
                "Name: Jane Smith, NRIC: S7654321B, Phone: 87654321, Home Address: Haji Lane, DOB: 02-02-1995, " +
                "Allergies: [], Sex: , Medical Histories: []\n" +
                "    Visit Date: 23-10-2024 14:31, Diagnosis: [Cough], Medications: [Cough Syrup]";

        String normalizedExpected = expectedOutput.replaceAll("\\s+\n", "\n");
        String normalizedActual = outputStreamCaptor.toString().trim().replaceAll("\\s+\n", "\n");
        assertEquals(normalizedExpected, normalizedActual);
    }
    //@@author kaboomzxc
    @Test
    void testAddVisitSuccess() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 v/01-10-2024 15:30", records);
        outputStreamCaptor.reset();
        command.addVisit("addVisit ic/S1234567A v/21-10-2024 15:48 d/Fever m/Paracetamol", records);

        String expectedOutput = "Visit added successfully for patient: John Doe" + System.lineSeparator() +
                "Visit date: 21-10-2024 15:48" + System.lineSeparator() +
                "Diagnoses: Fever" + System.lineSeparator() +
                "Medications: Paracetamol";

        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testAddVisitNonexistentPatient() throws IOException {
        command.addVisit("addVisit ic/S1234567A v/21-10-2024 15:48 d/Fever m/Paracetamol", records);

        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("No patient found with NRIC: S1234567A"));
    }

    //@@author kaboomzxc
    @Test
    void testAddVisitMissingNric() throws IOException {
        command.addVisit("addVisit v/21-10-2024 15:48 d/Fever m/Paracetamol", records);

        assertEquals("Please provide the patient's NRIC.", outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testAddVisitMissingVisitDate() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 v/01-10-2024 15:30", records);
        outputStreamCaptor.reset();

        command.addVisit("addVisit ic/S1234567A d/Fever m/Paracetamol", records);

        assertEquals("Please provide the visit date and time.", outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testAddVisitMultipleVisitsForSamePatient() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 v/01-10-2024 15:30", records);
        outputStreamCaptor.reset();

        command.addVisit("addVisit ic/S1234567A v/21-10-2024 15:48 d/Fever m/Paracetamol", records);
        String firstOutput = outputStreamCaptor.toString().trim();
        outputStreamCaptor.reset();

        command.addVisit("addVisit ic/S1234567A v/22-10-2024 16:30 d/Cough m/Cough Syrup", records);
        String secondOutput = outputStreamCaptor.toString().trim();

        Patient patient = records.getPatients().get(0);
        assertEquals(3, patient.getVisits().size()); // Including initial visit

        Visit firstAddedVisit = patient.getVisits().get(1);
        Visit secondAddedVisit = patient.getVisits().get(2);

        assertEquals("Fever", firstAddedVisit.getDiagnoses().get(0));
        assertEquals("Cough", secondAddedVisit.getDiagnoses().get(0));
        assertEquals("Paracetamol", firstAddedVisit.getMedications().get(0));
        assertEquals("Cough Syrup", secondAddedVisit.getMedications().get(0));

        String expectedFirstOutput = "Visit added successfully for patient: John Doe" + System.lineSeparator() +
                "Visit date: 21-10-2024 15:48" + System.lineSeparator() +
                "Diagnoses: Fever" + System.lineSeparator() +
                "Medications: Paracetamol";

        String expectedSecondOutput = "Visit added successfully for patient: John Doe" + System.lineSeparator() +
                "Visit date: 22-10-2024 16:30" + System.lineSeparator() +
                "Diagnoses: Cough" + System.lineSeparator() +
                "Medications: Cough Syrup";

        assertEquals(expectedFirstOutput, firstOutput);
        assertEquals(expectedSecondOutput, secondOutput);
    }

    //@@author kaboomzxc
    @Test
    void testAddVisitInvalidFormat() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 v/01-10-2024 15:30", records);
        outputStreamCaptor.reset();

        command.addVisit("addVisit ic/S1234567A v/2024-10-21 15:48 d/Fever", records);

        assertEquals("Invalid date format. Please use dd-MM-yyyy HH:mm format (e.g., 21-10-2024 15:48)",
                outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testAddVisitEmptyFields() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 v/01-10-2024 15:30", records);
        outputStreamCaptor.reset();

        command.addVisit("addVisit ic/S1234567A v/21-10-2024 15:48", records);

        Patient patient = records.getPatients().get(0);
        Visit addedVisit = patient.getVisits().get(1); // Second visit (index 1)

        assertEquals(2, patient.getVisits().size());
        assertEquals(0, addedVisit.getDiagnoses().size());
        assertEquals(0, addedVisit.getMedications().size());
        assertEquals("Visit added successfully for patient: John Doe" + System.lineSeparator() +
                "Visit date: 21-10-2024 15:48", outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testAddVisitMultipleDiagnoses() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 v/01-10-2024 15:30", records);
        outputStreamCaptor.reset();

        command.addVisit("addVisit ic/S1234567A v/21-10-2024 15:48 d/Fever,Cough,Headache m/Paracetamol,Ibuprofen"
                , records);

        Patient patient = records.getPatients().get(0);
        Visit addedVisit = patient.getVisits().get(1); // Second visit

        assertEquals(2, patient.getVisits().size());
        assertEquals(3, addedVisit.getDiagnoses().size());
        assertEquals(2, addedVisit.getMedications().size());
        assertEquals("Fever", addedVisit.getDiagnoses().get(0));
        assertEquals("Cough", addedVisit.getDiagnoses().get(1));
        assertEquals("Headache", addedVisit.getDiagnoses().get(2));
        assertEquals("Paracetamol", addedVisit.getMedications().get(0));
        assertEquals("Ibuprofen", addedVisit.getMedications().get(1));

        String expectedOutput = "Visit added successfully for patient: John Doe" + System.lineSeparator() +
                "Visit date: 21-10-2024 15:48" + System.lineSeparator() +
                "Diagnoses: Fever, Cough, Headache" + System.lineSeparator() +
                "Medications: Paracetamol, Ibuprofen";

        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testAddVisitSpecialCharacters() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 v/01-10-2024 15:30", records);
        outputStreamCaptor.reset();

        command.addVisit("addVisit ic/S1234567A v/21-10-2024 15:48 " +
                "d/High Blood-Pressure,Type-2 Diabetes m/Metformin-500mg,ACE-Inhibitor", records);

        Patient patient = records.getPatients().get(0);
        Visit addedVisit = patient.getVisits().get(1); // Second visit

        assertEquals(2, patient.getVisits().size());
        assertEquals("High Blood-Pressure", addedVisit.getDiagnoses().get(0));
        assertEquals("Type-2 Diabetes", addedVisit.getDiagnoses().get(1));
        assertEquals("Metformin-500mg", addedVisit.getMedications().get(0));
        assertEquals("ACE-Inhibitor", addedVisit.getMedications().get(1));

        String expectedOutput = "Visit added successfully for patient: John Doe" + System.lineSeparator() +
                "Visit date: 21-10-2024 15:48" + System.lineSeparator() +
                "Diagnoses: High Blood-Pressure, Type-2 Diabetes" + System.lineSeparator() +
                "Medications: Metformin-500mg, ACE-Inhibitor";

        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    // @@ author G13nd0n
    @Test
    void add_onePatient_onePatientInRecord() throws IOException{
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000" +
                "v/23-11-2024 12:29", records);
        assertEquals(1, records.getPatients().size());
    }

    // @@author G13nd0n
    @Test
    void delete_onePatient_twoPatientInRecord() throws IOException{
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000" +
                "v/23-11-2024 12:29", records);
        command.add("add n/Will Smith ic/S7654321B p/91234567 d/AIDS m/Paracetamol ha/CAPT dob/18-06-2003" +
                "v/15-10-2024 11:53", records);
        command.add("add n/Shawn Knowles ic/S2468013C p/87654321 d/Fever m/Aspirin ha/Tembusu dob/23-11-1998" +
                "v/02-04-2024 09:45", records);
        command.delete("S2468013C", records);
        assertEquals(2, records.getPatients().size());
    }

    // @@author G13nd0n
    @Test
    void testList_twoInputs_twoPatientsInRecord() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000" +
                "v/23-11-2024 12:29", records);
        command.add("add n/Will Smith ic/S7654321B p/91234567 d/AIDS m/Paracetamol ha/CAPT dob/18-06-2003" +
                "v/15-10-2024 11:53", records);
        command.list(records);

        String expectedOutput = "Patient John Doe with NRIC S1234567A added.\n" +
                "Patient Will Smith with NRIC S7654321B added.\n" +
                "Name: John Doe, NRIC: S1234567A, Phone: 98765432, Home Address: RC4, " +
                "DOB: 13-04-2000, Allergies: [], Sex: , Medical Histories: []\n" +
                "    Visit Date: 23-11-2024 12:29, Diagnosis: [COVID-19], Medications: [Paracetamol]\n" +
                "Name: Will Smith, NRIC: S7654321B, Phone: 91234567, Home Address: CAPT, " +
                "DOB: 18-06-2003, Allergies: [], Sex: , Medical Histories: []\n" +
                "    Visit Date: 15-10-2024 11:53, Diagnosis: [AIDS], Medications: [Paracetamol]";

        String normalizedExpected = expectedOutput.replaceAll("\\s+\n", "\n");
        String normalizedActual = outputStreamCaptor.toString().trim().replaceAll("\\s+\n", "\n");
        assertEquals(normalizedExpected, normalizedActual);
    }

    // @@author G13nd0n
    @Test
    void testFind() throws IOException{
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000" +
                        "v/23-11-2024 12:29", records);
        command.add("add n/Will Smith ic/S7654321B p/91234567 d/AIDS m/Paracetamol ha/CAPT dob/18-06-2003" +
                        "v/21-10-2024 15:30 al/peanuts s/male mh/diabetes", records);
        command.find("ic/S7654321B", records);
        String expectedOutput = "Patient John Doe with NRIC S1234567A added.\nPatient Will Smith with NRIC S7654321B " +
                "added.\n" + "Matching patients:\nName: Will Smith, NRIC: S7654321B, Phone: 91234567, Address: CAPT, " +
                "DOB: 18-06-2003, Allergy: [peanuts], Sex: male, Medical History: [diabetes]";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author G13nd0n
    @Test
    void testFindName_multipleOutputs() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000" +
                "v/23-11-2024 12:29", records);
        command.add("add n/Will Smith ic/S7654321B p/91234567 d/AIDS m/Paracetamol ha/CAPT dob/18-06-2003" +
                "v/21-10-2024 15:30", records);
        command.add("add n/John Smith ic/S2468024A p/87654321 d/Diabetes m/Insulin ha/CAPT dob/13-04-2002" +
                "v/15-10-2024 10:25", records);
        String expectedOutput = "Patient John Doe with NRIC S1234567A added.\nPatient Will Smith with NRIC S7654321B " +
                "added.\n" + "Patient John Smith with NRIC S2468024A added.\n"+
                "Matching patients:\nName: Will Smith, NRIC: S7654321B, Phone: 91234567, " +
                "Address: CAPT, DOB: 18-06-2003, Allergy: [], Sex: , Medical History: []\n" +
                "Name: John Smith, NRIC: S2468024A, Phone: 87654321, Address: CAPT, " +
                "DOB: 13-04-2002, Allergy: [], Sex: , Medical History: []";
        command.find("n/Smith", records);
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author G13nd0n
    @Test
    void testFindAddress_multipleOutputs() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000" +
                "v/23-11-2024 12:29", records);
        command.add("add n/Will Smith ic/S7654321B p/91234567 d/AIDS m/Paracetamol ha/CAPT dob/18-06-2003" +
                "v/21-10-2024 15:30", records);
        command.add("add n/John Smith ic/S2468024A p/87654321 d/Diabetes m/Insulin ha/CAPT dob/13-04-2002" +
                "v/15-10-2024 10:25", records);
        String expectedOutput = "Patient John Doe with NRIC S1234567A added.\nPatient Will Smith with NRIC S7654321B " +
                "added.\n" + "Patient John Smith with NRIC S2468024A added.\n"+
                "Matching patients:\nName: Will Smith, NRIC: S7654321B, Phone: 91234567, " +
                "Address: CAPT, DOB: 18-06-2003, " +
                "Allergy: [], Sex: , Medical History: []\nName: John Smith, NRIC: S2468024A, " +
                "Phone: 87654321, Address: CAPT, " +
                "DOB: 13-04-2002, Allergy: [], Sex: , Medical History: []";
        command.find("ha/CAPT", records);
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
        command.add("add n/Jack Wong ic/S9765432T p/87658976 d/Gastric m/Gaviscon " +
                        "v/01-10-2024 17:30 ha/Bukit Gombak dob/06071997", records);
        command.delete("S9587690H", records);
        assertEquals(1, records.getPatients().size());
    }
  
    //@@author yentheng0110
    @Test
    void testFind_invalidSearchFormat_expectAnErrorMessageWithGuidance() throws IOException {
        command.add("add n/Jack Wong ic/S9765432T p/87658976 d/Gastric m/Gaviscon " +
                        "v/01-10-2024 17:30 ha/Bukit Gombak dob/06071997", records);
        command.find("Jack", records);
        String expectedOutput = "Patient Jack Wong with NRIC S9765432T added.\nInvalid search parameters. " +
                "Please use the format: find n/NAME ic/NRIC [p/PHONE] " +
                "[ha/ADDRESS] [dob/DOB] [al/ALLERGY] [s/SEX] [mh/MEDICAL_HISTORY]";
        assertEquals(expectedOutput,
                  outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void testAdd_addPatientWithoutNRIC_expectAssertionError() throws IOException {
        AssertionError error = assertThrows(AssertionError.class, () -> {
            command.add("add n/Jane Tan", records);
        });
    }

    //@@author PrinceCatt
    @Test
    void testTextConverterFullInformation() {
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
    void testTextConverterPartialInformation() {
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

    @Test
    void testFileInitialization() throws IOException {
        Records records = new Records();            //initialize a new record to clear file content
        fileHandler.autosave(records);
        command.add("add n/Jack Wong ic/S9765432T p/87658976 d/Gastric m/Gaviscon " +
                        "v/01-10-2024 17:30 ha/Bukit Gombak dob/06071997", records);
        Patient patient = records.getPatients().get(0);
        fileHandler.autosave(records);
        fileHandler.initFile(records);
        Patient newPatient = records.getPatients().get(0);
        assertEquals(patient,newPatient);
    }
}
