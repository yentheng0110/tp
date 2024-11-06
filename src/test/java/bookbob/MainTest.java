package bookbob;

import bookbob.entity.Records;
import bookbob.entity.AppointmentRecord;
import bookbob.functions.CommandHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;
    private Records records;
    private AppointmentRecord appointmentRecord;
    private CommandHandler commandHandler;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final String TODAY = LocalDate.now().format(formatter);
    private final String TOMORROW = LocalDate.now().plusDays(1).format(formatter);
    private final String YESTERDAY = LocalDate.now().minusDays(1).format(formatter);


    //@@author kaboomzxc
    @BeforeEach
    void setUp() throws IOException {
        System.setOut(new PrintStream(outputStreamCaptor));
        records = new Records();
        appointmentRecord = new AppointmentRecord();
        commandHandler = new CommandHandler();
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    //@@author kaboomzxc
    // Test different command combinations
    @Test
    void testMultipleCommandSequences() {
        String input = "add n/John Doe ic/S9876543A p/91234567 d/Fever m/Paracetamol v/01-11-2024 14:30\n" +
                "add n/Jane Smith ic/S8765432B p/92345678 d/Cough m/Cough Syrup v/01-11-2024 15:30\n" +
                "list\n" +
                "findVisit S9876543A\n" +
                "findDiagnosis Fever\n" +
                "findMedication Paracetamol\n" +
                "appointment n/John Doe ic/S9876543A date/02-11-2024 time/14:30\n" +
                "listAppointments\n" +
                "exit\n";
        provideInput(input);

        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Patient John Doe with NRIC S9876543A added"));
            assertTrue(output.contains("Patient Jane Smith with NRIC S8765432B added"));
            assertTrue(output.contains("Appointment on 02-11-2024 14:30"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    // Test all error conditions in addVisit
    @Test
    void testAddVisitErrors() {
        String input = "addVisit\n" + // Missing all parameters
                "addVisit ic/S9876543A\n" + // Missing visit date
                "addVisit v/01-11-2024 14:30\n" + // Missing NRIC
                "addVisit ic/S9876543A v/invalid-date\n" + // Invalid date format
                "addVisit ic/INVALID v/01-11-2024 14:30\n" + // Invalid NRIC format
                "exit\n";
        provideInput(input);

        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Please provide the patient's NRIC"));
            assertTrue(output.contains("Please provide the visit date"));
            assertTrue(output.contains("Invalid date format"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    // Test all error conditions in editVisit
    @Test
    void testEditVisitErrors() {
        String input = "add n/John Doe ic/S9876543A v/01-11-2024 14:30\n" +
                "editVisit\n" + // Missing all parameters
                "editVisit ic/S9876543A\n" + // Missing date
                "editVisit date/01-11-2024 14:30\n" + // Missing NRIC
                "editVisit ic/S9876543A date/invalid-date\n" + // Invalid date
                "editVisit ic/INVALID date/01-11-2024 14:30\n" + // Invalid NRIC
                "exit\n";
        provideInput(input);

        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Please provide the patient's NRIC"));
            assertTrue(output.contains("Please provide a valid visit date"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    // Test all error conditions in appointment
    @Test
    void testAppointmentErrors() {
        String input = "appointment\n" + // Missing all parameters
                "appointment n/John Doe\n" + // Missing NRIC
                "appointment n/John Doe ic/S9876543A\n" + // Missing date and time
                "appointment n/John Doe ic/S9876543A date/01-11-2024\n" + // Missing time
                "appointment n/John Doe ic/S9876543A date/invalid time/14:30\n" + // Invalid date
                "appointment n/John Doe ic/S9876543A date/01-11-2024 time/invalid\n" + // Invalid time
                "exit\n";
        provideInput(input);

        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Please provide the patient's name"));
            assertTrue(output.contains("Please provide the patient's NRIC"));
            assertTrue(output.contains("Please provide the date"));
            assertTrue(output.contains("Please provide the time"));
            assertTrue(output.contains("Error in adding appointment"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    // Test appointment time slot validation
    @Test
    void testAppointmentTimeSlotValidation() {
        String input = "appointment n/John Doe ic/S9876543A date/01-11-2024 time/14:30\n" +
                "appointment n/Jane Smith ic/S8765432B date/01-11-2024 time/14:30\n" + // Same time slot
                "appointment n/Bob Wilson ic/S7654321C date/01-11-2024 time/14:45\n" + // Within 30 min
                "exit\n";
        provideInput(input);

        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("There is already an appointment at the given timeslot"));
            assertTrue(output.contains("The next available timeslot is:"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    // Test find command with various criteria
    @Test
    void testFindWithMultipleCriteria() {
        String input = "add n/John Doe ic/S9876543A p/91234567 d/Fever m/Paracetamol " +
                "ha/123 Main St dob/01-01-1990 v/01-11-2024 14:30 al/Peanuts s/Male mh/Asthma\n" +
                "find n/John\n" +
                "find ic/S987\n" +
                "find p/91234567\n" +
                "find ha/Main\n" +
                "find dob/01-01-1990\n" +
                "find al/Peanuts\n" +
                "find s/Male\n" +
                "find mh/Asthma\n" +
                "exit\n";
        provideInput(input);

        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Matching patients"));
            assertEquals(8, output.split("Matching patients").length - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    // Test deleteAppointment with various conditions
    @Test
    void testDeleteAppointmentScenarios() {
        String input = "appointment n/John Doe ic/S9876543A date/01-11-2024 time/14:30\n" +
                "deleteAppointment ic/S9876543A date/01-11-2024 time/14:30\n" + // Valid deletion
                "deleteAppointment ic/INVALID date/01-11-2024 time/14:30\n" + // Invalid NRIC
                "deleteAppointment ic/S9876543A date/invalid time/14:30\n" + // Invalid date
                "deleteAppointment ic/S9876543A date/01-11-2024 time/invalid\n" + // Invalid time
                "exit\n";
        provideInput(input);

        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("has been deleted"));
            assertTrue(output.contains("Error in deleting appointment"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    // Test input validation edge cases
    @Test
    void testInputValidationEdgeCases() {
        String input = "add n/   ic/S9876543A v/01-11-2024 14:30\n" + // Empty name
                "add n/John Doe ic/   v/01-11-2024 14:30\n" + // Empty NRIC
                "add n/John Doe ic/S9876543A v/   \n" + // Empty visit date
                "add n/John Doe ic/S9876543A v/01-11-2024 14:30 p/   \n" + // Empty phone
                "add n/John Doe ic/S9876543A v/01-11-2024 14:30 ha/   \n" + // Empty address
                "exit\n";
        provideInput(input);

        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Please provide"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    // Test concurrent operations
    @Test
    void testConcurrentOperations() {
        String input = "add n/John Doe ic/S9876543A v/01-11-2024 14:30\n" +
                "appointment n/John Doe ic/S9876543A date/01-11-2024 time/14:30\n" +
                "list\n" +
                "listAppointments\n" +
                "findVisit S9876543A\n" +
                "findAppointment n/John\n" +
                "exit\n";
        provideInput(input);

        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Patient John Doe"));
            assertTrue(output.contains("Appointment on 01-11-2024 14:30"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    //@@author kaboomzxc
    @Test
    void testAddCommandAllFields() {
        String input = "add n/John Doe ic/S9876543A p/91234567 d/Fever,Cough m/Paracetamol,Syrup " +
                "ha/123 Main St dob/01-01-1990 v/01-11-2024 14:30 al/Peanuts s/Male mh/Asthma\n" +
                "add n/Jane Smith ic/S8765432B p/92345678 v/01-11-2024 15:30\n" + // Minimal fields
                "add n/Bob Wilson ic/S7654321C v/01-11-2024 16:30 d/Flu\n" + // With diagnosis
                "add n/Alice Brown ic/S6543210D v/01-11-2024 17:30 m/Ibuprofen\n" + // With medication
                "list\n" +
                "exit\n";
        provideInput(input);
        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Patient John Doe with NRIC S9876543A added"));
            assertTrue(output.contains("Fever, Cough"));
            assertTrue(output.contains("Patient Jane Smith with NRIC S8765432B added"));
            assertTrue(output.contains("Patient Bob Wilson with NRIC S7654321C added"));
            assertTrue(output.contains("Patient Alice Brown with NRIC S6543210D added"));
            assertTrue(output.contains("Ibuprofen"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    @Test
    void testEditCommandAllFieldVariations() {
        String input = "add n/John Doe ic/S9876543A v/01-11-2024 14:30 p/91234567\n" +
                "edit ic/S9876543A /to n/James Doe\n" +
                "edit ic/S9876543A /to newic/S9876543B\n" +
                "edit ic/S9876543B /to p/98765432\n" +
                "edit ic/S9876543B /to ha/New Address\n" +
                "edit ic/S9876543B /to dob/01-01-1991\n" +
                "edit ic/S9876543B /to al/Peanuts,Dust\n" +
                "edit ic/S9876543B /to s/Male\n" +
                "edit ic/S9876543B /to mh/Asthma,Diabetes\n" +
                "list\n" +
                "exit\n";
        provideInput(input);
        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Patient record updated successfully"));
            assertTrue(output.contains("James Doe"));
            assertTrue(output.contains("S9876543B"));
            assertTrue(output.contains("98765432"));
            assertTrue(output.contains("New Address"));
            assertTrue(output.contains("01-01-1991"));
            assertTrue(output.contains("Peanuts, Dust"));
            assertTrue(output.contains("Male"));
            assertTrue(output.contains("Asthma, Diabetes"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    @Test
    void testVisitOperationsComplexScenarios() {
        String input = "add n/John Doe ic/S9876543A v/01-11-2024 14:30\n" +
                "addVisit ic/S9876543A v/02-11-2024 14:30 d/Fever m/Paracetamol\n" +
                "addVisit ic/S9876543A v/03-11-2024 14:30 d/Cold m/Syrup\n" +
                "editVisit ic/S9876543A date/02-11-2024 14:30 d/Severe Fever\n" +
                "editVisit ic/S9876543A date/03-11-2024 14:30 m/Strong Syrup\n" +
                "findVisit S9876543A\n" +
                "findDiagnosis Severe Fever\n" +
                "findMedication Strong Syrup\n" +
                "exit\n";
        provideInput(input);
        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Visit added successfully"));
            assertTrue(output.contains("Visit record updated successfully"));
            assertTrue(output.contains("Severe Fever"));
            assertTrue(output.contains("Strong Syrup"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    @Test
    void testDateTimeValidations() {
        String input = "add n/John Doe ic/S9876543A v/32-13-2024 14:30\n" + // Invalid date
                "add n/John Doe ic/S9876543A v/01-11-2024 25:00\n" + // Invalid hour
                "add n/John Doe ic/S9876543A v/01-11-2024 14:60\n" + // Invalid minute
                "appointment n/John Doe ic/S9876543A date/32-13-2024 time/14:30\n" + // Invalid date
                "appointment n/John Doe ic/S9876543A date/01-11-2024 time/25:00\n" + // Invalid hour
                "editVisit ic/S9876543A date/32-13-2024 14:30\n" + // Invalid date
                "exit\n";
        provideInput(input);
        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Invalid") || output.contains("Error"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    @Test
    void testSearchOperationsAllCriteria() {
        String input = "add n/John Doe ic/S9876543A p/91234567 d/Fever m/Paracetamol " +
                "ha/123 Main St dob/01-01-1990 v/01-11-2024 14:30 al/Peanuts s/Male mh/Asthma\n" +
                "find n/John\n" +
                "find ic/S987\n" +
                "find p/91234567\n" +
                "find d/Fever\n" +
                "find m/Paracetamol\n" +
                "find ha/Main\n" +
                "find dob/01-01-1990\n" +
                "find al/Peanuts\n" +
                "find s/Male\n" +
                "find mh/Asthma\n" +
                "exit\n";
        provideInput(input);
        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertEquals(10, output.split("Matching patients").length - 1);
            assertTrue(output.contains("John Doe"));
            assertTrue(output.contains("S9876543A"));
            assertTrue(output.contains("91234567"));
            assertTrue(output.contains("Main St"));
            assertTrue(output.contains("Peanuts"));
            assertTrue(output.contains("Male"));
            assertTrue(output.contains("Asthma"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    @Test
    void testDeleteOperationsComplexScenarios() {
        String input = "add n/John Doe ic/S9876543A v/01-11-2024 14:30\n" +
                "addVisit ic/S9876543A v/02-11-2024 14:30 d/Fever m/Paracetamol\n" +
                "appointment n/John Doe ic/S9876543A date/03-11-2024 time/14:30\n" +
                "add n/Jane Smith ic/S8765432B v/01-11-2024 15:30\n" +
                "delete S9876543A\n" + // Delete first patient
                "list\n" +
                "delete S8765432B\n" + // Delete second patient
                "list\n" +
                "exit\n";
        provideInput(input);
        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("has been deleted"));
            assertTrue(output.contains("No patients found"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    @Test
    void testInvalidCommandsAndErrors() {
        String input = "invalidcommand\n" +
                "add n/John Doe\n" + // Missing required fields
                "edit ic/nonexistent /to n/New Name\n" + // Nonexistent patient
                "editVisit ic/nonexistent date/01-11-2024 14:30\n" + // Nonexistent patient
                "addVisit ic/nonexistent v/01-11-2024 14:30\n" + // Nonexistent patient
                "find invalidformat\n" + // Invalid find format
                "findVisit nonexistent\n" + // Nonexistent patient
                "findDiagnosis nonexistent\n" + // Nonexistent diagnosis
                "findMedication nonexistent\n" + // Nonexistent medication
                "delete nonexistent\n" + // Nonexistent patient
                "help\n" + // Valid help command
                "exit\n";
        provideInput(input);
        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Unknown command"));
            assertTrue(output.contains("Please provide"));
            assertTrue(output.contains("No patient found"));
            assertTrue(output.contains("Action"));
            assertTrue(output.contains("Format"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //@@author kaboomzxc
    @Test
    void testCheckAvailabilityAllBranches() {
        String input = String.format(
                "appointment n/P1 ic/S1111111A date/%s time/09:00\n" +  // Initial appointment
                        "appointment n/P2 ic/S2222222B date/%s time/09:00\n" +  // Same time (should get next slot)
                        "appointment n/P3 ic/S3333333C date/%s time/09:15\n" +  // During first appointment
                        "appointment n/P4 ic/S4444444D date/%s time/09:00\n" +  // Different day
                        "appointment n/P5 ic/S5555555E date/%s time/09:00\n" +  // Previous day
                        "appointment n/P6 ic/S6666666F date/%s time/09:45\n" +  // Clear slot
                        "appointment n/P7 ic/S7777777G date/%s time/09:25\n" +  // Overlapping end
                        "listAppointments\nexit\n",
                TODAY, TODAY, TODAY, TOMORROW, YESTERDAY, TODAY, TODAY);

        provideInput(input);
        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("next available timeslot"));
            assertTrue(output.contains("Appointment on " + TODAY + " 09:00"));
            assertTrue(output.contains("Appointment on " + TODAY + " 09:45"));
            assertTrue(output.contains("Appointment on " + TOMORROW + " 09:00"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //@@author kaboomzxc
    @Test
    void testCompletePatientLifecycle() {
        String input = "add n/John Doe ic/S1234567A p/91234567 d/Fever m/Paracetamol v/01-11-2024 14:30\n" +
                "addVisit ic/S1234567A v/02-11-2024 14:30 d/Followup,Check m/None\n" +
                "editVisit ic/S1234567A date/02-11-2024 14:30 newDate/03-11-2024 14:30\n" +
                "edit ic/S1234567A /to p/98765432 ha/New Address\n" +
                "appointment n/John Doe ic/S1234567A date/04-11-2024 time/14:30\n" +
                "list\n" +
                "listAppointments\n" +
                "delete S1234567A\n" +
                "exit\n";

        provideInput(input);
        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Patient record updated successfully"));
            assertTrue(output.contains("Visit record updated successfully"));
            assertTrue(output.contains("has been deleted"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    @Test
    void testAppointmentTimeBoundaries() {
        String input = String.format(
                "appointment n/P1 ic/S1111111A date/%s time/00:00\n" +  // Start of day
                        "appointment n/P2 ic/S2222222B date/%s time/23:30\n" +  // End of day
                        "appointment n/P3 ic/S3333333C date/%s time/23:45\n" +  // Very end of day
                        "listAppointments\nexit\n",
                TODAY, TODAY, TODAY);

        provideInput(input);
        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("00:00"));
            assertTrue(output.contains("23:30"));
            assertTrue(output.contains("The next available timeslot"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@@author kaboomzxc
    @Test
    void testDateFormatHandling() {
        String input = "add n/John Doe ic/S1234567A v/2024-11-01 14:30\n" +  // Wrong date format
                "add n/John Doe ic/S1234567A v/01-11-2024 24:00\n" +  // Invalid time
                "appointment n/John ic/S1234567A date/2024-11-01 time/14:30\n" +  // Wrong date format
                "appointment n/John ic/S1234567A date/01-11-2024 time/24:00\n" +  // Invalid time
                "exit\n";

        provideInput(input);
        try {
            Main.main(new String[]{});
            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Invalid"));
            assertTrue(output.contains("Error"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
