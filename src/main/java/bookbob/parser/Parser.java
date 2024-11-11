package bookbob.parser;

import bookbob.entity.Records;
import bookbob.entity.AppointmentRecord;
import bookbob.functions.CommandHandler;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    private static final List<String> keywords = List.of("|");

    public static boolean handleCommand(String input, CommandHandler commandHandler, Records records,
                                     AppointmentRecord appointmentRecord) {
        String[] inputArray = input.split(" ", 2);

        boolean isBanned = keywords.stream().anyMatch(input::contains);
        if (isBanned) {
            System.out.println("You have entered a command containing illegal characters");
            return true;
        }

        String command = inputArray[0];

        try{
            switch (command) {
            case "help":
                if (inputArray.length > 1) {
                    printUnknownCommand(command);
                    break;
                }
                logAndExecute("help", commandHandler::help);
                break;

            case "add":
                logAndExecute("add", () -> {
                    try {
                        commandHandler.add(input, records);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                break;

            case "delete":
                logAndExecute("delete", () -> {
                    if (inputArray.length > 1) {
                        String nric = inputArray[1].trim();
                        try {
                            commandHandler.delete(nric, records);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("Please specify a NRIC to delete.");
                        logger.log(Level.INFO, "Empty NRIC inputted for delete command");
                    }
                });
                break;

            case "list":
                if (inputArray.length > 1) {
                    printUnknownCommand(command);
                    break;
                }
                logAndExecute("list", () -> commandHandler.list(records));
                break;

            case "edit":
                logAndExecute("edit", () -> {
                    try {
                        commandHandler.edit(input, records);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                break;

            case "find":
                logAndExecute("find", () -> commandHandler.find(input, records));
                break;

            //@@author kaboomzxc
            case "addVisit":
                logAndExecute("addVisit", () -> {
                    try {
                        commandHandler.addVisit(input, records);
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "IO error in addVisit", e);
                        System.out.println("Error saving data. Please try again.");
                        throw new RuntimeException("Failed to save data", e);
                    }
                });
                break;

            case "editVisit":
                logAndExecute("editVisit", () -> {
                    try {
                        commandHandler.editVisit(input, records);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                break;

            case "appointment":
                logAndExecute("appointment", () -> {
                    try {
                        commandHandler.appointment(input, appointmentRecord);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                break;

            case "listAppointments":
                if (inputArray.length > 1) {
                    printUnknownCommand(command);
                    break;
                }
                logAndExecute("listAppointments", () -> commandHandler.listAppointments(appointmentRecord));
                break;

            case "deleteAppointment":
                logAndExecute("deleteAppointment", () -> {
                    try {
                        commandHandler.deleteAppointment(input, appointmentRecord);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                break;

            case "findAppointment":
                if (inputArray.length > 1) {
                    logAndExecute("findAppointment", () -> commandHandler.
                        findAppointment(inputArray[1], appointmentRecord));
                } else {
                    System.out.println("Please provide search criteria for findAppointment.");
                    logger.log(Level.INFO, "Missing criteria for findAppointment");
                }
                break;

            case "findVisit":
                if (inputArray.length > 1) {
                    logAndExecute("findVisit", () -> commandHandler.findVisitByIc(inputArray[1], records));
                } else {
                    System.out.println("Please provide a NRIC for findVisit.");
                    logger.log(Level.INFO, "Missing NRIC for findVisit");
                }
                break;

            case "findMedication":
                if (inputArray.length > 1) {
                    logAndExecute("findMedication", () -> commandHandler.findVisitByMedication(inputArray[1], records));
                } else {
                    System.out.println("Please provide a medication for findMedication.");
                    logger.log(Level.INFO, "Missing medication for findMedication");
                }
                break;

            case "findDiagnosis":
                if (inputArray.length > 1) {
                    logAndExecute("findDiagnosis", () -> commandHandler.findVisitByDiagnosis(inputArray[1], records));
                } else {
                    System.out.println("Please provide a diagnosis for findDiagnosis.");
                    logger.log(Level.INFO, "Missing diagnosis for findDiagnosis");
                }
                break;

            case "exit":
                if (inputArray.length > 1) {
                    printUnknownCommand(command);
                    break;
                }
                logAndExecute("exit", () -> {
                    try {
                        commandHandler.removePastAppointments(appointmentRecord);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    commandHandler.exit(input);
                });
                break;

            default:
                printUnknownCommand(command);
                break;
            }
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in command execution", e);
            System.out.println("An error occurred. Please enter another command.");
            return true;
        }
    }

    private static void printUnknownCommand(String command) {
        System.out.println("Unknown command. Type 'help' for a list of commands.");
        logger.log(Level.INFO, "Unknown command received: {0}", command);
    }

    private static void logAndExecute(String commandName, Runnable action) {
        logger.log(Level.INFO, "Processing {0} command", commandName);
        try {
            action.run();
            logger.log(Level.INFO, "Successfully processed {0} command", commandName);
        } catch (DateTimeParseException e) {  // More specific exception first
            logger.log(Level.WARNING, "Error in {0} command: incorrect date/time format", commandName);
            System.out.println("Error: incorrect date/time format. Please use dd-MM-yyyy HH:mm");
        } catch (DateTimeException e) {  // More general exception second
            logger.log(Level.WARNING, "Error in {0} command: incorrect time format", commandName);
            System.out.println("Error: incorrect time format.");
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Invalid input for {0} command: {1}",
                    new Object[]{commandName, e.getMessage()});
            System.out.println("Invalid input: " + e.getMessage());
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, "Error in {0} command: No input is given for a mandatory field",
                    commandName);
            System.out.println("Error: No input is given for a mandatory field.");
        } catch (RuntimeException e) {
            if (e.getCause() instanceof IOException) {
                logger.log(Level.SEVERE, "IO error in " + commandName, e);
                System.out.println("Error saving data. Please try again.");
            } else {
                throw e;  // Rethrow other runtime exceptions
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error processing {0} command", commandName);
            System.out.println("An error occurred: " + e.getMessage());
            System.out.println("Please try again.");
        }
    }
}
