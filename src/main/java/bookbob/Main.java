package bookbob;
import bookbob.entity.Records;
import bookbob.functions.CommandHandler;
import bookbob.functions.SaveAndRetrieve;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to BookBob, Dr. Bob!");

        Scanner in = new Scanner(System.in);
        Records records = new Records();
        SaveAndRetrieve.initFile(records);
        CommandHandler commandHandler = new CommandHandler();

        while (true) {
            String input = in.nextLine();
            String[] inputArr = input.split(" ", 2);
            String command = inputArr[0];

            switch (command) {
            case "exit":
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
    }
}