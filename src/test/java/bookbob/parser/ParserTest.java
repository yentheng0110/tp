package bookbob.parser;

import bookbob.entity.Records;
import bookbob.entity.AppointmentRecord;
import bookbob.functions.CommandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

//@@author kaboomzxc
class ParserTest {
    @Mock
    private CommandHandler commandHandler;

    @Mock
    private Records records;

    @Mock
    private AppointmentRecord appointmentRecord;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    //@@author kaboomzxc
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Visit Command Tests")
    class VisitCommandTests {
        @Test
        @DisplayName("Test add visit command")
        void testAddVisitCommand() throws IOException {
            // Arrange
            String input = "addVisit ic/S9534567A v/21-10-2024 15:48 d/Fever m/Paracetamol";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).addVisit(input, records);
            Assertions.assertTrue(result);
        }
        //@@author kaboomzxc
        @Test
        @DisplayName("Test edit visit command")
        void testEditVisitCommand() throws IOException {
            // Arrange
            String input = "editVisit ic/S9534567A v/21-10-2024 15:48 d/Flu m/Ibuprofen";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).editVisit(input, records);
            Assertions.assertTrue(result);
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test find visit command")
        void testFindVisitCommand() throws IOException {
            // Arrange
            String input = "findVisit S9534567A";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).findVisitByIc("S9534567A", records);
            Assertions.assertTrue(result);
        }
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Appointment Command Tests")
    class AppointmentCommandTests {
        @Test
        @DisplayName("Test add appointment command")
        void testAddAppointmentCommand() throws IOException {
            // Arrange
            String input = "appointment n/John Doe ic/S9534567A date/21-10-2024 time/15:48";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).appointment(input, appointmentRecord);
            Assertions.assertTrue(result);
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test list appointments command")
        void testListAppointmentsCommand() throws IOException {
            // Arrange
            String input = "listAppointments";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).listAppointments(appointmentRecord);
            Assertions.assertTrue(result);
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test find appointment command")
        void testFindAppointmentCommand() throws IOException {
            // Arrange
            String input = "findAppointment ic/S9534567A";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).findAppointment("ic/S9534567A", appointmentRecord);
            Assertions.assertTrue(result);
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test delete appointment command")
        void testDeleteAppointmentCommand() throws IOException {
            // Arrange
            String input = "deleteAppointment ic/S9534567A date/21-10-2024 time/15:48";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).deleteAppointment(input, appointmentRecord);
            Assertions.assertTrue(result);
        }
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Search Command Tests")
    class SearchCommandTests {
        @Test
        @DisplayName("Test find diagnosis command")
        void testFindDiagnosisCommand() throws IOException {
            // Arrange
            String input = "findDiagnosis fever";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).findVisitByDiagnosis("fever", records);
            Assertions.assertTrue(result);
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test find medication command")
        void testFindMedicationCommand() throws IOException {
            // Arrange
            String input = "findMedication paracetamol";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).findVisitByMedication("paracetamol", records);
            Assertions.assertTrue(result);
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test find command with missing criteria")
        void testFindCommandWithMissingCriteria() throws IOException {
            // Arrange
            String[] inputs = {"findVisit", "findDiagnosis", "findMedication", "findAppointment"};

            for (String input : inputs) {
                // Act
                boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

                // Assert
                Assertions.assertTrue(result);
                Assertions.assertTrue(outputStreamCaptor.toString()
                        .contains("Please provide") || outputStreamCaptor.toString().contains("Missing"));

                // Reset output capturer
                outputStreamCaptor.reset();
            }
        }
    }

    //@@author kaboomzxc
    @Test
    @DisplayName("Test exit command with appointment cleanup")
    void testExitCommandWithCleanup() throws IOException {
        // Arrange
        String input = "exit";

        // Act
        boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

        // Assert
        Mockito.verify(commandHandler, Mockito.times(1)).removePastAppointments(appointmentRecord);
        Mockito.verify(commandHandler, Mockito.times(1)).exit(input);
        Assertions.assertTrue(result);
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Edit Command Tests")
    class EditCommandTests {
        @Test
        @DisplayName("Test edit command")
        void testEditCommand() throws IOException {
            // Arrange
            String input = "edit ic/S9534567A /to n/Jane Doe";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).edit(input, records);
            Assertions.assertTrue(result);
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test edit command without /to parameter")
        void testEditCommandWithoutToParameter() throws IOException {
            // Arrange
            String input = "edit ic/S9534567A n/Jane Doe";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);

            Mockito.verify(commandHandler, Mockito.times(1)).edit(input, records);

        }
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Find Command Tests")
    class FindCommandTests {
        @Test
        @DisplayName("Test find by name")
        void testFindByName() throws IOException {
            // Arrange
            String input = "find n/John";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).find(input, records);
            Assertions.assertTrue(result);
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test find by NRIC")
        void testFindByNric() throws IOException {
            // Arrange
            String input = "find ic/S9534567A";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).find(input, records);
            Assertions.assertTrue(result);
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test find by phone")
        void testFindByPhone() throws IOException {
            // Arrange
            String input = "find p/91234567";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).find(input, records);
            Assertions.assertTrue(result);
        }
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {
        @Test
        @DisplayName("Test RuntimeException handling")
        void testRuntimeExceptionHandling() throws IOException {
            // Arrange
            String input = "list";
            Mockito.doThrow(new RuntimeException("Test error")).when(commandHandler).list(records);

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            Assertions.assertTrue(outputStreamCaptor.toString().contains("An error occurred"));
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test IOException handling")
        void testIOExceptionHandling() throws IOException {
            // Arrange
            String input = "add n/John Doe ic/S9534567A v/21-10-2024 15:48";
            Mockito.doThrow(new RuntimeException(new IOException("Test IO error")))
                    .when(commandHandler).add(input, records);

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            Assertions.assertTrue(outputStreamCaptor.toString()
                    .contains("Error saving data") ||
                    outputStreamCaptor.toString().contains("An error occurred"));
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test IllegalArgumentException handling")
        void testIllegalArgumentExceptionHandling() throws IOException {
            // Arrange
            String input = "add n/John Doe ic/S9534567A v/invalid-date";
            Mockito.doThrow(new IllegalArgumentException("Invalid date format"))
                    .when(commandHandler).add(input, records);

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            Assertions.assertTrue(outputStreamCaptor.toString().contains("Invalid date format"));
        }
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Complex Command Tests")
    class ComplexCommandTests {
        @Test
        @DisplayName("Test add command with all optional fields")
        void testAddCommandWithAllFields() throws IOException {
            // Arrange
            String input = "add n/John Doe ic/S9534567A p/91234567 d/Fever m/Paracetamol " +
                    "ha/123 Main St dob/01-01-1990 v/21-10-2024 15:48 al/Peanuts s/Male mh/Asthma";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).add(input, records);
            Assertions.assertTrue(result);
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test appointment command with validation")
        void testAppointmentCommandWithValidation() throws IOException {
            // Arrange
            String input = "appointment n/John Doe ic/S9534567A date/21-10-2024 time/25:00"; // Invalid time
            Mockito.doThrow(new IllegalArgumentException("Invalid time format"))
                    .when(commandHandler).appointment(input, appointmentRecord);

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            Assertions.assertTrue(outputStreamCaptor.toString().contains("Invalid time format"));
        }
    }

    //@@author kaboomzxc
    @Test
    @DisplayName("Test command with multiple spaces")
    void testCommandWithMultipleSpaces() throws IOException {
        // Arrange
        String input = "add    n/John    Doe    ic/S9534567A    v/21-10-2024    15:48";

        // Act
        boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

        // Assert
        Mockito.verify(commandHandler, Mockito.times(1)).add(input.trim(), records);
        Assertions.assertTrue(result);
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Find Parameter Tests")
    class FindParameterTests {
        @Test
        @DisplayName("Test findAppointment with all parameters")
        void testFindAppointmentAllParameters() throws IOException {
            // Test each type of parameter for findAppointment
            String[] inputs = {
                "findAppointment n/John Doe",
                "findAppointment ic/S9534567A",
                "findAppointment date/21-10-2024",
                "findAppointment time/15:48"
            };

            for (String input : inputs) {
                // Act
                boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

                // Assert
                Assertions.assertTrue(result);
                Mockito.verify(commandHandler, Mockito.times(1))
                        .findAppointment(input.split(" ", 2)[1], appointmentRecord);

                // Reset mock
                Mockito.clearInvocations(commandHandler);
            }
        }
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Command Format Tests")
    class CommandFormatTests {
        @Test
        @DisplayName("Test listAppointments with extra arguments")
        void testListAppointmentsWithExtraArgs() throws IOException {
            // Arrange
            String input = "listAppointments extra arguments";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            Assertions.assertTrue(outputStreamCaptor.toString().contains("Unknown command"));
            Mockito.verify(commandHandler, Mockito.never()).listAppointments(appointmentRecord);
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test list with extra arguments")
        void testListWithExtraArgs() throws IOException {
            // Arrange
            String input = "list extra arguments";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            Assertions.assertTrue(outputStreamCaptor.toString().contains("Unknown command"));
            Mockito.verify(commandHandler, Mockito.never()).list(records);
        }
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Exception Handling Tests")
    class ExceptionHandlingTests {
        @Test
        @DisplayName("Test NullPointerException handling")
        void testNullPointerExceptionHandling() throws IOException {
            // Arrange
            String input = "add n/John Doe ic/S9534567A v/21-10-2024 15:48";
            Mockito.doThrow(new NullPointerException("Test null pointer"))
                    .when(commandHandler).add(input, records);

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            Assertions.assertTrue(outputStreamCaptor.toString()
                    .contains("Error: No input is given for a mandatory field"));
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test DateTimeException handling")
        void testDateTimeExceptionHandling() throws IOException {
            // Arrange
            String input = "addVisit ic/S9534567A v/21-13-2024 25:48"; // Invalid date/time
            Mockito.doThrow(new IllegalArgumentException("incorrect date/time format"))
                    .when(commandHandler).addVisit(input, records);

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            Assertions.assertTrue(outputStreamCaptor.toString()
                    .contains("incorrect date/time format"));
        }
    }

    //@@author kaboomzxc
    @Test
    @DisplayName("Test empty command parts")
    void testEmptyCommandParts() throws IOException {
        // Arrange
        String input = "  ";  // Multiple spaces

        // Act
        boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

        // Assert
        Assertions.assertTrue(result);
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Unknown command"));
    }

    //@@author kaboomzxc
    @Test
    @DisplayName("Test scanner error handling")
    void testScannerErrorHandling() throws IOException {
        // Arrange
        String input = "find \n/corrupted/input";

        // Act
        boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

        // Assert
        Assertions.assertTrue(result);
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Additional Command Validation Tests")
    class AdditionalCommandValidationTests {

        @Test
        @DisplayName("Test delete command with invalid NRIC format")
        void testDeleteCommandInvalidNRIC() throws IOException {
            // Arrange
            String input = "delete 12345678";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            // Parser still calls delete with the invalid NRIC, validation happens in CommandHandler
            Mockito.verify(commandHandler, Mockito.times(1)).delete("12345678", records);
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test addVisit with missing diagnosis")
        void testAddVisitMissingDiagnosis() throws IOException {
            // Arrange
            String input = "addVisit ic/S9534567A v/21-10-2024 15:48 m/Paracetamol";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).addVisit(input, records);
            Assertions.assertTrue(result);
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test editVisit with missing medication")
        void testEditVisitMissingMedication() throws IOException {
            // Arrange
            String input = "editVisit ic/S9534567A v/21-10-2024 15:48 d/Fever";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).editVisit(input, records);
            Assertions.assertTrue(result);
        }
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {
        @Test
        @DisplayName("Test consecutive spaces in command")
        void testConsecutiveSpacesInCommand() throws IOException {
            // Arrange
            String input = "add     n/John    Doe      ic/S9534567A    v/21-10-2024    15:48";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).add(input.trim(), records);
            Assertions.assertTrue(result);
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test empty string after command")
        void testEmptyStringAfterCommand() throws IOException {
            // Arrange
            String[] commands = {"findVisit", "findDiagnosis", "findMedication"};

            for (String cmd : commands) {
                String input = cmd;  // No extra spaces, just the command
                outputStreamCaptor.reset();

                // Act
                boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

                // Assert
                Assertions.assertTrue(result);
                String output = outputStreamCaptor.toString();
                Assertions.assertTrue(
                        output.contains("Please provide a NRIC") ||
                                output.contains("Please provide a diagnosis") ||
                                output.contains("Please provide a medication")
                );
            }
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test appointment command with past date")
        void testAppointmentWithPastDate() throws IOException {
            // Arrange
            String input = "appointment n/John Doe ic/S9534567A date/01-01-2020 time/15:48";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Mockito.verify(commandHandler, Mockito.times(1)).appointment(input, appointmentRecord);
            Assertions.assertTrue(result);
        }
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Additional Error Handling Tests")
    class AdditionalErrorHandlingTests {
        @Test
        @DisplayName("Test addVisit with invalid date format")
        void testAddVisitInvalidDateFormat() throws IOException {
            // Arrange
            String input = "addVisit ic/S9534567A v/2024-10-21 15:48";
            Mockito.doThrow(new IllegalArgumentException("Invalid date format"))
                    .when(commandHandler).addVisit(input, records);

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            Assertions.assertTrue(outputStreamCaptor.toString().contains("Invalid date format"));
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test find with invalid search parameter")
        void testFindInvalidSearchParameter() throws IOException {
            // Arrange
            String input = "find invalid/parameter";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            // The parser likely handles this as a standard find command but CommandHandler will validate parameters
            Mockito.verify(commandHandler, Mockito.times(1)).find(input, records);
        }
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Parser Input Edge Cases")
    class ParserInputEdgeCases {
        @Test
        @DisplayName("Test extremely long command")
        void testExtremelyLongCommand() throws IOException {
            // Arrange
            StringBuilder longInput = new StringBuilder("add ");
            for (int i = 0; i < 100; i++) {
                longInput.append("very");
            }
            longInput.append(" n/John Doe ic/S9534567A v/21-10-2024 15:48");

            // Act
            boolean result = Parser.handleCommand(longInput.toString(), commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            Mockito.verify(commandHandler, Mockito.times(1)).add(longInput.toString(), records);
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test multiple command handling")
        void testMultipleCommandHandling() throws IOException {
            // Testing how parser handles multiple commands in sequence
            String[] commands = {
                "list",
                "listAppointments",
                "help",
                "exit"
            };

            for (String command : commands) {
                outputStreamCaptor.reset();
                boolean result = Parser.handleCommand(command, commandHandler, records, appointmentRecord);
                Assertions.assertTrue(result);
            }
        }
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Complex Error Scenarios")
    class ComplexErrorScenarios {
        @Test
        @DisplayName("Test command with mixed case")
        void testMixedCaseCommand() throws IOException {
            // Arrange
            String input = "ADD n/John Doe ic/S9534567A v/21-10-2024 15:48";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            Assertions.assertTrue(outputStreamCaptor.toString().contains("Unknown command"));
            // Since uppercase command is treated as unknown
            Mockito.verify(commandHandler, Mockito.never()).add(anyString(), any(Records.class));
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test commands with special characters")
        void testSpecialCharacterCommands() throws IOException {
            // Testing various special characters that aren't the pipe character
            String[] inputs = {
                "add n/John@Doe ic/S9534567A v/21-10-2024 15:48",
                "add n/John_Doe ic/S9534567A v/21-10-2024 15:48",
                "add n/John&Doe ic/S9534567A v/21-10-2024 15:48"
            };

            for (String input : inputs) {
                outputStreamCaptor.reset();
                boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);
                Assertions.assertTrue(result);
                Mockito.verify(commandHandler, Mockito.times(1)).add(input, records);
                Mockito.clearInvocations(commandHandler);
            }
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test nested exception handling")
        void testNestedException() throws IOException {
            // Arrange
            String input = "add n/John Doe ic/S9534567A v/21-10-2024 15:48";
            RuntimeException exception = new RuntimeException("Test error");
            Mockito.doThrow(exception).when(commandHandler).add(input, records);

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            // The parser likely has a generic error message
            Assertions.assertTrue(outputStreamCaptor.toString().contains("An error occurred"));
        }
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Boundary Value Tests")
    class BoundaryValueTests {
        @Test
        @DisplayName("Test command with minimum required parameters")
        void testMinimumParameters() throws IOException {
            // Testing commands with minimum required parameters
            String[] inputs = {
                "add n/J ic/S9534567A v/21-10-2024 15:48",
                "edit ic/S9534567A /to n/J",
                "appointment n/J ic/S9534567A date/21-10-2024 time/15:48"
            };

            for (String input : inputs) {
                outputStreamCaptor.reset();
                boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);
                Assertions.assertTrue(result);
            }
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test command with leading/trailing whitespace")
        void testWhitespaceHandling() throws IOException {
            // Arrange
            String input = "    list    ";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            Assertions.assertTrue(outputStreamCaptor.toString().contains("Unknown command"));
            // Since the command with extra spaces is treated as unknown
            Mockito.verify(commandHandler, Mockito.never()).list(records);
        }
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Command Parser Edge Cases")
    class CommandParserEdgeCases {
        @Test
        @DisplayName("Test delete command without arguments")
        void testDeleteCommandNoArgs() throws IOException {
            // Arrange
            String input = "delete";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            Assertions.assertTrue(outputStreamCaptor.toString().contains("Please specify a NRIC"));
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test deleteAppointment with missing parameters")
        void testDeleteAppointmentMissingParams() throws IOException {
            String[] inputs = {
                "deleteAppointment ic/S9534567A", // missing date and time
                "deleteAppointment ic/S9534567A date/21-10-2024", // missing time
                "deleteAppointment ic/S9534567A time/15:48" // missing date
            };

            for (String input : inputs) {
                outputStreamCaptor.reset();

                // Act
                boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

                // Assert
                Assertions.assertTrue(result);
                // The command is still passed to CommandHandler for validation
                Mockito.verify(commandHandler, Mockito.times(1)).deleteAppointment(input, appointmentRecord);
                Mockito.clearInvocations(commandHandler);
            }
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test find commands with empty search criteria")
        void testFindEmptySearchCriteria() throws IOException {
            String[] inputs = {
                "find n/",
                "find ic/",
                "find p/",
                "find ha/",
                "find dob/"
            };

            for (String input : inputs) {
                outputStreamCaptor.reset();
                boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);
                Assertions.assertTrue(result);
                Mockito.verify(commandHandler, Mockito.times(1)).find(input, records);
                Mockito.clearInvocations(commandHandler);
            }
        }
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Additional Error Scenarios")
    class AdditionalErrorScenarios {
        @Test
        @DisplayName("Test findAppointment command with no args")
        void testFindAppointmentNoArgs() throws IOException {
            // Arrange
            String input = "findAppointment";

            // Act
            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            // Assert
            Assertions.assertTrue(result);
            Assertions.assertTrue(outputStreamCaptor.toString().contains("Please provide search criteria"));
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test multiple consecutive commands")
        void testMultipleConsecutiveCommands() throws IOException {
            // Test handling of multiple commands in quick succession
            String[] commands = {
                "list",
                "help",
                "listAppointments",
                "findVisit S9534567A",
                "findDiagnosis fever"
            };

            for (String cmd : commands) {
                outputStreamCaptor.reset();
                boolean result = Parser.handleCommand(cmd, commandHandler, records, appointmentRecord);
                Assertions.assertTrue(result);
            }
        }
    }

    //@@author kaboomzxc
    @Nested
    @DisplayName("Parameter Handling Tests")
    class ParameterHandlingTests {
        @Test
        @DisplayName("Test addVisit with varying parameter orders")
        void testAddVisitParameterOrder() throws IOException {
            String[] inputs = {
                "addVisit ic/S9534567A v/21-10-2024 15:48 d/Fever m/Paracetamol",
                "addVisit v/21-10-2024 15:48 ic/S9534567A m/Paracetamol d/Fever",
                "addVisit d/Fever m/Paracetamol ic/S9534567A v/21-10-2024 15:48"
            };

            for (String input : inputs) {
                outputStreamCaptor.reset();
                boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);
                Assertions.assertTrue(result);
                Mockito.verify(commandHandler, Mockito.times(1)).addVisit(input, records);
                Mockito.clearInvocations(commandHandler);
            }
        }

        //@@author kaboomzxc
        @Test
        @DisplayName("Test edit with multiple fields")
        void testEditMultipleFields() throws IOException {
            String input = "edit ic/S9534567A /to n/John Doe p/91234567 ha/New Address s/Male";

            boolean result = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            Assertions.assertTrue(result);
            Mockito.verify(commandHandler, Mockito.times(1)).edit(input, records);
        }
    }
}
