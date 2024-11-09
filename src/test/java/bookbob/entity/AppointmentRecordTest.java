package bookbob.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentRecordTest {
    private final Appointment firstAppointment = new Appointment("John Doe", "S1234567A",
            "18-11-2024", "18:00");
    private final Appointment secondAppointment = new Appointment("Helen Smith", "S7654321A",
            "18-11-2024", "19:00");
    private final AppointmentRecord appointmentRecord = new AppointmentRecord();
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown_for_helpCommand() {
        System.setOut(standardOut);
    }

    //@@author G13nd0n
    @Test
    void addAppointment_oneAppointment_oneAppointments() {
        appointmentRecord.addAppointment(firstAppointment);
        assertEquals(1, appointmentRecord.getAppointments().size());
    }

    //@@author G13nd0n
    @Test
    void checkAvailability_unavailableTime_nextAvailableTime() {
        appointmentRecord.addAppointment(firstAppointment);
        LocalTime time = appointmentRecord.checkAvailability(LocalDate.parse("2024-11-18"),
                LocalTime.parse("18:00"));
        assertEquals(LocalTime.parse("18:30"), time);
    }

    //@@author G13nd0n
    @Test
    void checkAvailability_availableTime_nextAvailableTime() {
        appointmentRecord.addAppointment(firstAppointment);
        LocalTime time = appointmentRecord.checkAvailability(LocalDate.parse("2024-11-18"),
                LocalTime.parse("18:30"));
        assertEquals(LocalTime.parse("18:30"), time);
    }

    //@@author G13nd0n
    @Test
    void checkAvailability_selectedTimeBetween2Appointment_nextAvailableTime() {
        appointmentRecord.addAppointment(firstAppointment);
        appointmentRecord.addAppointment(secondAppointment);
        LocalTime time = appointmentRecord.checkAvailability(LocalDate.parse("2024-11-18"),
                LocalTime.parse("18:40"));
        assertEquals(LocalTime.parse("19:30"), time);
    }

    //@@author G13nd0n
    @Test
    void checkAvailability_selectedTimeDuringAppointment_nextAvailableTime() {
        appointmentRecord.addAppointment(firstAppointment);
        LocalTime time = appointmentRecord.checkAvailability(LocalDate.parse("2024-11-18"),
                LocalTime.parse("18:20"));
        assertEquals(LocalTime.parse("18:30"), time);
    }

    //@@author G13nd0n
    @Test
    void checkAvailability_sameTimeDifferentDate_nextAvailableTime() {
        appointmentRecord.addAppointment(firstAppointment);
        LocalTime time = appointmentRecord.checkAvailability(LocalDate.parse("2024-11-19"),
                LocalTime.parse("18:00"));
        assertEquals(LocalTime.parse("18:00"), time);
    }

    @Test
    void findAppointments_name_noAppointments() {
        List<Appointment> appointmentList = appointmentRecord.findAppointments("n/Smith");
        assertEquals(0, appointmentList.size());
    }

    @Test
    void findAppointments_name_oneAppointments() {
        Appointment third = new Appointment ("Will Smith", "S9876543A", "11-04-2025",
                "00:00");
        appointmentRecord.addAppointment(firstAppointment);
        appointmentRecord.addAppointment(secondAppointment);
        appointmentRecord.addAppointment(third);
        List<Appointment> appointmentList = appointmentRecord.findAppointments("n/Will Smith");
        assertEquals(1, appointmentList.size());
    }

    @Test
    void findAppointments_nric_oneAppointment() {
        appointmentRecord.addAppointment(firstAppointment);
        appointmentRecord.addAppointment(secondAppointment);
        List<Appointment> appointmentList = appointmentRecord.findAppointments("ic/S1234567A");
        assertEquals(1, appointmentList.size());
    }

    //@@author G13nd0n
    @Test
    void findAppointments_date_twoAppointments() {
        Appointment third = new Appointment ("Will Smith", "S9876543A", "11-04-2025",
                "00:00");
        appointmentRecord.addAppointment(firstAppointment);
        appointmentRecord.addAppointment(secondAppointment);
        appointmentRecord.addAppointment(third);
        List<Appointment> appointmentList = appointmentRecord.findAppointments("date/18-11-2024");
        assertEquals(2, appointmentList.size());
    }

    //@@author G13nd0n
    @Test
    void findAppointments_time_twoAppointments() {
        Appointment second = new Appointment("Helen Smith", "S7654321A", "19-11-2024",
                "18:00");
        Appointment third = new Appointment ("Will Smith", "S9876543A", "11-04-2025",
                "00:00");
        appointmentRecord.addAppointment(firstAppointment);
        appointmentRecord.addAppointment(second);
        appointmentRecord.addAppointment(third);
        List<Appointment> appointmentList = appointmentRecord.findAppointments("time/18:00");
        assertEquals(2, appointmentList.size());
    }

    //@@author G13nd0n
    @Test
    void setAppointment_twoAppointments_twoAppointments() {
        AppointmentRecord updatedRecord = new AppointmentRecord();
        Appointment second = new Appointment("Helen Smith", "S7654321A", "19-11-2024",
                "18:00");
        Appointment third = new Appointment ("Will Smith", "S9876543A", "11-04-2025",
                "00:00");
        appointmentRecord.addAppointment(firstAppointment);
        updatedRecord.addAppointment(second);
        updatedRecord.addAppointment(third);
        appointmentRecord.setAppointments(updatedRecord.getAppointments());
        assertEquals(2, appointmentRecord.getAppointments().size());
    }

    //author G13nd0n
    @Test
    void testAppointmentNotice_noInput_noAppointment() {
        appointmentRecord.appointmentNotice();
        String expected = "No appointments scheduled for today";
        assertEquals(expected, outputStreamCaptor.toString().trim().replace(System.lineSeparator(),
                "\n"));
    }

    //author G13nd0n
    @Test
    void testAppointmentNotice_appointmentAfterToday_noAppointment() {
        String date = LocalDate.now().plusDays(1).format(formatter);
        Appointment tomorrow = new Appointment("Mary Ong", "S3456789A", date, "18:00");
        appointmentRecord.addAppointment(tomorrow);
        appointmentRecord.appointmentNotice();
        String expected = "No appointments scheduled for today";
        assertEquals(expected, outputStreamCaptor.toString().trim().replace(System.lineSeparator(),
                "\n"));
    }

    //author G13nd0n
    @Test
    void testAppointmentNotice_appointmentToday_oneAppointment() {
        String date = LocalDate.now().format(formatter);
        Appointment todayAppointment = new Appointment("Steven Lim", "S9876543A",
                date, "18:00");
        appointmentRecord.addAppointment(todayAppointment);
        appointmentRecord.appointmentNotice();
        String expected = "Appointment scheduled for today:\n" + "Appointment on " + date +" 18:00 with " +
                "Patient Steven Lim, S9876543A.";
        assertEquals(expected, outputStreamCaptor.toString().trim().replace(System.lineSeparator(),
                "\n"));
    }

}
