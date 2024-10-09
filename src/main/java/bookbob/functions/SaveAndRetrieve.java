package bookbob.functions;

import bookbob.entity.Patient;
import bookbob.entity.Records;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveAndRetrieve {

    public static void initFile(Records records){
        try {
            File file = new File("bookbob_data.txt");
            if(file.createNewFile()) {
            } else {
                retrieveData(records);
            }
        } catch(Exception e){
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }

    private static String convertPatientToOutputText(Patient patient) {
        String output = "";
        output += "Name: " + patient.getName() + " | " + "NRIC: " + patient.getNric() + " | "
                + "Phone Number: " + patient.getPhoneNumber() + " | " + "Date_Of_Birth: " + patient.getDateOfBirth()
                + " | " + "Home Address: " + patient.getHomeAddress() + " | "
                + "Diagnosis: " + patient.getDiagnosis() + " | " + "Medication: ";
        List<String> medications = patient.getMedication();
        for(int i = 0; i < medications.size(); i++) {
            output += medications.get(i) + ";";
        }
        return output;
    }

    public static void autosave(Records records) throws IOException {
        List<Patient> patients = records.getPatients();
        FileWriter fw = new FileWriter("bookbob_data.txt");
        for(int i = 0; i < patients.size(); i++) {
            Patient currPatient = patients.get(i);
            String toWrite = convertPatientToOutputText(currPatient);

            //for test
            System.out.println(toWrite);

            fw.write(toWrite + "\n");
        }
        fw.close();
    }

    public static void retrieveData(Records records){
        try {
            File file = new File("bookbob_data.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] data = line.split("\\|");
                String name = data[0].trim();
                String nric = data[1].trim();
                String phoneNumber = data[2].trim();
                String dateOfBirth = data[3].trim();
                String homeAddress = data[4].trim();
                String diagnosis = data[5].trim();
                ArrayList<String> medications = new ArrayList<>();
                String[] rawMedications = data[6].trim().split(";");
                for(int i = 0; i < rawMedications.length; i++) {
                    medications.add(rawMedications[i].trim());
                }
                Patient patient = new Patient(name, nric, phoneNumber,
                        dateOfBirth, homeAddress, diagnosis, medications);
                records.addPatient(patient);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}