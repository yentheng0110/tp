package bookbob.functions;

import bookbob.entity.Patient;
import bookbob.entity.Records;
import bookbob.entity.Visit;
import bookbob.entity.AppointmentRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandHandlerTest {
    CommandHandler command = new CommandHandler();
    Records records = new Records();
    AppointmentRecord appointmentRecord = new AppointmentRecord();
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
    void helpCommand_noInput_outputsCommandHelp() {
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
                        "|             | v/VISIT_DATE_TIME                     | v/06-11-2024 14:00              |\n" +
                        "|             | [newDate/NEW_DATE_TIME]  [d/DIAGNOSIS]| newDate/08-11-2024 14:00        |\n" +
                        "|             | [m/MEDICATION]                        | d/Asthma m/Panadol, Antibiotics |\n" +
                        "|             | DATE format: dd-mm-yyyy               |                                 |\n" +
                        "|             | TIME format: HH:mm                    |                                 |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| List        | list                                  | list                            |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Find        | find n/NAME            OR             | find n/John Doe                 |\n" +
                        "|             | find ic/NRIC           OR             | find ic/S1234                   |\n" +
                        "|             | find p/PHONE_NUMBER    OR             | find p/91234567                 |\n" +
                        "|             | find ha/HOME_ADDRESS   OR             | find ha/NUS PGPR                |\n" +
                        "|             | find dob/DATE_OF_BIRTH OR             | find dob/01011990               |\n" +
                        "|             | find al/ALLERGY        OR             | find al/Peanuts                 |\n" +
                        "|             | find s/SEX             OR             | find s/Female                   |\n" +
                        "|             | find mh/MEDICAL_HISTORY               | find mh/Diabetes                |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Delete      | delete NRIC                           | delete S9534567A                |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Add         | appointment n/NAME ic/NRIC            | appointment n/James Ho          |\n" +
                        "| Appointment | date/DATE time/TIME                   | ic/S9534567A date/01-04-2025    |\n" +
                        "|             | DATE format: dd-mm-yyyy               | time/12:00                      |\n" +
                        "|             | TIME format: HH:mm                    |                                 |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| List        | listAppointments                      | listAppointments                |\n" +
                        "| Appointment |                                       |                                 |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Find        | findAppointment n/NAME          OR    | findAppointment n/John Doe      |\n" +
                        "| Appointment | findAppointment ic/NRIC         OR    | findAppointment ic/S1234567A    |\n" +
                        "|             | findAppointment date/DATE       OR    | findAppointment date/01-04-2025 |\n" +
                        "|             | findAppointment time/TIME       OR    | findAppointment time/12:00      |\n" +
                        "|             | DATE format: dd-mm-yyyy               |                                 |\n" +
                        "|             | TIME format: HH:mm                    |                                 |\n" +
                        "+-------------+---------------------------------------+---------------------------------+\n" +
                        "| Delete      | deleteAppointment ic/NRIC             | deleteAppointment ic/S9534567A  |\n" +
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
                        "| Save        | save (automatic)                      |                                 |\n" +
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
    void addCommand_validPatientDetails_addedSuccessfully() throws IOException {
        command.add("add n/James-Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13-12-1995 " +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Diabetes", records);
        assertEquals("Patient James-Ho with NRIC S9534567A added.".trim(), outputStreamCaptor.toString().trim());
    }

    //@@author coraleaf0602
    @Test
    void deleteCommand_existingPatient_deletesSuccessfully() throws IOException {
        command.add("add n/James-Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13-12-1995 " +
                "v/21-10-2024 15:48 al/Pollen s/Female mh/Diabetes", records);
        command.delete("S9534567A", records);
        String expectedOutput = "Patient James-Ho with NRIC S9534567A added.\n" +
                "Patient James-Ho, S9534567A, has been deleted.";
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim().replace(System.lineSeparator(),
                "\n"));
    }

    //@@author coraleaf0602
    @Test
    void listCommand_onePatientInRecord_outputsPatientDetails() throws IOException {
        command.add("add n/James-Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13-12-1995 " +
                "v/21-10-2024 15:48 al/Pollen s/Female mh/Diabetes", records);
        command.list(records);
        assertEquals("Patient James-Ho with NRIC S9534567A added.\n" +
                        "Name: James-Ho, NRIC: S9534567A, Phone: 91234567, Home Address: NUS-PGPR, DOB: 13-12-1995, " +
                        "Allergies: [Pollen], Sex: Female, Medical Histories: [Diabetes]\n" +
                        "    Visit Date: 21-10-2024 15:48, Diagnosis: [Asthma], Medications: [Albuterol]",
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author coraleaf0602
    @Test
    void findCommand_nameDoesNotExist_returnsNotFound() {
        command.find("n/james", records);
        assertEquals("No matching patients found.", outputStreamCaptor.toString().trim());
    }

    //@@author coraleaf0602
    @Test
    void findCommand_nricDoesNotExist_returnsNotFound() {
        command.find("ic/S1234567Z", records);
        assertEquals("No matching patients found.", outputStreamCaptor.toString().trim());
    }

    //@@author coraleaf0602
    @Test
    void findCommand_phoneNumberDoesNotExist_returnsNotFound() {
        command.find("p/82529790", records);
        assertEquals("No matching patients found.", outputStreamCaptor.toString().trim());
    }

    //@@author coraleaf0602
    @Test
    void findCommand_homeAddressDoesNotExist_returnsNotFound() {
        command.find("ha/NUS Utown", records);
        assertEquals("No matching patients found.", outputStreamCaptor.toString().trim());
    }

    //@@author coraleaf0602
    @Test
    void findCommand_dateOfBirthDoesNotExist_returnsNotFound() {
        command.find("dob/13122005", records);
        assertEquals("No matching patients found.", outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testFindName() throws IOException {
        command.add("add n/James-Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13-12-1995 " +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Diabetes", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13-12-1995" +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Chronic Migraine", records);
        command.find("n/james", records);
        assertEquals("Patient James-Ho with NRIC S9534567A added." + System.lineSeparator() +
                "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                "Matching patients:" + System.lineSeparator() +
                "Name: James-Ho, NRIC: S9534567A, Phone: 91234567, Address: NUS-PGPR, DOB: 13/12/1995, " +
                "Allergy: [Pollen], Sex: Male, Medical History: [Diabetes]", outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testFindIc() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13-12-1995 " +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Diabetes", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13-12-1995" +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Chronic Migraine", records);
        command.find("ic/S1234567Z", records);
        assertEquals("Patient James Ho with NRIC S9534567A added." + System.lineSeparator() +
                "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                "Matching patients:" + System.lineSeparator() +
                "Name: John Doe, NRIC: S1234567Z, Phone: 97654321, " +
                "Address: Hougang Green, DOB: 13/12/1995, Allergy: [Pollen], " +
                "Sex: Male, Medical History: [Chronic Migraine]", outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testFindPhoneNumber() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13-12-1995 " +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Diabetes", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13-12-1995" +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Chronic Migraine", records);
        command.find("p/91234567", records);
        assertEquals("Patient James Ho with NRIC S9534567A added." + System.lineSeparator() +
                        "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                        "Matching patients:" + System.lineSeparator() +
                        "Name: James Ho, NRIC: S9534567A, Phone: 91234567, Address: NUS-PGPR, " +
                        "DOB: 13/12/1995, Allergy: [Pollen], Sex: Male, Medical History: [Diabetes]",
                outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testFindHomeAddress() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13-12-1995 " +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Diabetes", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13-12-1995" +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Chronic Migraine", records);
        command.find("ha/NUS PGPR", records);
        assertEquals("Patient James Ho with NRIC S9534567A added." + System.lineSeparator() +
                        "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                        "Matching patients:" + System.lineSeparator() +
                        "Name: James Ho, NRIC: S9534567A, Phone: 91234567, Address: NUS-PGPR, " +
                        "DOB: 13/12/1995, Allergy: [Pollen], Sex: Male, Medical History: [Diabetes]",
                outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testFindDateOfBirth() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13-12-1995 " +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Diabetes", records);
        command.add("add n/John Doe ic/S1234567Z p/97654321 d/Fever m/Panadol ha/Hougang Green dob/13-12-1995" +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Chronic Migraine", records);
        command.find("dob/13-12-1995", records);
        assertEquals("Patient James Ho with NRIC S9534567A added." + System.lineSeparator() +
                        "Patient John Doe with NRIC S1234567Z added." + System.lineSeparator() +
                        "Matching patients:" + System.lineSeparator() +
                        "Name: James Ho, NRIC: S9534567A, Phone: 91234567, Address: NUS-PGPR, " +
                        "DOB: 13/12/1995, Allergy: [Pollen], Sex: Male, " +
                        "Medical History: [Diabetes]" + System.lineSeparator() +
                        "Name: John Doe, NRIC: S1234567Z, Phone: 97654321, " +
                        "Address: Hougang Green, DOB: 13/12/1995, Allergy: [Pollen], " +
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
    void testAddVisitNonExistentPatient() throws IOException {
        command.addVisit("addVisit ic/S1234567A v/21-10-2024 15:48 d/Fever m/Paracetamol", records);

        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("No patient found with NRIC: S1234567A"));
    }

    //@@author kaboomzxc
    @Test
    void testAddVisit_missingNRIC_expectAssertionError() throws IOException {
        String input = "addVisit v/21-10-2024 15:48 d/Fever m/Paracetamol";
        assertThrows(AssertionError.class, () -> {
            command.addVisit(input, records);
        });
    }

    //@@author kaboomzxc
    @Test
    void testAddVisitMissingVisitDate() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 v/01-10-2024 15:30", records);
        outputStreamCaptor.reset();
        assertThrows(AssertionError.class, () -> {
            command.addVisit("addVisit ic/S1234567A d/Fever m/Paracetamol", records);
        });
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
    void testAddVisitInvalidDateFormat() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 v/01-10-2024 15:30", records);
        outputStreamCaptor.reset();

        command.addVisit("addVisit ic/S1234567A v/2024-10-21 15:48 d/Fever", records);

        assertEquals("Invalid date format. Please use dd-MM-yyyy HH:mm format (e.g., 21-10-2024 15:48)",
                outputStreamCaptor.toString().trim());
    }

    //@@author kaboomzxc
    @Test
    void testAddVisitEmptyOptionalFields() throws IOException {
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
  
    //@@author yentheng0110
    @Test
    void listCommand_emptyList_noPatientFoundMessage() {
        command.list(records);
        String expectedOutput = "No patients found.";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@author yentheng0110
    @Test
    void listCommand_onePatientRecordwithNoAllergy_expectOnePatientRecordGetsPrinted() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/01-01-1995 " +
                "v/21-10-2024 15:48 s/Male mh/Diabetes", records);
        command.list(records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nName: James Ho, NRIC: S9534567A, " +
                "Phone: 91234567, Home Address: NUS-PGPR, DOB: 01-01-1995, Allergies: [], Sex: Male, " +
                "Medical Histories: [Diabetes]\n    Visit Date: 21-10-2024 15:48, Diagnosis: [Asthma], " +
                "Medications: [Albuterol]";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@author yentheng0110
    @Test
    void listCommand_twoPatientRecord_recordSizeIsTwo() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/01-01-1995 " +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Diabetes", records);
        command.add("add n/Patricia Chan ic/S9087906B p/80796890 d/Gastric m/Gaviscon ha/Farrer Road " +
                "dob/09-08-1990 v/06-11-2024 19:00 al/Peanuts s/Female mh/Hypertension, Diabetes", records);
        command.list(records);
        assertEquals(2, records.getPatients().size());
    }

    //@@author yentheng0110
    @Test
    void deleteCommand_deleteNonExistingPatient_recordsSizeRemainsTheSame() throws IOException {
        command.add("add n/Jack Wong ic/S9765432T p/87658976 d/Gastric m/Gaviscon " +
                "v/01-10-2024 17:30 ha/Bukit Gombak dob/06-07-1997", records);
        command.delete("S9587690H", records);
        assertEquals(1, records.getPatients().size());
    }

    //@@author yentheng0110
    @Test
    void findCommand_invalidSearchFormat_expectAnErrorMessageWithGuidance() throws IOException {
        command.add("add n/Jack Wong ic/S9765432T p/87658976 d/Gastric m/Gaviscon " +
                "v/01-10-2024 17:30 ha/Bukit Gombak dob/06-07-1997", records);
        command.find("Jack", records);
        String expectedOutput = "Patient Jack Wong with NRIC S9765432T added.\nInvalid search parameters. " +
                "Please use the format: find [n/NAME] [ic/NRIC] [p/PHONE] " +
                "[ha/ADDRESS] [dob/DOB] [al/ALLERGY] [s/SEX] [mh/MEDICAL_HISTORY]";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    public void addCommand_inputWithoutName_expectAssertionError() {
        String input = "add ic/S1234567A v/15-11-2024 14:30";
        assertThrows(AssertionError.class, () -> {
            command.add(input, records);
        });
    }

    //@@author yentheng0110
    @Test
    void addCommand_inputWithoutNRIC_expectAssertionError() {
        assertThrows(AssertionError.class, () -> {
            command.add("add n/Jane Tan", records);
        });
    }

    //@@author yentheng0110
    @Test
    void addCommand_inputWithoutVisitDate_expectAssertionError() {
        assertThrows(AssertionError.class, () -> {
            command.add("add n/Jane Tan ic/S9087689B p/90876543", records);
        });
    }

    //@@author yentheng0110
    @Test
    void addCommand_visitDateWithWrongFormat_expectException() {
        String input = "add n/Jane Tan ic/S9087689B p/90876543 v/09112024 6:00";
        assertThrows(IllegalArgumentException.class, () -> {
            command.add(input, records);
        });
    }

    //@@author yentheng0110
    @Test
    void addCommand_addPatientDetailsWithoutPhoneNumber_addedSuccessfully() throws IOException {
        String input = "add n/James Ho ic/S9534567A v/06-11-2024 10:00 d/Asthma m/Albuterol ha/NUS-PGPR dob/01-01-1995";
        command.add(input, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void addCommand_addPatientDetailsWithoutDiagnosis_addedSuccessfully() throws IOException {
        String input = "add n/James Ho ic/S9534567A v/06-11-2024 10:00 " +
                "p/90879089 m/Albuterol ha/NUS-PGPR dob/01-01-1995";
        command.add(input, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void addCommand_addPatientDetailsWithoutMedication_addedSuccessfully() throws IOException {
        String input = "add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR dob/01-01-1995";
        command.add(input, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void editCommand_validFormatInputtedToEditName_editSuccessfully() throws IOException {
        command.add("add n/Jame Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR", records);
        String input = "edit ic/S9534567A /to n/James Ho";
        command.edit(input, records);
        String expectedOutput = "Patient Jame Ho with NRIC S9534567A added.\nPatient record updated successfully.\n" +
                "Updated patient details:\nName: James Ho, NRIC: S9534567A, Phone: 90879089, Address: NUS-PGPR, " +
                "DOB: , Allergy: [], Sex: , Medical History: []";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void editCommand_validFormatInputtedToEditNRIC_editSuccessfully() throws IOException {
        command.add("add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR", records);
        String input = "edit ic/S9534567A /to newic/S9534568A";
        command.edit(input, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nPatient record updated successfully.\n" +
                "Updated patient details:\nName: James Ho, NRIC: S9534568A, Phone: 90879089, Address: NUS-PGPR, " +
                "DOB: , Allergy: [], Sex: , Medical History: []";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void editCommand_validFormatInputtedToEditSex_editSuccessfully() throws IOException {
        command.add("add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR s/Female",
                records);
        String input = "edit ic/S9534567A /to s/Male";
        command.edit(input, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nPatient record updated successfully.\n" +
                "Updated patient details:\nName: James Ho, NRIC: S9534567A, Phone: 90879089, Address: NUS-PGPR, " +
                "DOB: , Allergy: [], Sex: Male, Medical History: []";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void editCommand_validFormatInputtedToEditDOB_editSuccessfully() throws IOException {
        String addInput = "add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 " +
                "d/Asthma ha/NUS-PGPR dob/09-08-1995";
        command.add(addInput, records);
        String editInput = "edit ic/S9534567A /to dob/08-08-1995";
        command.edit(editInput, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nPatient record updated successfully.\n" +
                "Updated patient details:\nName: James Ho, NRIC: S9534567A, Phone: 90879089, Address: NUS-PGPR, " +
                "DOB: 08/08/1995, Allergy: [], Sex: , Medical History: []";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void editCommand_validFormatInputtedToEditPhoneNumber_editSuccessfully() throws IOException {
        command.add("add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR", records);
        String input = "edit ic/S9534567A /to p/90876543";
        command.edit(input, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nPatient record updated successfully.\n" +
                "Updated patient details:\nName: James Ho, NRIC: S9534567A, Phone: 90876543, Address: NUS-PGPR, " +
                "DOB: , Allergy: [], Sex: , Medical History: []";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void editCommand_validFormatInputtedToEditHomeAddress_editSuccessfully() throws IOException {
        String addInput = "add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 " +
                "d/Asthma ha/NUS-PGPR dob/09-08-1995";
        command.add(addInput, records);
        String editInput = "edit ic/S9534567A /to ha/Bukit Batok";
        command.edit(editInput, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nPatient record updated successfully.\n" +
                "Updated patient details:\nName: James Ho, NRIC: S9534567A, Phone: 90879089, Address: Bukit Batok, " +
                "DOB: 09/08/1995, Allergy: [], Sex: , Medical History: []";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void editCommand_validFormatInputtedToEditAllergies_editSuccessfully() throws IOException {
        String addInput = "add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR al/Pollen";
        command.add(addInput, records);
        String editInput = "edit ic/S9534567A /to al/Pollen, Peanuts";
        command.edit(editInput, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nPatient record updated successfully.\n" +
                "Updated patient details:\nName: James Ho, NRIC: S9534567A, Phone: 90879089, Address: NUS-PGPR, " +
                "DOB: , Allergy: [Pollen, Peanuts], Sex: , Medical History: []";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    // @@ author G13nd0n
    @Test
    void add_onePatient_onePatientInRecord() throws IOException{
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000" +
                "v/01-11-2024 12:29", records);
        assertEquals(1, records.getPatients().size());
    }

    // @@author G13nd0n
    @Test
    void delete_onePatient_twoPatientInRecord() throws IOException{
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000" +
                "v/06-11-2024 12:29", records);
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
                "v/03-11-2024 12:29", records);
        command.add("add n/Will Smith ic/S7654321B p/91234567 d/AIDS m/Paracetamol ha/CAPT dob/18-06-2003" +
                "v/15-10-2024 11:53", records);
        command.list(records);

        String expectedOutput = "Patient John Doe with NRIC S1234567A added.\n" +
                "Patient Will Smith with NRIC S7654321B added.\n" +
                "Name: John Doe, NRIC: S1234567A, Phone: 98765432, Home Address: RC4, " +
                "DOB: 13-04-2000, Allergies: [], Sex: , Medical Histories: []\n" +
                "    Visit Date: 03-11-2024 12:29, Diagnosis: [COVID-19], Medications: [Paracetamol]\n" +
                "Name: Will Smith, NRIC: S7654321B, Phone: 91234567, Home Address: CAPT, " +
                "DOB: 18-06-2003, Allergies: [], Sex: , Medical Histories: []\n" +
                "    Visit Date: 15-10-2024 11:53, Diagnosis: [AIDS], Medications: [Paracetamol]";

        String normalizedExpected = expectedOutput.replaceAll("\\s+\n", "\n");
        String normalizedActual = outputStreamCaptor.toString().trim().replaceAll("\\s+\n", "\n");
        assertEquals(normalizedExpected, normalizedActual);
    }

    // @@author G13nd0n
    @Test
    void testFind_nric_oneOutput() throws IOException{
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000" +
                "v/02-11-2024 12:29", records);
        command.add("add n/Will Smith ic/S7654321B p/91234567 d/AIDS m/Paracetamol ha/CAPT dob/18-06-2003" +
                "v/21-10-2024 15:30 al/peanuts s/male mh/diabetes", records);
        command.find("ic/S7654321B", records);
        String expectedOutput = "Patient John Doe with NRIC S1234567A added.\nPatient Will Smith with NRIC S7654321B " +
                "added.\n" + "Matching patients:\nName: Will Smith, NRIC: S7654321B, Phone: 91234567, Address: CAPT, " +
                "DOB: 18/06/2003, Allergy: [peanuts], Sex: male, Medical History: [diabetes]";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void editCommand_validFormatInputtedToEditMedicalHistories_editSuccessfully() throws IOException {
        String addInput = "add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR mh/Diabetes";
        command.add(addInput, records);
        String editInput = "edit ic/S9534567A /to mh/Diabetes, Hypertension";
        command.edit(editInput, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nPatient record updated successfully.\n" +
                "Updated patient details:\nName: James Ho, NRIC: S9534567A, Phone: 90879089, Address: NUS-PGPR, " +
                "DOB: , Allergy: [], Sex: , Medical History: [Diabetes, Hypertension]";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author G13nd0n
    @Test
    void testFind_name_multipleOutputs() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000" +
                "v/01-11-2024 12:29", records);
        command.add("add n/Will Smith ic/S7654321B p/91234567 d/AIDS m/Paracetamol ha/CAPT dob/18-06-2003" +
                "v/21-10-2024 15:30", records);
        command.add("add n/John Smith ic/S2468024A p/87654321 d/Diabetes m/Insulin ha/CAPT dob/13-04-2002" +
                "v/15-10-2024 10:25", records);
        String expectedOutput = "Patient John Doe with NRIC S1234567A added.\nPatient Will Smith with NRIC S7654321B " +
                "added.\n" + "Patient John Smith with NRIC S2468024A added.\n"+
                "Matching patients:\nName: Will Smith, NRIC: S7654321B, Phone: 91234567, " +
                "Address: CAPT, DOB: 18/06/2003, Allergy: [], Sex: , Medical History: []\n" +
                "Name: John Smith, NRIC: S2468024A, Phone: 87654321, Address: CAPT, " +
                "DOB: 13/04/2002, Allergy: [], Sex: , Medical History: []";
        command.find("n/Smith", records);
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void editCommand_invalidFormatInputtedWithoutTo_editFailsWithErrorMessage() throws IOException {
        command.add("add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR", records);
        String input = "edit ic/S9534567A p/90876543";
        command.edit(input, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nNo fields provided to update.";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }
  
    //@@author G13nd0n
    @Test
    void testFind_address_multipleOutputs() throws IOException {
        command.add("add n/John Doe ic/S1234567A p/98765432 d/COVID-19 m/Paracetamol ha/RC4 dob/13-04-2000" +
                "v/10-11-2024 12:29", records);
        command.add("add n/Will Smith ic/S7654321B p/91234567 d/AIDS m/Paracetamol ha/CAPT dob/18-06-2003" +
                "v/21-10-2024 15:30", records);
        command.add("add n/John Smith ic/S2468024A p/87654321 d/Diabetes m/Insulin ha/CAPT dob/13-04-2002" +
                "v/15-10-2024 10:25", records);
        String expectedOutput = "Patient John Doe with NRIC S1234567A added.\nPatient Will Smith with NRIC S7654321B " +
                "added.\n" + "Patient John Smith with NRIC S2468024A added.\n"+
                "Matching patients:\nName: Will Smith, NRIC: S7654321B, Phone: 91234567, " +
                "Address: CAPT, DOB: 18/06/2003, " +
                "Allergy: [], Sex: , Medical History: []\nName: John Smith, NRIC: S2468024A, " +
                "Phone: 87654321, Address: CAPT, " +
                "DOB: 13/04/2002, Allergy: [], Sex: , Medical History: []";
        command.find("ha/CAPT", records);
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void editCommand_inputWithoutNRIC_expectAssertionError() throws IOException {
        command.add("add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR", records);
        String input = "edit n/James Ho /to p/90876543";
        assertThrows(AssertionError.class, () -> {
            command.edit(input, records);
        });
    }

    //@@author yentheng0110
    @Test
    void editCommand_nricInputtedNotInRecord_noPatientFoundMessageGetsPrinted() throws IOException {
        String input = "edit ic/T0267890J /to p/90876543";
        String expectedOutput = "No patient found.";
        command.edit(input, records);
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }
  
    //@@author G13nd0n
    @Test
    void testAppointment_onePatient_onePatient() throws IOException {
        command.appointment("n/John Doe ic/S1234567A date/18-11-2024 time/18:00", appointmentRecord);
        String expectedOutput = "Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A has been added.";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void editVisitCommand_validIInputFormat_editVisitSuccessfully() throws IOException {
        command.add("add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR", records);
        String input = "editVisit ic/S9534567A v/06-11-2024 10:00 d/Asthma m/Panadol, Antibiotics";
        command.editVisit(input, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nVisit record updated successfully.\n" +
                "Updated visit details:\n06-11-2024 10:00, Diagnosis: [Asthma], Medications: [Panadol, Antibiotics]";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void editVisitCommand_inputWithoutNRIC_expectAssertionError() throws IOException {
        command.add("add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR", records);
        String input = "editVisit date/06-11-2024 10:00 d/Asthma m/Panadol, Antibiotics";
        assertThrows(AssertionError.class, () -> {
            command.editVisit(input, records);
        });
    }

    //@@author yentheng0110
    @Test
    void editVisitCommand_nricInputtedNotInRecord_noPatientFoundMessageGetsPrinted() throws IOException {
        String input = "editVisit ic/T0267890J date/06-11-2024 10:00 d/Asthma";
        command.editVisit(input, records);
        String expectedOutput = "No patient found with the given NRIC";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }
    /*
    //@@author yentheng0110
    @Test
    void editVisitCommand_inputWithoutVisitDate_expectAssertionError() throws IOException {
        command.add("add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR", records);
        String input = "editVisit ic/S9534567A d/Asthma m/Panadol, Antibiotics";
        assertThrows(DateTimeParseException.class, () -> {
            command.editVisit(input, records);
        });
    }
    */

    //@@author yentheng0110
    @Test
    void editVisitCommand_visitDateInputtedNotInRecord_noPatientFoundMessageGetsPrinted() throws IOException {
        command.add("add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR", records);
        String input = "editVisit ic/S9534567A v/01-11-2024 10:00 d/Asthma, Gastric";
        command.editVisit(input, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nNo visit found on the given date.";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void editVisitCommand_editVisitDateOnlyWithCorrectInputFormat_editVisitSuccessfully() throws IOException {
        command.add("add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 ha/NUS-PGPR", records);
        String input = "editVisit ic/S9534567A v/06-11-2024 10:00 newDate/07-11-2024 10:00";
        command.editVisit(input, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nVisit record updated successfully.\n" +
                "Updated visit details:\n07-11-2024 10:00, Diagnosis: [], Medications: []";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }


    //@@author G13nd0n
    @Test
    void testdeleteAppointment_onePatient_onePatient() throws IOException {
        command.appointment("n/John Doe ic/S1234567A date/18-11-2024 time/18:00", appointmentRecord);
        command.appointment("n/Helen Smith ic/S7654321A date/19-11-2024 time/18:00", appointmentRecord);
        command.deleteAppointment("ic/S1234567A date/18-11-2024 time/18:00", appointmentRecord);
        assertEquals(1, appointmentRecord.getAppointments().size());
    }

    //author G13nd0n
    @Test
    void testlistAppointment_noInput_multipleOutput() throws IOException {
        command.appointment("n/John Doe ic/S1234567A date/18-11-2024 time/18:00", appointmentRecord);
        command.appointment("n/Helen Smith ic/S7654321A date/19-11-2024 time/18:00", appointmentRecord);
        String expectedOutput = "Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A has been added.\n" +
                "Appointment on 19-11-2024 18:00 with Patient Helen Smith, S7654321A has been added.\n" +
                "Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.\n" + "Appointment on 19-11-2024 " +
                "18:00 with Patient Helen Smith, S7654321A.";
        command.listAppointments(appointmentRecord);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim().replace(System.lineSeparator(),
                "\n"));
    }

    //author G13nd0n
    @Test
    void testFindAppointment_name_oneOutput() throws IOException {
        command.appointment("n/John Doe ic/S1234567A date/18-11-2024 time/18:00", appointmentRecord);
        command.appointment("n/Helen Smith ic/S7654321A date/19-11-2024 time/18:00", appointmentRecord);
        String expectedOutput = "Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A has been added.\n" +
                "Appointment on 19-11-2024 18:00 with Patient Helen Smith, S7654321A has been added.\n" +
                "Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.";
        command.findAppointment("n/John Doe", appointmentRecord);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim().replace(System.lineSeparator(),
                "\n"));
    }

    //author G13nd0n
    @Test
    void testFindAppointment_nric_oneOutput() throws IOException {
        command.appointment("n/John Doe ic/S1234567A date/18-11-2024 time/18:00", appointmentRecord);
        command.appointment("n/Helen Smith ic/S7654321A date/19-11-2024 time/18:00", appointmentRecord);
        String expectedOutput = "Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A has been added.\n" +
                "Appointment on 19-11-2024 18:00 with Patient Helen Smith, S7654321A has been added.\n" +
                "Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.";
        command.findAppointment("ic/S1234567A", appointmentRecord);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim().replace(System.lineSeparator(),
                "\n"));
    }

    //author G13nd0n
    @Test
    void testFindAppointment_date_oneOutput() throws IOException {
        command.appointment("n/John Doe ic/S1234567A date/18-11-2024 time/18:00", appointmentRecord);
        command.appointment("n/Helen Smith ic/S7654321A date/19-11-2024 time/18:00", appointmentRecord);
        String expectedOutput = "Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A has been added.\n" +
                "Appointment on 19-11-2024 18:00 with Patient Helen Smith, S7654321A has been added.\n" +
                "Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.";
        command.findAppointment("date/18-11-2024", appointmentRecord);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim().replace(System.lineSeparator(),
                "\n"));
    }

    //author G13nd0n
    @Test
    void testFindAppointment_time_twoOutput() throws IOException {
        command.appointment("n/John Doe ic/S1234567A date/18-11-2024 time/18:00", appointmentRecord);
        command.appointment("n/Helen Smith ic/S7654321A date/19-11-2024 time/18:00", appointmentRecord);
        String expectedOutput = "Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A has been added.\n" +
                "Appointment on 19-11-2024 18:00 with Patient Helen Smith, S7654321A has been added.\n" +
                "Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.\n" + "Appointment on 19-11-2024 " +
                "18:00 with Patient Helen Smith, S7654321A.";
        command.findAppointment("time/18:00", appointmentRecord);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim().replace(System.lineSeparator(),
                "\n"));
    }
}
