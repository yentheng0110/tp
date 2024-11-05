package bookbob.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentRecordTest {

    //@@author G13nd0n
    @Test
    void addAppointment_oneAppointment_oneAppointments() {
        AppointmentRecord appointmentRecord = new AppointmentRecord();
        Appointment first = new Appointment("John Doe", "S1234567A", "18-11-2024",
                "18:00");
        appointmentRecord.addAppointment(first);
        assertEquals(1, appointmentRecord.getAppointments().size());
    }

    //@@author G13nd0n
    @Test
    void checkAvailability_oneAppointment_nextAvailableTime() {
        AppointmentRecord appointmentRecord = new AppointmentRecord();
        Appointment first = new Appointment("John Doe", "S1234567A", "18-11-2024",
                "18:00");
        appointmentRecord.addAppointment(first);
        LocalTime time = appointmentRecord.checkAvailability(LocalDate.parse("2024-11-18"),
                LocalTime.parse("18:00"));
        assertEquals(LocalTime.parse("18:30"), time);
    }

    //@@author G13nd0n
    @Test
    void findAppointments_time_twoAppointments() {
        AppointmentRecord appointmentRecord = new AppointmentRecord();
        Appointment first = new Appointment("John Doe", "S1234567A", "18-11-2024",
                "18:00");
        Appointment second = new Appointment("Helen Smith", "S7654321A", "19-11-2024",
                "18:00");
        Appointment third = new Appointment ("Will Smith", "S9876543A", "11-04-2025",
                "00:00");
        appointmentRecord.addAppointment(first);
        appointmentRecord.addAppointment(second);
        appointmentRecord.addAppointment(third);
        List<Appointment> appointmentList = appointmentRecord.findAppointments("time/18:00");
        assertEquals(2, appointmentList.size());
    }

    //@@author G13nd0n
    @Test
    void setAppointment_twoAppointments_twoAppointments() {
        AppointmentRecord appointmentRecord = new AppointmentRecord();
        AppointmentRecord updatedRecord = new AppointmentRecord();
        Appointment first = new Appointment("John Doe", "S1234567A", "18-11-2024",
                "18:00");
        Appointment second = new Appointment("Helen Smith", "S7654321A", "19-11-2024",
                "18:00");
        Appointment third = new Appointment ("Will Smith", "S9876543A", "11-04-2025",
                "00:00");
        appointmentRecord.addAppointment(first);
        updatedRecord.addAppointment(second);
        updatedRecord.addAppointment(third);
        appointmentRecord.setAppointments(updatedRecord.getAppointments());
        assertEquals(2, appointmentRecord.getAppointments().size());
    }
}
