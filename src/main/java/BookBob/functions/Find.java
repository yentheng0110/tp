package BookBob.functions;

import BookBob.entity.Patient;
import BookBob.entity.Records;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Find {
    public static List<Patient> findPatients(CommandAttributeType commandAttributeType,
                                             Records records, String[] keywords) {
        return switch (commandAttributeType) {
        case NAME -> findPatientsByName(records, keywords);
        case NRIC -> findPatientsByNric(records, keywords);
        case DATE_OF_BIRTH -> findPatientsByDateOfBirth(records, keywords);
        case PHONE_NUMBER -> findPatientsByPhoneNumber(records, keywords);
        case HOME_ADDRESS -> findPatientsByHomeAddress(records, keywords);
        case DIAGNOSIS -> findPatientsByDiagnosis(records, keywords);
        case MEDICATION -> findPatientsByMedication(records, keywords);
        };
    }

    public static List<Patient> findPatientsByName(Records records, String[] keywords) {
        return findPatientsByAttribute(records, keywords, (p, keyword) ->
                p.getName().toLowerCase().contains(keyword.toLowerCase()));
    }

    public static List<Patient> findPatientsByNric(Records records, String[] keywords) {
        return findPatientsByAttribute(records, keywords, (p, keyword) ->
                p.getNric().toLowerCase().contains(keyword.toLowerCase()));
    }

    public static List<Patient> findPatientsByDateOfBirth(Records records, String[] keywords) {
        return findPatientsByAttribute(records, keywords, (p, keyword) -> {
            DateTimeFormatter dateInputFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            LocalDate searchDate = LocalDate.parse(keyword, dateInputFormatter);
            LocalDate patientDateOfBirth = LocalDate.parse(p.getDateOfBirth(), dateInputFormatter);
            return patientDateOfBirth.equals(searchDate);
        });
    }

    public static List<Patient> findPatientsByPhoneNumber(Records records, String[] keywords) {
        return findPatientsByAttribute(records, keywords, (p, keyword) ->
                p.getPhoneNumber().toLowerCase().contains(keyword.toLowerCase()));
    }

    public static List<Patient> findPatientsByHomeAddress(Records records, String[] keywords) {
        return findPatientsByAttribute(records, keywords, (p, keyword) ->
                p.getHomeAddress().toLowerCase().contains(keyword.toLowerCase()));
    }

    public static List<Patient> findPatientsByDiagnosis(Records records, String[] keywords) {
        return findPatientsByAttribute(records, keywords, (p, keyword) ->
                p.getDiagnosis().toLowerCase().contains(keyword.toLowerCase()));
    }

    public static List<Patient> findPatientsByMedication(Records records, String[] keywords) {
        return findPatientsByAttribute(records, keywords, (p, keyword) -> p.getMedication().contains(keyword));
    }

    public static List<Patient> findPatientsByAttribute(Records records, String[] keywords,
                                                        FindAttributeCheck attributeCheck) {
        List<Patient> findList = new ArrayList<>();

        for (Patient p : records.getPatients()) {
            for (String keyword : keywords) {
                if (attributeCheck.test(p, keyword)) {
                    findList.add(p);
                    break; // To avoid adding the same patient multiple times
                }
            }
        }

        return findList;
    }
}
