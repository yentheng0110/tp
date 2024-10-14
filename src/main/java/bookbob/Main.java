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
                    if (input == null || input.trim().isEmpty()) {
                        throw new IllegalArgumentException("Input cannot be null or empty");
                    }

                    commandHandler.find(input, records);
                    logger.log(Level.INFO, "Successfully processed find command");
                } catch (IllegalArgumentException e) {
                    logger.log(Level.WARNING, "Invalid input for find command: {0}", e.getMessage());
                    System.out.println("Invalid input: " + e.getMessage());
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error processing find command", e);
                    System.out.println("An error occurred while processing the find command: " + e.getMessage());
                }
                logger.log(Level.INFO, "End of processing find command");
                break;

            case "exit":
            logger.log(Level.INFO, "Processing exit command");
            try{
                commandHandler.exit(input);
                logger.log(Level.INFO, "End of processing exit command");
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error processing exit", e);
            }
            break;

            case "add":
                commandHandler.add(input, records);
                break;

            case "list":
                logger.log(Level.INFO, "Processing list command");
                try{
                    commandHandler.list(records);
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing list", e);
                    System.out.println("Error in listing patient record, specific error: " + e.getMessage());
                }
                break;

            case "delete":
                logger.log(Level.INFO, "Processing patient record deletion");
                try {
                    if (inputArr.length > 1) {
                        String nric = inputArr[1].trim();
                        commandHandler.delete(nric, records);
                    } else {
                        System.out.println("Please specify an NRIC to delete.");
                        logger.log(Level.INFO, "Empty NRIC inputted.");
                    }
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing deletion");
                    System.out.println("Error in deleting files, specific error: " + e.getMessage());
                }
                logger.log(Level.INFO, "Processing patient record deletion");
                break;

            case "help":
                logger.log(Level.INFO, "Processing help command");
                try{
                    commandHandler.help();
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing help", e);
                }
                logger.log(Level.INFO, "End of processing help command");
                break;

            default:
                System.out.println("Unknown command. Type 'help' for a list of commands.");
                break;
            }
        }
    }
}
