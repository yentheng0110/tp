package bookbob.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentTest {
    private final Appointment firstAppointment = new Appointment("John Doe", "S1234567A",
            "18-11-2024", "18:00");
    private final Appointment secondAppointment = new Appointment("Helen Smith", "S7654321A",
            "18-11-2024", "18:30");
    private final Appointment thirdAppointment = new Appointment("Will Ferrel", "S9876543A",
            "18-11-2024", "19:00");
    private final Appointment fourthAppointment = new Appointment("Tom Hanks", "S3456789A",
            "19-11-2024", "18:00");
    //@@author G13nd0n
    @Test
    void testNameGetter_noInput_name() {
        String name = firstAppointment.getPatientName();
        assertEquals("John Doe", name);
    }

    //@@author G13nd0n
    @Test
    void testNricGetter_noInput_nric() {
        String nric = firstAppointment.getPatientNric();
        assertEquals("S1234567A", nric);
    }

    //@@author G13nd0n
    @Test
    void testDateGetter_noInput_date() {
        LocalDate date = firstAppointment.getDate();
        assertEquals(LocalDate.parse("2024-11-18"), date);
    }

    //@@author G13nd0n
    @Test
    void testTimeGetter_noInput_time() {
        LocalTime time = firstAppointment.getTime();
        assertEquals(LocalTime.parse("18:00"), time);
    }

    //@@author G13nd0n
    @Test
    void testConsultationDurationGetter_noInput_duration() {
        long duration = firstAppointment.getConsultationDuration();
        assertEquals(30, duration);
    }

    //@@author G13nd0n
    @Test
    void compareTo_firstAppointmentBeforeSecondAppointmentInTime_negativeOne() {
        int result = firstAppointment.compareTo(secondAppointment);
        assertEquals(-1, result);
    }

    //@@author G13nd0n
    @Test
    void compareTo_twoInputs_one() {
        int result = thirdAppointment.compareTo(secondAppointment);
        assertEquals(1, result);
    }

    //@@author G13nd0n
    @Test
    void compareTo_firstAppointmentBeforeFourthAppointmentInDay_negativeOne() {
        int result = firstAppointment.compareTo(fourthAppointment);
        assertEquals(-1, result);
    }

    //@@author G13nd0n
    @Test
    void compareTo_fourthAppointmentAfterFirstAppointmentInDay_negativeOne() {
        int result = fourthAppointment.compareTo(firstAppointment);
        assertEquals(1, result);
    }

    //@@author G13nd0n
    @Test
    void toString_noInput_string() {
        String result ="Appointment on 18-11-2024 18:00 with Patient John Doe, S1234567A.";
        assertEquals(result, firstAppointment.toString());
    }

}
