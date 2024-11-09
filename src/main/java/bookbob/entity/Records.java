package bookbob.entity;

import bookbob.functions.FileHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Records implements FileOperation{
    private final ArrayList<Patient> patients;

    // default constructor: empty
    //@@author G13nd0n
    public Records() {
        this.patients = new ArrayList<>();
    }

    // add a patient to records
    //@@author G13nd0n
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    //@@author G13nd0n
    public void addPatient(String name, String nric, ArrayList<Visit> visits, String sex, String dateOfBirth,
                           String phoneNumber, String homeAddress, ArrayList<String> allergies,
                           ArrayList<String> medicalHistories) {
        Patient patient = new Patient(name, nric, visits);
        patient.setSex(sex);
        patient.setDateOfBirth(dateOfBirth);
        patient.setPhoneNumber(phoneNumber);
        patient.setHomeAddress(homeAddress);
        patient.setAllergies(allergies);
        patient.setMedicalHistories(medicalHistories);
        patients.add(patient);
        System.out.println("Patient " + name + " with NRIC " + nric + " added.");
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    //@@author PrinceCatt
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

    //@@author PrinceCatt
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

    //@@author PrinceCatt
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
                ArrayList<String> allergies = FileHandler.parseList(data[5].substring(9).trim());
                ArrayList<String> medicalHistories = FileHandler.parseList(data[7].substring(17).trim());

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
                        Visit visit = FileHandler.parseVisitInputString("[" + visitPart + "]");
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

    public void delete(String nric) {
        assert nric != null : "Please provide a valid NRIC";

        double initialSize = patients.size();
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        try {
            Integer.parseInt(nric.substring(1, 2));
        } catch (NumberFormatException e){
            System.out.println("Please provide the NRIC of the patient, not the name.");
            return;
        }
        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            if (patient.getNric().equals(nric)) {
                patients.remove(i);
                System.out.println("Patient " + patient.getName() + ", " + nric + ", has been deleted.");
                break;
            }
        }

        if (patients.size() == initialSize) {
            System.out.println("Patient with " + nric + " not found");

        }
    }
}
