package bookbob;
import bookbob.entity.Records;
import bookbob.functions.CommandHandler;
import bookbob.functions.FileHandler;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger( "Main.class" );

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to BookBob, Dr. Bob!");

        Scanner in = new Scanner(System.in);
        Records records = new Records();
        FileHandler.initFile(records);
        CommandHandler commandHandler = new CommandHandler();

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
                    commandHandler.exit(input);
                    logger.log(Level.INFO, "Successfully processed exit command");
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing exit", e);
                }
                break;
                
            case "add":
                logger.log(Level.INFO, "Processing add command");
                try{
                    int nameStart = input.indexOf("n/");
                    int nricStart = input.indexOf("ic/");

                    if (nameStart == -1) {
                        System.out.println("Please provide the patient's name.");
                        logger.log(Level.INFO, "Name of the patient is not provided.");
                        break;
                    }

                    if (nricStart == -1) {
                        System.out.println("Please provide the patient's NRIC.");
                        logger.log(Level.INFO, "NRIC of the patient is not provided.");
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
                try{
                    commandHandler.list(records);
                    logger.log(Level.INFO, "Successfully processed list command");
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing list", e);
                    System.out.println("Error in listing patient record, specific error: " + e.getMessage());
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
                        logger.log(Level.INFO, "Empty NRIC inputted.");
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

            default:
                System.out.println("Unknown command. Type 'help' for a list of commands.");
                break;
            }
        }
    }
}
