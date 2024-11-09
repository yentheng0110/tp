package bookbob.functions;

import bookbob.entity.Records;
import bookbob.entity.AppointmentRecord;
import bookbob.entity.FileOperation;
import bookbob.entity.Visit;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class FileHandler {
    private static final Logger logger = Logger.getLogger(FileHandler.class.getName());
    private static String filePath = "data" + File.separator + "bookbob_data.txt";
    private static String appointmentFilePath = "data" + File.separator + "bookbob_appointment.txt";

    public static void initFile(FileOperation fileOperation) {
        if (fileOperation instanceof AppointmentRecord) {
            fileOperation.initFile(appointmentFilePath);
        } else if (fileOperation instanceof Records) {
            fileOperation.initFile(filePath);
        }
    }

    public static void autosave(FileOperation fileOperation) throws IOException {
        if (fileOperation instanceof AppointmentRecord) {
            fileOperation.autosave(appointmentFilePath);
        } else if (fileOperation instanceof Records) {
            fileOperation.autosave(filePath);
        }
    }
  
    //@@author yentheng0110 and kaboomzxc
    public static ArrayList<String> parseList(String input) {
        ArrayList<String> list = new ArrayList<>();
        // Remove any number of surrounding brackets
        String content = input.replaceAll("^\\[+|\\]+$", "");
        if (!content.isEmpty()) {
            String[] items = content.split(",\\s*");
            list.addAll(Arrays.asList(items));
        }
        return list;
    }

    //@@author coraleaf0602
    public static Visit parseVisitInputString(String visitString) {
        try {
            int visitStartIndex = visitString.indexOf("[");
            int visitEndIndex = visitString.lastIndexOf("]");

            if (visitStartIndex == -1 || visitEndIndex == -1) {
                return null;
            }

            String visitDetails = visitString.substring(visitStartIndex + 1, visitEndIndex).trim();

            // Parse date time
            String dateTimeString;
            if (visitDetails.contains("Diagnosis:")) {
                dateTimeString = visitDetails.substring(0, visitDetails.indexOf("Diagnosis:")).trim();
            } else {
                dateTimeString = visitDetails;
            }
            if (dateTimeString.endsWith(",")) {
                dateTimeString = dateTimeString.substring(0, dateTimeString.length() - 1);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime visitDateTime = LocalDateTime.parse(dateTimeString, formatter);

            //@@author kaboomzxc
            // Parse diagnoses and medications
            ArrayList<String> diagnosisList = new ArrayList<>();
            ArrayList<String> medicationsList = new ArrayList<>();

            if (visitDetails.contains("Diagnosis:")) {
                // Find the diagnosis content between brackets and clean it
                int diagStart = visitDetails.indexOf("Diagnosis: [") + 11;
                int diagEnd = visitDetails.indexOf("]", diagStart);
                if (diagStart != -1 && diagEnd != -1) {
                    String diagContent = visitDetails.substring(diagStart, diagEnd);
                    // Remove any extra square brackets
                    diagContent = diagContent.replaceAll("^\\[+|\\]+$", "").trim();
                    if (!diagContent.isEmpty()) {
                        diagnosisList.addAll(Arrays.asList(diagContent.split(",\\s*")));
                    }
                }

                if (visitDetails.contains("Medications:")) {
                    // Find the medications content between brackets and clean it
                    int medStart = visitDetails.indexOf("Medications: [") + 13;
                    int medEnd = visitDetails.indexOf("]", medStart);
                    if (medStart != -1 && medEnd != -1) {
                        String medContent = visitDetails.substring(medStart, medEnd);
                        // Remove any extra square brackets
                        medContent = medContent.replaceAll("^\\[+|\\]+$", "").trim();
                        if (!medContent.isEmpty()) {
                            medicationsList.addAll(Arrays.asList(medContent.split(",\\s*")));
                        }
                    }
                }
            }
            //@@author coraleaf0602
            return new Visit(visitDateTime, diagnosisList, medicationsList);
        } catch (Exception e) {
            return null;
        }
    }
}


