package bookbob.entity;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

//@@author G13nd0n
public class AppointmentRecord implements FileOperation {
    private List<Appointment> appointments;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    //@@author G13nd0n
    public AppointmentRecord() {
        this.appointments = new ArrayList<>();
    }

    //@@author G13nd0n
    public void addAppointment(Appointment appointment) {
        LocalDate availableDate = appointment.getDate();
        LocalTime availableTime = appointment.getTime();
        LocalTime nextAvailableTime= this.checkAvailability(availableDate, availableTime);
        if (nextAvailableTime == availableTime) {
            appointments.add(appointment);
        }
    }

    //@@author G13nd0n
    public void addAppointment(String name, String nric, String date, String time) {
        LocalDate availableDate = LocalDate.parse(date, formatter);
        LocalTime availableTime = LocalTime.parse(time);
        LocalTime nextAvailableTime= this.checkAvailability(availableDate, availableTime);
        if (!checkDateTime(availableDate, availableTime)) {
            return;
        }
        String searchInput = "ic/" + nric;
        if (!checkExistingAppintmentRecords(name, nric, searchInput)) {
            return;
        }
        if (nextAvailableTime == availableTime) {
            Appointment appointment = new Appointment(name, nric, date, time);
            appointments.add(appointment);

            System.out.println("Appointment on " + appointment.getDate().format(formatter) + " " +
                    appointment.getTime() + " with Patient " + appointment.getPatientName() + ", " +
                    appointment.getPatientNric() + " has been added.");
        } else {
            System.out.println("There is already an appointment at the given timeslot. " +
                    "The next available timeslot is: " + nextAvailableTime.toString());
        }
        this.sort();
    }

    //@@author G13nd0n
    private static boolean checkDateTime(LocalDate availableDate, LocalTime availableTime) {
        LocalDate todayDate = LocalDate.now();
        LocalTime timeNow = LocalTime.now();
        if (availableDate.isBefore(todayDate)) {
            System.out.println("Date given is before today, please select another date");
            return false;
        } else if (availableDate.isEqual(todayDate) && availableTime.isBefore(timeNow)) {
            System.out.println("Time given is before current time, please select another time");
            return false;
        }
        return true;
    }

    //@@author G13nd0n
    private boolean checkExistingAppintmentRecords(String name, String nric, String searchInput) {
        List<Appointment> existingAppointments = findAppointments(searchInput);
        if (existingAppointments.isEmpty()) {
            return true;
        }
        Appointment existingAppointment = existingAppointments.get(0);
        String patientName = existingAppointment.getPatientName();
        if (!patientName.equals(name)) {
            System.out.println("Please check if the name is correct");
            System.out.println("Appointment made previously with patient nric, " + nric + ", has the name " +
                    patientName);
            return false;
        }
        return true;
    }

