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
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void addCommand_validPatientDetails_addedSuccessfully() throws IOException {
        command.add("add n/James-Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/13121995 " +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Diabetes", records);
        assertEquals("Patient James-Ho with NRIC S9534567A added.".trim(), outputStreamCaptor.toString().trim());
    }

    //@@author coraleaf0602
    @Test
    void deleteCommand_existingPatient_deletesSuccessfully() throws IOException {
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
    void listCommand_onePatientInRecord_outputsPatientDetails() throws IOException {
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
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/01011995 " +
                "v/21-10-2024 15:48 s/Male mh/Diabetes", records);
        command.list(records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nName: James Ho, NRIC: S9534567A, " +
                "Phone: 91234567, Home Address: NUS-PGPR, DOB: 01011995, Allergies: [], Sex: Male, " +
                "Medical Histories: [Diabetes]\n    Visit Date: 21-10-2024 15:48, Diagnosis: [Asthma], " +
                "Medications: [Albuterol]";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@author yentheng0110
    @Test
    void listCommand_twoPatientRecord_recordSizeIsTwo() throws IOException {
        command.add("add n/James Ho ic/S9534567A p/91234567 d/Asthma m/Albuterol ha/NUS-PGPR dob/01011995 " +
                "v/21-10-2024 15:48 al/Pollen s/Male mh/Diabetes", records);
        command.add("add n/Patricia Chan ic/S9087906B p/80796890 d/Gastric m/Gaviscon ha/Farrer Road " +
                "dob/09081990 v/06-11-2024 19:00 al/Peanuts s/Female mh/Hypertension, Diabetes", records);
        command.list(records);
        assertEquals(2, records.getPatients().size());
    }

    //@@author yentheng0110
    @Test
    void deleteCommand_deleteNonExistingPatient_recordsSizeRemainsTheSame() throws IOException {
        command.add("add n/Jack Wong ic/S9765432T p/87658976 d/Gastric m/Gaviscon " +
                "v/01-10-2024 17:30 ha/Bukit Gombak dob/06071997", records);
        command.delete("S9587690H", records);
        assertEquals(1, records.getPatients().size());
    }

    //@@author yentheng0110
    @Test
    void findCommand_invalidSearchFormat_expectAnErrorMessageWithGuidance() throws IOException {
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
        String input = "add n/James Ho ic/S9534567A v/06-11-2024 10:00 d/Asthma m/Albuterol ha/NUS-PGPR dob/01011995";
        command.add(input, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void addCommand_addPatientDetailsWithoutDiagnosis_addedSuccessfully() throws IOException {
        String input = "add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 m/Albuterol ha/NUS-PGPR dob/01011995";
        command.add(input, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void addCommand_addPatientDetailsWithoutMedication_addedSuccessfully() throws IOException {
        String input = "add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR dob/01011995";
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
        String addInput = "add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR dob/09081995";
        command.add(addInput, records);
        String editInput = "edit ic/S9534567A /to dob/08081995";
        command.edit(editInput, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nPatient record updated successfully.\n" +
                "Updated patient details:\nName: James Ho, NRIC: S9534567A, Phone: 90879089, Address: NUS-PGPR, " +
                "DOB: 08081995, Allergy: [], Sex: , Medical History: []";
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
        String addInput = "add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR dob/09081995";
        command.add(addInput, records);
        String editInput = "edit ic/S9534567A /to ha/Bukit Batok";
        command.edit(editInput, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nPatient record updated successfully.\n" +
                "Updated patient details:\nName: James Ho, NRIC: S9534567A, Phone: 90879089, Address: Bukit Batok, " +
                "DOB: 09081995, Allergy: [], Sex: , Medical History: []";
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

    //@@author yentheng0110
    @Test
    void editVisitCommand_validIInputFormat_editVisitSuccessfully() throws IOException {
        command.add("add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR", records);
        String input = "editVisit ic/S9534567A date/06-11-2024 10:00 d/Asthma m/Panadol, Antibiotics";
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
        String expectedOutput = "No patient found with the given NRIC.";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void editVisitCommand_inputWithoutVisitDate_expectAssertionError() throws IOException {
        command.add("add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR", records);
        String input = "editVisit ic/S9534567A d/Asthma m/Panadol, Antibiotics";
        assertThrows(AssertionError.class, () -> {
            command.editVisit(input, records);
        });
    }

    //@@author yentheng0110
    @Test
    void editVisitCommand_visitDateInputtedNotInRecord_noPatientFoundMessageGetsPrinted() throws IOException {
        command.add("add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 d/Asthma ha/NUS-PGPR", records);
        String input = "editVisit ic/S9534567A date/01-11-2024 10:00 d/Asthma, Gastric";
        command.editVisit(input, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nNo visit found on the given date.";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }

    //@@author yentheng0110
    @Test
    void editVisitCommand_editVisitDateOnlyWithCorrectInputFormat_editVisitSuccessfully() throws IOException {
        command.add("add n/James Ho ic/S9534567A v/06-11-2024 10:00 p/90879089 ha/NUS-PGPR", records);
        String input = "editVisit ic/S9534567A date/06-11-2024 10:00 newDate/07-11-2024 10:00";
        command.editVisit(input, records);
        String expectedOutput = "Patient James Ho with NRIC S9534567A added.\nVisit record updated successfully.\n" +
                "Updated visit details:\n07-11-2024 10:00, Diagnosis: [], Medications: []";
        assertEquals(expectedOutput,
                outputStreamCaptor.toString().trim().replace(System.lineSeparator(), "\n"));
    }
}
