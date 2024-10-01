package seedu.duke;

import java.util.ArrayList;

public class Patient {
    public String name;
    public String nric;
    public String address;
    public ArrayList<String> medication;
    public String diagnosis;
    public String dateOfBirth;

    Patient(String name, String nric) {
        this.name = name;
        this.nric = nric;
        this.address = "";
        this.medication = new ArrayList<>();
        this.diagnosis = "";
        this.dateOfBirth = "";
    }
}
