package bookbob.entity;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String name;
    private String nric;
    private String dateOfBirth;
    private String phoneNumber;
    private String homeAddress;
    private List<Visit> visits;
    private String allergy;
    private String sex;
    private String medicalHistory;

    // default constructor only takes in name and NRIC and visits - mandatory fields
    //@@author G13nd0n and kaboomzxc and coraleaf0602
    public Patient(String name, String nric, List<Visit> visits) {
        this.name = name;
        this.nric = nric;
        this.dateOfBirth = "";
        this.phoneNumber = "";
        this.homeAddress = "";
        this.visits = visits;
        this.sex = "";
        this.medicalHistory = "";
    }

    // constructor used in retrieving data
    // @@author G13nd0n and kaboomzxc
    public Patient(String name, String nric, String phoneNumber, String dateOfBirth, String homeAddress,
                   String allergy, String sex, String medicalHistory, List<Visit> visits) {
        this.name = name;
        this.nric = nric;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.homeAddress = homeAddress;
        this.allergy = allergy;
        this.sex = sex;
        this.medicalHistory = medicalHistory;
        this.visits = visits;
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

    // @@author kaboomzxc
    public String getAllergy() {
        return allergy;
    }
    // @@author kaboomzxc
    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }
    // @@author kaboomzxc
    public String getSex() {
        return sex;
    }
    // @@author kaboomzxc
    public void setSex(String sex) {
        this.sex = sex;
    }
    // @@author kaboomzxc
    public String getMedicalHistory() {
        return medicalHistory;
    }
    // @@author kaboomzxc
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }


    @Override
    public String toString() {
        return "Name: " + getName() + ", NRIC: " + getNric() +
                ", Phone: " + getPhoneNumber() +  ", Address: " + getHomeAddress() +
                ", DOB: " + getDateOfBirth() + ", Allergy: " + getAllergy() +
                ", Sex: " + getSex() + ", Medical History: " + getMedicalHistory();
    }
}
