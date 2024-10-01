package BookBob.entity;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String name;
    private String nric;
    private String dateOfBirth;
    private String phoneNumber;
    private String homeAddress;
    private String diagnosis;
    private List<String> medication;

    // default constructor only takes in name and NRIC
    public Patient(String name, String nric) {
        this.name = name;
        this.nric = nric;
        this.dateOfBirth = "";
        this.phoneNumber = "";
        this.homeAddress = "";
        this.diagnosis = "";
        this.medication = new ArrayList<>();
    }

    // constructor used in retrieving data
    public Patient(String name, String nric, String dateOfBirth, String phoneNumber, String homeAddress,
                   String diagnosis, ArrayList<String> medications) {
        this.name = name;
        this.nric = nric;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.homeAddress = homeAddress;
        this.diagnosis = diagnosis;
        this.medication = medications;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<String> getMedication() {
        return medication;
    }

    public void setMedication(ArrayList<String> medication) {
        this.medication = medication;
    }
}
