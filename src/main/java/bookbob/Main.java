package bookbob;

import bookbob.entity.Records;
import bookbob.entity.AppointmentRecord;
import bookbob.functions.CommandHandler;
import bookbob.functions.FileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger("Main.class");
    private static final String directoryName = "logs";
    private static final String logFilePath = directoryName + "/logs.log";

    private static void loggerConfig() {
        try {
            String currentDirectory = System.getProperty("user.dir");
            String directory = currentDirectory + File.separator + directoryName;
            Files.createDirectories(Paths.get(directoryName));
            java.util.logging.FileHandler fh = new java.util.logging.FileHandler(logFilePath, true);
            fh.setFormatter(new java.util.logging.SimpleFormatter());
            fh.setLevel(Level.FINE);
            logger.addHandler(fh);
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            for (Handler handler : handlers) {
                if (handler instanceof ConsoleHandler) {
                    rootLogger.removeHandler(handler);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void logAndExecute(String commandName, Runnable action) {
        logger.log(Level.INFO, "Processing {0} command", commandName);
        try {
            action.run();
            logger.log(Level.INFO, "Successfully processed {0} command", commandName);
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Invalid input for {0} command: {1}", new Object[]{commandName, e.getMessage()});
            System.out.println("Invalid input: " + e.getMessage());
        } catch (DateTimeParseException e) {
            logger.log(Level.WARNING, "Error in {0} command: incorrect date format", commandName);
            System.out.println("Error: incorrect date format.");
        } catch (DateTimeException e) {
            logger.log(Level.WARNING, "Error in {0} command: incorrect time format", commandName);
            System.out.println("Error: incorrect time format.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error processing {0} command", e);
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static boolean validateParameters(String input, String... requiredMarkers) {
        for (String marker : requiredMarkers) {
            if (!input.contains(marker)) {
                System.out.println("Please provide the required parameter: " + marker);
                logger.log(Level.INFO, "Missing required parameter: {0}", marker);
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to BookBob, Dr. Bob!");
        loggerConfig();
        Scanner in = new Scanner(System.in);
        Records records = new Records();
        AppointmentRecord appointmentRecord = new AppointmentRecord();
        FileHandler.initFile(records);
        FileHandler.initFile(appointmentRecord);
        CommandHandler commandHandler = new CommandHandler();
        appointmentRecord.appointmentNotice();

        while (true) {
            String input = in.nextLine();
            String[] inputArray = input.split(" ", 2);
            String command = inputArray[0];

            switch (command) {
            case "find":
                logAndExecute("find", () -> commandHandler.find(input, records));
                break;

            case "exit":
                logAndExecute("exit", () -> {
                    try {
                        commandHandler.removePastAppointments(appointmentRecord);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    commandHandler.exit(input);
                });
                break;

            case "add":
                if (validateParameters(input, "n/", "ic/", "v/")) {
                    logAndExecute("add", () -> {
                        try {
                            commandHandler.add(input, records);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                break;

            case "list":
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

            case "editVisit":
                logAndExecute("editVisit", () -> {
                    try {
                        commandHandler.editVisit(input, records);
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
                        System.out.println("Please specify an NRIC to delete.");
                        logger.log(Level.INFO, "Empty NRIC inputted for delete command");
                    }
                });
                break;

            case "help":
                logAndExecute("help", commandHandler::help);
                break;

            case "appointment":
                if (validateParameters(input, "n/", "ic/", "date/", "time/")) {
                    logAndExecute("appointment", () -> {
                        try {
                            commandHandler.appointment(input, appointmentRecord);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                break;

            case "listAppointments":
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
                    System.out.println("Please provide NRIC for findVisit.");
                    logger.log(Level.INFO, "Missing NRIC for findVisit");
                }
                break;

            case "findMedication":
                if (inputArray.length > 1) {
                    logAndExecute("findMedication", () -> commandHandler.findVisitByMedication(inputArray[1], records));
                } else {
                    System.out.println("Please provide medication for findMedication.");
                    logger.log(Level.INFO, "Missing medication for findMedication");
                }
                break;

            case "findDiagnosis":
                if (inputArray.length > 1) {
                    logAndExecute("findDiagnosis", () -> commandHandler.findVisitByDiagnosis(inputArray[1], records));
                } else {
                    System.out.println("Please provide diagnosis for findDiagnosis.");
                    logger.log(Level.INFO, "Missing diagnosis for findDiagnosis");
                }
                break;

            case "addVisit":
                if (validateParameters(input, "ic/", "v/")) {
                    logAndExecute("addVisit", () -> {
                        try {
                            commandHandler.addVisit(input, records);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                break;

            default:
                System.out.println("Unknown command. Type 'help' for a list of commands.");
                logger.log(Level.INFO, "Unknown command received: {0}", command);
                break;
            }
        }
    }
}
