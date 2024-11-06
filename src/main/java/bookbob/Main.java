package bookbob;

import bookbob.entity.Records;
import bookbob.entity.AppointmentRecord;
import bookbob.functions.CommandHandler;
import bookbob.functions.FileHandler;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger("Main.class");

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to BookBob, Dr. Bob!");
        Scanner in = new Scanner(System.in);
        Records records = new Records();
        AppointmentRecord appointmentRecord = new AppointmentRecord();
        FileHandler.initFile(records);
        FileHandler.initFile(appointmentRecord);
        CommandHandler commandHandler = new CommandHandler();
        appointmentRecord.appointmentNotice();

        while (true) {
            String input = in.nextLine();
            String[] inputArr = input.split(" ", 2);
            String command = inputArr[0];

            switch (command) {
            case "find":
                logger.log(Level.INFO, "Processing find command");
                try {
                    commandHandler.find(input, records);
                    logger.log(Level.INFO, "Successfully processed find command");
                } catch (IllegalArgumentException e) {
                    logger.log(Level.WARNING, "Invalid input for find command: {0}", e.getMessage());
                    System.out.println("Invalid input: " + e.getMessage());
                    e.printStackTrace(); // prints the stack trace to the console
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error processing find command", e);
                    System.out.println("An error occurred while processing the find command: " + e.getMessage());
                }
                break;

            case "exit":
                logger.log(Level.INFO, "Processing exit command");
                try{
                    commandHandler.removePastAppointments(appointmentRecord);
                    commandHandler.exit(input);
                    logger.log(Level.INFO, "Successfully processed exit command");
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing exit", e);
                }
                break;

            case "add":
                logger.log(Level.INFO, "Processing add command");

                try {
                    int nameStart = input.indexOf("n/");
                    int nricStart = input.indexOf("ic/");
                    int visitStart = input.indexOf("v/");

                    if (nameStart == -1) {
                        System.out.println("Please provide the patient's name.");
                        logger.log(Level.INFO, "Name of the patient is not provided");
                        break;
                    }

                    if (nricStart == -1) {
                        System.out.println("Please provide the patient's NRIC.");
                        logger.log(Level.INFO, "NRIC of the patient is not provided");
                        break;

                    }

                    if (visitStart == -1) {
                        System.out.println("Please provide the visit date.");
                        logger.log(Level.INFO, "Visit date is not provided");
                        break;
                    }

                    commandHandler.add(input, records);
                    logger.log(Level.INFO, "Successfully processed add command");
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing add command", e);
                    System.out.println("Error in adding patient record, specific error: " + e.getMessage());
                }
                break;

            case "list":
                logger.log(Level.INFO, "Processing list command");
                try {
                    commandHandler.list(records);
                    logger.log(Level.INFO, "Successfully processed list command");
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing list", e);
                    System.out.println("Error in listing patient record, specific error: " + e.getMessage());
                }
                break;

            case "edit":
                logger.log(Level.INFO, "Processing edit command");
                try {
                    commandHandler.edit(input, records);
                    logger.log(Level.INFO, "Successfully processed edit command");
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing edit", e);
                    System.out.println("Error in editing patient record, specific error: " + e.getMessage());
                }
                break;

            case "editVisit":
                logger.log(Level.INFO, "Processing editVisit command");
                try {
                    commandHandler.editVisit(input, records);
                    logger.log(Level.INFO, "Successfully processed editVisit command");
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing editVisit", e);
                    System.out.println("Error in editing patient visit, specific error: " + e.getMessage());
                }
                break;

            case "delete":
                logger.log(Level.INFO, "Processing delete command");
                try {
                    if (inputArr.length > 1) {
                        String nric = inputArr[1].trim();
                        commandHandler.delete(nric, records);
                        logger.log(Level.INFO, "Successfully processed delete command");
                    } else {
                        System.out.println("Please specify an NRIC to delete.");
                        logger.log(Level.INFO, "Empty NRIC inputted");
                    }
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing deletion");
                    System.out.println("Error in deleting files, specific error: " + e.getMessage());
                }
                break;

            case "help":
                logger.log(Level.INFO, "Processing help command");
                try{
                    commandHandler.help();
                    logger.log(Level.INFO, "Successfully processed help command");
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing help", e);
                }
                break;

            case "appointment":
                logger.log(Level.INFO, "Processing appointment command");
                try {
                    int nameStart = input.indexOf("n/");
                    int nricStart = input.indexOf("ic/");
                    int dateStart = input.indexOf("date/");
                    int timeStart = input.indexOf("time/");

                    if (nameStart == -1) {
                        System.out.println("Please provide the patient's name.");
                        logger.log(Level.INFO, "Name of the patient is not provided");
                        break;
                    }

                    if (nricStart == -1) {
                        System.out.println("Please provide the patient's NRIC.");
                        logger.log(Level.INFO, "NRIC of the patient is not provided");
                        break;
                    }

                    if (dateStart == -1) {
                        System.out.println("Please provide the date.");
                        logger.log(Level.INFO, "Date of the appointment is not provided");
                        break;
                    }

                    if (timeStart == -1) {
                        System.out.println("Please provide the time.");
                        logger.log(Level.INFO, "Time of the appointment is not provided");
                        break;
                    }

                    commandHandler.appointment(input, appointmentRecord);
                } catch (DateTimeParseException e) {
                    logger.log(Level.WARNING, "Error processing appointment command", e);
                    System.out.println("Error in adding appointment, wrong date format");
                } catch (DateTimeException e) {
                    logger.log(Level.WARNING, "Error processing appointment command", e);
                    System.out.println("Error in adding appointment, wrong time format");
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing appointment command", e);
                    System.out.println("Error in adding appointment, specific error: " + e.getMessage());
                }
                break;

            case "listAppointments":
                logger.log(Level.INFO, "Processing list appointments command");
                try{
                    commandHandler.listAppointments(appointmentRecord);
                    logger.log(Level.INFO, "Successfully processed list appointments command");
                } catch (DateTimeParseException e) {
                    logger.log(Level.WARNING, "Error processing appointment command", e);
                    System.out.println("Error in adding appointment, wrong date format");
                } catch (DateTimeException e) {
                    logger.log(Level.WARNING, "Error processing appointment command", e);
                    System.out.println("Error in adding appointment, wrong time format");
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing list appointments", e);
                    System.out.println("Error in listing appointments, specific error: " + e.getMessage());
                }
                break;

            case "deleteAppointment":
                logger.log(Level.INFO, "Processing delete appointment command");
                try {
                    commandHandler.deleteAppointment(input, appointmentRecord);
                    logger.log(Level.INFO, "Successfully processed deletion of appointment command");
                } catch (DateTimeParseException e) {
                    logger.log(Level.WARNING, "Error processing deletion of appointment command", e);
                    System.out.println("Error in adding appointment, wrong date format");
                } catch (DateTimeException e) {
                    logger.log(Level.WARNING, "Error processing deletion of appointment command", e);
                    System.out.println("Error in adding appointment, wrong time format");
                } catch (IllegalArgumentException e) {
                    logger.log(Level.WARNING, "Error processing deletion of appointment command");
                    System.out.println("Error in deleting appointment, missing nric, date or time.");
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing deletion of appointment");
                    System.out.println("Error in deleting appointment, specific error: " + e.getMessage());
                }
                break;

            case "findAppointment":
                logger.log(Level.INFO, "Processing find appointment command");
                try {
                    commandHandler.findAppointment(inputArr[1], appointmentRecord);
                    logger.log(Level.INFO, "Successfully processed find appointment command");
                } catch (DateTimeParseException e) {
                    logger.log(Level.WARNING, "Error processing appointment command", e);
                    System.out.println("Error in adding appointment, wrong date format");
                } catch (DateTimeException e) {
                    logger.log(Level.WARNING, "Error processing appointment command", e);
                    System.out.println("Error in adding appointment, wrong time format");
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error processing find command", e);
                    System.out.println("An error occurred while processing the find command: " + e.getMessage());
                }
                break;

            case "findVisit":
                logger.log(Level.INFO, "Processing find visit command");
                try {
                    commandHandler.findVisitByIc(inputArr[1], records);
                    logger.log(Level.INFO, "Successfully processed find visit command");
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing find visit command", e);
                }
                break;

            case "findMedication":
                logger.log(Level.INFO, "Processing find medication command");
                try {
                    commandHandler.findVisitByMedication(inputArr[1], records);
                    logger.log(Level.INFO, "Successfully processed find medication command");
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing find medication command", e);
                }
                break;


            case "findDiagnosis":
                logger.log(Level.INFO, "Processing find diagnosis command");
                try {
                    commandHandler.findVisitByDiagnosis(inputArr[1], records);
                    logger.log(Level.INFO, "Successfully processed find diagnosis command");
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing find diagnosis command", e);
                }
                break;

            case "addVisit":
                logger.log(Level.INFO, "Processing addVisit command");
                try {
                    // Check for required fields
                    int nricStart = input.indexOf("ic/");
                    int visitStart = input.indexOf("v/");

                    if (nricStart == -1) {
                        System.out.println("Please provide the patient's NRIC.");
                        logger.log(Level.INFO, "NRIC not provided");
                        break;
                    }

                    if (visitStart == -1) {
                        System.out.println("Please provide the visit date and time.");
                        logger.log(Level.INFO, "Visit date/time not provided");
                        break;
                    }

                    commandHandler.addVisit(input, records);
                    logger.log(Level.INFO, "Successfully processed addVisit command");
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing addVisit command", e);
                    System.out.println("Error processing addVisit command: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Unknown command. Type 'help' for a list of commands.");
                break;
            }
        }
    }
}
