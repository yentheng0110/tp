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
            Parser.handleCommand(input, commandHandler, records, appointmentRecord);
        }
    }
}
