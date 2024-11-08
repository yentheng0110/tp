package bookbob.entity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;

public class Records implements FileOperation{
    private final ArrayList<Patient> patients;

    // default constructor: empty
    //@@author G13nd0n
    public Records() {
        this.patients = new ArrayList<>();
    }

    // add a patient to records
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    // getter
    public ArrayList<Patient> getPatients() {
        return patients;
    }

    @Override
    public void initFile(String filePath) {
        try {
            String directoryName = "data";
            String currentDirectory = System.getProperty("user.dir");
            String directory = currentDirectory + File.separator + directoryName;
            File directoryFile = new File(directory);

            if (directoryFile.mkdirs()) {           //directory was not created
                File file = new File(filePath);
                file.createNewFile();              //create new data file
            } else {                               //directory already created
                File file = new File(filePath);
                if(file.createNewFile()) {         //file was not created
                } else {
                    this.retrieveData(filePath);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void autosave(String filePath) throws IOException {
        ArrayList<Patient> patients = this.getPatients();
        FileWriter fw = new FileWriter(filePath);
        for (Patient currPatient : patients) {
            String toWrite = currPatient.convertPatientToOutputText();
            fw.write(toWrite + "\n");
        }
        fw.close();
    }

    @Override
    public void retrieveData(String filePath) {
        try {
            File file = new File(filePath);
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] data = line.split("\\|");

                if (data.length < 9) {
                    continue;
                }

                // Parse basic patient information
                String name = data[0].substring(6).trim();
                String nric = data[1].substring(6).trim();
                String phoneNumber = data[2].substring(15).trim();
                String dateOfBirth = data[3].substring(16).trim();
                String homeAddress = data[4].substring(15).trim();
                //@@author kaboomzxc
                String sex = data[6].substring(5).trim();
                ArrayList<String> allergies = parseList(data[5].substring(9).trim());
                ArrayList<String> medicalHistories = parseList(data[7].substring(17).trim());

                // Parse visits
                ArrayList<Visit> visits = new ArrayList<>();
                String visitsString = data[8].trim();

                // Extract the content between the outer-most brackets
                int firstBracket = visitsString.indexOf("[");
                int lastBracket = visitsString.lastIndexOf("]");

                if (firstBracket != -1 && lastBracket != -1) {
                    visitsString = visitsString.substring(firstBracket + 1, lastBracket);

                    // Split into individual visits by looking for date pattern
                    String[] visitParts = visitsString.split("(?<=\\]),\\s*(?=\\d{2}-\\d{2}-\\d{4})");

                    for (String visitPart : visitParts) {
                        Visit visit = parseVisitInputString("[" + visitPart + "]");
                        if (visit != null) {
                            visits.add(visit);
                        }
                    }
                }

                Patient patient = new Patient(name, nric, phoneNumber, dateOfBirth, homeAddress,
                        allergies, sex, medicalHistories, visits);
                this.addPatient(patient);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@@author yentheng0110 and kaboomzxc
    private static ArrayList<String> parseList(String input) {
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
