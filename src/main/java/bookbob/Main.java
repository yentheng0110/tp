package bookbob;
import bookbob.entity.Records;
import bookbob.functions.CommandHandler;
import bookbob.functions.FileHandler;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to BookBob, Dr. Bob!");
        Scanner in = new Scanner(System.in);
        Records records = new Records();
        FileHandler.initFile(records);
        CommandHandler commandHandler = new CommandHandler();

        boolean isRunning = true;
        while (isRunning) {
            String input = in.nextLine();
            String[] inputArr = input.split(" ", 2);
            String command = inputArr[0];

            switch (command) {
                case "find":
                    commandHandler.find(input, records);
                    break;

                case "exit":
                    isRunning = false;  // This will cause the loop to exit
                    commandHandler.exit(input);
                    break;

                case "add":
                    commandHandler.add(input, records);
                    break;

                case "list":
                    commandHandler.list(records);
                    break;

                case "delete":
                    if (inputArr.length > 1) {
                        String nric = inputArr[1].trim();
                        commandHandler.delete(nric, records);
                    } else {
                        System.out.println("Please specify an NRIC to delete.");
                    }
                    break;

                case "help":
                    commandHandler.help();
                    break;

                default:
                    System.out.println("Unknown command. Type 'help' for a list of commands.");
                    break;
            }
        }
        in.close();  // Close the scanner when done
    }
}
