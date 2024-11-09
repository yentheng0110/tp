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
        appointments.add(appointment);
        this.sort();
    }

    //@@author G13nd0n
    public List<Appointment> findAppointments(String input) {
        List<Appointment> results = new ArrayList<>();
        String[] inputs = input.split("/");
        String details = inputs[1];
        if (inputs[0].equals("n")) {
            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                String patientName = appointment.getPatientName();
                if (patientName.equals(details)) {
                    results.add(appointment);
                }
            }
        } else if (inputs[0].equals("ic")) {
            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                String nric = appointment.getPatientNric();
                if (nric.equals(details)) {
                    results.add(appointment);
                }
            }
        } else if (inputs[0].equals("date")){
            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                String date = appointment.getDate().format(formatter);
                if (date.equals(details)) {
                    results.add(appointment);
                }
            }
        } else if (inputs[0].equals("time")) {
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
            System.out.println("No appointments scheduled for today");
            return;
        }

        LocalDate firstAppointmentDate = appointments.get(0).getDate();
        LocalDate today = LocalDate.now();

        if (firstAppointmentDate.isAfter(today)) {
            System.out.println("No appointments scheduled for today");
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
            } else if (appointmentEndTime.isBefore(nextAvailableTime) || appointmentEndTime.equals(nextAvailableTime)) {
                continue;
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
}

