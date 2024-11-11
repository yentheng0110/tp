package bookbob;

import bookbob.entity.Records;
import bookbob.entity.AppointmentRecord;
import bookbob.functions.CommandHandler;
import bookbob.functions.FileHandler;
import bookbob.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public static void main(String[] args) throws IOException {

        loggerConfig();
        Scanner in = new Scanner(System.in);
        Records records = new Records();
        AppointmentRecord appointmentRecord = new AppointmentRecord();

        try {
            FileHandler.initFile(records);
            FileHandler.initFile(appointmentRecord);

            records.getPatients();
            appointmentRecord.appointmentNotice();
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, e.getMessage());
            System.out.println("Data file corrupted");
            System.out.println("Please check data file or delete it to start afresh");
            System.out.println("Exiting");
            System.exit(0);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during initialization: " + e.getMessage(), e);
            System.out.println("Error initializing program. Please check data files.");
            System.exit(0);
        }
        System.out.println("Welcome to BookBob, Dr. Bob!");
        CommandHandler commandHandler = new CommandHandler();


        //@@author kaboomzxc
        try {
            if (records.getPatients() == null) {
                throw new IllegalStateException("Records not properly initialized");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Records initialization failed", e);
            System.out.println("Error loading patient records. Please restart the program.");
            System.exit(0);
        }

        //@@author kaboomzxc
        boolean isRunning = true;
        while (isRunning) {
            System.out.print("Enter command: ");
            System.out.flush();

            try {
                String input = "";
                if (in.hasNextLine()) {
                    input = in.nextLine().trim();
                } else {
                    System.out.println("Error reading input. Restarting input scanner...");
                    in.close();
                    in = new Scanner(System.in);
                    continue;
                }

                // Skip empty input
                if (input.isEmpty()) {
                    continue;
                }

                // Handle the command and get continuation status
                isRunning = Parser.handleCommand(input, commandHandler, records, appointmentRecord);

            } catch (Exception e) {
                logger.log(Level.SEVERE, "Critical error in main loop: " + e.getMessage(), e);
                System.out.println("A critical error occurred. Please try again.");
            }
        }
    }
}
