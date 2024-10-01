package BookBob.functions;

import BookBob.entity.Patient;
import BookBob.entity.Records;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Find {
    public List<Patient> findPatientsByAttribute(Records records, String[] keywords,
                                                 FindPatientAttributeCheck attributeCheck) {
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

    public List<Patient> findPatientsByName(Records records, String[] keywords) {
        return findPatientsByAttribute(records, keywords, (p, keyword) -> p.getName().contains(keyword));
    }

    public List<Patient> findPatientsByNric(Records records, String[] keywords) {
        return findPatientsByAttribute(records, keywords, (p, keyword) -> p.getNric().contains(keyword));
    }

    public List<Patient> findPatientsByDateOfBirth(Records records, String[] keywords) {
        return findPatientsByAttribute(records, keywords, (p, keyword) -> {
            DateTimeFormatter dateInputFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            LocalDate searchDate = LocalDate.parse(keyword, dateInputFormatter);
            LocalDate patientDateOfBirth = LocalDate.parse(p.getDateOfBirth(), dateInputFormatter);
            return patientDateOfBirth.equals(searchDate);

        });
    }

    public List<Patient> findPatientsByPhoneNumber(Records records, String[] keywords) {
        return findPatientsByAttribute(records, keywords, (p, keyword) -> p.getPhoneNumber().contains(keyword));
    }

    public List<Patient> findPatientsByHomeAddress(Records records, String[] keywords) {
        return findPatientsByAttribute(records, keywords, (p, keyword) -> p.getHomeAddress().contains(keyword));
    }

    public List<Patient> findPatientsByDiagnosis(Records records, String[] keywords) {
        return findPatientsByAttribute(records, keywords, (p, keyword) -> p.getDiagnosis().contains(keyword));
    }

    public List<Patient> findPatientsByMedication(Records records, String[] keywords) {
        return findPatientsByAttribute(records,keywords, (p, keyword) -> p.getMedication().contains(keyword));
    }
}
