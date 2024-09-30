package BookBob.entity;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String name;
    private String NRIC;
    private String diagnosis;
    private ArrayList<String> medication;
    private int phoneNumber;
    private String dateOfBirth;
    private String homeAddress;

    //default constructor only takes in name and NRIC
    public Patient(String name, String NRIC) {
        this.name = name;
        this.NRIC = NRIC;
        this.homeAddress = "";
        this.medication = new ArrayList<>();
        this.diagnosis = "";
        this.dateOfBirth = "";
    }

    //constructor used in retrieving data
    public Patient(String name, String NRIC, String phoneNumber, String dateOfBirth, String homeAddress, String diagnosis, ArrayList<String> medications) {
        this.name = name;
        this.NRIC = NRIC;
        this.dateOfBirth = dateOfBirth;
        this.homeAddress = homeAddress;
        this.diagnosis = diagnosis;
        this.medication = medications;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNRIC() {
        return NRIC;
    }

    public void setNRIC(String NRIC) {
        this.NRIC = NRIC;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public ArrayList<String> getMedication() {
        return medication;
    }

    public void setMedication(ArrayList<String> medication) {
        this.medication = medication;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }
}
