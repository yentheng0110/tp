package bookbob.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Patient implements OutputConversion {
    private String name;
    private String nric;
    private String sex;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String homeAddress;
    private ArrayList<Visit> visits;
    private ArrayList<String> allergies;
    private ArrayList<String> medicalHistories;

    // default constructor only takes in name, NRIC and visits - mandatory fields
    //@@author G13nd0n and kaboomzxc and coraleaf0602 and yentheng0110
    public Patient(String name, String nric, ArrayList<Visit> visits) {
        this.name = name;
        this.nric = nric;
        this.sex = "";
        this.dateOfBirth = null;
        this.phoneNumber = "";
        this.homeAddress = "";
        this.visits = visits;
        this.allergies = new ArrayList<>();
        this.medicalHistories = new ArrayList<>();
    }

    // constructor used in retrieving data
    // @@author G13nd0n and kaboomzxc and yentheng0110
    public Patient(String name, String nric, String phoneNumber, LocalDate dateOfBirth, String homeAddress,
                   ArrayList<String> allergies, String sex, ArrayList<String> medicalHistories,
                   ArrayList<Visit> visits) {
        this.name = name;
        this.nric = nric;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.homeAddress = homeAddress;
        this.visits = visits;
        this.allergies = allergies;
        this.medicalHistories = medicalHistories;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
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

    public ArrayList<Visit> getVisits() {
        return visits;
    }

    public void setVisits(ArrayList<Visit> visits) {
        this.visits = visits;
    }

    //@@author kaboomzxc
    public ArrayList<String> getAllergies() {
        return allergies;
    }
    //@@author kaboomzxc
    public void setAllergies(ArrayList<String> allergies) {
        this.allergies = allergies;
    }
    //@@author kaboomzxc
    public String getSex() {
        return sex;
    }
    //@@author kaboomzxc
    public void setSex(String sex) {
        this.sex = sex;
    }
    //@@author kaboomzxc
    public ArrayList<String> getMedicalHistories() {
        return medicalHistories;
    }
    //@@author kaboomzxc
    public void setMedicalHistories(ArrayList<String> medicalHistories) {
        this.medicalHistories = medicalHistories;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateOfBirthString = "";
        if (getDateOfBirth() != null) {
            dateOfBirthString = getDateOfBirth().format(formatter);
        }
        return "Name: " + getName() + ", NRIC: " + getNric() +
                ", Phone: " + getPhoneNumber() +  ", Address: " + getHomeAddress() +
                ", DOB: " + dateOfBirthString + ", Allergy: " + getAllergies() +
                ", Sex: " + getSex() + ", Medical History: " + getMedicalHistories();
    }

    //@@author PrinceCatt
    @Override
    public String convertPatientToOutputText() {
        String output = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateOfBirthString = "";
        if (getDateOfBirth() != null) {
            dateOfBirthString = getDateOfBirth().format(formatter);
        }
        output += "Name: " + this.getName() + " | " + "NRIC: " + this.getNric() + " | "
                + "Phone Number: " + this.getPhoneNumber() + " | " + "Date_Of_Birth: " + dateOfBirthString
                + " | " + "Home Address: " + this.getHomeAddress() + " | " + "Allergy: " + this.getAllergies()
                + " | " + "Sex: " + this.getSex() + " | " + "Medical History: " + this.getMedicalHistories()
                + " | " + "Visit: " + this.getVisits() + ";";
        return output;
    }
}
