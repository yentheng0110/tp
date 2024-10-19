package bookbob.entity;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@@author G13nd0n
public class AppointmentRecord {
    private List<Appointment> appointments;

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
    public void deletesAppointment(String input) {
        String[] inputs = input.split("\\|");
        String nric = inputs[0].substring(2);
        String date = inputs[1].substring(5);
        String time = inputs[2].substring(5);
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            String patientNric = appointment.getPatientName();
            String patientDate = appointment.getDate().toString();
            String patientTime = appointment.getTime().toString();
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
    }

    //@@author G13nd0n
    public List<Appointment> findAppointments(String input) {
        List<Appointment> appointments = new ArrayList<>();
        String[] inputs = input.split("/");
        String details = inputs[1];
        if (inputs[0].equals("n")) {
            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                String patientName = appointment.getPatientName();
                if (patientName.equals(details)) {
                    appointments.add(appointment);
                }
            }
        } else if (inputs[0].equals("ic")) {
            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                String nric = appointment.getPatientNric();
                if (nric.equals(details)) {
                    appointments.add(appointment);
                }
            }
        } else if (inputs[0].equals("date")){
            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                String date = appointment.getDate().toString();
                if (date.equals(details)) {
                    appointments.add(appointment);
                }
            }
        } else {
            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);
                String time = appointment.getTime().toString();
                if (time.equals(details)) {
                    appointments.add(appointment);
                }
            }

        }
        return appointments;
    }

    //@@author G13nd0n
    public List<Appointment> getAppointments() {
        return appointments;
    }

    //@@author G13nd0n
    private void sort() {
        Collections.sort(appointments);
    }

    //@@author G13nd0n
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}

