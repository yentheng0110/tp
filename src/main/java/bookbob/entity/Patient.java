package bookbob.entity;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String name;
    private String nric;
    private String dateOfBirth;
    private String phoneNumber;
    private String homeAddress;
    private String gender;
    private List<String> allergy;
    private List<String> medicalHistory;
    private List<Visit> visits;

    // default constructor only takes in name and NRIC
    //@@author G13nd0n
    public Patient(String name, String nric) {
        this.name = name;
        this.nric = nric;
        this.dateOfBirth = "";
        this.phoneNumber = "";
        this.homeAddress = "";
        this.visits = new ArrayList<>();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getAllergy() {
        return allergy;
    }

    public void setAllergy(List<String> allergy) {
        this.allergy = allergy;
    }

    public List<String> getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(List<String> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    // constructor used in retrieving data
    // @@author G13nd0n
    public Patient(String name, String nric, String dateOfBirth, String phoneNumber, String homeAddress) {
        this.name = name;
        this.nric = nric;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.homeAddress = homeAddress;
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

    public List<Visit> getVisit() {
        return visits;
    }

    public void setVisit(List<Visit> visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", NRIC: " + getNric() +
                ", Phone: " + getPhoneNumber() +  ", Address: " + getHomeAddress() +
                ", DOB: " + getDateOfBirth();
    }
}