    //@@author G13nd0n
    public List<Appointment> findAppointments(String input) {
        List<Appointment> results = new ArrayList<>();
        String[] inputs = input.split("/");
        String filters = inputs[0];
        String details = inputs[1];

        if (filters.equals("n")) {
            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                String patientName = appointment.getPatientName().toLowerCase();
                details = details.toLowerCase();
                if (patientName.contains(details)) {
                    results.add(appointment);
                }
            }
        } else if (filters.equals("ic")) {
            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                String nric = appointment.getPatientNric().toLowerCase();
                details = details.toLowerCase();
                if (nric.equals(details)) {
                    results.add(appointment);
                }
            }
        } else if (filters.equals("date")){
            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                String date = appointment.getDate().format(formatter);
                if (date.equals(details)) {
                    results.add(appointment);
                }
            }
        } else if (filters.equals("time")) {
            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                String time = appointment.getTime().toString();
                if (time.equals(details)) {
                    results.add(appointment);
                }
            }

        }
        return results;
    }

    //@@author G13nd0n
    public List<Appointment> getAppointments() {
        return appointments;
    }

    //@@author G13nd0n
    private void sort() {
        Collections.sort(this.appointments);
    }

    //@@author G13nd0n
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    //@@author G13nd0n
    public void appointmentNotice() {
        if (appointments.size() == 0) {
            noAppointmentMessage();
            return;
        }

        LocalDate firstAppointmentDate = appointments.get(0).getDate();
        LocalDate today = LocalDate.now();

        if (firstAppointmentDate.isAfter(today)) {
            noAppointmentMessage();
            return;
        }
        System.out.println("Appointment scheduled for today:");
        for (Appointment appointment : appointments) {
            String appointmentDate = appointment.getDate().format(formatter);
            String todayDate = LocalDate.now().format(formatter);
            if (appointmentDate.equals(todayDate)) {
                System.out.println(appointment);
            }
        }
    }

    //@@author G13nd0n
    private static void noAppointmentMessage() {
        System.out.println("No appointments scheduled for today");
    }

    //@@author G13nd0n
    public LocalTime checkAvailability(LocalDate date, LocalTime time) {
        LocalTime nextAvailableTime = time;
        long consultationDuration = 30;
        LocalTime endTime = time.plusMinutes(consultationDuration);
        for (Appointment appointment : appointments) {
            LocalDate appointmentDate = appointment.getDate();
            LocalTime appointmentTime = appointment.getTime();
            LocalTime appointmentEndTime = appointmentTime.plusMinutes(consultationDuration);
            if (appointmentDate.isAfter(date)) {
                break;
            } else if (appointmentDate.isBefore(date)) {
                continue;
            } else if (appointmentTime.equals(nextAvailableTime)) {
                nextAvailableTime = appointmentEndTime;
                break;
            } else if (appointmentTime.isBefore(nextAvailableTime)
                    && appointmentEndTime.isAfter(nextAvailableTime)) {
                nextAvailableTime = appointmentEndTime;
                endTime = appointmentEndTime.plusMinutes(consultationDuration);
            } else if (appointmentEndTime.isBefore(nextAvailableTime)) {
                continue;
            } else if (appointmentEndTime.equals(nextAvailableTime)) {
                nextAvailableTime = appointmentEndTime;
            } else if (appointmentTime.isBefore(endTime)) {
                nextAvailableTime = appointmentEndTime;
                endTime = appointmentEndTime.plusMinutes(consultationDuration);
            }
        }
        return nextAvailableTime;
    }

    //@@author G13nd0n
    @Override
    public void initFile(String appointmentFilePath) {
        try {
            String directoryName = "data";
            String currentDirectory = System.getProperty("user.dir");
            String directory = currentDirectory + File.separator + directoryName;
            File directoryFile = new File(directory);

            if(directoryFile.mkdirs()) {           //directory was not created
                File file = new File(appointmentFilePath);
                file.createNewFile();              //create new data file
            } else {                               //directory already created
                File file = new File(appointmentFilePath);
                if (file.createNewFile()) {         //file was not created
                } else {
                    this.retrieveData(appointmentFilePath);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //@@author G13nd0n
    @Override
    public void autosave(String appointmentFilePath) throws IOException, IOException {
        List<Appointment> appointments = this.getAppointments();
        FileWriter fw = new FileWriter(appointmentFilePath);
        for (Appointment appointment : appointments) {
            String toWrite = appointment.convertPatientToOutputText();
            fw.write(toWrite + "\n");
        }
        fw.close();
    }

    //@@author G13nd0n
    @Override
    public void retrieveData(String appointmentFilePath) {
        try {
            File file = new File(appointmentFilePath);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] data = line.split("\\|");
                String name = data[0].substring(6).trim();
                String nric = data[1].substring(6).trim();
                String date = data[2].substring(6).trim();
                String time = data[3].substring(6).trim();
                Appointment appointment = new Appointment(name, nric, date, time);
                this.addAppointment(appointment);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //@@author G13nd0n
    public void listAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    //@@author G13nd0n
    public void removePastAppointments() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        List<Appointment> updatedAppointments = new ArrayList<Appointment>();
        for (int i = 0; i < appointments.size(); i++) {
            Appointment currentAppointment = appointments.get(i);
            LocalDate appointmentDate = currentAppointment.getDate();
            LocalTime appointmentTime = currentAppointment.getTime();
            if (appointmentDate.isAfter(today)) {
                updatedAppointments.add(currentAppointment);
            } else if (appointmentDate.isEqual(today) && appointmentTime.isAfter(now)) {
                updatedAppointments.add(currentAppointment);
            }
        }
        this.appointments = updatedAppointments;
    }

    //@@author G13nd0n
    public void deleteAppointment(String nric, String date, String time) {
        String patientName = "";
        String originalNric = nric;
        int initialAppointmentSize = appointments.size();

        for (int i = 0; i < initialAppointmentSize; i++) {
            Appointment appointment = appointments.get(i);
            patientName = appointment.getPatientName();
            String patientNric = appointment.getPatientNric().toLowerCase();
            String patientDate = appointment.getDate().format(formatter);
            String patientTime = appointment.getTime().toString();
            nric = nric.toLowerCase();
            if (!patientNric.equals(nric)) {
                continue;
            }
            if (!patientDate.equals(date)) {
                continue;
            }
            if (!patientTime.equals(time)) {
                continue;
            }
            appointments.remove(i);
            break;
        }

        if (appointments.size() == initialAppointmentSize) {
            System.out.println("Patient with " + originalNric + " do not have appointment on the given date and time.");
            return;
        }
        System.out.println("Appointment on " + date + " " + time + " with Patient " + patientName + ", " +
                originalNric + " has been deleted.");

    }
}

