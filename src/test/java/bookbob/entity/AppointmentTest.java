package bookbob.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {
    //@@author G13nd0n
    @Test
    void test_nameGetter() {
        Appointment first = new Appointment("John Doe", "S1234567A", "18-11-2024",
                "18:00");
        String name = first.getPatientName();
        assertEquals("John Doe", name);
    }

    //@@author G13nd0n
    @Test
    void test_nricGetter() {
        Appointment first = new Appointment("John Doe", "S1234567A", "18-11-2024",
                "18:00");
        String nric = first.getPatientNric();

        assertEquals("S1234567A", nric);
    }

    //@@author G13nd0n
    @Test
    void test_dateGetter() {
        Appointment first = new Appointment("John Doe", "S1234567A", "18-11-2024",
                "18:00");
        LocalDate date = first.getDate();

        assertEquals(LocalDate.parse("2024-11-18"), date);
    }

    //@@author G13nd0n
    @Test
    void test_timeGetter() {
        Appointment first = new Appointment("John Doe", "S1234567A", "18-11-2024",
                "18:00");
        LocalTime time = first.getTime();

        assertEquals(LocalTime.parse("18:00"), time);
    }

    //@@author G13nd0n
    @Test
    void test_consultationTimeGetter() {
        Appointment first = new Appointment("John Doe", "S1234567A", "18-11-2024",
                "18:00");
        long duration = first.getConsultationDuration();
        assertEquals(LocalTime.parse("18:00"), duration);
    }

    //@@author G13nd0n
    @Test
    void compareTo_firstBeforeSecond_negativeOne() {
        Appointment first = new Appointment("John Doe", "S1234567A", "18-11-2024",
                "18:00");
        Appointment second = new Appointment("Helen Smith", "S7654321A", "18-11-2024",
                "18:30");
        int result = first.compareTo(second);

        assertEquals(-1, result);
    }

    //@@author G13nd0n
    @Test
    void compareTo_firstAfterSecond_one() {
        Appointment first = new Appointment("John Doe", "S1234567A", "19-11-2024",
                "18:00");
        Appointment second = new Appointment("Helen Smith", "S7654321A", "18-11-2024",
                "18:30");
        int result = first.compareTo(second);

        assertEquals(1, result);
    }
}