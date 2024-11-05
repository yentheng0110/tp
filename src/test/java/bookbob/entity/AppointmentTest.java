package bookbob.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentTest {
    //@@author G13nd0n
    @Test
    void testNameGetter_noInput_name() {
        Appointment first = new Appointment("John Doe", "S1234567A", "18-11-2024",
                "18:00");
        String name = first.getPatientName();
        assertEquals("John Doe", name);
    }

    //@@author G13nd0n
    @Test
    void testNricGetter_noInput_nric() {
        Appointment first = new Appointment("John Doe", "S1234567A", "18-11-2024",
                "18:00");
        String nric = first.getPatientNric();

        assertEquals("S1234567A", nric);
    }

    //@@author G13nd0n
    @Test
    void testDateGetter_noInput_date() {
        Appointment first = new Appointment("John Doe", "S1234567A", "18-11-2024",
                "18:00");
        LocalDate date = first.getDate();

        assertEquals(LocalDate.parse("2024-11-18"), date);
    }

    //@@author G13nd0n
    @Test
    void testTimeGetter_noInput_time() {
        Appointment first = new Appointment("John Doe", "S1234567A", "18-11-2024",
                "18:00");
        LocalTime time = first.getTime();

        assertEquals(LocalTime.parse("18:00"), time);
    }

    //@@author G13nd0n
    @Test
    void testConsultationDurationGetter_noInput_duration() {
        Appointment first = new Appointment("John Doe", "S1234567A", "18-11-2024",
                "18:00");
        long duration = first.getConsultationDuration();
        assertEquals(30, duration);
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
    void compareTo_twoInputs_one() {
        Appointment first = new Appointment("John Doe", "S1234567A", "19-11-2024",
                "18:00");
        Appointment second = new Appointment("Helen Smith", "S7654321A", "18-11-2024",
                "18:30");
        int result = first.compareTo(second);

        assertEquals(1, result);
    }
}
